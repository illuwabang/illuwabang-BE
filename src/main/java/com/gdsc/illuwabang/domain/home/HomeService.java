package com.gdsc.illuwabang.domain.home;

import com.gdsc.illuwabang.domain.location.UserInterestLocationRepository;
import com.gdsc.illuwabang.domain.recentlyviews.RecentlyViewsRepository;
import com.gdsc.illuwabang.domain.user.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@Transactional(readOnly = true)
public class HomeService {

    @Autowired
    private UserInterestLocationRepository userInterestLocationRepository;

    @Autowired
    private RecentlyViewsRepository recentlyViewsRepository;


    public HomeResponseDto getRecommendInfo(User user) {
        List<HomeDao> ogRecommendedRooms = userInterestLocationRepository.findRecommendedRooms(user, PageRequest.of(0, 10));
        Pageable pageable = PageRequest.of(0, 10, Sort.by("viewDate").descending());
        List<HomeDao> ogRecentlyRooms = recentlyViewsRepository.findTop10ByUser(user, pageable);

        List<HomeSelectRoomDto> recommendedRooms = HomeSelectRoomDto.ofOgDto(ogRecommendedRooms);
        List<HomeSelectRoomDto> recentlyRooms = HomeSelectRoomDto.ofOgDto(ogRecentlyRooms);

        return new HomeResponseDto(recommendedRooms, recentlyRooms);
    }
}
