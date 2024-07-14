package com.gdsc.illuwabang.domain.user.google;

import com.gdsc.illuwabang.auth.jwt.JwtTokenProvider;
import com.gdsc.illuwabang.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final UserRepository userRepository;
    @Value("${jwt.secret}")
    private String secretKey;

    @Value("${refresh.secret}")
    private String secretKet2;

    private Long expiredMs = 1000 * 60 * 60L;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        log.info("loadUser called with userRequest: {}", userRequest);
        OAuth2User oAuth2User = super.loadUser(userRequest);
        log.info("OAuth2User loaded: {}", oAuth2User.getAttributes());

        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;

        if(provider.equals("google")){
            log.info("구글 로그인");
            oAuth2UserInfo = new GoogleUserDetails(oAuth2User.getAttributes());
        }

        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String name = oAuth2UserInfo.getName();

        User user = userRepository.findByEmail(email);
        System.out.println(user);
        if (user == null) {
            user = User.builder()
                    .sub(providerId)
                    .name(name)
                    .email(email)
                    .provider(Provider.google)
                    .image((String) oAuth2User.getAttributes().get("picture"))
                    .build();
            userRepository.save(user);
            System.out.println(user.getName());
        }
        //토큰 생성
        TokenDto tokenDto = new TokenDto();
        tokenDto.setAccessToken(JwtTokenProvider.createJwt(user, secretKey, expiredMs));
        tokenDto.setRefreshToken(JwtTokenProvider.createRefreshToken(user, secretKet2, expiredMs));
        tokenDto.setGrantType("Bearer");
        tokenDto.setExpiresIn(expiredMs);
        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getName(), user.getEmail(), tokenDto);
        return new CustomOauth2UserDetails(user, oAuth2User.getAttributes(),userResponseDto);
    }
}
