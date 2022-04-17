package eventsms.events.Repositories;

import eventsms.events.Models.LocationModel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface LocationRepository extends CrudRepository<LocationModel, Long> {

    Optional<LocationModel> findByLocationNameAndLocationCity(String name, String city);
    Boolean existsByLocationNameAndLocationCity(String name, String city);

}
