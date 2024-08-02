package com.gdsc.illuwabang.domain.location;

import com.gdsc.illuwabang.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name="user_interest_location")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserInterestLocation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "location_id")
    private Location locationId;

    public UserInterestLocation(User user, Location location) {
        this.userId = user;
        this.locationId = location;
    }
}
