package com.walhalla.lib.service;

import com.google.gson.JsonObject;
import com.walhalla.lib.datamodel.pkg0.Response0;
import com.walhalla.lib.datamodel.pkg1.Response1;

public interface RxnormRepositoryCallback
{

    void successResponse(String message);

    void successResponse(Response1 response1);

    void successResponse(int opCode, JsonObject body);

    void successResponse(Response0 response0);

    void handleThrowable(Throwable tr);
}
