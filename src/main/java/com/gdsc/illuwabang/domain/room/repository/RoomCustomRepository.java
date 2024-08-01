package com.gdsc.illuwabang.domain.room.repository;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.dto.AllRoomResponseDto;
import com.gdsc.illuwabang.domain.room.dto.RoomSearchCriteria;

import java.util.List;

public interface RoomCustomRepository {
    List<Room> findRooms(RoomSearchCriteria criteria);
}
