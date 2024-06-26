package com.valletta.fintech.domain;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.Where;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Where(clause = "is_deleted=false")
public class Balance extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, updatable = false)
    private Long balanceId;

    @Column(columnDefinition = "bigint NOT NULL COMMENT '신청 ID'")
    private Long applicationId;

    @Column(columnDefinition = "decimal(15,2) NOT NULL COMMENT '잔여 대출 금액'")
    private BigDecimal balance;

    public void updateBalance(Long applicationId, BigDecimal balance) {
        this.applicationId = applicationId;
        this.balance = balance;
    }

    public void updateBalance(BigDecimal balance) {
        this.balance = balance;
    }
}
