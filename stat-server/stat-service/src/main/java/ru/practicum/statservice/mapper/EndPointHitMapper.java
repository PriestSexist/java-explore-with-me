package ru.practicum.statservice.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.statdto.dto.EndpointHitDto;
import ru.practicum.statservice.model.EndpointHit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static ru.practicum.statdto.dto.Constants.DATETIME_FORMAT;

@UtilityClass
public class EndPointHitMapper {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(DATETIME_FORMAT);

    public static EndpointHitDto createEndPointHitDto(EndpointHit endpointHit) {
        return EndpointHitDto.builder()
                .id(endpointHit.getId())
                .app(endpointHit.getApp())
                .uri(endpointHit.getUri())
                .ip(endpointHit.getIp())
                .timestamp(endpointHit.getTimestamp().format(FORMATTER))
                .build();
    }

    public static EndpointHit createEndPointHit(EndpointHitDto endpointHitDto) {
        return EndpointHit.builder()
                .id(endpointHitDto.getId())
                .app(endpointHitDto.getApp())
                .uri(endpointHitDto.getUri())
                .ip(endpointHitDto.getIp())
                .timestamp(LocalDateTime.parse(endpointHitDto.getTimestamp(), FORMATTER))
                .build();
    }
}
