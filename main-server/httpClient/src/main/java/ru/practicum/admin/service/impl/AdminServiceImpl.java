package ru.practicum.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.service.AdminService;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.service.impl.CompilationServiceImpl;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.service.impl.UserServiceImpl;

import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserServiceImpl userService;

    private final CategoryServiceImpl categoryService;

    private final CompilationServiceImpl compilationService;

    @Override
    public UserDto createUser(UserDto userDto) {
        return userService.creteUser(userDto);
    }

    @Override
    public List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size) {
        return userService.getUsers(ids, from, size);
    }

    @Override
    public void deleteUser(Integer userId) {
        userService.deleteUser(userId);
    }

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        return categoryService.createCategory(categoryDto);
    }

    @Override
    public void deleteCategory(Integer catId) {
        categoryService.deleteCategory(catId);
    }

    @Override
    public CategoryDto patchCategory(Integer catId, CategoryDto categoryDto) {
        return categoryService.patchCategory(catId, categoryDto);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }
}
