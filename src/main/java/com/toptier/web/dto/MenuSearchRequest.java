package com.toptier.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MenuSearchRequest extends PageConditionRequest {
    private Integer category = null;
}
