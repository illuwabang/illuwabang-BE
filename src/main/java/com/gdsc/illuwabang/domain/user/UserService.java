package com.gdsc.illuwabang.domain.user;

import com.gdsc.illuwabang.auth.jwt.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${refresh.secret}")
    private String secretKet2;

    private final UserRepository userRepository;
    private Long expiredMs = 1000 * 60 * 60L;


    public TokenDto publish_token(User user) {
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(JwtTokenProvider.createJwt(user, secretKey, expiredMs));
        tokenDto.setRefreshToken(JwtTokenProvider.createRefreshToken(user, secretKet2, expiredMs));
        tokenDto.setGrantType("Bearer");
        tokenDto.setExpiresIn(expiredMs);
        return tokenDto;
    }

    public Optional<User> findBySub(String sub){
        return Optional.ofNullable(userRepository.findBySub(sub));
    }
}
