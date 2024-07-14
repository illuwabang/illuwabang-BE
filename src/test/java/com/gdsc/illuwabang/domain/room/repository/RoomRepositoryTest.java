package com.gdsc.illuwabang.domain.room.repository;

import com.gdsc.illuwabang.domain.room.ImageUrl;
import com.gdsc.illuwabang.domain.room.Room;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

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
                .latitude(37.1234f)
                .longitude(127.5678f)
                .startDate(LocalTime.of(9, 0))
                .endDate(LocalTime.of(18, 0))
                .createdAt(LocalTime.now())
                .updatedAt(LocalTime.now())
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