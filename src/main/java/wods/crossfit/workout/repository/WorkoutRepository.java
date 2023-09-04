package wods.crossfit.workout.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import wods.crossfit.workout.domain.Workout;

public interface WorkoutRepository extends JpaRepository<Workout, Long> {

    List<Workout> findAll();

    Page<Workout> findByTitleContaining(String keyword, Pageable pageable);

    Page<Workout> findByContentContaining(String keyword, Pageable pageable);

    Page<Workout> findByMember_NameContaining(String keyword, Pageable pageable);

    @Query("select w from Workout w join WorkoutHashtag wh on w.id = wh.workout.id join Hashtag h on wh.hashtag.id = h.id where h.content = :hashtag")
    Page<Workout> findByHashtags(@Param("hashtag") String hashtag, Pageable pageable);

    Page<Workout> findAllByMemberId(Pageable pageable, long id);

}
