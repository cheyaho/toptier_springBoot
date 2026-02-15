package com.toptier.web.repository;

import com.toptier.web.entity.SiteUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SiteUserRepostitory extends JpaRepository<SiteUser, Integer> {
    Optional<SiteUser> findByUserId(String userId);
    Boolean existsByUserId(String userId);
    Boolean existsByEmail(String email);
}
