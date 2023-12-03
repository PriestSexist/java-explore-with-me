package ru.practicum.ewmserver.event.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmserver.event.model.Event;
import ru.practicum.ewmserver.event.model.EventState;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("select e " +
            "from Event as e " +
            "where (?1 is null or e.initiator.id in ?1) " +
            "and (?2 is null or e.state in ?2) " +
            "and (?3 is null or e.category.id in ?3) " +
            "and (e.eventDate between ?4 and ?5) ")
    Page<Event> getByUserIdsStatesCategories(List<Integer> userIds,
                                             List<String> states,
                                             List<Integer> categoryIds,
                                             LocalDateTime start,
                                             LocalDateTime end,
                                             Pageable page);

    @Query("select e " +
            "from Event as e " +
            "left join Request as r on r.event = e and r.status = 'CONFIRMED' " +
            "where e.state = 'PUBLISHED' " +
            "and (?1 is null or e.annotation like %?1% OR e.description like %?1%) " +
            "and (?2 is null or e.category.id in ?2) " +
            "and e.eventDate between ?3 and ?4" +
            "group by e " +
            "having count (r) < e.participantLimit " +
            "order by ?5 ")
    Page<Event> getBySearchAvailable(String search,
                                     List<Integer> categoryIds,
                                     LocalDateTime start,
                                     LocalDateTime end,
                                     String sort,
                                     Pageable page);

    @Query("select e " +
            "from Event as e " +
            "where e.state = 'PUBLISHED' " +
            "and (?1 is null or e.annotation like %?1% or e.description like %?1%) " +
            "and (?2 is null or e.category.id in ?2) " +
            "and e.eventDate between ?3 and ?4" +
            "order by ?5 ")
    Page<Event> getBySearch(String search,
                            List<Integer> categoryIds,
                            LocalDateTime start,
                            LocalDateTime end,
                            String sort,
                            Pageable page);

    @Query("select e " +
            "from Event e " +
            "left join Request as r on r.event = e and r.status = 'CONFIRMED' " +
            "where e.state = 'PUBLISHED' " +
            "and (?1 is null or e.annotation like %?1% or e.description like %?1%) " +
            "and (?2 is null or e.category.id in ?2) " +
            "and e.eventDate between ?3 and ?4" +
            "and e.paid = ?5 " +
            "group by e " +
            "having count (r) < e.participantLimit " +
            "order by ?6 ")
    Page<Event> getBySearchAndPaidAvailable(String search,
                                            List<Integer> categoryIds,
                                            LocalDateTime start,
                                            LocalDateTime end,
                                            boolean paid,
                                            String sort,
                                            Pageable page);

    @Query("select e " +
            "from Event e " +
            "where e.state = 'PUBLISHED' " +
            "and (?1 is null or e.annotation like %?1% or e.description like %?1%) " +
            "and (?2 is null or e.category.id in ?2) " +
            "and e.eventDate between ?3 and ?4" +
            "and e.paid = ?5 " +
            "order by ?6 ")
    Page<Event> getBySearchAndPaid(String search,
                                   List<Integer> categoryIds,
                                   LocalDateTime start,
                                   LocalDateTime end,
                                   boolean paid,
                                   String sort,
                                   Pageable page);

    Optional<Event> getByIdAndState(int eventId, EventState state);

    Page<Event> getByInitiatorId(int initiatorId, Pageable page);
}
