package com.ms.climbing.climb.entity;

import com.ms.climbing.climb.dto.CreateClimbRequest;
import com.ms.climbing.climb.dto.UpdateClimbRequest;
import com.ms.climbing.common.Base;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
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

    @Builder
    private Climb(String description, String date, String place, List<Bouldering> boulderingList) {
        this.description = description;
        this.date = date;
        this.place = place;
        this.boulderingList = boulderingList;
    }

    public static Climb createClimb(CreateClimbRequest request) {
        return Climb.builder()
                .description(request.getDescription())
                .date(request.getDate())
                .place(request.getPlace())
                .build();
    }

    public void setBoulderingList(List<Bouldering> boulderingList) {
        this.boulderingList.addAll(boulderingList);
    }

    public void removeBoulderingById(List<Long> boulderingIds) {
        this.boulderingList.removeIf(bouldering -> boulderingIds.contains(bouldering.getId()));
    }

    public void update(UpdateClimbRequest request) {
        this.description = description;
        this.date = date;
        this.place = place;
    }

    public void addBoulderingList(Bouldering newBouldering) {
        this.boulderingList.add(newBouldering);
    }
}
