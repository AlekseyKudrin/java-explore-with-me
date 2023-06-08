package ru.practicum.admin.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.practicum.admin.model.StateAction;
import ru.practicum.admin.model.UpdateEventAdminRequest;
import ru.practicum.admin.service.AdminService;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.category.model.NewCategoryDto;
import ru.practicum.category.service.impl.CategoryServiceImpl;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.compilation.model.UpdateCompilationRequest;
import ru.practicum.compilation.service.impl.CompilationServiceImpl;
import ru.practicum.event.model.Event;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.enums.State;
import ru.practicum.event.service.impl.EventServiceImpl;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.UserDto;
import ru.practicum.user.service.impl.UserServiceImpl;

import javax.validation.Valid;
import javax.validation.ValidationException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
        categoryService.getCategoryById(catId);
        categoryDto.setId(catId);
        return categoryService.patchCategory(categoryDto);
    }

    @Override
    public CompilationDto createCompilation(NewCompilationDto newCompilationDto) {
        return compilationService.createCompilation(newCompilationDto);
    }

    @Override
    public void deleteCompilation(Integer comId) {
        compilationService.deleteComplation(comId);
    }

    @Override
    public CompilationDto cangeCompilation(Integer comId, UpdateCompilationRequest compilation) {
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
        return eventService.getEvents(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @Override
    public EventFullDto changeEventAndStatus(Integer eventId, UpdateEventAdminRequest event) {
        Event updateEvent = eventService.findEventbyId(eventId);
        if (updateEvent.getEventDate().minusHours(1).isAfter(LocalDateTime.now())) {
            updateEvent.setPublishedOn(LocalDateTime.now());
        } else {
            throw new ValidationException("The event cannot be published because the edit date is earlier than the publication date");
        }
        if (event.getStateAction() != null) {
            if (event.getStateAction().equals(StateAction.PUBLISH_EVENT)) {
                if (!State.PENDING.equals(updateEvent.getState())) {
                    throw new ValidationException("Cannot publish the event because it's not in the right state: " + updateEvent.getState());
                } else {
                    updateEvent.setState(State.PUBLISHED);
                    updateEvent.setPublishedOn(LocalDateTime.now());
                }
            }
            if (event.getStateAction().equals(StateAction.REJECT_EVENT)) {
                if (State.PUBLISHED.equals(updateEvent.getState())) {
                    throw new ValidationException("Unable to cancel the event because it is in the wrong state: PUBLISHING");
                } else {
                    updateEvent.setState(State.CANCELED);
                }
            }
        }
        if (event.getAnnotation() != null) {
            updateEvent.setAnnotation(event.getAnnotation());
        }
        if (event.getCategory() != null) {
            updateEvent.setCategory(categoryService.findCategoryById(event.getCategory()));
        }
        if (event.getDescription() != null) {
            updateEvent.setDescription(event.getDescription());
        }
        if (event.getEventDate() != null) {
            updateEvent.setEventDate(event.getEventDate());
        }
        if (event.getLocation() != null) {
            updateEvent.setLocation(event.getLocation());
        }
        if (event.getPaid() != null) {
            updateEvent.setLocation(event.getLocation());
        }
        if (event.getParticipantLimit() != null) {
            updateEvent.setParticipantLimit(event.getParticipantLimit());
        }
        if (event.getRequestModeration() != null) {
            updateEvent.setRequestModeration(event.getRequestModeration());
        }
        if (event.getTitle() != null) {
            updateEvent.setTitle(event.getTitle());
        }
        return eventService.saveUpdateEvent(updateEvent);
    }
}
