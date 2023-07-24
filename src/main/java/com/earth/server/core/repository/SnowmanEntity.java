package com.earth.server.core.repository;

import com.earth.server.user.infra.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "snowmen")
public class SnowmanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate endDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(nullable = false)
    private Integer headSize;
    @Column(nullable = false)
    private Integer bodySize;

    public SnowmanEntity(LocalDate startDate, LocalDate endDate, UserEntity user, Integer headSize, Integer bodySize) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.user = user;
        this.headSize = headSize;
        this.bodySize = bodySize;
    }
}
