package ru.practicum.ewmserver.compilation.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;

import javax.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class UpdateCompilationRequest {
    private final List<Integer> events;
    private final Boolean pinned;
    @Size(min = 1, message = ErrorStrings.TOO_SHORT)
    @Size(max = 50, message = ErrorStrings.TOO_LONG)
    private final String title;
}
