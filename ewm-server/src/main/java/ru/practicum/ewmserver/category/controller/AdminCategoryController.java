package ru.practicum.ewmserver.category.controller;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;
import ru.practicum.ewmserver.category.dto.CategoryDto;

import javax.validation.constraints.Positive;

public interface AdminCategoryController {
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    CategoryDto postCategoryAdmin(@RequestBody @Validated NewCategoryDto newCategoryDto);

    @DeleteMapping("/users/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteCategoryAdmin(@PathVariable @Positive int catId);

    @PatchMapping("/{catId}")
    CategoryDto patchCategoryAdmin(@RequestBody CategoryDto categoryDto,
                                   @PathVariable @Positive int catId);
}
