package com.williams.appraisalcompany.model.Response;

import com.williams.appraisalcompany.model.Constant.Status;
import lombok.*;

import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse extends  AppraisalApiResponse{

    private Status status;
    private Map<String, Object> data;
}
