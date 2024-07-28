package com.gdsc.illuwabang.domain.location;

import com.gdsc.illuwabang.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserInterestLocationRepository extends JpaRepository<UserInterestLocation, Long> {
    UserInterestLocation findByUserIdAndLocationId(User user, Location location);

    @Modifying
    @Transactional
    @Query("SELECT new com.gdsc.illuwabang.domain.location.ResponseInterestLocationDto(uil.locationId.id, uil.locationId.name) " +
            "FROM UserInterestLocation uil " +
            "WHERE uil.userId = :user ")
    List<ResponseInterestLocationDto> findByUserIdTransResponse(User user);


    @Modifying
    @Transactional
    void deleteByUserIdAndLocationId(User user, Location location);
}
