package wods.crossfit.heart.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import wods.crossfit.heart.domain.Heart;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;
import wods.crossfit.heart.domain.dto.HeartDto;
import wods.crossfit.heart.repository.HeartRepository;
import wods.crossfit.member.repository.MemberRepository;
import wods.crossfit.workout.repository.WorkoutRepository;
import wods.crossfit.heart.service.HeartService;

@Service
@RequiredArgsConstructor
public class HeartServiceImpl implements HeartService {

    private final HeartRepository heartRepository;

    private final MemberRepository memberRepository;

    private final WorkoutRepository workoutRepository;

    @Override
    @Transactional
    public void saveHeart(HeartDto.HeartRequest dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new NotFoundException("오늘의 운동을 찾을 수 없습니다."));

        Heart heart = Heart.builder()
                .member(member)
                .workout(workout)
                .build();

        // 오늘의 운동 좋아요 수 증가
        workout.increaseHeartCount();

        heartRepository.save(heart);
    }

    @Override
    @Transactional
    public void deleteHeart(HeartDto.HeartRequest dto) {
        Member member = memberRepository.findById(dto.getMemberId())
                .orElseThrow(() -> new NotFoundException("회원을 찾을 수 없습니다."));

        Workout workout = workoutRepository.findById(dto.getWorkoutId())
                .orElseThrow(() -> new NotFoundException("오늘의 운동을 찾을 수 없습니다."));

        Heart heart = heartRepository.findByMemberAndWorkout(member, workout)
                .orElseThrow(() -> new NotFoundException("해당 좋아요는 없습니다."));

        // 오늘의 운동 좋아요 수 감소
        workout.decreaseHeartCount();

        heartRepository.delete(heart);
    }

    public boolean checkLike(long memberId, long workoutId) {
        return heartRepository.existsByMemberIdAndWorkoutId(memberId, workoutId);
    }

}
