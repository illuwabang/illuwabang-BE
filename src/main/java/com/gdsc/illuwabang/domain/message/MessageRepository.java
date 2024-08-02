package com.gdsc.illuwabang.domain.message;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("SELECT new com.gdsc.illuwabang.domain.message.MessageRoomDto(m.roomId.id, r.title, r.userId.id, r.userId.name, CASE WHEN r.userId = :user THEN 'host' ELSE 'guest' END ,"
            + "r.userId.id, r.userId.name, m.content, (SELECT COUNT(m3)*1L FROM Message m3 WHERE m3.roomId = m.roomId AND m3.receiveId = :user AND m3.state = false), m.timestamp) " +
            "FROM Message m " +
            "JOIN m.roomId r " +
            "WHERE (m.senderId = :user OR m.receiveId = :user) AND m.timestamp = (SELECT MAX(m2.timestamp) FROM Message m2 WHERE m2.roomId = m.roomId) " +
            "GROUP BY m.roomId.id, r.title, r.userId.id, r.userId.name, r.userId.id, r.userId.name, m.content, m.timestamp")
    List<MessageRoomDto> findAllMessageRoom(User user);

    @Query("SELECT new com.gdsc.illuwabang.domain.message.MessageDetailDto(m.id, m.senderId.id, m.receiveId.id, m.content, m.timestamp) " +
            "FROM Message m " +
            "WHERE (m.senderId = :user AND m.receiveId = :other AND m.roomId = :room) " +
            "OR (m.senderId = :other AND m.receiveId = :user AND m.roomId = :room) " +
            "ORDER BY m.timestamp")
    List<MessageDetailDto> findAllMessagesBetweenUsersInRoom(User user, Room room, User other);

    @Modifying
    @Transactional
    @Query("UPDATE Message m " +
            "SET m.state = true " +
            "WHERE m.senderId = :other AND m.roomId = :room AND m.receiveId = :user")
    void updateAllByMessagesStatus(User user, Room room, User other);

    @Modifying
    @Transactional
    void deleteMessageBySenderIdAndId(User user, Long messageId);
}
