package ru.practicum.ewmserver.category.service;

import ru.practicum.ewmserver.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    List<CategoryDto> getCategories(int from, int size);

    CategoryDto getCategoryById(int catId);
}
