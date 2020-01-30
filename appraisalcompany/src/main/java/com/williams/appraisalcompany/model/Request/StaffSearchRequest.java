package com.williams.appraisalcompany.model.Request;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.williams.appraisalcompany.model.Constant.MaritalStatus;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.QRecruitment;
import lombok.*;
import org.springframework.data.querydsl.QSort;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder@EqualsAndHashCode(callSuper = false)
public class StaffSearchRequest extends PaginatedSearchRequest {

    private final static String CREATED_AT = "createdAt";
    private final static String UPDATED_AT = "updatedAt";
    private final static String ASC = "asc";
    private final static String DESC = "desc";
    private Long toCreatedAt;
    private Long fromCreatedAt;
    private Long toUpdatedAt;
    private Long fromUpdatedAt;
    private String sortBy;
    private String surname;
    private String firstName;
    private String middleName;
    private String department;
    private String sex;
    private String age;
    private MaritalStatus maritalStatus;
    private String employeeNumber;
    private String nameOfSchool;
    private String level;
    private Status status;
    private String email;
    private String uniqueKey;
    private String lastLoginDate;
    private String sortDirection;
    private String fullText;
    private int pageNumber;
    private int pageSize;

    @Override
    public BooleanExpression getBooleanExpression() {
        QRecruitment staff = QRecruitment.recruitment;
        List<BooleanExpression> booleanExpressions = new ArrayList<>(2);
        matchUniqueKey(staff, booleanExpressions);
        matchFirstName(staff, booleanExpressions);
        matchMiddleName(staff, booleanExpressions);
        matchEmail(staff, booleanExpressions);
        matchSurname(staff, booleanExpressions);
        matchDepartment(staff, booleanExpressions);
        matchEmployeeNumber(staff, booleanExpressions);
        matchLevel(staff, booleanExpressions);
        matchMaritalStatus(staff, booleanExpressions);
        matchSex(staff, booleanExpressions);
        matchAge(staff, booleanExpressions);
        matchStatus(staff, booleanExpressions);
        matchToCreatedAt(staff, booleanExpressions);
        matchFromCreatedAt(staff, booleanExpressions);
        matchToUpdatedAt(staff, booleanExpressions);
        matchFromUpdatedAt(staff, booleanExpressions);
        return mergeMatchers(booleanExpressions);
    }

    @Override
    protected QSort computeSort() {
        QRecruitment staff = QRecruitment.recruitment;
        QSort sort = new QSort(staff.createdAt.desc());
        if (sortBy == null) {
            return sort;
        }
        if (sortBy.equals(UPDATED_AT)) {
            return sortByUpdatedAt(staff);
        }
        if (sortBy.equals(CREATED_AT)) {
            return sortByCreatedAt(staff);
        }
        return sort;
    }

    private QSort sortByUpdatedAt(QRecruitment user) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(user.updatedAt.asc());
        } else {
            return new QSort(user.updatedAt.desc());
        }
    }

    private QSort sortByCreatedAt(QRecruitment user) {
        if ((sortDirection != null) && sortDirection.toLowerCase().equals(ASC)) {
            return new QSort(user.createdAt.asc());
        } else {
            return new QSort(user.createdAt.desc());
        }
    }

    private void matchUniqueKey(QRecruitment user, List<BooleanExpression> expressions) {
        if (uniqueKey != null) {
            expressions.add(user.uniqueKey.eq(uniqueKey));
        }
    }

    private void matchDepartment(QRecruitment user, List<BooleanExpression> expressions) {
        if (department != null) {
            expressions.add(user.department.eq(department));
        }
    }

    private void matchSurname(QRecruitment user, List<BooleanExpression> expressions) {
        if (surname != null) {
            expressions.add(user.surname.eq(surname));
        }
    }

    private void matchLevel(QRecruitment user, List<BooleanExpression> expressions) {
        if (level != null) {
            expressions.add(user.level.eq(level));
        }
    }

    private void matchMaritalStatus(QRecruitment user, List<BooleanExpression> expressions) {
        if (maritalStatus != null) {
            expressions.add(user.maritalStatus.eq(maritalStatus));
        }
    }

    private void matchSex(QRecruitment user, List<BooleanExpression> expressions) {
        if (sex != null) {
            expressions.add(user.sex.eq(sex));
        }
    }

    private void matchAge(QRecruitment user, List<BooleanExpression> expressions) {
        if (age != null) {
            expressions.add(user.age.eq(age));
        }
    }

    private void matchStatus(QRecruitment user, List<BooleanExpression> expressions) {
        if (status != null) {
            expressions.add(user.status.eq(status));
        }
    }

    private void matchEmployeeNumber(QRecruitment user, List<BooleanExpression> expressions) {
        if (employeeNumber != null) {
            expressions.add(user.employeeNumber.eq(String.valueOf(employeeNumber)));
        }
    }

    private void matchFirstName(QRecruitment user, List<BooleanExpression> expressions) {
        if (firstName != null) {
            expressions.add(user.firstName.eq(firstName));
        }
    }

    private void matchMiddleName(QRecruitment user, List<BooleanExpression> expressions) {
        if (middleName != null) {
            expressions.add(user.middleName.eq(middleName));
        }
    }

    private void matchEmail(QRecruitment user, List<BooleanExpression> expressions) {
        if (email != null) {
            expressions.add(user.email.eq(email));
        }
    }

    private void matchToCreatedAt(QRecruitment user, List<BooleanExpression> expressions) {
        if (toCreatedAt != null) {
            expressions.add(user.createdAt.before(new Timestamp(toCreatedAt)));
        }
    }

    private void matchFromCreatedAt(QRecruitment user, List<BooleanExpression> expressions) {
        if (fromCreatedAt != null) {
            expressions.add(user.createdAt.after(new Timestamp(fromCreatedAt)));
        }
    }

    private void matchToUpdatedAt(QRecruitment user, List<BooleanExpression> expressions) {
        if (toUpdatedAt != null) {
            expressions.add(user.updatedAt.before(new Timestamp(toUpdatedAt)));
        }
    }

    private void matchFromUpdatedAt(QRecruitment user, List<BooleanExpression> expressions) {
        if (fromUpdatedAt != null) {
            expressions.add(user.updatedAt.after(new Timestamp(fromUpdatedAt)));
        }
    }

}
