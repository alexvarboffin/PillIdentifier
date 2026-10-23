package com.walhalla.lib.service

import com.google.gson.JsonObject
//import com.walhalla.lib.datamodel.common.response.Response0
//import com.walhalla.lib.datamodel.common.response.Response1
import gov.nih.nlm.rxnorm.models.IdGroupResponse

interface RxnormRepositoryCallback {
    fun successResponse(message: String)

    //fun successResponse(response1: Response1) // unused, RxNorm properties now come from gov.nih.nlm.rxnorm DefaultApi

    fun successResponse(opCode: Int, body: JsonObject)

    //fun successResponse(response0: Response0)
    fun successResponse(response0: IdGroupResponse?) // RxNormLib.globalRequest (rxcui.json), null on non-2xx

    fun handleThrowable(tr: Throwable)
}
