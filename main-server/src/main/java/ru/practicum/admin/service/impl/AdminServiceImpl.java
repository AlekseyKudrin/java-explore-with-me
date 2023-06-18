package ru.practicum.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.model.UpdateEventAdminRequest;
import ru.practicum.admin.service.AdminService;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;
import ru.practicum.compilation.service.impl.CompilationServiceImpl;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.service.impl.EventServiceImpl;
import ru.practicum.exceptionHandler.exception.ValidateFieldException;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.service.impl.UserServiceImpl;

import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserServiceImpl userService;

    private final CategoryServiceImpl categoryService;

    private final CompilationServiceImpl compilationService;

    private final EventServiceImpl eventService;

    @Override
    public UserDto createUser(NewUserRequest newUserRequest) {
        return userService.creteUser(newUserRequest);
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
    public CategoryDto createCategory(NewCategoryDto newCategoryDto) {
        return categoryService.createCategory(newCategoryDto);
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

    @Override
    public void deleteCompilation(Integer comId) {
        compilationService.deleteCompilation(comId);
    }

    @Override
    public CompilationDto changeCompilation(Integer comId, UpdateCompilationRequest compilation) {
        if (compilation.getTitle() != null && compilation.getTitle().length() > 50) {
            throw new ValidateFieldException("Length title cannot be > 50");
        }
        return compilationService.changeCompilation(comId, compilation);
    }

    @Override
    public List<EventFullDto> searchEvents(List<Integer> users, List<String> states, List<Integer> categories, LocalDateTime rangeStart, LocalDateTime rangeEnd, Integer from, Integer size) {
        if (users != null) {
            users.forEach(userService::findUserById);
        }
        if (states != null) {
            states.forEach(i -> {
                for (State s : State.values()) {
                    int t = 0;
                    if (!i.equals(s.toString())) {
                        t++;
                    } else {
                        break;
                    }
                    if (t == State.values().length)
                        throw new ValidationException("Incorrect state");
                }
            });
        }
        if (categories != null) {
            categories.forEach(categoryService::findCategoryById);
        }
        if (rangeStart != null && rangeEnd != null) {
            if (rangeStart.isAfter(rangeEnd)) {
                throw new ValidateFieldException("Start date cannot be before than end date");
            }
        }
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto changeEventAndStatus(Integer eventId, UpdateEventAdminRequest event) {
        return eventService.updateEventAdmin(eventId, event);
    }
}
