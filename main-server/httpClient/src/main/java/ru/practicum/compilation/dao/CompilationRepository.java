package ru.practicum.compilation.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.compilation.model.Compilation;


public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
}