package com.gdsc.illuwabang.domain.room.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Type {
    ONE_ROOM("원룸"),
    SHORT_TERM_ONE_ROOM("단기원룸"),
    SHARE_HOUSE("쉐어하우스"),
    ROOMMATE("자취메이트");

    private final String type;
}
