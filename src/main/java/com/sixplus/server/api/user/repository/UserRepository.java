package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.domain.TB_USER;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<TB_USER, String> {
}



