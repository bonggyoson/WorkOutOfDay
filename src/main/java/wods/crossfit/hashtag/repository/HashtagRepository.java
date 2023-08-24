package wods.crossfit.hashtag.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import wods.crossfit.hashtag.domain.Hashtag;

public interface HashtagRepository extends JpaRepository<Hashtag, Long> {

    @Query("select distinct h.content from Hashtag h")
    List<String> findHashtags();

    List<Hashtag> findByWorkoutId(long id);
}
