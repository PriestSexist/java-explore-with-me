package ru.practicum.ewmserver.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.request.dto.ParticipationRequestDto;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventRequestStatusUpdateResult {
    private final List<ParticipationRequestDto> confirmedRequests;
    private final List<ParticipationRequestDto> rejectedRequests;
}
