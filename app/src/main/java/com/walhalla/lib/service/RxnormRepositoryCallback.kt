package com.walhalla.lib.service

import com.google.gson.JsonObject
import com.walhalla.lib.datamodel.pkg0.Response0
import com.walhalla.lib.datamodel.pkg1.Response1

interface RxnormRepositoryCallback {
    fun successResponse(message: String)

    fun successResponse(response1: Response1)

    fun successResponse(opCode: Int, body: JsonObject)

    fun successResponse(response0: Response0)

    fun handleThrowable(tr: Throwable)
}
