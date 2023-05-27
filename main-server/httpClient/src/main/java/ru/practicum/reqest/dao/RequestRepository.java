package ru.practicum.reqest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.reqest.model.Request;


public interface RequestRepository extends JpaRepository<Request, Integer> {
}
