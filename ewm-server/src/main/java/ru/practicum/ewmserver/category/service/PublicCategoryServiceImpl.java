package ru.practicum.ewmserver.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.mapper.CategoryMapper;
import ru.practicum.ewmserver.category.model.Category;
import ru.practicum.ewmserver.category.storage.CategoryRepository;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.CATEGORY_NOT_FOUND_BY_ID;

@Service
@RequiredArgsConstructor
public class PublicCategoryServiceImpl implements PublicCategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, readOnly = true)
    public List<CategoryDto> getCategories(int from, int size) {
        PageRequest pageRequest = PageRequest.of(from > 0 ? from / size : 0, size);
        return categoryRepository.findAll(pageRequest).getContent().stream()
                .map(CategoryMapper::createCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, categoryId)));
        return CategoryMapper.createCategoryDto(category);
    }
}
