package com.veras.mythOrFactLGBT.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private String tokenType = "Bearer";
    private Long userId;
    private String username;


    public AuthResponse(String token, Long userId, String username) {
        this.token = token;
        this.userId = userId;
        this.username = username;
    }
}
