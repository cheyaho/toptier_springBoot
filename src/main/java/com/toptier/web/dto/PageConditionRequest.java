package com.toptier.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class PageConditionRequest {
    private int currentPage = 1;
    private int pageSize = 10;
    private String sort = "insDate,desc";
    public Pageable pageable() {
        String[] sorts = sort.split(",");
        Sort.Direction direction = (sorts.length > 1 && "asc".equalsIgnoreCase(sorts[1])) ? Sort.Direction.ASC :Sort.Direction.DESC;
        return PageRequest.of(Math.max(currentPage - 1, 0), pageSize, Sort.by(direction, sorts[0]));
    }
}
