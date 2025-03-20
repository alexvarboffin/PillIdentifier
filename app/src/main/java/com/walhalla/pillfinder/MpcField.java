package com.walhalla.pillfinder;

import java.io.Serializable;

public enum MpcField implements Serializable {
    //Fields
    IMPRINT("imprint"),
    COLOR("color"),
    SHAPE("shape"),
    SCORE("score"),
    SIZE("size"),


    //New
    IMPRINT_COLOR("imprintColor"),
    IMPRINT_TYPE("imprintType"),
    SYMBOL("symbol");

    public String getValue() {
        return value;
    }

    private final String value;

    MpcField(String value) {
        this.value = value;
    }
}
