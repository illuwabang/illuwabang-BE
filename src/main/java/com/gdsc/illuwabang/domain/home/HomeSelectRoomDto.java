package com.gdsc.illuwabang.domain.home;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
public class HomeSelectRoomDto {
    private Long roomId;
    private String thumbnail;

    public static List<HomeSelectRoomDto> ofOgDto(List<HomeDao> ogRecommendedRooms) {
        return ogRecommendedRooms.stream()
                .map(og -> new HomeSelectRoomDto(og.getRoomId(), og.getImageUrl().getThumbnail()))
                .collect(Collectors.toList());
    }
}
