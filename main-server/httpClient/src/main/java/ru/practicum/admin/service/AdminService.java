package ru.practicum.admin.service;

import ru.practicum.user.model.UserDto;
import ru.practicum.category.model.CategoryDto;

import java.util.List;

public interface AdminService {
    UserDto createUser(UserDto userDto);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    CategoryDto createCategory(CategoryDto categoryDto);

    void deleteCategory(Integer catId);

    CategoryDto patchCategory(Integer catId, CategoryDto categoryDto);
}
