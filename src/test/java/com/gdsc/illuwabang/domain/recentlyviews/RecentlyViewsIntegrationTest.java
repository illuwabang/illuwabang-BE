package com.gdsc.illuwabang.domain.recentlyviews;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.recentlyviews.dto.RecentlyViewDto;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.room.repository.RoomRepository;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RecentlyViewsIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RecentlyViewsService recentlyViewsService;

    @Autowired
    private RecentlyViewsRepository recentlyViewRepository;


    Room savedRoom;
    User user;
    @BeforeEach
    void setup() {
        roomRepository.deleteAll();
        userRepository.deleteAll();

        User user = User.builder()
                .sub("1234")
                .name("Test User")
                .build();
        this.user = userRepository.save(user);

        Room room = Room.builder()
                .title("Test Room")
                .content("Test Content")
                .user(user)
                .deposit(1000)
                .rent(500)
                .maintenanceCost(50)
                .options("Option1")
                .roadAddress("Test Address")
                .detailAddress("Test detail Address")
                .state(State.AVAILABLE)
                .startDate(LocalDateTime.of(2024, 3, 21, 19, 1))
                .endDate(LocalDateTime.of(2024, 3, 23, 20, 2))
                .floor(3)
                .createdAt(LocalDateTime.now())
                .buildingInfo("test building info")
                .type(Type.ONE_ROOM)
                .build();

        savedRoom = roomRepository.save(room);
    }

    @Test
    @DisplayName("최근 본 방 조회")
    @WithMockUser(value = "1234")
    void recentlyViewsRegisterTest() throws Exception {
        mockMvc.perform(get("/api/rooms/1")
                .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Room")))
                .andExpect(jsonPath("$.content", is("Test Content")));


        List<RecentlyViewDto> views = recentlyViewsService.getUsersRecentlyViews(user);
        assertThat(views.size()).isEqualTo(1);
        assertThat(views.get(0).getRoomId()).isEqualTo(1);

    }

}
