package com.gdsc.illuwabang.domain.bookmark;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {


    //get all Rooms from Room table of bookmarks by user id
    @Query("SELECT b.room FROM Bookmark b WHERE b.user.id = :userId")
    List<Room> findAllByUserId(Long userId);

    @Query("SELECT b FROM Bookmark b WHERE b.user = :user AND b.room = :room")
    Bookmark findByUserAndRoom(User user, Room room);
}
