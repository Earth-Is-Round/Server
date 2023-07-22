package com.earth.server.core.repository;

import com.earth.server.user.infra.persistence.UserEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "steps")
public class StepEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private Integer count;

    public StepEntity(UserEntity user, LocalDate date, Integer count) {
        this.user = user;
        this.date = date;
        this.count = count;
    }
}
