package ru.practicum.ewmserver.category.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class NewCategoryDto {
    @NotBlank
    private final String name;
}
