package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.State;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AllRoomResponseDto {
    private Long roomId;
    private String thumbnail;
    private String type;
    private Integer deposit;
    private Integer rent;
    private String roadAddress;
    private String floor;
    private String startDate;
    private String endDate;
    private State state;
    private Double latitude;
    private Double longitude;
}
