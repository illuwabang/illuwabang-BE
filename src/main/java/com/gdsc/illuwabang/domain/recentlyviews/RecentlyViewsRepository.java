package com.gdsc.illuwabang.domain.recentlyviews;

import com.gdsc.illuwabang.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecentlyViewsRepository extends JpaRepository<RecentlyViews, Long> {
}
