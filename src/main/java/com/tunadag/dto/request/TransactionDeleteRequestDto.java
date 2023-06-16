package com.tunadag.dto.request;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionDeleteRequestDto {
    private Long accountId;
    private Long transactionId;
}
