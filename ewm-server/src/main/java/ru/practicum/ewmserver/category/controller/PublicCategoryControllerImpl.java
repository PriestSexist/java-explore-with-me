package ru.practicum.ewmserver.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.service.PublicCategoryService;

import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/categories")
@Validated
public class PublicCategoryControllerImpl implements PublicCategoryController {

    private final PublicCategoryService publicCategoryService;

    @Override
    @GetMapping
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero int from,
                                           @RequestParam(defaultValue = "10") @Positive int size) {
        log.debug("Вызван метод getCategories");
        return publicCategoryService.getCategories(from, size);
    }

    @Override
    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(@PathVariable @PositiveOrZero int catId) {
        log.debug("Вызван метод getCategoryById");
        return publicCategoryService.getCategoryById(catId);
    }
}
