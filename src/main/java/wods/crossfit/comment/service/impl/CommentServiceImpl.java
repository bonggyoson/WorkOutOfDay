package wods.crossfit.comment.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.comment.domain.Comment;
import wods.crossfit.comment.domain.dto.CommentDto.CommentRequest;
import wods.crossfit.comment.domain.dto.CommentDto.CommentResponse;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.comment.repository.CommentRepository;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.workout.repository.WorkoutRepository;
import wods.crossfit.comment.service.CommentService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    private final WorkoutRepository workoutRepository;

    private final MemberRepository memberRepository;

    @Override
    @Transactional
    public void saveComment(CommentRequest dto) {

        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new NotFoundException("해당하는 오늘의 운동이 없습니다."));

        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException("해당 회원은 존재하지 않습니다."));

        workout.increaseCommentCount();

        commentRepository.save(dto.toEntity(workout, member));
    }

    @Override
    public Page<CommentResponse> getCommentsByMemberId(Pageable pageable, long id) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 5, Sort.by("id").descending());

        return commentRepository.findAllByMemberId(pageable, id);
    }

    @Override
    @Transactional
    public void updateComment(long id, CommentRequest dto) {
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당하는 댓글이 없습니다."));

        comment.update(dto.getContent());
    }

    @Override
    @Transactional
    public void deleteComment(long id) {

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("해당하는 댓글이 없습니다."));

        Workout workout = workoutRepository.findById(comment.getWorkout().getId())
                .orElseThrow(() -> new NotFoundException("해당하는 오늘의 운동이 없습니다."));

        workout.decreaseCommentCount();

        commentRepository.deleteById(id);
    }
}
