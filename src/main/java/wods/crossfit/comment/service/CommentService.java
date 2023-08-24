package wods.crossfit.comment.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import wods.crossfit.comment.domain.dto.CommentDto.CommentRequest;
import wods.crossfit.comment.domain.dto.CommentDto.CommentResponse;

public interface CommentService {

    void saveComment(CommentRequest dto);

    void updateComment(long id, CommentRequest dto);

    void deleteComment(long id);

    Page<CommentResponse> getCommentsByMemberId(Pageable pageable, long id);
}
