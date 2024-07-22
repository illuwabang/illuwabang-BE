package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.room.dto.RoomRegisterDto;
import com.gdsc.illuwabang.domain.room.dto.SelectRoomResponseDto;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api/rooms")
@RequiredArgsConstructor
public class RoomController {

    @Autowired
    UserService userService;
    @Autowired
    RoomService roomService;


    @ResponseBody
    @GetMapping("/allRooms")
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok().body(roomService.getAllRooms());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerRoom(Authentication authentication, @RequestBody RoomRegisterDto roomInfo) {
        Long userId = 0L;
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if (exist_user.isPresent()) {
            User user = exist_user.get();
            userId = user.getId();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok().body(roomService.registerRoom(userId, roomInfo));
    }
}
