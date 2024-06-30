package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.domain.TB_QUEUE;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<TB_QUEUE, String> {
    long countByGender(String gender);
}