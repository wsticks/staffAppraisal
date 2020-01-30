package com.williams.appraisalcompany.Service.facade;

import com.williams.appraisalcompany.exception.AccessException;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PermissionHandler {

    private static final String ACCESS_DENIED = "Access Denied";

    public void hasPermission(List permissions, String permission) {
        if (!permissions.contains(permission)) {
            throw new AccessException(ACCESS_DENIED);
        }
    }

}
