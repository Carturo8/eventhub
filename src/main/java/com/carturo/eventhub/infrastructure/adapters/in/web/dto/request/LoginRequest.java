package com.carturo.eventhub.infrastructure.adapters.in.web.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "{user.username.notblank}")
    @Email(message = "{user.username.email}")
    private String username;

    @NotBlank(message = "{user.password.notblank}")
    private String password;
}