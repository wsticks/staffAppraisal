package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Entity.Loan;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LoanRepository extends PagingAndSortingRepository<Loan,Long>, QueryDslPredicateExecutor<Loan> {


    Loan findOneByUniqueKey(String uniqueKey);
    List<Loan> findAllByEmployee(String employeeUniqueNumber);
}
