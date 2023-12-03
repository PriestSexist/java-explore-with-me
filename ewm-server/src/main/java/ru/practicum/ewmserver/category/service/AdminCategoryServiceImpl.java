package ru.practicum.ewmserver.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.ewmserver.category.dto.CategoryDto;
import ru.practicum.ewmserver.category.dto.NewCategoryDto;
import ru.practicum.ewmserver.category.mapper.CategoryMapper;
import ru.practicum.ewmserver.category.model.Category;
import ru.practicum.ewmserver.category.storage.CategoryRepository;
import ru.practicum.ewmserver.error.exception.DataConflictException;
import ru.practicum.ewmserver.error.exception.EntityNotFoundException;

import static ru.practicum.ewmserver.error.constants.ErrorStrings.CATEGORY_NOT_FOUND_BY_ID;
import static ru.practicum.ewmserver.error.constants.ErrorStrings.CATEGORY_WITH_THIS_NAME_ALREADY_EXISTS;

@RequiredArgsConstructor
@Service
public class AdminCategoryServiceImpl implements AdminCategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryDto postCategoryAdmin(NewCategoryDto newCategoryDto) {
        if (categoryRepository.existsByName(newCategoryDto.getName())) {
            throw new DataConflictException(CATEGORY_WITH_THIS_NAME_ALREADY_EXISTS);
        }
        Category category = CategoryMapper.createCategory(newCategoryDto);
        return CategoryMapper.createCategoryDto(categoryRepository.save(category));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void deleteCategoryAdmin(int categoryId) {
        if (!categoryRepository.existsById(categoryId)) {
            throw new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, categoryId));
        }
        categoryRepository.deleteById(categoryId);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public CategoryDto patchCategoryAdmin(CategoryDto categoryDto, int categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND_BY_ID, categoryId)));

        if (!categoryDto.getName().isBlank()) {
            category.setName(categoryDto.getName());
        }

        return CategoryMapper.createCategoryDto(categoryRepository.save(category));
    }
}
