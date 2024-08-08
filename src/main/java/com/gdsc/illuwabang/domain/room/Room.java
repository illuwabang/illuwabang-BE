package com.gdsc.illuwabang.domain.room;

import com.gdsc.illuwabang.domain.room.enums.State;
import com.gdsc.illuwabang.domain.room.enums.Type;
import com.gdsc.illuwabang.domain.user.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDateTime;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder @AllArgsConstructor
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

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
}
