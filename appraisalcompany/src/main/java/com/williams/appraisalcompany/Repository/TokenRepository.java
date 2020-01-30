package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Entity.Token;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends PagingAndSortingRepository<Token, Long>, QueryDslPredicateExecutor<Token>{

    Token findOneByToken(String token);

    Token findOneByUserAndToken(String userKey, String token);
}
