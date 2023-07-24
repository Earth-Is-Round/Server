package com.earth.server.core.repository;

import com.earth.server.user.infra.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface ItemRepository extends JpaRepository<ItemEntity, Long> {
    List<ItemEntity> findAllByUserAndDateBetween(UserEntity user, LocalDate startDate, LocalDate endDate);
}
