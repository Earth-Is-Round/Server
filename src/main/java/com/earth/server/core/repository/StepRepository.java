package com.earth.server.core.repository;

import com.earth.server.user.infra.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface StepRepository extends JpaRepository<StepEntity, Long> {
    Optional<StepEntity> findByUserAndDate(UserEntity user, LocalDate date);
}
