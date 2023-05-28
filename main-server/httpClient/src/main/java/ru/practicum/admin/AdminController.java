package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.service.impl.AdminServiceImpl;
import ru.practicum.category.model.CategoryDto;
import ru.practicum.compilation.model.CompilationDto;
import ru.practicum.compilation.model.NewCompilationDto;
import ru.practicum.user.model.UserDto;

import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/admin")
public class AdminController {

    private final AdminServiceImpl adminService;

    @GetMapping("/users")
    public List<UserDto> getUsers(
            @RequestParam(required = false) List<Integer> ids,
            @PositiveOrZero @RequestParam(defaultValue = "0") Integer from,
            @Positive @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return user list");
        return adminService.getUsers(ids, from, size);
    }

    @PostMapping("/users")
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto createUser(
            @RequestBody @Valid UserDto userDto
    ) {
        log.info("Received a request to create user");
        return adminService.createUser(userDto);
    }

    @DeleteMapping("/users/{userId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(
            @PathVariable Integer userId
    ) {
        log.info("Received a request to delete user id={}", userId);
        adminService.deleteUser(userId);
    }


    @PostMapping("/compilations")
    public CompilationDto createCompilation(
            @RequestBody NewCompilationDto newCompilationDto
    ) {
        log.info("Received a request to create compilation");
        return adminService.createCompilation(newCompilationDto);
    }
//
//    @DeleteMapping("/compilations/{comId}")
//    public ResponseEntity<Object> deleteCompilation(
//            @PathVariable Integer comId
//    ) {
//        log.info("Received a request to delete compilation id={}", comId);
//        return adminService.deleteCompilation(comId);
//    }
//
//    @PatchMapping("/compilations/{comId}")
//    public ResponseEntity<Object> changeCompilation(
//            @PathVariable Integer comId,
//            @RequestBody Compilation compilation
//    ) {
//        log.info("Received a request to change compilation id={}", comId);
//        return adminService.cangeCompilation(comId, compilation);
//    }
//
    @PostMapping("/categories")
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto createCategory(
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.info("Received a request to create a category");
        return adminService.createCategory(categoryDto);
    }

    @DeleteMapping("/categories/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(
            @PathVariable Integer catId
    ) {
        log.info("Received a request to delete a category {}", catId);
        adminService.deleteCategory(catId);
    }

    @PatchMapping("/categories/{catId}")
    public CategoryDto changeCategory(
            @PathVariable Integer catId,
            @RequestBody @Valid CategoryDto categoryDto
    ) {
        log.info("Received a request to change a category {}", catId);
        return adminService.patchCategory(catId, categoryDto);
    }
//
//    @GetMapping("/events")
//    public ResponseEntity<Object> searchEvents(
//            @RequestParam List<Integer> users,
//            @RequestParam List<Integer> states,
//            @RequestParam List<Integer> categories,
//            @RequestParam String rangeStart,
//            @RequestParam String rangeEnd,
//            @RequestParam(defaultValue = "0") Integer from,
//            @RequestParam(defaultValue = "10") Integer size
//    ) {
//        log.info("Received a request to event search");
//        return adminService.searchEvents(
//                users, states, categories, rangeStart, rangeEnd, from, size);
//    }
//
//    @PatchMapping("/events/{eventId}")
//    public ResponseEntity<Object> changeEventAndStatus(
//            @PathVariable Integer eventId,
//            @RequestBody Event event
//    ) {
//        log.info("Received a request to change event id={}", eventId);
//        return adminService.changeEventAndStatus(eventId, event);
//    }
}
