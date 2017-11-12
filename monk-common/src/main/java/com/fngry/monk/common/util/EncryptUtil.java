package com.fngry.monk.common.util;

import org.apache.commons.codec.digest.DigestUtils;

/**
 *
 * 加密解密工具
 *
 * Created by gaorongyu on 16/11/30.
 */
public class EncryptUtil {

    public static String sha1Hex(String sid, String sPassword, String requestData) {
        StringBuffer origin = new StringBuffer();
        origin.append(sid).append(sPassword).append(requestData);
        return DigestUtils.sha1Hex(origin.toString());
    }

    public static String md5Hex(String content) {
        return DigestUtils.md5Hex(content);
    }

}
