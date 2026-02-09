package com.toptier.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class FranchiseSearchRequest extends PageConditionRequest {
    private String hidden = "N";
}
