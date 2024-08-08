package com.gdsc.illuwabang.domain.recentlyviews;

import com.gdsc.illuwabang.domain.home.HomeDao;
import com.gdsc.illuwabang.domain.home.HomeSelectRoomDto;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentlyViewsRepository extends JpaRepository<RecentlyViews, Long> {
    @Query("SELECT new com.gdsc.illuwabang.domain.home.HomeDao(rv.room.id, rv.room.imageUrl) " +
            "FROM RecentlyViews rv " +
            "WHERE rv.user = :user " +
            "ORDER BY rv.viewDate DESC")
    List<HomeDao> findTop10ByUser(@Param("user") User user, Pageable pageable);
}
