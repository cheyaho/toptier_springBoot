package com.toptier.web.service;

import com.toptier.web.dto.FranchiseRequest;
import com.toptier.web.dto.FranchiseResponse;
import com.toptier.web.entity.Franchise;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface FranchiseService {
    Integer createFranchise(FranchiseRequest reqFranchise);

    Page<FranchiseResponse> getAllFranchiseList(Pageable pageable, String hidden);

    FranchiseResponse getFranchiseInfo(int id);

    void updateFranchiseUpdate(Integer id, String confirmYN);

    void deleteFranchiseUpdate(Integer id, String hidden);
}
