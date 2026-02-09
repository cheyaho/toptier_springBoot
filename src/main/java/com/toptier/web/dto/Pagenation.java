package com.toptier.web.dto;

import lombok.*;
import org.springframework.data.domain.Page;

@Getter
@Setter
@NoArgsConstructor
public class Pagenation {
    int totalPages;
    long totalElements;
    int page;
    int pageSize;
    int startPage;
    int endPage;
    Boolean hasNext;
    Boolean hasPrevious;

    public Pagenation(Page<?> page, int pageSize){
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.page = page.getNumber() + 1;
        this.pageSize = pageSize;
        this.startPage = Math.min((page.getNumber() / pageSize) * pageSize + 1, page.getTotalPages());
        this.endPage = Math.min(startPage + pageSize, page.getTotalPages());
        this.hasNext = page.hasNext();
        this.hasPrevious = page.hasPrevious();
    }
}
