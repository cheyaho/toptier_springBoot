package com.toptier.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonSearchRequest extends PageConditionRequest{
    private Integer category = null;
}
