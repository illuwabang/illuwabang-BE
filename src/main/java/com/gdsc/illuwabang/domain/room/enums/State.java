package com.gdsc.illuwabang.domain.room.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum State {
    AVAILABLE("판매중"),
    SOLD("판매완료");

    private final String state;
}
