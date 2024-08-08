package com.gdsc.illuwabang.domain.home;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HomeResponseDto {
    private List<HomeSelectRoomDto> recommendedRooms;
    private List<HomeSelectRoomDto> recentWatchRooms;
}
