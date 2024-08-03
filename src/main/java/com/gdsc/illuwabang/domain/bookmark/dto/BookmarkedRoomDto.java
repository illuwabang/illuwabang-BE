package com.gdsc.illuwabang.domain.bookmark.dto;

import com.gdsc.illuwabang.domain.room.Room;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class BookmarkedRoomDto {
    private Long roomId;
    private String thumbnail;
    private String type;
    private Integer deposit;
    private Integer rent;
    private String location;
    private String floor;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    @Builder
    public BookmarkedRoomDto(Long roomId, String thumbnail, String type, Integer deposit, Integer rent, String location, String floor, LocalDateTime startDate, LocalDateTime endDate) {
        this.roomId = roomId;
        this.thumbnail = thumbnail;
        this.type = type;
        this.deposit = deposit;
        this.rent = rent;
        this.location = location;
        this.floor = floor;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public BookmarkedRoomDto of(Room room) {
        //숫자층 => 문자 층
        String floor = "";
        if (room.getFloor() == -1) {
            floor = "basement";
        } else if (room.getFloor() == 100) {
            floor = "rooftop";
        } else {
            floor = String.valueOf(room.getFloor());
        }

        return BookmarkedRoomDto.builder()
                .roomId(room.getId())
                .thumbnail(room.getImageUrl() != null ? room.getImageUrl().getThumbnail() : null)
                .type(room.getType().name())
                .deposit(room.getDeposit())
                .rent(room.getRent())
                .location(room.getRoadAddress())
                .floor(floor)
                .startDate(room.getStartDate())
                .endDate(room.getEndDate())
                .build();
    }
}
