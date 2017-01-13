package com.fngry.monk.common.util;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by gaorongyu on 16/11/30.
 */
public class StringUtil {

    public static boolean isBlank(final CharSequence cs) {
        return StringUtils.isBlank(cs);
    }

    public static String[] split(String str, String delimeter) {
        if (str == null || delimeter == null) {
            return new String[0];
        }

        List<String> result =  new ArrayList<>();
        StringTokenizer st = new StringTokenizer(str, delimeter);
        while (st.hasMoreTokens()) {
            result.add(st.nextToken());
        }
        return result.toArray(new String[0]);
    }

}
