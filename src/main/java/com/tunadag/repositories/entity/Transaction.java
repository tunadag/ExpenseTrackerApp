package com.tunadag.repositories.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunadag.repositories.entity.base.BaseEntity;
import com.tunadag.repositories.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_transactions")
public class Transaction extends BaseEntity {

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String category;

    @Column(nullable = false)
    private TransactionType type;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
