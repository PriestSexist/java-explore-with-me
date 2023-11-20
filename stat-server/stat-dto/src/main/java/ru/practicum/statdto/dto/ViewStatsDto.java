package ru.practicum.statdto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ViewStatsDto {
    private final String app;
    private final String uri;
    private final Long hits;
}
