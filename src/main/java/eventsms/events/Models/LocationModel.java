package eventsms.events.Models;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "Location")
public class LocationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long locationId;
    @NotBlank
    private String locationName;
    @NotBlank
    private String locationAddress;
    @NotBlank
    private String locationCity;
    @NotNull
    private int locationCapacity;

    // ------------------- RELATIONSHIP --------------------- //
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "eventLocation")
    private List<EventModel> event;

}
