package com.gdsc.illuwabang.domain.user.kakao;

import com.gdsc.illuwabang.domain.user.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequiredArgsConstructor
public class KakaoController {
    private final KakaoService kakaoService;
    private final UserRepository userRepository;
    private final UserService userService;
    @GetMapping("api/login/oauth2/kakao")
    public ResponseEntity<?> callback(@RequestParam("code") String code) {
        //코드로 토큰 받음
        String accessToken = kakaoService.getAccessTokenFromKakao(code);
        //토큰을 통해 유저정보 가져옴 or 첫 가입 계정이면 저장
        KakaoUserInfoResponseDto userInfo = kakaoService.getUserInfo(accessToken);
        User user = userRepository.findBySub(userInfo.getId());
        //서버에서 자체적인 jwt토큰 발행
        TokenDto tokenDto = userService.publish_token(user);
        UserResponseDto userResponseDto = new UserResponseDto(user.getId(), user.getName(), user.getEmail(), tokenDto);
        return ResponseEntity.ok(userResponseDto);
    }
}
