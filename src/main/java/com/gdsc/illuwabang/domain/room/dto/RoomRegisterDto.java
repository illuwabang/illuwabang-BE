package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class RoomRegisterDto {
    private User user;
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

    public Room toEntity() {
        //문자열 층수 => 숫자 층수
        Integer parsedFloor = 0;
        if (this.floor.equals("basement")) {
            parsedFloor = -1;
        } else if (this.floor.equals("rooftop")) {
            parsedFloor = 100;
        } else {
            parsedFloor = Integer.parseInt(this.floor);
        }

        return Room.builder()
                .user(user)
                .title(title)
                .content(content)
                .type(type)
                .deposit(deposit)
                .rent(rent)
                .maintenanceCost(maintenanceCost)
                .options(options)
                .imageUrl(imageUrl)
                .roadAddress(roadAddress)
                .detailAddress(detailAddress)
                .latitude(latitude)
                .longitude(longitude)
                .state(state)
                .startDate(startDate)
                .endDate(endDate)
                .floor(parsedFloor)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .buildingInfo(buildingInfo)
                .size(size)
                .build();
    }
}
