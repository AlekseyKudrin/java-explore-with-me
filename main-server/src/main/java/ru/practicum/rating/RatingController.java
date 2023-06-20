package ru.practicum.rating;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.rating.service.RatingService;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/rating")

public class RatingController {

    private final RatingService ratingService;

}
