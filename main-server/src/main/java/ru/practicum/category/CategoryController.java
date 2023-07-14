package ru.practicum.category;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.category.dto.CategoryDto;
import ru.practicum.category.service.CategoryService;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping()
    public List<CategoryDto> getCategories(
            @RequestParam(defaultValue = "0") Integer from,
            @RequestParam(defaultValue = "10") Integer size
    ) {
        log.info("Received a request to return categories");
        List<CategoryDto> list = categoryService.getCategories(from, size);
        log.info("Request to return a categories completed");
        return list;
    }

    @GetMapping("/{catId}")
    public CategoryDto getCategoryById(
            @PathVariable Integer catId
    ) {
        log.info("Received a request to return category by Id={}", catId);
        CategoryDto categoryDto = categoryService.getCategoryById(catId);
        log.info("Request to return a category by Id={} completed", catId);
        return categoryDto;
    }

}
