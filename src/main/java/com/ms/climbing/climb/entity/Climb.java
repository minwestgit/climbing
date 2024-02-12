package com.ms.climbing.climb.entity;

import com.ms.climbing.common.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Climb extends Base {
    @Id @GeneratedValue
    @Column(name = "climb_id")
    private Long id;
    @Column
    private String description;
    @Column(nullable = false)
    private String date;
    @Column(nullable = false)
    private String place;
    @OneToMany(mappedBy = "climb", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Bouldering> boulderingList = new ArrayList<>();
}
