package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Entity.Department;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends PagingAndSortingRepository<Department, Long>, QueryDslPredicateExecutor<Department> {

    Department  findOneByUniqueKey (String uniqueKey);

}
