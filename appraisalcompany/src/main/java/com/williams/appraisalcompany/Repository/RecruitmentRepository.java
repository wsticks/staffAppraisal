package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Entity.Recruitment;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecruitmentRepository extends PagingAndSortingRepository<Recruitment, Long>, QueryDslPredicateExecutor<Recruitment>{

    Recruitment findOneByUniqueKey (String uniqueKey);
}
