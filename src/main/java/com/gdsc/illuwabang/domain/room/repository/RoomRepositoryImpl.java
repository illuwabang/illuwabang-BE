package com.gdsc.illuwabang.domain.room.repository;

import com.gdsc.illuwabang.domain.room.Room;
import com.gdsc.illuwabang.domain.room.dto.RoomSearchCriteria;
import com.gdsc.illuwabang.domain.room.enums.FloorConstants;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.gdsc.illuwabang.domain.room.QRoom.room;

@Repository
@RequiredArgsConstructor
public class RoomRepositoryImpl implements RoomCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Room> findRooms(RoomSearchCriteria criteria) {
        BooleanBuilder builder = new BooleanBuilder();

        if (criteria.getType() != null) {
            builder.and(room.type.eq(criteria.getType()));
        }
        if (criteria.getDepositMin() != null) {
            builder.and(room.deposit.goe(criteria.getDepositMin()));
        }
        if (criteria.getDepositMax() != null) {
            builder.and(room.deposit.loe(criteria.getDepositMax()));
        }
        if (criteria.getRentMin() != null) {
            builder.and(room.rent.goe(criteria.getRentMin()));
        }
        if (criteria.getRentMax() != null) {
            builder.and(room.rent.loe(criteria.getRentMax()));
        }
        if (criteria.getSizeMin() != null) {
            builder.and(room.size.goe(criteria.getSizeMin()));
        }
        if (criteria.getSizeMax() != null) {
            builder.and(room.size.loe(criteria.getSizeMax()));
        }
        if (criteria.getIsBasement() != null && criteria.getIsBasement()) {
            // 지하
            builder.and(room.floor.eq(FloorConstants.BASEMENT));
        }
        if (criteria.getIsGround() != null && criteria.getIsGround()) {
            // 지상층
            builder.and(room.floor.gt(0));
        }
        if (criteria.getIsRooftop() != null && criteria.getIsRooftop()) {
            // 옥탑
            builder.and(room.floor.eq(FloorConstants.ROOFTOP));
        }
        if (criteria.getOptions() != null && !criteria.getOptions().isEmpty()) {
            for (String option : criteria.getOptions()) {
                builder.and(room.options.contains(option));
            }
        }

        return queryFactory.selectFrom(room)
                .where(builder)
                .fetch();
    }
}