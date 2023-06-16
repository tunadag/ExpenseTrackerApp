package com.tunadag.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class LoginRequestDto {

    @Size(min = 3, message = "Email must be valid.")
    @Email(message = "Email must be a valid address.")
    @NotNull(message = "Email must not be null.")
    private String email;

    @Size(min = 8, max = 256, message = "Password should have at least 8 characters.")
    @NotBlank(message = "Password must not be blank.")
    @NotNull(message = "Password must not be null.")
    private String password;
}
