package ru.practicum.ewmserver.compilation.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.practicum.ewmserver.event.model.Event;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "event_compilation_connection")
public class EventCompilationConnection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @JoinColumn(name = "event_id")
    private int event_id;
    @JoinColumn(name = "compilation_id")
    private int compilation_id;
}
