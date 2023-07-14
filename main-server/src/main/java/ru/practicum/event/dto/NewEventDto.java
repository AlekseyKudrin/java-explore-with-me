package ru.practicum.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.location.model.Location;
import ru.practicum.util.annotation.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewEventDto {
    @NotBlank
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Integer category;
    @NotBlank
    @Size(min = 20, max = 7000)
    String description;
    @NotNull
    @DateTime
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;
    @NotNull
    @Valid
    Location location;
    @Builder.Default
    Boolean paid = false;
    @Builder.Default
    Integer participantLimit = 0;
    @Builder.Default
    Boolean requestModeration = true;
    @Size(min = 3, max = 120)
    String title;
}
