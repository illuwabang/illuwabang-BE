package com.gdsc.illuwabang.domain.bookmark;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.repository.RoomRepository;
import com.gdsc.illuwabang.domain.room.RoomService;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import com.gdsc.illuwabang.domain.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class BookmarkIntegrationTest {

    Room savedRoom;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Autowired
    private BookmarkService bookmarkService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookmarkRepository bookmarkRepository;

    @BeforeEach
    void setUp() {
        // 테스트 전에 필요한 초기 데이터를 생성합니다.
        // 예: 사용자, 방 생성
        bookmarkRepository.deleteAll();
        roomRepository.deleteAll();
        userRepository.deleteAll();

        User user = User.builder()
                .sub("test-user")
                .name("Test User")
                .build();
        User savedUser = userRepository.save(user);

        Room room = Room.builder()
                .title("Test Room")
                .content("Test Content")
                .user(savedUser)
                .deposit(1000)
                .floor(3)
                .type(Type.ONE_ROOM)
                .rent(500)
                .roadAddress("Test Address")
                .build();
        savedRoom = roomRepository.save(room);
    }

    @Test
    @DisplayName("즐겨찾기 조회 [/api/bookmarks]")
    @WithMockUser(username = "test-user")
    void testGetAllBookmarks() throws Exception {
        User user = userService.findBySub("test-user").orElseThrow();
        Room room = roomService.findRoomById(savedRoom.getId());
        bookmarkService.addBookmark(user, room);

        mockMvc.perform(get("/api/bookmarks/"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].roomId").value(room.getId()))
                .andExpect(jsonPath("$[0].deposit").value(room.getDeposit()))
                .andExpect(jsonPath("$[0].rent").value(room.getRent()));
    }

    @Test
    @DisplayName("즐겨찾기 추가 [/api/bookmarks/{roomId}]")
    @WithMockUser(username = "test-user")
    @Transactional
    void testAddBookmark() throws Exception {
        Room room = roomService.findRoomById(savedRoom.getId());

        mockMvc.perform(post("/api/bookmarks/" + room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));

        User user = userService.findBySub("test-user").orElseThrow();
        Bookmark bookmark = bookmarkRepository.findByUserAndRoom(user, room);

        assertThat(bookmark.getRoom().getTitle()).isEqualTo(room.getTitle());
        assertThat(bookmark.getUser().getName()).isEqualTo(user.getName());
    }

    @Test
    @DisplayName("즐겨찾기 삭제 [/api/bookmarks/{roomId}]")
    @WithMockUser(username = "test-user")
    @Transactional
    void testDeleteBookmark() throws Exception {
        User user = userService.findBySub("test-user").orElseThrow();
        Room room = roomService.findRoomById(savedRoom.getId());
        bookmarkService.addBookmark(user, room);
        Bookmark bookmark = bookmarkService.findBookmarkByUserAndRoom(user, room);

        mockMvc.perform(delete("/api/bookmarks/" + room.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("success"));

        //여기 아쉬움..null일 경우는 많음
        assertThat(bookmarkRepository.findByUserAndRoom(user, room)).isNull();
    }
}
