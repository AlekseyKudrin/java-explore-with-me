package ru.practicum.category.service;

import ru.practicum.admin.model.NewCategoryDto;
import ru.practicum.category.model.CategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    CategoryDto patchCategory(CategoryDto categoryDto);

    void deleteCategory(Integer catId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Integer catId);
}
