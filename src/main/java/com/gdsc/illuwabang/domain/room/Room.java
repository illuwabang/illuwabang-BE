package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;


@Entity
@Getter
@Table(name = "room")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    //foreign key
    @Column
    private Long userId;

    @Column
    private String title;

    @Column
    private String content;

    @Enumerated(EnumType.STRING)
    @Column
    private Type type;

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
    private Integer floor;

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

    @Enumerated(EnumType.STRING)
    @Column
    private State state;

    @Column
    private Double latitude;

    @Column
    private Double longitude;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private LocalDateTime createdAt;

    @Column
    private LocalDateTime updatedAt;

    @Builder
    public Room(Long id, Long userId,String title, String content, Type type,
                Integer deposit, Integer rent, Integer maintenanceCost,
                String options, Integer floor, String buildingInfo, Float size,
                ImageUrl imageUrl, String roadAddress, String detailAddress, State state,
                Double latitude, Double longitude, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.userId = userId;
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
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Builder
    public Room(Long userId,String title, String content, Type type,
                Integer deposit, Integer rent, Integer maintenanceCost,
                String options, Integer floor, String buildingInfo, Float size,
                ImageUrl imageUrl, String roadAddress, String detailAddress, State state,
                Double latitude, Double longitude, LocalDateTime startDate, LocalDateTime endDate, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.userId = userId;
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
        this.state = state;
        this.latitude = latitude;
        this.longitude = longitude;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
