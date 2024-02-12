package com.ms.climbing.climb.entity;

import com.ms.climbing.climb.dto.CreateBoulderingRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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
    private Climb climb;

    @Builder
    private Bouldering(String color, int num, Climb climb) {
        this.color = color;
        this.num = num;
        this.climb = climb;
    }

    public static Bouldering createBouldering(CreateBoulderingRequest boulderingRequest, Climb climb) {
        return Bouldering.builder()
                .color(boulderingRequest.getColor())
                .num(boulderingRequest.getNum())
                .climb(climb)
                .build();
    }
}
