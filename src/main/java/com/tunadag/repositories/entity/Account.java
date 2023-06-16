package com.tunadag.repositories.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.tunadag.repositories.entity.base.BaseEntity;
import com.tunadag.repositories.entity.enums.Currency;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_accounts")
public class Account extends BaseEntity {

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Currency currency;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id", nullable = false)
    private User user;

    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Transaction> transactions;

    @Builder.Default
    @Column(nullable = false)
    private double balance = 0.0;
    @Override
    public String toString() {
        return "Account{" +
                ", name='" + name + '\'' +
                ", currency=" + currency +
                '}';
    }
}
