package ru.practicum.reqest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.model.enums.Status;


public interface RequestRepository extends JpaRepository<Request, Integer> {
    Integer countByEventAndStatus(Integer eventId, Status status);
}
