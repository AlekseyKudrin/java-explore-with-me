package ru.practicum.category.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Size;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class CategoryDto{
    Integer id;
    @Size(max = 50)
    String name;
}
