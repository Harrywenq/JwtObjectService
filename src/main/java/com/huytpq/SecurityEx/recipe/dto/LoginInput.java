package com.huytpq.SecurityEx.recipe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class LoginInput {
    private String username;
    private String password;
}
