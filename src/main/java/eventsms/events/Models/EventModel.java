package eventsms.events.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Event")
public class EventModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long eventId;
    @NotBlank
    private String artist;
    @NotBlank
    private String eventName;
    @NotNull
    private Date eventDate;
    @NotBlank
    private String eventCity;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(
            name = "eventLocation"
    )
    private LocationModel eventLocation;
    @NotBlank
    private String eventState;
    @NotBlank
    private String eventOrganizer;
    @NotBlank
    private String eventDescription;
    @NotBlank
    private String eventCategory;
    @NotBlank
    private String eventFlyer;
    @NotBlank
    private String eventTicketsURL;


}
