package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Getter @Setter
@AllArgsConstructor @Builder
public class SelectRoomResponseDto {
    private Long roomId;
    private String title;
    private String content;
    private Integer deposit;
    private Integer rent;
    private Integer maintenanceCost;
    private Float size;
    private String floor;
    private String buildingInfo;
    private String state;
    private Integer bookmarkNumber;
    private List<String> options;
    private String roadAddress;
    private Double latitude;
    private Double longitude;
    private List<String> images;
    private TransferorInfo transferorInfo;

    @Getter @Setter @AllArgsConstructor
    public static class TransferorInfo {
        private Long userId;
        private String name;
        private String profileImg;
    }

    public static SelectRoomResponseDto of(Room room, User user) {

        // 숫자 층수 => 문자 층수로 변환
        Integer floor = room.getFloor();
        String parsedFloor = "";
        if (floor == -1) {
            parsedFloor = "basement";
        } else if (floor == 100) {
            parsedFloor = "rooftop";
        } else {
            parsedFloor = String.valueOf(floor);
        }

        return SelectRoomResponseDto.builder()
                .roomId(room.getId())
                .title(room.getTitle())
                .content(room.getContent())
                .deposit(room.getDeposit())
                .rent(room.getRent())
                .maintenanceCost(room.getMaintenanceCost())
                .size(room.getSize())
                .floor(parsedFloor)
                .buildingInfo(room.getBuildingInfo())
                .state(room.getState().toString())
//                .bookmarkNumber(room.getBookmarkNumber())
                .options(Arrays.asList(room.getOptions().split(",")))
                .roadAddress(room.getRoadAddress())
                .latitude(room.getLatitude())
                .longitude(room.getLongitude())
                .images(room.getImageUrl() != null ? room.getImageUrl().getUrls() : Collections.emptyList())
                .transferorInfo(new TransferorInfo(user.getId(), user.getName(), user.getImage()))
                .build();
    }
}
