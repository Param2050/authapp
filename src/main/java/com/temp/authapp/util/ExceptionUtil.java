package com.temp.authapp.util;

import com.temp.authapp.exception.ValidationException;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Collection;
import java.util.Map;

public class ExceptionUtil {
    private ExceptionUtil() {
    }

    public static void validateNotEmpty(String s, String message) throws ValidationException {
        if (StringUtils.isEmpty(s)) {
            throw new ValidationException(message);
        }
    }

    public static void validateTrue(boolean s1, String message) throws ValidationException {
        if (!s1) {
            throw new ValidationException(message);
        }
    }

    public static void validateNotEmpty(Collection s, String message) throws ValidationException {
        if (CollectionUtils.isEmpty(s)) {
            throw new ValidationException(message);
        }
    }

    public static void validateNotEmpty(Map s, String message) throws ValidationException {
        if (CollectionUtils.isEmpty(s)) {
            throw new ValidationException(message);
        }
    }

    public static void validateEmpty(String s, String message) throws ValidationException {
        if (!StringUtils.isEmpty(s)) {
            throw new ValidationException(message);
        }
    }

    public static void validateEmpty(Collection c, String message) throws ValidationException {
        if (!CollectionUtils.isEmpty(c)) {
            throw new ValidationException(message);
        }
    }

    public static void validateNotNull(Object s, String message) throws ValidationException {
        if (s == null) {
            throw new ValidationException(message);
        }
    }

    public static void validateNull(Object s, String message) throws ValidationException {
        if (s != null) {
            throw new ValidationException(message);
        }
    }
}
