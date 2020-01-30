package com.williams.appraisalcompany.Util;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.beans.FeatureDescriptor;
import java.beans.PropertyDescriptor;
import java.util.stream.Stream;

public class GatewayBeanUtil {

    public static String[] getNullPropertyNames(Object source) {
        final BeanWrapper wrappedSource = new BeanWrapperImpl(source);
        PropertyDescriptor[] propertyDescriptors = wrappedSource.getPropertyDescriptors();
        return Stream.of(propertyDescriptors)
                .map(FeatureDescriptor::getName)
                .filter(propertyName -> wrappedSource.getPropertyValue(propertyName) == null)
                .toArray(String[]::new);
    }

    public static void copyProperties(Object source, Object target) {
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }
}
