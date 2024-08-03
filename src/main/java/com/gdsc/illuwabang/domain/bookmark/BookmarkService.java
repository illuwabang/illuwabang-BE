package com.gdsc.illuwabang.domain.bookmark;

import com.gdsc.illuwabang.domain.bookmark.dto.BookmarkedRoomDto;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BookmarkService {

    @Autowired
    BookmarkRepository bookmarkRepository;

    public List<BookmarkedRoomDto> getUsersBookmarks(Long userId) {
        List<Room> allByUserId = bookmarkRepository.findAllByUserId(userId);

        BookmarkedRoomDto bookmarkedRoomDto = new BookmarkedRoomDto();
        return allByUserId.stream()
                .map(bookmarkedRoomDto::of)
                .toList();
    }

    public Map addBookmark(User user, Room room) {

        bookmarkRepository.save(Bookmark.builder()
                .user(user)
                .room(room)
                .build());

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("status", "success");
        return stringStringMap;
    }

    public Bookmark findBookmarkByUserAndRoom(User user, Room room) {
        return bookmarkRepository.findByUserAndRoom(user, room);
    }

    public Object deleteBookmark(Bookmark bookmark) {
        bookmarkRepository.delete(bookmark);
        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("status", "success");
        return stringStringMap;
    }
}
