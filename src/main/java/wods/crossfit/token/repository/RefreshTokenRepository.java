package wods.crossfit.token.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import wods.crossfit.token.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByMemberId(Long memberId);
    Optional<RefreshToken> findByRefreshToken(String refreshToken);

}
