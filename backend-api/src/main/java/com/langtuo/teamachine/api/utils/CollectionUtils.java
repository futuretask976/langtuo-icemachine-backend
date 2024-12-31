package com.langtuo.teamachine.api.utils;

import java.util.List;

public class CollectionUtils {
    public static boolean isEmpty(List list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        return false;
    }
}
