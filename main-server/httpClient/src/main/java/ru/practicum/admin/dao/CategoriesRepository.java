package ru.practicum.admin.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.admin.models.category.Category;


public interface CategoriesRepository extends JpaRepository<Category, Integer> {
}
