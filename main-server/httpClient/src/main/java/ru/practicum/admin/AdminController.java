package ru.practicum.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.practicum.admin.model.UserDto;
import ru.practicum.admin.service.impl.AdminServiceImpl;

import javax.validation.Valid;
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
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
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
//
//    @DeleteMapping("/users/{userId}")
//    public ResponseEntity<Object> createUser(
//            @PathVariable Integer userId
//    ) {
//        log.info("Received a request to delete user id={}", userId);
//        return adminService.deleteUser(userId);
//    }
//
//    @PostMapping("/compilations")
//    public ResponseEntity<Object> createCompilation(
//            @RequestBody Compilation compilation
//    ) {
//        log.info("Received a request to create compilation");
//        return adminService.createCompilation(compilation);
//    }
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
//    @PostMapping("/categories")
//    public ResponseEntity<Object> createCategory(
//            @RequestBody @Valid Category category
//    ) {
//        log.info("Received a request to create a category");
//        return adminService.createCategory(category);
//    }
//
//    @DeleteMapping("/categories/{catId}")
//    public ResponseEntity<Object> deleteCategory(
//            @PathVariable Integer catId
//    ) {
//        log.info("Received a request to delete a category {}", catId);
//        return adminService.deleteCategory(catId);
//    }
//
//    @PatchMapping("/categories/{catId}")
//    public ResponseEntity<Object> changeCategory(
//            @PathVariable Integer catId,
//            @RequestBody @Valid Category category
//    ) {
//        log.info("Received a request to change a category {}", catId);
//        return adminService.patchCategory(catId, category);
//    }
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
