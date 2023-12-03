package ru.practicum.ewmserver.category.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;
import ru.practicum.ewmserver.category.service.AdminCategoryService;
import ru.practicum.ewmserver.category.dto.CategoryDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/admin/categories")
@Validated
public class AdminCategoryControllerImpl implements AdminCategoryController {

    private final AdminCategoryService adminCategoryService;

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto postCategoryAdmin(@RequestBody @Valid NewCategoryDto newCategoryDto) {
        log.debug("Вызван метод postCategory");
        return adminCategoryService.postCategoryAdmin(newCategoryDto);
    }

    @Override
    @DeleteMapping("/users/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategoryAdmin(@PathVariable @Positive int catId) {
        log.debug("Вызван метод deleteCategory");
        adminCategoryService.deleteCategoryAdmin(catId);
    }

    @Override
    @PatchMapping("/{catId}")
    public CategoryDto patchCategoryAdmin(@RequestBody CategoryDto categoryDto,
                                          @PathVariable @Positive int catId) {
        log.debug("Вызван метод patchCompilationAdmin");
        return adminCategoryService.patchCategoryAdmin(categoryDto, catId);
    }
}
