package ru.practicum.event.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.location.model.Location;
import ru.practicum.util.DateTime;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@RequiredArgsConstructor
public class NewEventDto {
    @Size(min = 20, max = 2000)
    String annotation;
    @NotNull
    Integer category;
    @Size(min = 20, max = 7000)
    String description;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTime
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
