package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.State;
import com.gdsc.illuwabang.domain.room.Type;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Getter @Setter
public class RoomRegisterDto {
    private Long userId;
    private String title;
    private String content;
    private Type type;
    private Integer deposit;
    private Integer rent;
    private Integer maintenanceCost;
    private String floor;
    private String options;
    private ImageUrl images;
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

    public Room toEntity() {
        return Room.builder()
                .userId(userId)
                .title(title)
                .content(content)
                .type(type)
                .deposit(deposit)
                .rent(rent)
                .maintenanceCost(maintenanceCost)
                .options(options)
                .imageUrl(images)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .latitude(latitude)
                .longitude(longitude)
                .state(state)
                .startDate(startDate)
                .endDate(endDate)
                .floor(floor)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .buildingInfo(buildingInfo)
                .size(size)
                .build();
    }
}
