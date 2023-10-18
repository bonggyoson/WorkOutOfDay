package wods.crossfit.profileBenchmark.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;

public interface ProfileBenchmarkRepository extends JpaRepository<ProfileBenchmark, Long> {

    List<ProfileBenchmark> findByProfileId(long id);

}
