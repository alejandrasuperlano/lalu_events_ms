package eventsms.events.Repositories;

import eventsms.events.Models.EventModel;
import eventsms.events.Models.LocationModel;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.neo4j.repository.ReactiveNeo4jRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends CrudRepository<EventModel, Long> {
    Optional<EventModel> findByArtist (String artistName);
    Boolean existsByEventDate(Date date);
    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e inner join location as loc" +
            "           WHERE e.event_date = :date and e.event_state = 'Active'",
            nativeQuery = true
    )
    List<EventModel> verifyLocationAndCity(@Param("date") Date date);
    EventModel findByArtistAndAndEventName (String artist, String name);
    boolean existsByArtistAndEventName (String artist, String name);
    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e inner join location as loc" +
            "           WHERE e.event_name = :name " +
            "                   and " +
            "                   loc.location_name = :location " +
            "                   and " +
            "                   e.event_city = :city" +
            "                   and" +
            "                   e.artist = :artist" +
            "                   and " +
            "                   DAY(e.event_date) = DAY(:date)" +
            "                   and" +
            "                   MONTH(e.event_date) = MONTH(:date)" +
            "                   and" +
            "                   YEAR(e.event_date) = YEAR(:date)" +
            "                   and e.event_state = 'Active'",
            nativeQuery = true
    )
    EventModel searchByArtistAndCityAndLocationAndName(@Param("name") String name,
                                                       @Param("location") String location,
                                                       @Param("city") String city,
                                                       @Param("artist") String artist,
                                                       @Param("date") Date date);

    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e inner join location as loc" +
            "           WHERE e.event_name = :name " +
            "                   and " +
            "                   loc.location_name = :location " +
            "                   and " +
            "                   e.event_city = :city" +
            "                   and " +
            "                   e.event_date = :date",
            nativeQuery = true
    )
    EventModel searchByCityAndLocationAndNameAndDate(@Param("name") String name,
                                                        @Param("location") String location,
                                                        @Param("city") String city,
                                                        @Param("date") Date date);
    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e " +
            "           WHERE e.event_state = 'Active'",
            nativeQuery = true
    )
    List<EventModel> allEventsAvailables();

    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e " +
            "           WHERE e.event_state = 'Cancelled'",
            nativeQuery = true
    )
    List<EventModel> allEventsCancelled();

    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e " +
            "           WHERE e.event_state = 'Done'",
            nativeQuery = true
    )
    List<EventModel> allEventsDone();

    @Query( value = "   SELECT e.event_id, " +
            "                   e.artist, " +
            "                   e.event_category, " +
            "                   e.event_city, " +
            "                   e.event_date, " +
            "                   e.event_description, " +
            "                   e.event_name, " +
            "                   e.event_organizer, " +
            "                   e.event_state, " +
            "                   e.event_location, " +
            "                   e.event_flyer, " +
            "                   e.event_ticketsurl " +
            "           FROM event as e ",
            nativeQuery = true
    )
    List<EventModel> allEvents();

    List<EventModel> findAllByArtist(String artistName);

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query( value = "   UPDATE event " +
            "           SET event.event_state = 'Done' " +
            "           WHERE event.event_date < :date" +
            "               and" +
            "               event.event_state = 'Active'",
            nativeQuery = true
    )
    void flush(@Param("date") Date date);
}
