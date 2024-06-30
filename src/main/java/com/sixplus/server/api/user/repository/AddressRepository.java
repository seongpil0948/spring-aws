package com.sixplus.server.api.user.repository;

import com.sixplus.server.api.user.domain.TB_ADDRESS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<TB_ADDRESS, UUID> {
}