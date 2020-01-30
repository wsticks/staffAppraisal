package com.williams.appraisalcompany.model.Request;

import com.williams.appraisalcompany.model.Constant.LeaveType;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpsertLeaveRequest {

    private String numberOfDays;
    private String startDate;
    private String endDate;
    private LeaveType leaveType;
    private Status status;
    private String employeeUniqueKey;
    private String uniqueKey;

    public Leave toLeave(){
        Leave leave = new Leave();
        leave.setNumberOfDays(numberOfDays);
        leave.setStartDate(startDate);
        leave.setEndDate(endDate);
        leave.setLeaveType(leaveType);
        leave.setStatus(status);
        if(status == null || startDate.equals("")){
            leave.setStatus(Status.NEW);
        }
        leave.setEmployeeNumber(employeeUniqueKey);
        return leave;
    }
}
