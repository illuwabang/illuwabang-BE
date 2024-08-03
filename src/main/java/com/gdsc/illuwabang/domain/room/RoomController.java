package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.recentlyviews.RecentlyViewsService;
import com.gdsc.illuwabang.domain.room.dto.*;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Autowired
    RecentlyViewsService recentlyViewsService;


    @ResponseBody
    @GetMapping("/allRooms")
    public ResponseEntity<?> getAllRooms() {
        return ResponseEntity.ok().body(roomService.getAllRooms());
    }

    @ResponseBody
    @GetMapping("/search")
    public List<AllRoomResponseDto> getRooms(@RequestParam(required = false) Type type,
                                             @RequestParam(required = false) Integer depositMin,
                                             @RequestParam(required = false) Integer depositMax,
                                             @RequestParam(required = false) Integer rentMin,
                                             @RequestParam(required = false) Integer rentMax,
                                             @RequestParam(required = false) Float sizeMin,
                                             @RequestParam(required = false) Float sizeMax,
                                             @RequestParam(required = false) Boolean isBasement,
                                             @RequestParam(required = false) Boolean isGround,
                                             @RequestParam(required = false) Boolean isRooftop,
                                             @RequestParam(required = false) List<String> options) {
        RoomSearchCriteria criteria = RoomSearchCriteria.builder()
                .type(type)
                .depositMin(depositMin)
                .depositMax(depositMax)
                .rentMin(rentMin)
                .rentMax(rentMax)
                .sizeMin(sizeMin)
                .sizeMax(sizeMax)
                .isBasement(isBasement)
                .isGround(isGround)
                .isRooftop(isRooftop)
                .options(options)
                .build();

        return roomService.findRooms(criteria);
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerRoom(Authentication authentication, @RequestBody RoomRegisterDto roomInfo) {
        User user;
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if (exist_user.isPresent()) {
            user = exist_user.get();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        return ResponseEntity.ok().body(roomService.registerRoom(user, roomInfo));
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<?> getRoomInfo(Authentication authentication, @PathVariable Long roomId) {
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        User user;
        Room room;
        if (exist_user.isPresent()) {
            user = exist_user.get();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        recentlyViewsService.saveRecentlyViews(user, roomId);
        SelectRoomResponseDto roomInfo = roomService.getRoomInfo(roomId);
        return ResponseEntity.ok().body(roomInfo);
    }

    @PatchMapping("/{roomId}")
    public ResponseEntity<UpdatedRoomResponseDto> updateRoomInfo(@PathVariable Long roomId, @RequestBody RoomRegisterDto roomInfo) {
        return ResponseEntity.ok().body(roomService.updateRoomInfo(roomId, roomInfo));
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<Object> deleteRoom(@PathVariable Long roomId) {
        return ResponseEntity.ok().body(roomService.deleteRoom(roomId));
    }
}
