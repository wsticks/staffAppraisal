package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.Util.TimeUtil;
import com.williams.appraisalcompany.model.Constant.LeaveType;
import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Entity.Leave;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LeaveResponse {

    private String uniqueKey;
    private String numberOfDays;
    private String startDate;
    private String endDate;
    private LeaveType leaveType;
    private String employeeNumber;
    private String pendingLeaveDays;
    private Status status;
    private String createdAt;
    private String updatedAt;

    public static LeaveResponse fromLeaveUpdate(Leave leave){
        return LeaveResponse.builder()
                .uniqueKey(leave.getUniqueKey())
                .numberOfDays(leave.getNumberOfDays())
                .startDate(leave.getStartDate())
                .endDate(leave.getEndDate())
                .leaveType(leave.getLeaveType())
                .employeeNumber(leave.getEmployeeNumber())
                .pendingLeaveDays(leave.getPendingLeaveDays())
                .status(leave.getStatus())
                .createdAt(TimeUtil.getIsoTime(leave.getCreatedAt()))
                .updatedAt(TimeUtil.getIsoTime(leave.getUpdatedAt()))
                .build();
    }

    public static List<LeaveResponse> fromLeaveUpdates(List<Leave> leaves) {
        return leaves.stream().map(leave -> {
            return fromLeaveUpdate(leave);
        }).collect(
                Collectors.toList());
    }


}
