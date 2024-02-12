package com.ms.climbing.climb.repository;

import com.ms.climbing.climb.entity.Climb;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClimbRepository extends JpaRepository<Climb, Long> {
}
