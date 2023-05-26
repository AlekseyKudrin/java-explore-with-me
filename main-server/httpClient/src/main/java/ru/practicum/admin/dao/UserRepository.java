package ru.practicum.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.models.user.User;

import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByIdIn(List<Integer> ids);
}
