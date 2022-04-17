package eventsms.events.Controllers;


import eventsms.events.Dto.*;
import eventsms.events.Models.EventModel;
import eventsms.events.Models.LocationModel;
import eventsms.events.Repositories.EventRepository;
import eventsms.events.Repositories.LocationRepository;
import eventsms.events.Services.Interfaces.EventService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {
    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private EventService eventService;

    @Autowired
    private LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;

    @PostMapping("/create")
    public ResponseEntity createEvent (@Valid @RequestBody EventDto eventDto, BindingResult result){
        if (!result.hasErrors()){
            try {
                EventModel event = new EventModel();
                event = modelMapper.map(eventDto, EventModel.class);
                if (locationRepository.existsByLocationNameAndLocationCity(eventDto.getEventLocation(),
                        eventDto.getEventCity())){
                    LocationModel locationDB = locationRepository.findByLocationNameAndLocationCity(eventDto.getEventLocation(),
                            eventDto.getEventCity()).get();
                    event.setEventLocation(locationDB);
                    if (!eventService.verifyDisponibility(event)){
                        eventRepository.save(event);
                        return new ResponseEntity(new Response("OK", "The event was successfully created!",
                                null), HttpStatus.OK);
                    } else {
                        return new ResponseEntity(new Response("BAD", "This location is already reserved in this date ",
                                null), HttpStatus.BAD_REQUEST);
                    }
                 } else {
                    return new ResponseEntity(new Response("BAD", "Sorry, the location isn't available",
                            null), HttpStatus.BAD_REQUEST);
                }
            } catch (Exception e){
                return new ResponseEntity(new Response("BAD","We have found a problem: " +
                        e.getMessage(),null), HttpStatus.BAD_REQUEST);
            }
        }
        return new ResponseEntity(new Response("BAD","One or more fields are blank",null),
                HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/cancel")
    public ResponseEntity cancelEvent(@RequestParam("eventId") long eventId){
        EventModel event = new EventModel();
        try{
            event = eventService.cancelEvent(eventId);
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD", e.getMessage(), event.getEventName()),
                    HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Response("OK","The event was cancelled :(",event.getEventName()),
                HttpStatus.OK);
    }

    @PutMapping("/edit")
    public ResponseEntity editEvent(@RequestBody EditEventDto eventDto, @RequestParam("eventId") long eventId,
                                    BindingResult result){
        if (!result.hasErrors()){
            EventModel editedEvent = new EventModel();
            try{
                editedEvent = eventService.editEvent(eventDto, eventId);
            } catch (Exception e){
                return new ResponseEntity(new Response("BAD", e.getMessage(), eventDto.getEventName()),
                        HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity(new Response("OK","The event was edited successfully",
                    editedEvent.getEventId()), HttpStatus.OK);
        }
        return new ResponseEntity(new Response("BAD","One or more fields are blank",null),
                HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/view-all-events")
    public ResponseEntity viewAllEvents(@RequestParam("filter") String filter){
        try {
            if (filter.equals("all")){
                List<AllEventsDto> allEvents = eventService.listAllEvents();
                if (allEvents != null){
                    return new ResponseEntity(new Response("OK","The events availables are:",allEvents),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity(new Response("OK","There's no events :(",null),
                            HttpStatus.OK);
                }
            } else if (filter.equals("cancelled")){
                List<AllEventsDto> allEvents = eventService.listCancelledEvents();
                if (allEvents != null){
                    return new ResponseEntity(new Response("OK","The events that were sadly cancelated are:",
                            allEvents), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new Response("OK","There's no events cancelated like the Jamming," +
                            " YUPI!",null), HttpStatus.OK);
                }
            } else if (filter.equals("active")){
                List<AllEventsDto> allEvents = eventService.listAllEventsAvailables();
                if (allEvents != null){
                    return new ResponseEntity(new Response("OK","The events availables are:",allEvents),
                            HttpStatus.OK);
                } else {
                    return new ResponseEntity(new Response("OK","There's no events :(",null),
                            HttpStatus.OK);
                }
            } else if (filter.equals("done")){
                List<AllEventsDto> allEvents = eventService.listDoneEvents();
                if (allEvents != null){
                    return new ResponseEntity(new Response("OK","The events that were already done are:",
                            allEvents), HttpStatus.OK);
                } else {
                    return new ResponseEntity(new Response("OK","You haven't lose a concert yet",null),
                            HttpStatus.OK);
                }
            } else {
                return new ResponseEntity(new Response("BAD","No no! We don't have that filter",null),
                        HttpStatus.BAD_REQUEST);
            }

        } catch(Exception e){
            return new ResponseEntity(new Response("BAD","Oh! We have found a problem: " + e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/view-event")
    public ResponseEntity viewAnEvent(@RequestParam("eventId") long eventId){
        try {
            AllEventsDto event = eventService.eventInformation(eventId);
            if (event != null){
                String status = event.getEventState();
                return new ResponseEntity(new Response("OK","You are looking for this event: " +
                        "it's " + status, event),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new Response("OK","There's no events :(",null),
                        HttpStatus.OK);
            }
        } catch(Exception e){
            return new ResponseEntity(new Response("BAD","Oh! We have found a problem: " + e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/view-my-events")
    public ResponseEntity viewMyEvents(@RequestParam("artistName") String artistName){
        try {
            List<AllEventsDto> allEvents = eventService.listAllMyEvents(artistName);
            if (allEvents != null){
                return new ResponseEntity(new Response("OK","Your events are:",allEvents),
                        HttpStatus.OK);
            } else {
                return new ResponseEntity(new Response("OK","You don't have events yet",null),
                        HttpStatus.OK);
            }
        } catch(Exception e){
            return new ResponseEntity(new Response("BAD","Oh! We have found a problem: " + e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/flush")
    public ResponseEntity flushEvents(){
        try {
            Date date = new Date();
            eventRepository.flush(date);
        } catch (Exception e){
            return new ResponseEntity(new Response("BAD","Oh! We have found a problem: " + e.getMessage(),
                    null), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity(new Response("OK","We flushed the database",null), HttpStatus.OK);
    }

}
