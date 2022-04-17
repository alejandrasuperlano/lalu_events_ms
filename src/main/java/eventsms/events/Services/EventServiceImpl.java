package eventsms.events.Services;

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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EventServiceImpl implements EventService {

    @Autowired
    EventRepository eventRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    private ModelMapper modelMapper;


    public void saveEvent(EventModel event){
        eventRepository.save(event);
    }

    @Override
    public EventModel cancelEvent(long id) throws Exception{
        EventModel event = eventRepository.findById(id).get();
        String status = event.getEventState();
        if (status.equals("Cancelled")){
            throw new Exception("You cannot cancel an event that is cancelled");
        } else if (event != null) {
            event.setEventState("Cancelled");
            eventRepository.save(event);
        } else {
            throw new Exception("Sorry, we couldn't find that event in the DB");
        }
        return event;
    }

    @Override
    public EventModel editEvent(EditEventDto eventDto, long eventId) throws Exception {
        EventModel eventDB = eventRepository.findById(eventId).get();
        if (eventDB == null){
            throw new Exception("Sorry, we couldn't find that event in the DB");
        }

        String status = eventDB.getEventState();
        if (status.equals("Cancelled")) {
            throw new Exception("Sorry, you can´t edit an event that is cancelled");
        } else {
            if (eventDB != null) {
                LocationModel locationDB = new LocationModel();
                if (locationRepository.existsByLocationNameAndLocationCity(eventDto.getEventLocation(),
                        eventDto.getEventCity())) {
                    locationDB = locationRepository.findByLocationNameAndLocationCity(eventDto.getEventLocation(),
                            eventDto.getEventCity()).get();
                    eventDB.setEventLocation(locationDB);
                    EventModel e = eventRepository.verifyLocationAndCity(eventDB.getEventDate()).get(0);
                    if (verifyDisponibility(eventDB) && e.getEventDate() == eventDB.getEventDate()) {
                        eventDB.setEventDate(eventDto.getEventDate());
                    } else {
                        throw new Exception("Sorry, this location is already reserved in this date");
                    }
                } else {
                    throw new Exception("Sorry, the location isn't available");
                }
                eventDB.setEventCity(eventDto.getEventCity());
                eventDB.setEventName(eventDto.getEventName());
                eventDB.setEventOrganizer(eventDto.getEventOrganizer());
                eventDB.setEventDescription(eventDto.getEventDescription());
                eventDB.setEventCategory(eventDto.getEventCategory());

                eventRepository.save(eventDB);
            } else {
                throw new Exception("Sorry, we couldn't find that event in the DB");
            }
        }
        return eventDB;
    }

    @Override
    public boolean verifyDisponibility(EventModel event) {
        return eventRepository.verifyLocationAndCity(event.getEventDate()).size() > 0;
    }

    @Override
    public List<AllEventsDto> listAllEvents() {
        List<EventModel> allEventsDB = eventRepository.allEvents();
        List<AllEventsDto> allEvents = new ArrayList<>();
        if (allEventsDB.size() == 0){
            return null;
        } else {
            for (EventModel eventDB : allEventsDB){
                AllEventsDto event = new AllEventsDto();
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                allEvents.add(event);
            }
        }
        return allEvents;
    }

    @Override
    public List<AllEventsDto> listAllEventsAvailables() {
        List<EventModel> allEventsDB = eventRepository.allEventsAvailables();
        List<AllEventsDto> allEvents = new ArrayList<>();
        if (allEventsDB.size() == 0){
            return null;
        } else {
            for (EventModel eventDB : allEventsDB){
                AllEventsDto event = new AllEventsDto();
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                allEvents.add(event);
            }
        }
        return allEvents;
    }

    @Override
    public List<AllEventsDto> listCancelledEvents() {
        List<EventModel> allEventsDB = eventRepository.allEventsCancelled();
        List<AllEventsDto> allEvents = new ArrayList<>();
        if (allEventsDB.size() == 0){
            return null;
        } else {
            for (EventModel eventDB : allEventsDB){
                AllEventsDto event = new AllEventsDto();
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                allEvents.add(event);
            }
        }
        return allEvents;
    }

    @Override
    public List<AllEventsDto> listDoneEvents() {
        List<EventModel> allEventsDB = eventRepository.allEventsDone();
        List<AllEventsDto> allEvents = new ArrayList<>();
        if (allEventsDB.size() == 0){
            return null;
        } else {
            for (EventModel eventDB : allEventsDB){
                AllEventsDto event = new AllEventsDto();
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                allEvents.add(event);
            }
        }
        return allEvents;
    }

    @Override
    public AllEventsDto eventInformation(long id) throws Exception {
        try {
            AllEventsDto event = new AllEventsDto();
            EventModel eventDB = eventRepository.findById(id).get();
            if (eventDB != null){
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                return event;
            }
        } catch (Exception e){
            throw new Exception("Sorry, we couldn't find that event in the DB");
        }
        return null;
    }
    @Override
    public List<AllEventsDto> listAllMyEvents(String artistName) {
        List<EventModel> allMyEventsDB = eventRepository.findAllByArtist(artistName);
        List<AllEventsDto> allMyEvents = new ArrayList<>();
        if (allMyEventsDB.size() == 0){
            return null;
        } else {
            for (EventModel eventDB : allMyEventsDB){
                AllEventsDto event = new AllEventsDto();
                event.setId(eventDB.getEventId());
                event.setArtist(eventDB.getArtist());
                event.setEventName(eventDB.getEventName());
                event.setEventDate(eventDB.getEventDate());
                event.setEventCity(eventDB.getEventCity());
                event.setEventLocation(eventDB.getEventLocation().getLocationName());
                event.setEventOrganizer(eventDB.getEventOrganizer());
                event.setEventCategory(eventDB.getEventCategory());
                event.setEventState(eventDB.getEventState());
                event.setEventDescription(eventDB.getEventDescription());
                event.setEventFlyer(eventDB.getEventFlyer());
                event.setEventTicketsURL(eventDB.getEventTicketsURL());
                allMyEvents.add(event);
            }
        }
        return allMyEvents;
    }
}
