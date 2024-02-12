package com.ms.climbing.climb.service;

import com.ms.climbing.climb.dto.CreateClimbRequest;
import com.ms.climbing.climb.entity.Bouldering;
import com.ms.climbing.climb.entity.Climb;
import com.ms.climbing.climb.repository.ClimbRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClimbService {

    private final ClimbRepository climbRepository;

    public void create(CreateClimbRequest request) {
        Climb climb = Climb.createClimb(request);
        List<Bouldering> boulderingList = request.getBoulderingList()
                .stream()
                .map(v -> Bouldering.createBouldering(v, climb))
                .toList();
        climb.setBoulderingList(boulderingList);
        climbRepository.save(climb);
    }
}
