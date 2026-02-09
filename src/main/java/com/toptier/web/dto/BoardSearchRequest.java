package com.toptier.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BoardSearchRequest extends PageConditionRequest {
    private Integer type = null;
    private String keyword;
}
