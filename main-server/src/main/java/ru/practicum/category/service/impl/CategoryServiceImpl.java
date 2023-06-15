package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.service.CategoryService;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;

import java.util.List;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(newCategoryDto));
        log.info("Category successfully created");
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public CategoryDto patchCategory(CategoryDto categoryDto) {
        Category category = categoryRepository.save(CategoryMapper.toCategory(categoryDto));
        log.info("Category successfully change");
        return CategoryMapper.toCategoryDto(category);
    }

    @Override
    public void deleteCategory(Integer catId) {
        try {
            categoryRepository.deleteById(catId);
        } catch (EmptyResultDataAccessException e) {
            throw new ValueNotFoundDbException("Category with id=" + catId + " was not found");
        }
        log.info("Category deleted successfully");
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        int page = from / size;
        PageRequest pageRequest = PageRequest.of(page, size);
        List<CategoryDto> categoryDtoList = categoryRepository.findAll(pageRequest).stream()
                .map(CategoryMapper::toCategoryDto).collect(Collectors.toList());
        return categoryDtoList;
    }

    @Override
    public CategoryDto getCategoryById(Integer catId) {
        Category category = categoryRepository.findById(catId).orElseThrow(
                () -> new ValueNotFoundDbException("Category with id=" + catId + " was not found"));
        return CategoryMapper.toCategoryDto(category);
    }

    public Category findCategoryById(Integer catId) {
        return categoryRepository.findById(catId).orElseThrow(
                () -> new ValueNotFoundDbException("Category not found"));
    }
}
