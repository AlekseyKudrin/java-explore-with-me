package ru.practicum.admin.service;

import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.user.dto.NewUserRequest;
import ru.practicum.user.dto.UserDto;

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
