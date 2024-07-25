package com.gdsc.illuwabang.domain.message;

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
@Table(name="message")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User senderId;

    @Column
    private String content;

    @Column
    private LocalDateTime timestamp;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room roomId;

    @Column
    private Boolean state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiveId;

    @Builder
    public Message(User senderId, String content, Room roomId, User receiveId, LocalDateTime timestamp) {
        this.senderId = senderId;
        this.content = content;
        this.roomId = roomId;
        this.state = false;
        this.timestamp = timestamp;
        this.receiveId = receiveId;
    }
}
