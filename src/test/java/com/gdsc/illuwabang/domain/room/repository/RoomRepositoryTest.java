package com.gdsc.illuwabang.domain.room.repository;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.RoomRepository;
import com.gdsc.illuwabang.domain.room.State;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class RoomRepositoryTest {
    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    @DisplayName("엔티티를 DB에 저장")
    public void testSaveRoom() {
        // Given
        ImageUrl imageUrl = new ImageUrl("thumbnail", "image1", "image2", "image3", "image4");
        Room room = Room.builder()
                .title("Sample Title")
                .content("Sample Content")
                .type("Sample Type")
                .deposit(1000)
                .rent(500)
                .maintenanceCost(50)
                .options("Sample Options")
                .floor("1")
                .buildingInfo("Sample Building Info")
                .size(45.5f)
                .imageUrl(imageUrl)
                .roadAddress("Sample Road Address")
                .detailAddress("Sample Detail Address")
                .state(State.AVAILABLE)
                .latitude(37.1234d)
                .longitude(127.5678d)
                .startDate(LocalDateTime.of(2024,7,21,9, 0))
                .endDate(LocalDateTime.of(2024,7,23,10, 0))
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // When
        Room savedRoom = roomRepository.save(room);

        entityManager.flush();

        // Then
        assertThat(savedRoom).isNotNull();
        assertThat(savedRoom.getId()).isNotNull();
        assertThat(savedRoom.getTitle()).isEqualTo("Sample Title");
    }
}