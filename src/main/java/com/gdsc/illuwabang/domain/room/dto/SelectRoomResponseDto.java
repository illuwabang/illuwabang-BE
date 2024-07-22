package com.gdsc.illuwabang.domain.room.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
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

    @Getter @Setter
    public static class TransferorInfo {
        private Long userId;
        private String name;
        private String profileImg;
    }
}
