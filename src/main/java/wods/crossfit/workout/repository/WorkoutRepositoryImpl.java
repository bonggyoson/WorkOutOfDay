package wods.crossfit.workout.repository;

import static wods.crossfit.workout.domain.QWorkout.*;

import com.querydsl.jpa.impl.JPAQueryFactory;
import wods.crossfit.workout.domain.QWorkout;
import wods.crossfit.workout.domain.Workout;

public class WorkoutRepositoryImpl implements WorkoutRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    public WorkoutRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    /**
     * 오늘의 운동 최고 조회수 단건 조회
     */
    @Override
    public Workout findViewTopWorkout() {
        return jpaQueryFactory.selectFrom(workout)
                .orderBy(workout.views.desc())
                .limit(1)
                .fetchOne();
    }
}
