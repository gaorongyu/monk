package com.fngry.monk.common.id;

public interface MKIDSeqGenerator {

    long getNextSequence(ModelCode modelCode, String table);

}
