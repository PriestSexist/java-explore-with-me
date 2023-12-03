package ru.practicum.ewmserver.request.storage;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmserver.request.model.Request;
import ru.practicum.ewmserver.request.model.RequestStatus;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<Request, Integer> {
    boolean existsByRequesterIdAndEventId(int eventId, int userId);

    int countRequestByEventIdAndStatus(int eventId, RequestStatus requestStatus);

    List<Request> getRequestsByRequesterId(int requesterId);

    List<Request> getRequestsByEventId(int eventId);
}
