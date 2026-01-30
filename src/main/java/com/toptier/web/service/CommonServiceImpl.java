package com.toptier.web.service;

import com.toptier.web.dto.CategoryResponse;
import com.toptier.web.dto.ShopResponse;
import com.toptier.web.repository.CategoryRepository;
import com.toptier.web.repository.ShopRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {
    private final CategoryRepository categoryRepository;
    private final ShopRepository shopRepository;

    @Override
    public List<CategoryResponse> findAllCategory() {
        return categoryRepository.findAll().stream()
                .map(CategoryResponse::from)
                .collect(Collectors.toList());
    }

    @Override
    public List<ShopResponse> findAllShop() {
        return shopRepository.findAll().stream()
                .map(ShopResponse::from)
                .collect(Collectors.toList());
    }
}
