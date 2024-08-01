package com.gdsc.illuwabang.domain.recentlyviews.dto;

import com.gdsc.illuwabang.domain.recentlyviews.RecentlyViews;
import com.gdsc.illuwabang.domain.room.ImageUrl;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RecentlyViewDto {
    private Long roomId;
    private String thumbnail;

    @Builder
    public RecentlyViewDto(Long roomId, String thumbnail) {
        this.roomId = roomId;
        this.thumbnail = thumbnail;
    }

    public static RecentlyViewDto of(RecentlyViews recentlyViews) {
        ImageUrl imageUrl = recentlyViews.getRoom().getImageUrl();
        String thumbnail;
        if (imageUrl != null) {
            thumbnail = imageUrl.getThumbnail();
        } else {
            thumbnail = null;
        }

        return RecentlyViewDto.builder()
                .roomId(recentlyViews.getRoom().getId())
                .thumbnail(thumbnail)
                .build();
    }
}
