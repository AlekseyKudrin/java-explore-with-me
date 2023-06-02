package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.service.impl.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {

    private final EventServiceImpl eventService;

    @GetMapping("/events")
    public List<EventShortDto> getEvents(
            @RequestParam String text,
            @RequestParam List<Integer> categories,
            @RequestParam Boolean paid,
            @RequestParam String rangeStart,
            @RequestParam String rangeEnd,
            @RequestParam Boolean onlyAvailable,
            @RequestParam String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return events list");
        return eventService.getEvents(
                text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
    }
    @GetMapping("/{id}")
    public EventFullDto findById(@PathVariable Integer eventId) {
        return eventService.findById(eventId);
    }
}
