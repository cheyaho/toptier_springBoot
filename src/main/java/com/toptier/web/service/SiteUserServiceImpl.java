package com.toptier.web.service;

import com.toptier.web.dto.SiteUserRequest;
import com.toptier.web.entity.SiteUser;
import com.toptier.web.repository.SiteUserRepostitory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SiteUserServiceImpl implements SiteUserService {
    private final SiteUserRepostitory siteUserRepostitory;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void createSiteUser(SiteUserRequest reqUser){
        SiteUser siteUser = SiteUser.builder()
                        .userId(reqUser.getUserId())
                        .name(reqUser.getName())
                        .email(reqUser.getEmail())
                        .password(passwordEncoder.encode(reqUser.getPassword()))
                        .build();
        siteUserRepostitory.save(siteUser);
    }

    @Override
    public Boolean checkDuplicateId(String userId) {
        return siteUserRepostitory.existsByUserId(userId);
    }

    @Override
    public Boolean checkDuplicateEmail(String email) {
        return siteUserRepostitory.existsByEmail(email);
    }
}
