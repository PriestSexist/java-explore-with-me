package ru.practicum.ewmserver.category.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;

public interface AdminCategoryService {
    @Transactional(propagation = Propagation.REQUIRED)
    CategoryDto postCategoryAdmin(NewCategoryDto newCategoryDto);

    @Transactional(propagation = Propagation.REQUIRED)
    void deleteCategoryAdmin(int catId);

    @Transactional(propagation = Propagation.REQUIRED)
    CategoryDto patchCategoryAdmin(CategoryDto categoryDto, int catId);
}
