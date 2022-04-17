package eventsms.events.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class AllEventsDto {
    private long id;
    private String artist;
    private String eventName;
    private Date eventDate;
    private String eventCity;
    private String eventLocation;
    private String eventState;
    private String eventOrganizer;
    private String eventDescription;
    private String eventCategory;
    private String eventTicketsURL;
    private String eventFlyer;
}
