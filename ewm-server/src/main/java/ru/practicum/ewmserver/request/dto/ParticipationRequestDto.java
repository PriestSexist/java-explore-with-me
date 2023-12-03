package ru.practicum.ewmserver.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.statdto.dto.Constants;

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
