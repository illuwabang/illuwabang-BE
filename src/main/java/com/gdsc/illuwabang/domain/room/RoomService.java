package com.gdsc.illuwabang.domain.room;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gdsc.illuwabang.domain.room.dto.RoomRegisterDto;
import com.gdsc.illuwabang.domain.room.dto.RoomResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    RoomRepository roomRepository;


    public List<RoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private RoomResponseDto convertToDto(Room room) {
        RoomResponseDto roomResponseDto = new RoomResponseDto();

        String thumbnail = "";
        if (room.getImageUrl() != null) {
            thumbnail = room.getImageUrl().getThumbnail();
        } else {
            thumbnail = "room_default.png";
        }

        roomResponseDto.setRoomId(room.getId());
        roomResponseDto.setType(room.getType());
        roomResponseDto.setDeposit(room.getDeposit());
        roomResponseDto.setRent(room.getRent());
        roomResponseDto.setRoadAddress(room.getRoadAddress());
        roomResponseDto.setFloor(room.getFloor());
        roomResponseDto.setStartDate(room.getStartDate().toString());
        roomResponseDto.setEndDate(room.getEndDate().toString());
        roomResponseDto.setState(room.getState());
        roomResponseDto.setLatitude(room.getLatitude());
        roomResponseDto.setLongitude(room.getLongitude());

        return roomResponseDto;
    }

    public Object registerRoom(Long userId, RoomRegisterDto roomInfo) {
        roomInfo.setUserId(userId);
        roomInfo.setCreatedAt(LocalDateTime.now());

        Room room = roomInfo.toEntity();

        return roomRepository.save(room);
    }
}
