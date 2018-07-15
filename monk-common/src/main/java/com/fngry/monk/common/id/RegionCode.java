package com.fngry.monk.common.id;

public enum RegionCode {

    HZ("11", 11L, "HZ");

    /**
     * part of biz model id
     */
    private final String code;

    /**
     * for DRC sync
     */
    private final long routerId;

    private final String regionNo;

    RegionCode(String code, long routerId, String regionNo) {
        this.code = code;
        this.routerId = routerId;
        this.regionNo = regionNo;
    }

}
