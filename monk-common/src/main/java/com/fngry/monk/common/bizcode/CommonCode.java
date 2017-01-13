package com.fngry.monk.common.bizcode;

/**
 * Created by gaorongyu on 16/11/30.
 */
public class CommonCode extends BaseBizCode {

    public static final CommonCode USER_NOT_EXIST_OR_PASSWORD_ERROR =
            new CommonCode("020101", "该用户不存在或者密码错误");

    public CommonCode(String code, String message) {
        super(code, message);
    }

}
