package ru.practicum.ewmserver.compilation.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmserver.compilation.model.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Integer> {
    boolean existsByTitle(String title);

    Page<Compilation> getAllByPinned(boolean pinned, Pageable pageable);
}
