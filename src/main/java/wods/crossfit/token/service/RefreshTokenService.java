package wods.crossfit.token.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import wods.crossfit.token.domain.RefreshToken;
import wods.crossfit.token.repository.RefreshTokenRepository;

@RequiredArgsConstructor
@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;

    public RefreshToken findByRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(() -> new IllegalArgumentException("UnExpected token"));
    }

}
