package com.fngry.monk.common.id;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class MKID {
    private static Version CURRENT_VERSION = Version.V_01;

    private static final String DATE_FORMAT = "yyyyMMdd";

    private String id;

    private RegionCode regionCode;

    private ConditionCode conditionCode;

    private ModelCode modelCode;

    private Version version;

    private String year;

    private String month;

    private String date;

    private String seq;

    private String table;

    public RegionCode getRegionCode() {
        return regionCode;
    }

    public void setRegionCode(RegionCode regionCode) {
        this.regionCode = regionCode;
    }

    public ConditionCode getConditionCode() {
        return conditionCode;
    }

    public void setConditionCode(ConditionCode conditionCode) {
        this.conditionCode = conditionCode;
    }

    public ModelCode getModelCode() {
        return modelCode;
    }

    public void setModelCode(ModelCode modelCode) {
        this.modelCode = modelCode;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSeq() {
        return seq;
    }

    public void setSeq(String seq) {
        this.seq = seq;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public static MKID createWithExternalId(RegionCode regionCode, ModelCode modelCode, String externalId,
                                            MKIDSeqGenerator seqGenerator, boolean preProd) {
        MKID mkid = new MKID();
        mkid.regionCode = regionCode;
        mkid.version = CURRENT_VERSION;
        mkid.modelCode = modelCode;

        long hashCode = mkid.version.getSharding().hashExternalId(modelCode, Long.parseLong(externalId));
        mkid.table = StringUtils.leftPad(String.valueOf(hashCode), mkid.version.TABLE.getLength(), "0");

        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern(DATE_FORMAT));
        mkid.year = dateStr.substring(0, 2);
        mkid.month = dateStr.substring(2, 4);
        mkid.date = dateStr.substring(4, 6);

        long seq = seqGenerator.getNextSequence(modelCode, mkid.table);
        mkid.seq = stringifySeq(seq, mkid.version.TABLE.getLength());
        return mkid;
    }

    private static String stringifySeq(long seq, int length) {
        seq &= Long.MAX_VALUE;
        seq %= Math.pow(10, length);
        return StringUtils.leftPad(String.valueOf(seq), length, "0");
    }

}
