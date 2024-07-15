package com.gdsc.illuwabang.domain.room;

import io.hypersistence.utils.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.Type;
import org.hibernate.type.SqlTypes;

import java.time.LocalTime;
import java.util.List;


@Entity
@Data
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

//    @Enumerated(EnumType.STRING)
    @Column
    private String type;

    @Column
    private Integer deposit;

    @Column
    private Integer rent;

    @Column
    private Integer maintenanceCost;

//    @Enumerated(EnumType.STRING)
    @Column
    private String options;

//    @Enumerated(EnumType.STRING)
    @Column
    private String floor;

    @Column
    private String buildingInfo;

    @Column
    private Float size;

    @Column(name = "imageUrl", columnDefinition = "json")
    @JdbcTypeCode(SqlTypes.JSON)
    private ImageUrl imageUrl;

    @Column
    private String roadAddress;

    @Column
    private String detailAddress;

    @Column
    private Float latitude;

    @Column
    private Float longitude;

    @Column
    private LocalTime startDate;

    @Column
    private LocalTime endDate;

    @Column
    private LocalTime createdAt;

    @Column
    private LocalTime updatedAt;

    @Builder
    public Room(String title, String content, String type, Integer deposit, Integer rent, Integer maintenanceCost, String options, String floor, String buildingInfo, Float size, ImageUrl imageUrl, String roadAddress, String detailAddress, Float latitude, Float longitude, LocalTime startDate, LocalTime endDate, LocalTime createdAt, LocalTime updatedAt) {
        this.title = title;
        this.content = content;
        this.type = type;
        this.deposit = deposit;
        this.rent = rent;
        this.maintenanceCost = maintenanceCost;
        this.options = options;
        this.floor = floor;
        this.buildingInfo = buildingInfo;
        this.size = size;
        this.imageUrl = imageUrl;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
