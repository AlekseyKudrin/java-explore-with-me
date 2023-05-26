package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.service.impl.CategoryServiceImpl;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryServiceImpl categoryService;

}
