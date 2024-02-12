package com.ms.climbing.climb.service;

import com.ms.climbing.climb.dto.*;
import com.ms.climbing.climb.entity.Bouldering;
import com.ms.climbing.climb.entity.Climb;
import com.ms.climbing.climb.exception.ClimbErrorCode;
import com.ms.climbing.climb.exception.ClimbException;
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
                .map(bouldering -> Bouldering.createBouldering(bouldering, climb))
                .toList();
        climb.setBoulderingList(boulderingList);
        climbRepository.save(climb);
    }

    public GetClimbResponse get(Long id) {
        Climb climb = climbRepository.findById(id)
                .orElseThrow(() -> new ClimbException(ClimbErrorCode.CLIMB_NOT_FOUND));
        List<GetBoulderingResponse> boulderingList = climb.getBoulderingList().stream()
                .map(GetBoulderingResponse::of)
                .toList();
        return GetClimbResponse.of(climb, boulderingList);
    }

    public void delete(Long id) {
        Climb climb = climbRepository.findById(id)
                .orElseThrow(() -> new ClimbException(ClimbErrorCode.CLIMB_NOT_FOUND));
        climbRepository.delete(climb);
    }
}
