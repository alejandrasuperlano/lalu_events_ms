package eventsms.events.Services.Interfaces;

import eventsms.events.Dto.AllEventsDto;
import eventsms.events.Dto.CancelEventDto;
import eventsms.events.Dto.EditEventDto;
import eventsms.events.Dto.EventDto;
import eventsms.events.Models.EventModel;

import java.util.List;


public interface EventService {

    public void saveEvent(EventModel event);
    public EventModel cancelEvent(long id) throws Exception;
    public EventModel editEvent(EditEventDto eventDto, long eventId) throws Exception;
    public boolean verifyDisponibility(EventModel event);
    public List<AllEventsDto> listAllEvents();
    public List<AllEventsDto> listAllEventsAvailables();
    public List<AllEventsDto> listCancelledEvents();
    public List<AllEventsDto> listDoneEvents();
    public AllEventsDto eventInformation(long id) throws Exception;
    public List<AllEventsDto> listAllMyEvents(String artistName);

}
