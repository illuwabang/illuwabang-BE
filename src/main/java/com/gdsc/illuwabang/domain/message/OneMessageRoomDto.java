package com.gdsc.illuwabang.domain.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OneMessageRoomDto {
    private OpponentUser opponentUser;
    private RoomInfo roomInfo;
    private List<MessageDetailDto> messageDetailDtoList;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpponentUser {
        private Long userId;
        private String name;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoomInfo {
        private Long roomId;
        private String thumbnail;
        private String type;
        private Integer deposit;
        private Integer rent;
        private String location;
        private String floor;
        private LocalDateTime startDate;
        private LocalDateTime endDate;
    }

}
