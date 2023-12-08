package ru.practicum.ewmserver.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.error.constants.ErrorStrings;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CategoryDto {
    private final int id;
    @NotBlank
    @Size(min = 1, message = ErrorStrings.TOO_SHORT)
    @Size(max = 50, message = ErrorStrings.TOO_LONG)
    private final String name;
}
