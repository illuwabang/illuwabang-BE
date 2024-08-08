package com.gdsc.illuwabang.domain.home;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HomeDao {
    private Long roomId;
    private ImageUrl imageUrl;
}
