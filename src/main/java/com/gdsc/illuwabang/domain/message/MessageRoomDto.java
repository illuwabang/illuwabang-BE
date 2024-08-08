package com.gdsc.illuwabang.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomDto {
    private Long roomId;
    private String roomName;
    private Long hostId;
    private String hostName;
    private String status;
    private Long otherUserId;
    private String otherUserName;
    private String lastContent;
    private Long newMessageCount;
    private LocalDateTime timestamp;
}
