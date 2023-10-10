package wods.crossfit.workout.service.impl;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wods.crossfit.hashtag.domain.Hashtag;
import wods.crossfit.hashtag.service.HashtagService;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutRequest;
import wods.crossfit.workout.domain.dto.WorkoutDto.WorkoutResponse;
import wods.crossfit.workout.repository.WorkoutRepository;
import wods.crossfit.workout.service.WorkoutService;
import wods.crossfit.workoutHashtag.service.WorkoutHashtagService;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WorkoutServiceImpl implements WorkoutService {

    private final WorkoutRepository workoutRepository;

    private final MemberRepository memberRepository;

    private final HashtagService hashtagService;

    private final WorkoutHashtagService workoutHashtagService;

    @Override
    public Page<WorkoutResponse> getWorkouts(Pageable pageable, String keyword,
            String type, String sort, String hashtag) {
        if (sort == null) {
            sort = "id";
        }
        // page 는 index 처럼 0부터 시작
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 9, Sort.by(sort).descending());

        if (type != null) {
            switch (type) {
                case "title":
                    return workoutRepository.findByTitleContaining(keyword, pageable)
                            .map(WorkoutResponse::of);
                case "content":
                    return workoutRepository.findByContentContaining(keyword, pageable)
                            .map(WorkoutResponse::of);
                case "writer":
                    return workoutRepository.findByMember_NameContaining(keyword, pageable)
                            .map(WorkoutResponse::of);
            }
        }

        if (!Objects.equals(hashtag, "")) {
            return workoutRepository.findByHashtags(hashtag, pageable).map(WorkoutResponse::of);
        }

        return workoutRepository.findAll(pageable).map(WorkoutResponse::of);
    }

    @Override
    @Transactional
    public WorkoutResponse getWorkout(long id) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 게시글은 존재하지 않습니다."));

        // 조회수 업데이트
        workout.updateViewCount(workout.getViews());

        return WorkoutResponse.of(workout);
    }

    @Override
    @Transactional
    public long saveWorkout(WorkoutRequest dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not Found Id : [%S]", dto.getMemberId())));

        // 오늘의 운동 저장
        long savedWorkoutId = workoutRepository.save(dto.toEntity(member)).getId();

        // 해시 태그 저장
        List<Hashtag> hashtags = hashtagService.saveHashtag(dto.getHashtag());

        // 오늘의 운동 - 해시 태그 저장
        workoutHashtagService.saveWorkoutHashtag(savedWorkoutId, hashtags);

        return savedWorkoutId;
    }

    @Override
    @Transactional
    public void updateWorkout(long id, WorkoutRequest dto) {
        Workout workout = workoutRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("Not Found Id : [%S]", dto.getMemberId())));

        // 오늘의 운동 수정
        workout.update(dto.getTitle(), dto.getContent());

        // 해시 태그 수정
        List<Hashtag> hashtags = hashtagService.updateHashtag(workout.getId(), dto.getHashtag());

//        List<WorkoutHashtagResponse> hashtag = workoutHashtagRepository.findByWorkoutId(
//                workout.getId());
//        for (WorkoutHashtagResponse workoutHashtagResponse : hashtag) {
//            workoutHashtagRepository.deleteByWorkoutId(workout.getId());
//            hashtagRepository.deleteById(workoutHashtagResponse.getHashtag().getId());
//        }

//        hashtagService.saveHashtag(dto.getHashtag(), workout.getId());

    }

    @Override
    @Transactional
    public void deleteWorkout(long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public Page<WorkoutResponse> findAllByMemberId(Pageable pageable, long id) {
        int page = (pageable.getPageNumber() == 0) ? 0 : (pageable.getPageNumber() - 1);
        pageable = PageRequest.of(page, 9, Sort.by("id").descending());

        return workoutRepository.findAllByMemberId(pageable, id)
                .map(WorkoutResponse::of);
    }

    @Override
    public List<WorkoutResponse> findWorkouts() {
        return workoutRepository.findAll().stream().map(WorkoutResponse::of)
                .collect(Collectors.toList());
    }

    @Override
    public WorkoutResponse findHotWorkout() {
        return WorkoutResponse.of(workoutRepository.findViewTopWorkout());
    }
}
