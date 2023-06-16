package com.tunadag.dto.request;

import com.tunadag.repositories.entity.enums.Currency;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountCreateRequestDto {
    @NotBlank(message = "Name field is required")
    private String name;

    @NotNull(message = "Currency field is required")
    private Currency currency;

}
