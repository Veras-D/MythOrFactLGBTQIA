package com.veras.mythOrFactLGBT.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Request object for user registration")
public class RegisterRequest {
    @NotBlank
    @Size(min = 3, max = 50)
    @Schema(description = "Username for the new user", example = "newuser", requiredMode = Schema.RequiredMode.REQUIRED)
    private String username;

    @NotBlank
    @Size(max = 100)
    @Email
    @Schema(description = "Email address for the new user", example = "user@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @NotBlank
    @Size(min = 6, max = 255) // Max 255 to align with User.password length
    @Schema(description = "Password for the new user", example = "password123", requiredMode = Schema.RequiredMode.REQUIRED)
    private String password;
}
