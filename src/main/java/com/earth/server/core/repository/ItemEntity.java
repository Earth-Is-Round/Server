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
@Table(name = "items")
public class ItemEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private LocalDate date;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String position;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;
    @ManyToOne
    @JoinColumn(name = "snowman_id")
    @Setter
    private SnowmanEntity snowman;

    public ItemEntity(LocalDate date, String name, UserEntity user, SnowmanEntity snowman, String position) {
        this.date = date;
        this.name = name;
        this.user = user;
        this.snowman = snowman;
        this.position = position;
    }
}
