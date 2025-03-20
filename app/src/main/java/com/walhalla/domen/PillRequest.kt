package com.walhalla.domen

import com.walhalla.domen.rest.QueryConstants
import com.walhalla.domen.rest.RequestMap
import com.walhalla.pillfinder.Constants
import com.walhalla.pillfinder.MpcField

enum class PillRequest {
    INSTANCE;

    @JvmField
    var optionChanged: Boolean = false

    //API Query
    var map: HashMap<String, String> = HashMap()
        private set

    init {
        this.reset()
    }

    /**
     * Not use
     */
    fun reset() {
        this.map = HashMap()
        this.mapInit()
    }

    fun mapInit() {
        //mMap = new HashMap<>();//Disable auto result...
        map[RequestMap.KEY_RESOLUTION] = QueryConstants.Resolution.R_300.value
        map[RequestMap.KEY_PAGE_SIZE] = "20"


        //        this.var0.put("includeActive", "true");//Включите в ответ данные об активных ингредиентах.
//        this.var0.put("includeInactive", "true");//Включите в ответ данные о неактивных ингредиентах.
        map["includeIngredients"] =
            "true" //Включите в ответ данные о ингредиентах.
        map["includeMpc"] = "true" //physical characteristics (MPC)


        this.optionChanged = false
    }

    fun get(key: String): String? {
        return map[key]
    }

    fun get(field: MpcField): String? {
        return map[field.value]
    }

    /**
     * Add New data in Request Map, optionChanged - trigger, ignore pagination key
     *
     */
    fun put(key: String, value: String) {
        map[key] = value

        if (Constants.FIELD_PAGE != key) {
            this.optionChanged = true
        }
    }

    fun put(places: HashMap<String, String>) {
        for ((key, value) in places) {
            //if(entry.getValue(all)){
            map[key] = value
            //}
        }
        this.optionChanged = true
    }

    fun remove(key: String): Boolean {
        val value = map.remove(key)
        if (value != null) {
            this.optionChanged = true
        }
        return (value != null)
    }

    fun remove(field: MpcField): Boolean {
        val value = map.remove(field.value)
        if (value != null) {
            this.optionChanged = true
        }
        return (value != null)
    }
}
