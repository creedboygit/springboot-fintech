package com.valletta.fintech.repository;

import com.valletta.fintech.domain.Repayment;
import java.util.List;

public interface RepaymentRepository {

    List<Repayment> findAllByApplicationId(Long applicationId);
}
