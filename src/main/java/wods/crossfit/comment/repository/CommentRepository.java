package wods.crossfit.comment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.comment.domain.Comment;
import wods.crossfit.comment.domain.dto.CommentDto.CommentResponse;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<CommentResponse> findAllByMemberId(Pageable pageable, long id);
}
