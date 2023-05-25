package ru.practicum.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.admin.model.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findByIdIn(List<Integer> ids);
}
