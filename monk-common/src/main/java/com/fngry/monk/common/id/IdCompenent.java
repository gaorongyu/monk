package com.fngry.monk.common.id;

public class IdCompenent {

    private final int startIndex;

    private final int endIndex;

    public IdCompenent(int startIndex, int endIndex) {
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public int getEndIndex() {
        return endIndex;
    }

    public int getLength() {
        return endIndex - startIndex;
    }

}
