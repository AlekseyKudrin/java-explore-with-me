package ru.practicum.admin.service;

import ru.practicum.admin.model.UpdateEventAdminRequest;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.UserDto;

import java.time.LocalDateTime;
import java.util.List;

public interface AdminService {

    UserDto createUser(NewUserRequest newUserRequest);

    List<UserDto> getUsers(List<Integer> ids, Integer from, Integer size);

    void deleteUser(Integer userId);

    CategoryDto createCategory(NewCategoryDto newCategoryDto);

    void deleteCategory(Integer catId);

    CategoryDto patchCategory(Integer catId, CategoryDto categoryDto);

    CompilationDto createCompilation(NewCompilationDto newCompilationDto);

    void deleteCompilation(Integer comId);

    CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest compilation);

    List<EventFullDto> searchEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size);

    EventFullDto changeEventAndStatus(Integer eventId, UpdateEventAdminRequest event);
}
