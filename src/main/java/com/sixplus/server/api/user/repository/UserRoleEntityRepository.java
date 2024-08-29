package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {
    Optional<UserRoleEntity> findByName(String name);
}