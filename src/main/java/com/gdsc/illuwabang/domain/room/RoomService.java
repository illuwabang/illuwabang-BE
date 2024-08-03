package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.room.dto.*;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.repository.RoomCustomRepository;
import com.gdsc.illuwabang.domain.room.repository.RoomRepository;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomCustomRepository roomCustomRepository;

    @Autowired
    UserRepository userRepository;


    public Room findRoomById(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));
    }

    public List<AllRoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(AllRoomResponseDto::of)
                .collect(Collectors.toList());
    }

    public Room registerRoom(User user, RoomRegisterDto roomInfo) {
        roomInfo.setUser(user);
        roomInfo.setCreatedAt(LocalDateTime.now());
        roomInfo.setState(State.AVAILABLE);
        //임시 좌표 //좌표 설정 필요
        roomInfo.setLatitude(0.0);
        roomInfo.setLongitude(0.0);


        Room room = roomInfo.toEntity();

        return roomRepository.save(room);
    }

    public SelectRoomResponseDto getRoomInfo(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));;

        User user = room.getUser();

        // RoomDto 생성 및 설정
        SelectRoomResponseDto roomDto = SelectRoomResponseDto.of(room, user);

        return roomDto;
    }

    public UpdatedRoomResponseDto updateRoomInfo(Long roomId, RoomRegisterDto inputRoomInfo) {
        Room originalRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        //문자열 층수 => 숫자 층수로 변환
        Integer parsedFloor = 0;
        if (inputRoomInfo.getFloor() != null) {
            if (inputRoomInfo.getFloor().equals("basement")) {
                parsedFloor = -1;
            } else if (inputRoomInfo.getFloor().equals("rooftop")) {
                parsedFloor = 100;
            } else {
                parsedFloor = Integer.parseInt(inputRoomInfo.getFloor());
            }
        }

        Room newRoom = Room.builder()
                .id(originalRoom.getId())
                .user(originalRoom.getUser())
                .title(inputRoomInfo.getTitle() == null ? originalRoom.getTitle() : inputRoomInfo.getTitle())
                .content(inputRoomInfo.getContent() == null ? originalRoom.getContent() : inputRoomInfo.getContent())
                .type(inputRoomInfo.getType() == null ? originalRoom.getType() : inputRoomInfo.getType())
                .deposit(inputRoomInfo.getDeposit() == null ? originalRoom.getDeposit() : inputRoomInfo.getDeposit())
                .rent(inputRoomInfo.getRent() == null ? originalRoom.getRent() : inputRoomInfo.getRent())
                .maintenanceCost(inputRoomInfo.getMaintenanceCost() == null ? originalRoom.getMaintenanceCost() : inputRoomInfo.getMaintenanceCost())
                .options(inputRoomInfo.getOptions() == null ? originalRoom.getOptions() : inputRoomInfo.getOptions())
                .floor(inputRoomInfo.getFloor() == null ? originalRoom.getFloor() : parsedFloor)
                .buildingInfo(inputRoomInfo.getBuildingInfo() == null ? originalRoom.getBuildingInfo() : inputRoomInfo.getBuildingInfo())
                .size(inputRoomInfo.getSize() == null ? originalRoom.getSize() : inputRoomInfo.getSize())
                .imageUrl(inputRoomInfo.getImageUrl() == null ? originalRoom.getImageUrl() : inputRoomInfo.getImageUrl())
                .roadAddress(inputRoomInfo.getRoadAddress() == null ? originalRoom.getRoadAddress() : inputRoomInfo.getRoadAddress())
                .detailAddress(inputRoomInfo.getDetailAddress() == null ? originalRoom.getDetailAddress() : inputRoomInfo.getDetailAddress())
                .state(inputRoomInfo.getState() == null ? originalRoom.getState() : inputRoomInfo.getState())
                .latitude(inputRoomInfo.getLatitude() == null ? originalRoom.getLatitude() : inputRoomInfo.getLatitude())
                .longitude(inputRoomInfo.getLongitude() == null ? originalRoom.getLongitude() : inputRoomInfo.getLongitude())
                .startDate(inputRoomInfo.getStartDate() == null ? originalRoom.getStartDate() : inputRoomInfo.getStartDate())
                .endDate(inputRoomInfo.getEndDate() == null ? originalRoom.getEndDate() : inputRoomInfo.getEndDate())
                .createdAt(originalRoom.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        Room savedRoom = roomRepository.save(newRoom);

        return UpdatedRoomResponseDto.of(savedRoom);
    }



    public Map deleteRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        roomRepository.delete(room);

        Map<String, String> stringStringMap = new HashMap<>();
        stringStringMap.put("status", "success");
        return stringStringMap;
    }

    public List<AllRoomResponseDto> findRooms(RoomSearchCriteria criteria) {
        List<Room> rooms = roomCustomRepository.findRooms(criteria);
        List<AllRoomResponseDto> allRoomResponseDtos;
        allRoomResponseDtos = rooms.stream()
                .map(AllRoomResponseDto::of)
                .collect(Collectors.toList());
        return allRoomResponseDtos;
    }
}
