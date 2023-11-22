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

    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count(eh.ip)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between ?1 and ?2 and eh.uri IN ?3 " +
            "group by eh.app, eh.uri  " +
            "order by count (eh.ip) desc ")
    List<ViewStatsDto> getStat(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count(distinct eh.ip)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between ?1 and ?2 and eh.uri IN ?3 " +
            "group by eh.app, eh.uri  " +
            "order by count (distinct eh.ip) desc ")
    List<ViewStatsDto> getStatUniqueIp(LocalDateTime start, LocalDateTime end, List<String> uris);

    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count (eh)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between ?1 and ?2 " +
            "group by eh.app, eh.uri " +
            "order by count (eh) desc ")
    List<ViewStatsDto> getStatNoUris(LocalDateTime start, LocalDateTime end);

    @Query("select new ru.practicum.statdto.dto.ViewStatsDto(eh.app, eh.uri, count (distinct eh.ip)) " +
            "from EndpointHit as eh " +
            "where eh.timestamp between ?1 and ?2 " +
            "group by eh.app, eh.uri " +
            "order by count (distinct eh.ip) desc ")
    List<ViewStatsDto> getStatNoUrisUniqueIp(LocalDateTime start, LocalDateTime end);
}
