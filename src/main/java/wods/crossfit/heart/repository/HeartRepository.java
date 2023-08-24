package wods.crossfit.heart.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.heart.domain.Heart;
import wods.crossfit.member.domain.Member;
import wods.crossfit.workout.domain.Workout;

public interface HeartRepository extends JpaRepository<Heart, Long> {

    Optional<Heart> findByMemberAndWorkout(Member member, Workout workout);

    boolean existsByMemberIdAndWorkoutId(long memberId, long workoutId);

}
