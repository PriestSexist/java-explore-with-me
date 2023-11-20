package ru.practicum.statservice.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.statdto.dto.ViewStatsDto;
import ru.practicum.statservice.model.EndpointHit;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StatServiceRepository extends JpaRepository<EndpointHit, Integer> {
    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count(eh.id)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp > ?1 and eh.timestamp < ?2 AND eh.uri IN ?3 " +
            "group by eh.app, eh.uri  " +
            "order by count (eh.ip) DESC")
    List<ViewStatsDto> getUniqueIpStat(LocalDateTime start, LocalDateTime end, List<String> uris);


    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count(distinct eh.id)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp > ?1 and eh.timestamp < ?2 AND eh.uri IN ?3 " +
            "group by eh.app, eh.uri  " +
            "order by count (distinct eh.ip) DESC")
    List<ViewStatsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris);

}
