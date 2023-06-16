package com.tunadag.dto.response;

import com.tunadag.repositories.entity.enums.Currency;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountAddResponseDto {
    private String name;
    private Currency currency;
}
