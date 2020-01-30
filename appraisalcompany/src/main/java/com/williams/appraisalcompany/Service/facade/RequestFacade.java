package com.williams.appraisalcompany.Service.facade;


import com.williams.appraisalcompany.model.Constant.Status;
import com.williams.appraisalcompany.model.Response.SuccessResponse;
import org.springframework.data.domain.Page;

import java.util.HashMap;
import java.util.Map;

public abstract class RequestFacade {

    public SuccessResponse buildSuccessResponse(Map<String, Object> data) {
        return SuccessResponse.builder()
                .status(Status.SUCCESS)
                .data(data)
                .build();
    }

    public SuccessResponse buildPaginatedSuccessResponse(
            String contentName, Page<?> page) {
        Map<String, Object> data = new HashMap<>();
        Map<String, Object> pageData = new HashMap<>();
        pageData.put("content", page.getContent());
        pageData.put("totalPages", page.getTotalPages());
        pageData.put("pageNumber", page.getNumber());
        pageData.put("size", page.getSize());
        pageData.put("totalElements", page.getTotalElements());
        pageData.put("numberOfElements", page.getNumberOfElements());
        pageData.put("first", page.isFirst());
        pageData.put("last", page.isLast());
        data.put(contentName, pageData);
        return buildSuccessResponse(data);
    }

}
