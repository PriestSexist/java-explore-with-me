package ru.practicum.ewmserver.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.PositiveOrZero;

public interface AdminCategoryController {

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto postCategory(@RequestBody @Valid NewCategoryDto newCategoryDto);

    @DeleteMapping("/users/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategory(@PathVariable @PositiveOrZero int catId);

    @PatchMapping("/{catId}")
    CategoryDto patchCategory(@RequestBody @Valid CategoryDto categoryDto,
                              @PathVariable @PositiveOrZero int catId);
}
