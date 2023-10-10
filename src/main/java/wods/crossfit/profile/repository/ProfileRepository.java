package wods.crossfit.profile.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.profile.domain.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {

    Optional<Profile> findByMemberId(long id);

    void deleteByMemberId(long id);
}
