package com.whyuan.$6utils.config;

import com.google.common.base.Strings;
import org.slf4j.Logger;

/**
 * 通过反射创建对象
 */
public class ClassLoaderUtils {
    private static final String NOT_FOUND_ERROR      = "Unable to find %s class %s";
    private static final String ILLEGAL_ACCESS_ERROR = "Unable to access %s class %s";
    private static final String INSTANTIATION_ERROR  = "Unable to instantiate %s class %s";

    public static Object newInstance(String className, String name, Logger logger) {
        if (Strings.isNullOrEmpty(className)) {
            logger.error("A {} must be provided", name);
            throw new RuntimeException("You must provide a parser class");
        }

        try {
            Class<?> classObject = Class.forName(className);
            return classObject.newInstance();
        } catch (ClassNotFoundException ex) {
            String error = String.format(NOT_FOUND_ERROR, name, className);
            logger.error(error, ex);
            throw new RuntimeException(error, ex);
        } catch (IllegalAccessException ex) {
            String error = String.format(ILLEGAL_ACCESS_ERROR, name, className);
            logger.error(error, ex);
            throw new RuntimeException(error, ex);
        } catch (InstantiationException ex) {
            String error = String.format(INSTANTIATION_ERROR, name, className);
            logger.error(error, ex);
            throw new RuntimeException(error, ex);
        }
    }
}