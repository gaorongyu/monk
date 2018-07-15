package com.fngry.monk.common.id;

public enum ConditionCode {
    NORMAL("0"),
    PRE_PROD("1");

    private final String code;

    ConditionCode(String code) {
        this.code = code;
    }

    public static ConditionCode get(String code) {
        for (ConditionCode e : ConditionCode.values()) {
            if (e.code.equals(code)) {
                return e;
            }
        }
        return null;
    }

}
