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
    private int id;
    private String app;
    private String uri;
    private String ip;
    private String timestamp;
}
