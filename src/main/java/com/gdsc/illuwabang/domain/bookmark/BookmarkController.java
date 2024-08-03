package com.gdsc.illuwabang.domain.bookmark;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.RoomService;
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
@RequestMapping("/api/bookmarks")
@RequiredArgsConstructor
public class BookmarkController {

    @Autowired
    UserService userService;

    @Autowired
    RoomService roomService;

    @Autowired
    BookmarkService bookmarkService;

    @ResponseBody
    @GetMapping("/")
    public ResponseEntity<?> getAllBookmarks(Authentication authentication) {
        Long userId = 0L;
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if (exist_user.isPresent()) {
            User user = exist_user.get();
            userId = user.getId();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }
        return ResponseEntity.ok().body(bookmarkService.getUsersBookmarks(userId));
    }

    @ResponseBody
    @PostMapping("/{roomId}")
    public ResponseEntity<?> addBookmark(Authentication authentication, @PathVariable Long roomId) {
        User user;
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if (exist_user.isPresent()) {
            user = exist_user.get();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        Room room = roomService.findRoomById(roomId);

        return ResponseEntity.ok().body(bookmarkService.addBookmark(user, room));
    }

    @ResponseBody
    @DeleteMapping("/{roomId}")
    public ResponseEntity<?> deleteBookmark(Authentication authentication, @PathVariable Long roomId) {

        User user;
        Optional<User> exist_user = userService.findBySub(authentication.getName());
        if (exist_user.isPresent()) {
            user = exist_user.get();
        } else {
            return ResponseEntity.badRequest().body("User not found");
        }

        Room room = roomService.findRoomById(roomId);

        Bookmark bookmark = bookmarkService.findBookmarkByUserAndRoom(user, room);

        return ResponseEntity.ok().body(bookmarkService.deleteBookmark(bookmark));
    }
}
