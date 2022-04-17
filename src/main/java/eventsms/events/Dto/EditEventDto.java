package eventsms.events.Dto;

import eventsms.events.Models.LocationModel;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
public class EditEventDto {
    private String eventName;
    private Date eventDate;
    private String eventCity;
    private String eventLocation;
    private String eventOrganizer;
    private String eventDescription;
    private String eventCategory;
    
}
