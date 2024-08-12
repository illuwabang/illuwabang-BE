package com.gdsc.illuwabang.domain.message;

import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class MessageController {
    private final UserService userService;
    private final MessageService messageService;

    @GetMapping("/api/messages")
    public ResponseEntity<?> view_all_message(Authentication authentication){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isPresent()){
            User user = exist_user.get();
            MessageRoomListDto messageRoomListDto = new MessageRoomListDto(messageService.viewAllByMessageRoom(user));
            System.out.println(messageRoomListDto);
            return ResponseEntity.ok(messageRoomListDto);
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @PostMapping("/api/message/send/{roomId}")
    public ResponseEntity<?> sendRoomMessage(Authentication authentication, @PathVariable Long roomId, @RequestBody ContentDto contentDto){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        System.out.println("hello");
        if(exist_user.isPresent()){
            User user = exist_user.get();
            System.out.println(roomId);
            messageService.sendRoomMessage(user,contentDto.getContent(),roomId);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid request");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @PostMapping("/api/message/send/{roomId}/{otherId}")
    public ResponseEntity<?> sendMessage(Authentication authentication, @PathVariable Long roomId, @PathVariable Long otherId, @RequestBody ContentDto contentDto){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isPresent()){
            User user = exist_user.get();
            System.out.println(roomId);
            messageService.sendMessage(user,contentDto.getContent(),roomId, otherId);
            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            return ResponseEntity.ok(response);
        }else{
            Map<String, String> response = new HashMap<>();
            response.put("error", "Invalid request");
            return ResponseEntity.badRequest().body(response);
        }
    }
    @GetMapping("/api/messages/{roomId}/{otherId}")
    public ResponseEntity<?> viewMessage(Authentication authentication, @PathVariable Long roomId, @PathVariable Long otherId){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isPresent()) {
            User user = exist_user.get();
            OneMessageRoomDto oneMessageRoomDto = messageService.viewMessageRoom(user, roomId, otherId);
            if(oneMessageRoomDto != null)
                return ResponseEntity.ok(oneMessageRoomDto);
            else{
                return ResponseEntity.badRequest().body("fail");
            }
        }
        else{
            return ResponseEntity.badRequest().body("fail");
        }
    }

    @DeleteMapping("/api/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(Authentication authentication, @PathVariable Long messageId){
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if(exist_user.isPresent()) {
            User user = exist_user.get();
            messageService.deleteMessage(user, messageId);
            return ResponseEntity.ok("success");
        }else
            return ResponseEntity.badRequest().body("fail");
    }


}

/*

/*
INSERT INTO room (title, user_id, content, type, deposit, rent, maintenance_cost, options, floor, building_info, size, image_url, road_address, detail_address, latitude, longitude, start_date, end_date, created_at, updated_at)
VALUES ('Default Title', 1, 'Default Content', 'Default Type', 0, 0, 0, 'Default Options', 'Default Floor', 'Default Building Info', 0.0, null, 'Default Road Address', 'Default Detail Address', 0.0, 0.0, '2024-01-01 00:00:00', '2024-01-01 00:00:00', '2024-01-01 00:00:00', '2024-01-01 00:00:00');
*/

