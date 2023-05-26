package ru.practicum.category.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.practicum.category.dao.CategoryRepository;
import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.CategoryMapper;
import ru.practicum.category.service.CategoryService;
import ru.practicum.exceptionHandler.exception.ValueNotFoundDbException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

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
        Optional<Category> category = categoryRepository.findById(catId);
        if (category.isPresent()) {
            return CategoryMapper.toCategoryDto(category.get());
        }else {
            throw new ValueNotFoundDbException("Category not found");
        }
    }
}
