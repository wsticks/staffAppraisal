package com.williams.appraisalcompany.Repository;


import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Permission;
import org.springframework.data.querydsl.QueryDslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends PagingAndSortingRepository<Permission, Long>, QueryDslPredicateExecutor<Permission> {

    List<Permission> findAllByIdIn(List<Integer> ids);

    Permission findOneById(String id);

    List<Permission> findAllByStatus(Status status);

    List<String> findAllCodeByIdIn(List<Integer> ids);

    List<Permission> findAllByStatusAndIsHidden(Status status, Status isHidden);
}
