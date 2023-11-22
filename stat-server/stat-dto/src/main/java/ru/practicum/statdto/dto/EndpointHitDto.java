package ru.practicum.statdto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class EndpointHitDto {
    private final int id;
    private final String app;
    private final String uri;
    private final String ip;
    private final String timestamp;
}
