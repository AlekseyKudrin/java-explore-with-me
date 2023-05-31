package ru.practicum.reqest.model;

import lombok.*;
import lombok.experimental.FieldDefaults;
import ru.practicum.reqest.model.enums.Status;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {
    List<Integer> requestIds;
    String status;
}
