package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.dto.UpdateEventAdminRequest;
import ru.practicum.admin.service.AdminService;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.dto.NewCategoryDto;
import ru.practicum.compilation.dto.CompilationDto;
import ru.practicum.compilation.dto.NewCompilationDto;
import ru.practicum.compilation.dto.UpdateCompilationRequest;
import ru.practicum.event.dto.EventFullDto;
import ru.practicum.user.model.NewUserRequest;
import ru.practicum.user.model.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminService adminService;


    @GetMapping("/users")
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Integer> ids,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return users");
        List<UserDto> userDtoList = adminService.getUsers(ids, from, size);
        log.info("Request to return users completed");
        return userDtoList;
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(
            @RequestBody @Valid NewUserRequest newUserRequest
    ) {
        log.info("Received a request to create user");
        UserDto userDto = adminService.createUser(newUserRequest);
        log.info("User id={} successfully created", userDto.getId());
        return userDto;
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @Positive @PathVariable Integer userId
    ) {
        log.info("Received a request to delete user id={}", userId);
        adminService.deleteUser(userId);
        log.info("User id={} successfully delete", userId);
    }


    @PostMapping("/compilations")
    @ResponseStatus(HttpStatus.CREATED)
    public CompilationDto createCompilation(
            @RequestBody @Valid NewCompilationDto newCompilationDto
    ) {
        log.info("Received a request to create compilation");
        CompilationDto compilationDto = adminService.createCompilation(newCompilationDto);
        log.info("Compilation id={} successfully created", compilationDto.getId());
        return compilationDto;
    }

    @DeleteMapping("/compilations/{comId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCompilation(
            @PathVariable Integer comId
    ) {
        log.info("Received a request to delete compilation id={}", comId);
        adminService.deleteCompilation(comId);
        log.info("Compilation id={} successfully delete", comId);
    }

    @PatchMapping("/compilations/{comId}")
    public CompilationDto changeCompilation(
            @PathVariable Integer comId,
            @RequestBody UpdateCompilationRequest compilation
    ) {
        log.info("Received a request to change compilation id={}", comId);
        CompilationDto compilationDto = adminService.changeCompilation(comId, compilation);
        log.info("Compilation id={} successfully change", compilationDto.getId());
        return compilationDto;
    }

    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(
            @RequestBody @Valid NewCategoryDto newCategoryDto
    ) {
        log.info("Received a request to create a category");
        CategoryDto categoryDto = adminService.createCategory(newCategoryDto);
        log.info("Category id={} successfully created", categoryDto.getId());
        return categoryDto;
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable Integer catId
    ) {
        log.info("Received a request to delete a category {}", catId);
        adminService.deleteCategory(catId);
        log.info("Category id={} successfully delete", catId);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto changeCategory(
            @PathVariable Integer catId,
            @RequestBody @Valid CategoryDto updateCategory
    ) {
        log.info("Received a request to change a category {}", catId);
        CategoryDto categoryDto = adminService.patchCategory(catId, updateCategory);
        log.info("Category id={} successfully change", catId);
        return categoryDto;
    }

    @GetMapping("/events")
    public List<EventFullDto> searchEvents(
            @RequestParam(required = false) List<Integer> users,
            @RequestParam(required = false) List<String> states,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to events search");
        return adminService.searchEvents(
                users, states, categories, rangeStart, rangeEnd, from, size);
    }

    @PatchMapping("/events/{eventId}")
    public EventFullDto changeEventAndStatus(
            @PositiveOrZero @PathVariable Integer eventId,
            @RequestBody UpdateEventAdminRequest event
    ) {
        log.info("Received a request to change event id={}", eventId);
        return adminService.changeEventAndStatus(eventId, event);
    }
}
