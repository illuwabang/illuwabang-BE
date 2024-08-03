package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder @AllArgsConstructor
public class UpdatedRoomResponseDto {
    private Long id;
    private String userName;
    private String title;
    private String content;
    private Type type;
    private Integer deposit;
    private Integer rent;
    private Integer maintenanceCost;
    private String floor;
    private String options;
    private ImageUrl imageUrl;
    private String roadAddress;
    private String detailAddress;
    private Double latitude;
    private Double longitude;
    private State state;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String buildingInfo;
    private Float size;

    public static UpdatedRoomResponseDto of(Room savedRoom) {
        return UpdatedRoomResponseDto.builder()
                .id(savedRoom.getId())
                .userName(savedRoom.getUser().getName())
                .title(savedRoom.getTitle())
                .content(savedRoom.getContent())
                .type(savedRoom.getType())
                .deposit(savedRoom.getDeposit())
                .rent(savedRoom.getRent())
                .maintenanceCost(savedRoom.getMaintenanceCost())
                .floor(savedRoom.getFloor().toString())
                .options(savedRoom.getOptions())
                .imageUrl(savedRoom.getImageUrl())
                .roadAddress(savedRoom.getRoadAddress())
                .detailAddress(savedRoom.getDetailAddress())
                .latitude(savedRoom.getLatitude())
                .longitude(savedRoom.getLongitude())
                .state(savedRoom.getState())
                .startDate(savedRoom.getStartDate())
                .endDate(savedRoom.getEndDate())
                .createdAt(savedRoom.getCreatedAt())
                .updatedAt(savedRoom.getUpdatedAt())
                .buildingInfo(savedRoom.getBuildingInfo())
                .size(savedRoom.getSize())
                .build();
    }
}
