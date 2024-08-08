package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllRoomResponseDto {
    private Long roomId;
    private String thumbnail;
    private Type type;
    private Integer deposit;
    private Integer rent;
    private String roadAddress;
    private String floor;
    private String startDate;
    private String endDate;
    private State state;
    private Double latitude;
    private Double longitude;


    public static AllRoomResponseDto of (Room room) {
        AllRoomResponseDto allRoomResponseDto = new AllRoomResponseDto();

        String thumbnail = "";
        if (room.getImageUrl() != null) {
            thumbnail = room.getImageUrl().getThumbnail();
        } else {
            thumbnail = "room_default.png";
        }

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

        allRoomResponseDto.setRoomId(room.getId());
        allRoomResponseDto.setThumbnail(thumbnail);
        allRoomResponseDto.setType(room.getType());
        allRoomResponseDto.setDeposit(room.getDeposit());
        allRoomResponseDto.setRent(room.getRent());
        allRoomResponseDto.setRoadAddress(room.getRoadAddress());
        allRoomResponseDto.setFloor(parsedFloor);
        allRoomResponseDto.setStartDate(room.getStartDate().toString());
        allRoomResponseDto.setEndDate(room.getEndDate().toString());
        allRoomResponseDto.setState(room.getState());
        allRoomResponseDto.setLatitude(room.getLatitude());
        allRoomResponseDto.setLongitude(room.getLongitude());

        return allRoomResponseDto;
    }
}
