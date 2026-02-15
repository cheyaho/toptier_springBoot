package com.toptier.web.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SiteUserRequest {
    @Size(min = 3, max = 25)
    @NotEmpty(message = "아이디를 입력해주세요. ")
    private String userId;

    @NotEmpty(message = "이름을 입력해주세요.")
    private String name;

    @NotEmpty(message = "이메일을 입력해주세요.")
    private String email;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    String password;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    String passwordConfirm;
}
