package wods.crossfit.profileBenchmark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.profileBenchmark.domain.ProfileBenchmark;

public interface ProfileBenchmarkRepository extends JpaRepository<ProfileBenchmark, Long> {

}
