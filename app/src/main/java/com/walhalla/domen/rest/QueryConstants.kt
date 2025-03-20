package com.walhalla.domen.rest

/**
 * Created by ponch on 14.03.17.
 */
object QueryConstants {
    const val DEFAULT_ANY: String = "ANY"

    @JvmField
    val shapes: Array<String> = arrayOf(
        DEFAULT_ANY, "BULLET", "CAPSULE", "CLOVER",
        "DIAMOND", "DOUBLE CIRCLE", "FREEFORM",
        "GEAR", "HEPTAGON", "HEXAGON", "OCTAGON",
        "OVAL", "PENTAGON", "RECTANGLE", "ROUND",
        "SEMI-CIRCLE", "SQUARE", "TEAR", "TRAPEZOID",
        "TRIANGLE"
    )

    @JvmField
    val scoring: Array<String> = arrayOf(
        DEFAULT_ANY, "1", "2", "3", "4"
    )

    enum class Resolution(@JvmField val value: String) {
        R_600("600"),
        R_300("300"),
        R_120("120")
    }
}
