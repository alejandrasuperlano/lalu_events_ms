package eventsms.events.Dto;

import lombok.Data;

import java.util.Date;

@Data
public class CancelEventDto {
    private String artist;
    private String eventName;
    private String eventCity;
    private String eventLocation;
    private Date eventDate;
}
