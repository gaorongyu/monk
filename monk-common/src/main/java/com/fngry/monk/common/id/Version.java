package com.fngry.monk.common.id;

public enum Version {

    V_01("01", 27, Sharding.V_01,
            new IdCompenent(0, 2),
            new IdCompenent(2, 4),
            new IdCompenent(4, 5),
            new IdCompenent(5, 9),
            new IdCompenent(9, 13),
            new IdCompenent(13, 15),
            new IdCompenent(15, 17),
            new IdCompenent(17, 19),
            new IdCompenent(19, 27)
            );

    private final String code;

    private final int length;

    public final IdCompenent REGION;
    public final IdCompenent VERSION;
    public final IdCompenent CONDITION;
    public final IdCompenent MODEL;
    public final IdCompenent TABLE;
    public final IdCompenent YEAR;
    public final IdCompenent MONTH;
    public final IdCompenent DATE;
    public final IdCompenent SEQ;

    private final Sharding sharding;

    Version(String code, int length, Sharding sharding, IdCompenent... compenents) {
        this.code = code;
        this.length = length;
        this.sharding = sharding;

        REGION = compenents[0];
        VERSION = compenents[1];
        CONDITION = compenents[2];
        MODEL = compenents[3];
        TABLE = compenents[4];
        YEAR = compenents[5];
        MONTH = compenents[6];
        DATE = compenents[7];
        SEQ = compenents[8];
    }

    public Sharding getSharding() {
        return sharding;
    }
}
