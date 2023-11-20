package com.earth.server.core.repository;

import com.earth.server.user.infra.persistence.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SnowmanRepository extends JpaRepository<SnowmanEntity, Long> {
    Optional<SnowmanEntity> findTopByUserAndStartDateOrderByIdDesc(UserEntity user, LocalDate startDate);
    Long countByUserAndStartDateLessThanEqual(UserEntity user, LocalDate startDate);
    List<SnowmanEntity> findFirst10ByUserAndStartDateLessThanEqualOrderByStartDateDesc(UserEntity user, LocalDate startDate);
}
