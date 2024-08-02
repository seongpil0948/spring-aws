package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.model.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleEntityRepository extends JpaRepository<UserRoleEntity, Long> {
}