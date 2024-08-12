package com.gdsc.illuwabang.domain.recentlyviews;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "recently_views")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecentlyViews {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;

    @Column
    private LocalDateTime viewDate;

    @Builder
    public RecentlyViews(User user, Room room, LocalDateTime viewDate) {
        this.user = user;
        this.room = room;
        this.viewDate = viewDate;
    }
}