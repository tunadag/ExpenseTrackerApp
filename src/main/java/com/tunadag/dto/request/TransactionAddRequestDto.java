package com.tunadag.dto.request;

import com.tunadag.repositories.entity.enums.TransactionType;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class TransactionAddRequestDto {
    private Long accountId;
    private String date;
    private Double amount;
    private String category;
    private TransactionType type;
}
