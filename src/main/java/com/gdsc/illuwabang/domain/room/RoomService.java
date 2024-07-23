package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.room.dto.RoomRegisterDto;
import com.gdsc.illuwabang.domain.room.dto.AllRoomResponseDto;
import com.gdsc.illuwabang.domain.room.dto.SelectRoomResponseDto;
import com.gdsc.illuwabang.domain.user.User;
import com.gdsc.illuwabang.domain.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class RoomService {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    UserRepository userRepository;


    public List<AllRoomResponseDto> getAllRooms() {
        return roomRepository.findAll().stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private AllRoomResponseDto convertToDto(Room room) {
        AllRoomResponseDto allRoomResponseDto = new AllRoomResponseDto();

        String thumbnail = "";
        if (room.getImageUrl() != null) {
            thumbnail = room.getImageUrl().getThumbnail();
        } else {
            thumbnail = "room_default.png";
        }

        allRoomResponseDto.setRoomId(room.getId());
        allRoomResponseDto.setType(room.getType());
        allRoomResponseDto.setDeposit(room.getDeposit());
        allRoomResponseDto.setRent(room.getRent());
        allRoomResponseDto.setRoadAddress(room.getRoadAddress());
        allRoomResponseDto.setFloor(room.getFloor());
        allRoomResponseDto.setStartDate(room.getStartDate().toString());
        allRoomResponseDto.setEndDate(room.getEndDate().toString());
        allRoomResponseDto.setState(room.getState());
        allRoomResponseDto.setLatitude(room.getLatitude());
        allRoomResponseDto.setLongitude(room.getLongitude());

        return allRoomResponseDto;
    }

    public Object registerRoom(Long userId, RoomRegisterDto roomInfo) {
        roomInfo.setUserId(userId);
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

        User user = userRepository.findById(room.getUserId())
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + room.getUserId()));

        // RoomDto 생성 및 설정
        SelectRoomResponseDto roomDto = new SelectRoomResponseDto();
        roomDto.setRoomId(room.getId());
        roomDto.setTitle(room.getTitle());
        roomDto.setContent(room.getContent());
        roomDto.setDeposit(room.getDeposit());
        roomDto.setRent(room.getRent());
        roomDto.setMaintenanceCost(room.getMaintenanceCost());
        roomDto.setSize(room.getSize());
        roomDto.setFloor(room.getFloor());
        roomDto.setBuildingInfo(room.getBuildingInfo());
        roomDto.setState(room.getState().toString());
//        roomDto.setBookmarkNumber(room.getBookmarkNumber());
        roomDto.setOptions(Arrays.asList(room.getOptions().split(",")));
        roomDto.setRoadAddress(room.getRoadAddress());
        roomDto.setLatitude(room.getLatitude());
        roomDto.setLongitude(room.getLongitude());
        //null check
        if (room.getImageUrl() != null) {
            roomDto.setImages(room.getImageUrl().getUrls());
        } else {
            roomDto.setImages(Collections.emptyList());
        }

        // TransferorInfo 설정
        SelectRoomResponseDto.TransferorInfo transferorInfo = new SelectRoomResponseDto.TransferorInfo();
        transferorInfo.setUserId(user.getId());
        transferorInfo.setName(user.getName());
        transferorInfo.setProfileImg(user.getImage());
        roomDto.setTransferorInfo(transferorInfo);

        return roomDto;
    }

    public Room updateRoomInfo(Long roomId, RoomRegisterDto inputRoomInfo) {
        Room originalRoom = roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomId));

        Room newRoom = Room.builder()
                .id(originalRoom.getId())
                .userId(originalRoom.getUserId())
                .title(inputRoomInfo.getTitle() == null ? originalRoom.getTitle() : inputRoomInfo.getTitle())
                .content(inputRoomInfo.getContent() == null ? originalRoom.getContent() : inputRoomInfo.getContent())
                .type(inputRoomInfo.getType() == null ? originalRoom.getType() : inputRoomInfo.getType())
                .deposit(inputRoomInfo.getDeposit() == null ? originalRoom.getDeposit() : inputRoomInfo.getDeposit())
                .rent(inputRoomInfo.getRent() == null ? originalRoom.getRent() : inputRoomInfo.getRent())
                .maintenanceCost(inputRoomInfo.getMaintenanceCost() == null ? originalRoom.getMaintenanceCost() : inputRoomInfo.getMaintenanceCost())
                .options(inputRoomInfo.getOptions() == null ? originalRoom.getOptions() : inputRoomInfo.getOptions())
                .floor(inputRoomInfo.getFloor() == null ? originalRoom.getFloor() : inputRoomInfo.getFloor())
                .buildingInfo(inputRoomInfo.getBuildingInfo() == null ? originalRoom.getBuildingInfo() : inputRoomInfo.getBuildingInfo())
                .size(inputRoomInfo.getSize() == null ? originalRoom.getSize() : inputRoomInfo.getSize())
                .imageUrl(inputRoomInfo.getImages() == null ? originalRoom.getImageUrl() : inputRoomInfo.getImages())
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

        return roomRepository.save(newRoom);
    }
}
