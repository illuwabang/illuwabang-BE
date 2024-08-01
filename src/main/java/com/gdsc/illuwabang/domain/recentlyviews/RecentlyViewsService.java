package com.gdsc.illuwabang.domain.recentlyviews;

import com.gdsc.illuwabang.domain.recentlyviews.dto.RecentlyViewDto;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class RecentlyViewsService {

    private final RecentlyViewsRepository recentlyViewsRepository;

    public void saveRecentlyViews(User user, Long roomId) {
        RecentlyViews recentlyViews = RecentlyViews.builder()
                .user(user)
                .room(Room.builder().id(roomId).build())
                .build();

        recentlyViewsRepository.save(recentlyViews);
    }

    public List<RecentlyViewDto> getUsersRecentlyViews(Long userId) {
        List<RecentlyViews> allByUserId = recentlyViewsRepository.find10ByUserId(userId);

        return allByUserId.stream()
                .map(RecentlyViewDto::of)
                .toList();
    }
}
