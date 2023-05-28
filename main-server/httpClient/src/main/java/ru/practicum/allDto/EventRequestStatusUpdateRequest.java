package ru.practicum.allDto;

import ru.practicum.reqest.model.enums.Status;

import java.util.List;

public class EventRequestStatusUpdateRequest {
    List<Integer> requestIds;
    Status status;
}
