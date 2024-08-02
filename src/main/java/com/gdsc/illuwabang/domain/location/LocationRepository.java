package com.gdsc.illuwabang.domain.location;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location,Long> {

    @Modifying
    @Transactional
    @Query("SELECT new com.gdsc.illuwabang.domain.location.LocationDto(lc.name) FROM Location lc")
    List<LocationDto> findALL();

    Location findByName(String name);

}
