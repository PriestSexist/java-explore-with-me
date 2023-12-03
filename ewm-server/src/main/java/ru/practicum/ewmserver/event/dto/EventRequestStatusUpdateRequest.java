package ru.practicum.ewmserver.event.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.request.model.RequestStatus;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class EventRequestStatusUpdateRequest {
    private final List<Integer> requestIds;
    private final RequestStatus status;
}
