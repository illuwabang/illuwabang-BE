package com.gdsc.illuwabang.domain.home;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@Slf4j
@RestController
@RequiredArgsConstructor
public class HomeController {

    @Autowired
    UserService userService;

    @Autowired
    HomeService homeService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<?> home(Authentication authentication) {
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        User user;
        if (exist_user.isPresent()) {
            user = exist_user.get();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok().body(homeService.getRecommendInfo(user));
    }
}
