package ru.practicum.ewmserver.event.storage;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.practicum.ewmserver.event.model.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    Page<Comment> getAllByAuthorId(int authorId, Pageable page);
}
