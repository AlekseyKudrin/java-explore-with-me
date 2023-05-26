package ru.practicum.admin.service;

import ru.practicum.admin.models.category.CategoryDto;
import ru.practicum.admin.models.user.UserDto;

import java.util.List;

public interface AdminService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    CategoryDto createCategory(CategoryDto categoryDto);

    void deleteCategory(Integer catId);

    CategoryDto patchCategory(Integer catId, CategoryDto categoryDto);
}
