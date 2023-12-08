package ru.practicum.ewmserver.request.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class ParticipationRequestDto {
    private final int id;
    private final int event;
    private final String created;
    private final int requester;
    private final String status;
}
