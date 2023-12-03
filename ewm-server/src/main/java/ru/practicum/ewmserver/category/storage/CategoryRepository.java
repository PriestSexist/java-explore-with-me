package ru.practicum.ewmserver.category.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.ewmserver.category.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByName(String name);
}
