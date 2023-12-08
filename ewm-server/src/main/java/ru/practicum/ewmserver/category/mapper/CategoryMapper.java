package ru.practicum.ewmserver.category.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;
import ru.practicum.ewmserver.category.model.Category;

@UtilityClass
public class CategoryMapper {
    public static CategoryDto createCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category createCategory(CategoryDto categoryDto) {
        return Category.builder()
                .id(categoryDto.getId())
                .name(categoryDto.getName())
                .build();
    }

    public static Category createCategory(NewCategoryDto newCategoryDto) {
        return Category.builder()
                .name(newCategoryDto.getName())
                .build();
    }
}
