//package ru.practicum;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Controller;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.*;
//import ru.practicum.*;
//import ru.practicum.client.ServerClient;
//
//import javax.validation.Valid;
//import java.util.List;
//
//@Slf4j
//@Controller
//@Validated
//@RequiredArgsConstructor
//@RequestMapping
//public class ServerController {
//
//    private final ServerClient serverClient;
//
//    @GetMapping("/compilations")
//    public ResponseEntity<Object> getCompilations(
//            @RequestParam(defaultValue = "0") Integer from,
//            @RequestParam(defaultValue = "10") Integer size,
//            @RequestParam(defaultValue = "false") Boolean pinned
//    ) {
//        log.info("Received a request to return a collection of events");
//        return serverClient.getCompilations(from, size, pinned);
//    }
//
//    @GetMapping("/compilations/{compId}")
//    public ResponseEntity<Object> getCompilationsById(
//            @PathVariable Integer compId
//    ) {
//        log.info("Received a request to return a collection of events by Id={}", compId);
//        return serverClient.getCompilationsById(compId);
//    }
//

//
//
//    @GetMapping("/events")
//    public ResponseEntity<Object> getEvents(
//            @RequestParam String text,
//            @RequestParam List<Integer> categories,
//            @RequestParam Boolean paid,
//            @RequestParam String rangeStart,
//            @RequestParam String rangeEnd,
//            @RequestParam Boolean onlyAvailable,
//            @RequestParam String sort,
//            @RequestParam(defaultValue = "0") Integer from,
//            @RequestParam(defaultValue = "10") Integer size
//    ) {
//        log.info("Received a request to return events list");
//        return serverClient.getEvents(
//                text, categories, paid, rangeStart, rangeEnd,
//                onlyAvailable, sort, from, size);
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//}
