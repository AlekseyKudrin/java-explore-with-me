package ru.practicum.reqest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.model.enums.Status;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Integer> {
    Integer countByEventAndStatus(Integer eventId, Status status);

    List<Request> findAllByEvent(Integer eventId);

    List<Request> findByEventAndStatus(Integer eventId, Status status);

    List<Request> findALLByRequester(Integer userId);

    Request findByIdAndRequester(Integer requestId, Integer requester);

    Request findByEventAndRequester(Integer eventId, Integer userId);
}