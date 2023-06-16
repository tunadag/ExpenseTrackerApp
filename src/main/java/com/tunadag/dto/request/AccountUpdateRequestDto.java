package com.tunadag.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class AccountUpdateRequestDto {
    @NotNull
    private Long accountOid;
    @NotBlank
    @NotNull
    private String accountName;
}
