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

    public void update(UpdateClimbRequest request) {
        Climb climb = climbRepository.findById(request.getId())
                .orElseThrow(() -> new ClimbException(ClimbErrorCode.CLIMB_NOT_FOUND));

        List<Long> deleteBoulderingIds = request.getDeleteBoulderingIds();
        if(deleteBoulderingIds != null) {
            climb.removeBoulderingById(deleteBoulderingIds);
        }

        List<Bouldering> existsBoulderingList = climb.getBoulderingList();
        for (CreateBoulderingRequest updateBouldering : request.getUpdateBoulderingList()) {
            boolean existingBouldering = existsBoulderingList.stream()
                    .anyMatch(bouldering -> bouldering.getLevel() == updateBouldering.getLevel());
            if (existingBouldering) {
                for (Bouldering bouldering : existsBoulderingList) {
                    if (bouldering.getLevel() == updateBouldering.getLevel()) {
                        bouldering.updateNum(updateBouldering.getNum());
                        break;
                    }
                }
            } else {
                Bouldering newBouldering = Bouldering.createBouldering(updateBouldering, climb);
                climb.addBoulderingList(newBouldering);
            }
        }

        climb.update(request);
    }

    public void delete(Long id) {
        Climb climb = climbRepository.findById(id)
                .orElseThrow(() -> new ClimbException(ClimbErrorCode.CLIMB_NOT_FOUND));
        climbRepository.delete(climb);
    }
}
