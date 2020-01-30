package com.williams.appraisalcompany.Service;

import com.williams.appraisalcompany.Repository.RecruitmentRepository;
import com.williams.appraisalcompany.Util.SecurityUtil;
import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Entity.Recruitment;
import com.williams.appraisalcompany.model.Request.StaffSearchRequest;
import com.williams.appraisalcompany.model.Request.UpsertStaffRequest;
import com.williams.appraisalcompany.model.Response.RecruitmentResponse;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Service
public class RecruitmentService {

    private RecruitmentRepository recruitmentRepository;

    public RecruitmentService(RecruitmentRepository recruitmentRepository) {
        Assert.notNull(recruitmentRepository);
        this.recruitmentRepository = recruitmentRepository;
    }

    public RecruitmentResponse prepareStaffForSave(UpsertStaffRequest upsertStaffRequest){
        Recruitment staffToSave = upsertStaffRequest.toStaff();
        generateUniqueKey(staffToSave);
        Recruitment savedStaff = recruitmentRepository.save(staffToSave);
        RecruitmentResponse recruitmentResponse = new RecruitmentResponse();
        recruitmentResponse.setAge(savedStaff.getAge());
        recruitmentResponse.setCreatedAt(TimeUtil.getIsoTime(savedStaff.getCreatedAt()));
        recruitmentResponse.setDepartment(savedStaff.getDepartment());
        recruitmentResponse.setEmail(savedStaff.getEmail());
        recruitmentResponse.setEmployeeNumber(savedStaff.getEmployeeNumber());
        recruitmentResponse.setFirstName(savedStaff.getFirstName());
        recruitmentResponse.setEntitleNumberOfDays(savedStaff.getEntitledLeaveDays());
        recruitmentResponse.setLevel(savedStaff.getLevel());
        recruitmentResponse.setMaritalStatus(String.valueOf(savedStaff.getStatus()));
        recruitmentResponse.setMiddleName(savedStaff.getMiddleName());
        recruitmentResponse.setNameOfSchool(savedStaff.getNameOfSchool());
        recruitmentResponse.setSex(savedStaff.getSex());
        recruitmentResponse.setStatus(String.valueOf(savedStaff.getStatus()));
        recruitmentResponse.setSurname(savedStaff.getSurname());
        recruitmentResponse.setUniqueKey(savedStaff.getUniqueKey());
        return recruitmentResponse;
    }

    private void generateUniqueKey(Recruitment recruitment) throws ProcessingException {
        if (recruitment.getUniqueKey() != null) {
            return;
        }
        String rawKey = recruitment.getEmail() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        recruitment.setUniqueKey(uniqueKey);
    }

    private Recruitment fetchRecruitmentByUniqueKey(String uniqueKey){
        Recruitment savedRecruitment = recruitmentRepository.findOneByUniqueKey(uniqueKey);
        if(savedRecruitment == null){
            throw new ProcessingException("Staff not found");
        }
        return savedRecruitment;

    }

    public Recruitment fetchStaff(String uniqueKey){
        Recruitment savedStaff = fetchRecruitmentByUniqueKey(uniqueKey);
        return savedStaff;
    }

    public Page<Recruitment> findAllStaff(StaffSearchRequest request){
        return recruitmentRepository.findAll(request.getBooleanExpression(),request.getPaginationQuery());
    }

    public Recruitment prepareStaffForUpdate(Recruitment staffToSave, String uniqueKey){
        Recruitment savedStaff = fetchRecruitmentByUniqueKey(uniqueKey);
        savedStaff.setAge(staffToSave.getAge());
        savedStaff.setEmployeeNumber(staffToSave.getEmployeeNumber());
        savedStaff.setFirstName(staffToSave.getFirstName());
        savedStaff.setLevel(staffToSave.getLevel());
        savedStaff.setMaritalStatus(staffToSave.getMaritalStatus());
        savedStaff.setMiddleName(staffToSave.getMiddleName());
        savedStaff.setNameOfSchool(staffToSave.getNameOfSchool());
        savedStaff.setSex(staffToSave.getSex());
        savedStaff.setStatus(staffToSave.getStatus());
        savedStaff.setSurname(staffToSave.getSurname());
        savedStaff.setSalary(staffToSave.getSalary());
        savedStaff.setDepartment(staffToSave.getDepartment());
        return recruitmentRepository.save(savedStaff);
    }
}
