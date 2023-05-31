package ru.practicum.reqest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.model.enums.Status;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Integer> {
    Integer countByEventAndStatus(Integer eventId, Status status);

    List<Request> findByEventAndAndRequester(Integer eventId, Integer userId);

    @Query("select r from Request r where r.id in :ids")
    List<Request> findRequestForChange(@Param("ids") List<Integer> ids);

    List<Request> findByEventAndStatus(Integer eventId, Status status);

    Request findByRequester(Integer userId);

    Request findByRequesterAndEvent(Integer userId, Integer eventId);
}
