package com.gdsc.illuwabang.domain.room;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor @Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class ImageUrl {
    private String thumbnail;
    private String image1;
    private String image2;
    private String image3;
    private String image4;

    public List<String> getUrls() {
        return List.of(thumbnail, image1, image2, image3, image4).stream()
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
