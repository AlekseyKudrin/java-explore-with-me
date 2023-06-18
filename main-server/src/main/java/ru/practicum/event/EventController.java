package ru.practicum.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.client.ServerClient;
import ru.practicum.event.model.EventFullDto;
import ru.practicum.event.model.EventShortDto;
import ru.practicum.event.service.impl.EventServiceImpl;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/events")
public class EventController {

    private final EventServiceImpl eventService;

    private final ServerClient serverClient;

    @GetMapping
    public List<EventShortDto> getEvents(
            @RequestParam(required = false) String text,
            @RequestParam(required = false) List<Integer> categories,
            @RequestParam(required = false) Boolean paid,
            @RequestParam(required = false) LocalDateTime rangeStart,
            @RequestParam(required = false) LocalDateTime rangeEnd,
            @RequestParam(defaultValue = "false") Boolean onlyAvailable,
            @RequestParam(required = false) String sort,
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size,
            HttpServletRequest request
    ) {
        log.info("Received a request to return events by filter");
        serverClient.create(request);
        List<EventShortDto> eventShortDtoList = eventService.getEvents(
                text, categories, paid, rangeStart, rangeEnd,
                onlyAvailable, sort, from, size);
        log.info("Request to return events by filter completed");
        return eventShortDtoList;
    }

    @GetMapping("/{id}")
    public EventFullDto findById(
            @PathVariable Integer id,
            HttpServletRequest request
    ) {
        log.info("Received a request to return a event Id={}", id);
        serverClient.create(request);
        EventFullDto eventFullDto = eventService.findPublishedEventById(id);
        log.info("Request to return a event Id={} completed", id);
        return eventFullDto;
    }
}
