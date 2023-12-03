package ru.practicum.ewmserver.category.service;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.dto.CategoryDto;

import java.util.List;

public interface PublicCategoryService {
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    List<CategoryDto> getCategories(int from, int size);

    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    CategoryDto getCategoryById(int catId);
}
