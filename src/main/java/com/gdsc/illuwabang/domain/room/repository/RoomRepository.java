package com.gdsc.illuwabang.domain.room.repository;

import com.gdsc.illuwabang.domain.room.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
}
