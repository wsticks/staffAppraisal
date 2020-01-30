package com.williams.appraisalcompany.Repository;

import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Role;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends PagingAndSortingRepository<Role, Long>, QueryDslPredicateExecutor<Role> {

    List<Role> findAllByUniqueKey(String uniqueKey);

    List<Role> findAll();

    Role findOneByUniqueKey(String uniqueKey);

    Role findOneById(Integer id);

    List<Role> findAllByIsHidden(Status isHidden);


}
