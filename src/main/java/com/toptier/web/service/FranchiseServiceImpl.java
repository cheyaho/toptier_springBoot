package com.toptier.web.service;

import com.toptier.web.dto.FranchiseRequest;
import com.toptier.web.dto.FranchiseResponse;
import com.toptier.web.dto.ResultResponse;
import com.toptier.web.dto.ShopResponse;
import com.toptier.web.entity.Franchise;
import com.toptier.web.repository.FranchiseRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class FranchiseServiceImpl implements FranchiseService {
    private final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(this.getClass());
    private final FranchiseRepository franchiseRepository;

    @Override
    @Transactional
    public Integer createFranchise(FranchiseRequest reqFranchise) {
        Franchise franchise = new Franchise(reqFranchise);
        Franchise saved = franchiseRepository.save(franchise);
        franchiseRepository.flush();
        return saved.getId();
    }

    @Override
    public Page<FranchiseResponse> getAllFranchiseList(Pageable pageable) {
        Page<Franchise> franchiseList = franchiseRepository.findAll(pageable);
        return franchiseList.map(FranchiseResponse::from);
    }
}
