package ru.practicum.rating.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.rating.model.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long> {

}
