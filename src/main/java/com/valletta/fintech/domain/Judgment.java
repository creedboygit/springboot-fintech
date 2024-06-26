package com.valletta.fintech.domain;

import com.valletta.fintech.dto.JudgmentDto.Request;
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
public class Judgment extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(nullable = false, updatable = false)
  private Long judgmentId;

  @Column(columnDefinition = "bigint NOT NULL COMMENT '신청 ID'")
  private Long applicationId;

  @Column(columnDefinition = "varchar(12) DEFAULT NULL COMMENT '심사자'")
  private String name;

  @Column(columnDefinition = "decimal(15,2) DEFAULT NULL COMMENT '승인 금액'")
  private BigDecimal approvalAmount;

  @Column(columnDefinition = "decimal(5,4) DEFAULT NULL COMMENT '승인 금리'")
  private BigDecimal approvalInterestRate;

  public void update(Request request) {

    this.name = request.getName();
    this.approvalAmount = request.getApprovalAmount();
  }
}
