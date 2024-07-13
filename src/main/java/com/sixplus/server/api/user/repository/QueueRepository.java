package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.model.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, String> {
    long countByGender(String gender);
}