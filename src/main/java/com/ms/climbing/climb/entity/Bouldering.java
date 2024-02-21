package com.ms.climbing.climb.entity;

import com.ms.climbing.climb.dto.CreateBoulderingRequest;
import com.ms.climbing.common.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Bouldering extends Base {
    @Id @GeneratedValue
    @Column(name = "bouldering_id")
    private Long id;
    @Column(nullable = false)
    private BoulderingLevel level;
    @Column(nullable = false)
    private int num;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "climb_id")
    private Climb climb;

    @Builder
    private Bouldering(BoulderingLevel level, int num, Climb climb) {
        this.level = level;
        this.num = num;
        this.climb = climb;
    }

    public static Bouldering createBouldering(CreateBoulderingRequest boulderingRequest, Climb climb) {
        return Bouldering.builder()
                .level(boulderingRequest.getLevel())
                .num(boulderingRequest.getNum())
                .climb(climb)
                .build();
    }

    public void updateNum(int num) {
        this.num = num;
    }
}
