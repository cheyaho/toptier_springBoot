package com.toptier.web.service;

import com.toptier.web.dto.SiteUserRequest;

public interface SiteUserService {
    void createSiteUser(SiteUserRequest reqUser);

    Boolean checkDuplicateId(String userId);
    Boolean checkDuplicateEmail(String email);
}
