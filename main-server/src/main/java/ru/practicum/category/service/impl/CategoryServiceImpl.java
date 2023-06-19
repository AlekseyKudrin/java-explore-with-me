package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.repository.CategoryRepository;
import ru.practicum.category.service.CategoryService;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;
import ru.practicum.util.General;

import java.util.List;
import java.util.stream.Collectors;

import static ru.practicum.category.model.CategoryMapper.toCategoryDto;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));

        return toCategoryDto(category);
    }

    @Override
    public CategoryDto patchCategory(Integer catId, CategoryDto categoryDto) {
        findCategoryById(catId);
        categoryDto.setId(catId);
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));

        return toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new ValueNotFoundDbException("Category with id=" + catId + " was not found");
        }
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        return categoryRepository.findAll(General.toPage(from, size))
                .stream()
                .map(CategoryMapper::toCategoryDto)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        return toCategoryDto(findCategoryById(catId));
    }

    @Override
    public Category findCategoryById(Integer catId) {
        return categoryRepository.findById(catId).orElseThrow(
                () -> new ValueNotFoundDbException("Category with id=" + catId + " was not found"));
    }
}
