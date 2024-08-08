package com.gdsc.illuwabang.domain.home;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.recentlyviews.RecentlyViewsRepository;
import com.gdsc.illuwabang.domain.recentlyviews.RecentlyViewsService;
import com.gdsc.illuwabang.domain.recentlyviews.dto.RecentlyViewDto;
import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.room.repository.RoomRepository;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
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
import java.util.Map;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeIntegrationTest {
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

        Room room1 = Room.builder()
                .title("Test Room")
                .content("Test Content")
                .user(user)
                .deposit(1000)
                .rent(500)
                .maintenanceCost(50)
                .options("Option1")
                .roadAddress("서울특별시 종로구")
                .detailAddress("Test detail Address")
                .state(State.AVAILABLE)
                .startDate(LocalDateTime.of(2024, 3, 21, 19, 1))
                .endDate(LocalDateTime.of(2024, 3, 23, 20, 2))
                .floor(3)
                .size(30F)
                .imageUrl(ImageUrl.builder()
                        .thumbnail("test thumbnail")
                        .image1("test image1")
                        .image2("test image2")
                        .image3("test image3")
                        .image4("test image4")
                        .build())
                .createdAt(LocalDateTime.now())
                .buildingInfo("test building info")
                .type(Type.ONE_ROOM)
                .build();


        Room room2 = Room.builder()
                .title("Test Room2")
                .content("Test Content2")
                .user(user)
                .deposit(2000)
                .rent(100)
                .maintenanceCost(10)
                .options("Option2")
                .roadAddress("서울특별시 영등포구")
                .detailAddress("Test2 detail Address")
                .state(State.AVAILABLE)
                .startDate(LocalDateTime.of(2024, 4, 21, 19, 1))
                .endDate(LocalDateTime.of(2024, 4, 23, 20, 2))
                .floor(2)
                .size(20F)
                .imageUrl(ImageUrl.builder()
                        .thumbnail("test2 thumbnail")
                        .image1("test2 image1")
                        .image2("test2 image2")
                        .image3("test2 image3")
                        .image4("test2 image4")
                        .build())
                .createdAt(LocalDateTime.now())
                .buildingInfo("test2 building info")
                .type(Type.ROOMMATE)
                .build();

        savedRoom = roomRepository.save(room1);
        savedRoom = roomRepository.save(room2);
    }

    @Test
    @WithMockUser(value = "1234")
    void 최근본방등록_관심지역등록_홈화면가져오기() throws Exception {

        //최근 본 방 등록
        mockMvc.perform(get("/api/rooms/1")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Room")))
                .andExpect(jsonPath("$.content", is("Test Content")));


        //관심지역 등록
        Map<String, String> input = Map.of("city", "서울특별시 영등포구");
        mockMvc.perform(post("/api/interest-location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(input))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("success"));


        //홈 화면 로딩
        mockMvc.perform(get("/")
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendedRooms[0].roomId", is(2)))
                .andExpect(jsonPath("$.recommendedRooms[0].thumbnail", is("test2 thumbnail")))
                .andExpect(jsonPath("$.recentWatchRooms[0].roomId", is(1)))
                .andExpect(jsonPath("$.recentWatchRooms[0].thumbnail", is("test thumbnail")));

    }
}
