package com.langtuo.teamachine.api.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.regex.Pattern;

public class RegexUtils {
    public static boolean isValidCode(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^[A-Za-z0-9_\\-]{0,50}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLongCode(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^[A-Za-z0-9_\\-]{0,100}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidName(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{0,50}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidLongName(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{0,100}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidVersion(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^\\d+(\\.\\d+){1,2}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidComment(String input, boolean required) {
        if (StringUtils.isBlank(input)) {
            if (!required) {
                return true;
            }
        } else {
            String pattern = "^[A-Za-z0-9_\\-" +
                    "\\u4e00-\\u9fa5" + // 中文字符
                    "\\u3002" + // 。
                    "\\uff1b" + // ；
                    "\\uff0c" + // ，
                    "\\uff1a" + // ：
                    "\\u201c" + // “
                    "\\u201d" + // ”
                    "\\uff08" + // （
                    "\\uff09" + // ）
                    "\\u3001" + // 、
                    "\\uff1f" + // ？
                    "\\u300a" + // 《
                    "\\u300b" + // 》
                    "\\uFF01" + // ！
                    "]{0,200}$";
            boolean isMatch = Pattern.matches(pattern, input);
            if (isMatch) {
                return true;
            }
        }
        return false;
    }

    public static void main(String args[]) {
        String[] versions = {"pass", "pass`", "pass-", "pass="};
        for (String version : versions) {
            System.out.println(version + " is valid: " + isValidVersion(version, true));
        }
    }
}
