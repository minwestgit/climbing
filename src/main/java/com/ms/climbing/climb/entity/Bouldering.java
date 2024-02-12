package com.ms.climbing.climb.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Bouldering {
    @Id @GeneratedValue
    @Column(name = "bouldering_id")
    private Long id;
    @Column(nullable = false)
    private String color;
    @Column(nullable = false)
    private int num;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "climb_id")
    private Bouldering bouldering;
}
