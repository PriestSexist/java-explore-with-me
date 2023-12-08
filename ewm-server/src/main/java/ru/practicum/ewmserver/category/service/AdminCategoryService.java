package ru.practicum.ewmserver.category.service;

import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    CategoryDto postCategoryAdmin(NewCategoryDto newCategoryDto);

    void deleteCategoryAdmin(int catId);

    CategoryDto patchCategoryAdmin(CategoryDto categoryDto, int catId);
}
