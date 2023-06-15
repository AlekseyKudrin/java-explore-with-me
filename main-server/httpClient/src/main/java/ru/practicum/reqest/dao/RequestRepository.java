package ru.practicum.reqest.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.reqest.model.Request;
import ru.practicum.reqest.model.enums.Status;

import java.util.List;


public interface RequestRepository extends JpaRepository<Request, Integer> {
    Integer countByEventAndStatus(Integer eventId, Status status);

    List<Request> findAllByEvent(Integer eventId);

    @Query("select r from Request r where r.id in :ids")
    List<Request> findRequestForChange(@Param("ids") List<Integer> ids);

    List<Request> findByEventAndStatus(Integer eventId, Status status);

    List<Request> findALLByRequester(Integer userId);

    @Modifying
    @Query(value = "update REQUESTS " +
            "set STATUS = 'CANCELED' where ID=?1 " +
            "and REQUESTER=?2", nativeQuery = true)
    void updateCancelingParticipate(Integer requestId, Integer requester);

    Request findByEventAndRequester(Integer eventId, Integer userId);
}