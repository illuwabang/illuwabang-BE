package com.gdsc.illuwabang.domain.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MessageRoomListDto {
    private List<MessageRoomDto> messageRoomDtoList;
}
