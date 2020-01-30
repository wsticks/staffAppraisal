package com.williams.appraisalcompany.Service;

import com.williams.appraisalcompany.Repository.LeaveRepository;
import com.williams.appraisalcompany.Repository.RecruitmentRepository;
import com.williams.appraisalcompany.Util.SecurityUtil;
import com.williams.appraisalcompany.exception.ProcessingException;
import com.williams.appraisalcompany.model.Entity.Leave;
import com.williams.appraisalcompany.model.Entity.Recruitment;
import com.williams.appraisalcompany.model.Request.UpsertLeaveRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LeaveService {

    private LeaveRepository leaveRepository;
    private RecruitmentRepository recruitmentRepository;

    public LeaveService(LeaveRepository leaveRepository,
                        RecruitmentRepository recruitmentRepository) {
        Assert.notNull(leaveRepository);
        Assert.notNull(recruitmentRepository);
        this.leaveRepository = leaveRepository;
        this.recruitmentRepository = recruitmentRepository;
    }



    public Leave prepareLeaveForSave(UpsertLeaveRequest upsertLeaveRequest, Recruitment recruitment){
        Leave leaveToSave =upsertLeaveRequest.toLeave();
        if(Integer.parseInt(leaveToSave.getNumberOfDays()) > Integer.parseInt(recruitment.getEntitledLeaveDays())){
            throw new ProcessingException("Number of leave days exceeds the entitled days");
        }
        Integer lastestNumberOfDays =
                Integer.parseInt(recruitment.getEntitledLeaveDays())- Integer.parseInt(leaveToSave.getNumberOfDays());
        recruitment.setEntitledLeaveDays(String.valueOf(lastestNumberOfDays));
        recruitmentRepository.save(recruitment);
        generateUniqueKey(leaveToSave);
        return leaveRepository.save(leaveToSave);
    }

    private void generateUniqueKey(Leave leave) throws ProcessingException {
        if (leave.getUniqueKey() != null) {
            return;
        }
        String rawKey = leave.getEmployeeNumber() + leave.getEndDate() + LocalDateTime.now() + Math.random();
        String uniqueKey = SecurityUtil.hashWithMd5(rawKey);
        leave.setUniqueKey(uniqueKey);
    }

    public Leave fetchLeaveByUniqueKey(String uniqueKey){
        Leave savedLeave = leaveRepository.findOneByUniqueKey(uniqueKey);
        if(savedLeave == null){
            throw new ProcessingException("Leave not found");
        }
        return savedLeave;
    }

    public List<Leave> fetchLeaveByEmployeeUniqueKey(String uniqueKey){
        List<Leave> savedLeave = leaveRepository.findAllByEmployeeNumber(uniqueKey);
        return savedLeave;
    }

    public List<Leave> findAllLeaves() {
        return (List<Leave>) leaveRepository.findAll();
    }

    public Leave  prepareLeaveForUpdate( Leave leaveToSave, String uniqueKey){
        Leave savedLeave = fetchLeaveByUniqueKey(uniqueKey);
        savedLeave.setStatus(leaveToSave.getStatus());
        return leaveRepository.save(savedLeave);
    }

}

