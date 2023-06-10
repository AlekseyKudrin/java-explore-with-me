package ru.practicum.compilation.model;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class NewCompilationDto {
    @Builder.Default
    List<Integer> events = List.of();
    @Builder.Default
    Boolean pinned = false;
    @NotBlank
    @Size(max = 50)
    String title;
}
