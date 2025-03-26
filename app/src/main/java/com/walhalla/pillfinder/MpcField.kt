package com.walhalla.pillfinder

import java.io.Serializable

enum class MpcField(val value: String) : Serializable {
    //Fields
    IMPRINT("imprint"),
    COLOR("color"),
    SHAPE("shape"),
    SCORE("score"),
    SIZE("size"),


    //New
    IMPRINT_COLOR("imprintColor"),
    IMPRINT_TYPE("imprintType"),
    SYMBOL("symbol")
}
