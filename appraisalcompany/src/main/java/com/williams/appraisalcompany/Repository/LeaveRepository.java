package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Entity.Leave;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends PagingAndSortingRepository<Leave, Long>, QueryDslPredicateExecutor<Leave>{

    Leave findOneByUniqueKey(String uniqueKey);

    List<Leave> findAllByEmployeeNumber(String employeeUniqueKey);
}
