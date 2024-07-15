package com.gdsc.illuwabang.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ImageUrl {
    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;
    private String image4;
}
