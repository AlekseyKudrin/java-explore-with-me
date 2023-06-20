package ru.practicum.category.service;

import ru.practicum.category.model.Category;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto patchCategory(Integer catId, CategoryDto categoryDto);

    void deleteCategory(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Integer catId);

    Category findCategoryById(Integer catId);
}
