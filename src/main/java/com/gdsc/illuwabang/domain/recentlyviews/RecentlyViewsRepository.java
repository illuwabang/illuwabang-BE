package com.gdsc.illuwabang.domain.recentlyviews;

import com.gdsc.illuwabang.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecentlyViewsRepository extends JpaRepository<RecentlyViews, Long> {

    @Query(value = "SELECT * FROM recently_views WHERE user_id = ?1 ORDER BY view_date DESC LIMIT 10", nativeQuery = true)
    List<RecentlyViews> find10ByUserId(Long userId);
}
