package ru.practicum.ewmserver.compilation.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmserver.compilation.model.EventCompilationConnection;

@Repository
public interface EventCompilationConnectionRepository extends JpaRepository<EventCompilationConnection, Integer> {

}
