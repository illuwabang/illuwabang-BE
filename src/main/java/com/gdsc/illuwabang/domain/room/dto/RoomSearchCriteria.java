package com.gdsc.illuwabang.domain.room.dto;

import com.gdsc.illuwabang.domain.room.enums.Type;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class RoomSearchCriteria {
    private Type type;
    private Integer depositMin;
    private Integer depositMax;
    private Integer rentMin;
    private Integer rentMax;
    private Float sizeMin;
    private Float sizeMax;
    private Boolean isBasement;
    private Boolean isGround;
    private Boolean isRooftop;
    private List<String> options;
}

