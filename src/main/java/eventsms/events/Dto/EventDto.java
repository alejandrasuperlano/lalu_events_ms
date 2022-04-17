package eventsms.events.Dto;

import eventsms.events.Models.LocationModel;
import lombok.Data;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Date;

@Data
public class EventDto {
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
