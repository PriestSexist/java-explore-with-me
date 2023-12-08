package ru.practicum.ewmserver.event.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
@Embeddable
public class Location {
    private final float lat;
    private final float lon;
}
