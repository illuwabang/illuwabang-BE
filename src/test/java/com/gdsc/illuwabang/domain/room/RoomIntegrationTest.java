package com.gdsc.illuwabang.domain.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.room.dto.RoomRegisterDto;
import com.gdsc.illuwabang.domain.user.Provider;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import com.gdsc.illuwabang.domain.user.UserService;
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

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class RoomIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomService roomService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;


    @BeforeEach
    public void setup() {
        roomRepository.deleteAll();
        userRepository.deleteAll();

        // 테스트 사용자 추가
        User testUser = User.builder()
                .sub("1234")
                .name("testUser")
                .email("test@test2.com")
                .provider(Provider.google)
                .build();
        userRepository.save(testUser);
    }


    @Test
    @DisplayName("방 등록 및 조회 [/api/rooms/register] [/api/allRooms]")
    @WithMockUser(value = "1234")
    public void testRegisterAndRetrieveRoom() throws Exception {
        // 방 등록
        RoomRegisterDto roomRegisterDto = new RoomRegisterDto();
        roomRegisterDto.setUserId(1L);
        roomRegisterDto.setTitle("Test Room");
        roomRegisterDto.setContent("Test Content");
        roomRegisterDto.setType(Type.ONE_ROOM);
        roomRegisterDto.setDeposit(1000);
        roomRegisterDto.setRent(500);
        roomRegisterDto.setMaintenanceCost(50);
        roomRegisterDto.setOptions("Option1");
        roomRegisterDto.setRoadAddress("123 Main St");
        roomRegisterDto.setDetailAddress("Apt 12");
        roomRegisterDto.setState(State.AVAILABLE);
        roomRegisterDto.setStartDate(LocalDateTime.of(2024,3,21,19, 1));
        roomRegisterDto.setEndDate(LocalDateTime.of(2024,3,23,20, 2));
        roomRegisterDto.setFloor("2nd");
        roomRegisterDto.setCreatedAt(LocalDateTime.now());
        roomRegisterDto.setUpdatedAt(LocalDateTime.now());
        roomRegisterDto.setBuildingInfo("Building Info");
        roomRegisterDto.setSize(45.0f);

        mockMvc.perform(post("/api/rooms/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRegisterDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Test Room")));

        // 방 정보 가져오기
        mockMvc.perform(get("/api/rooms/allRooms"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].type", is("ONE_ROOM")))
                .andExpect(jsonPath("$[0].deposit", is(1000)))
                .andExpect(jsonPath("$[0].rent", is(500)))
                .andExpect(jsonPath("$[0].roadAddress", is("123 Main St")))
                .andExpect(jsonPath("$[0].floor", is("2nd")))
                .andExpect(jsonPath("$[0].startDate", is("2024-03-21T19:01")))
                .andExpect(jsonPath("$[0].endDate", is("2024-03-23T20:02")))
                .andExpect(jsonPath("$[0].state", is("AVAILABLE")));


        roomRegisterDto = new RoomRegisterDto();
        roomRegisterDto.setUserId(1L);
        roomRegisterDto.setTitle("Change Room");
        roomRegisterDto.setContent("Change Content");
        roomRegisterDto.setState(State.SOLD);


        mockMvc.perform(patch("/api/rooms/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(roomRegisterDto))
                        .with(csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Change Room")))
                .andExpect(jsonPath("$.content", is("Change Content")))
                .andExpect(jsonPath("$.state", is("SOLD")));


    }
}