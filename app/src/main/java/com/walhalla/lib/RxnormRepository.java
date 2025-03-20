package com.walhalla.lib;

import androidx.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import com.walhalla.lib.datamodel.pkg0.Response0;
import com.walhalla.lib.datamodel.pkg1.Response1;
import com.walhalla.lib.datamodel.pkg5.Response5;
import com.walhalla.lib.datamodel.pkg6.Response6;
import com.walhalla.lib.service.RxnormApi;
import com.walhalla.lib.service.RxnormRepositoryCallback;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RxnormRepository {


    private static String pd;
    public static final String RX_NAV_CALLER = "RxNav";
    private static RxnormRepository repo;
    private final RxnormRepositoryCallback callback;

    //ALL "ATC1-4","CHEM","DISEASE","DISPOS","EPC","MESHPA","MOA","PE","PK","SCHEDULE","STRUCT","TC","VA"

    public static final String ENDPOINT_RXNAV = "https://rxnav.nlm.nih.gov/REST/";

    private static final long KEY_TIMEOUT = 60;
    private static final String CLASS_ALL = "";
    private final RxnormApi api;


    public RxnormRepository(RxnormApi api, RxnormRepositoryCallback callback) {
        this.api = api;
        this.callback = callback;
    }

//    public static void main(String[] args) {
//
//        okhttp3.logging.HttpLoggingInterceptor logging = new okhttp3.logging.HttpLoggingInterceptor();
//        logging.setLevel(okhttp3.logging.HttpLoggingInterceptor.Level.BASIC);
//
//        Gson gson = new GsonBuilder()
//                .setLenient()
//                .create();
//        OkHttpClient.Builder builder = new OkHttpClient.Builder()
//                .addInterceptor(logging)
//                .followRedirects(true)
//                .followSslRedirects(true)
//                .retryOnConnectionFailure(true)
//                .cache(null)
//                .connectTimeout(KEY_TIMEOUT, TimeUnit.SECONDS)
//                .writeTimeout(KEY_TIMEOUT, TimeUnit.SECONDS)
//                .readTimeout(KEY_TIMEOUT, TimeUnit.SECONDS);
//        OkHttpClient client = builder.build();
//
//        Retrofit RETROFIT_RX = new Retrofit.Builder()
//                .baseUrl(ENDPOINT_RXNAV)
//                .addConverterFactory(GsonConverterFactory.create(gson))
//                .client(client)
//                .build();
//
//        RxnormApi api = RETROFIT_RX.create(RxnormApi.class);
//        //Call<JsonObject> aa = api.searchDrugsBase("Lipitor");
//        //Call<JsonObject> aa = api.getClassTypes();
//
//
//        //Call<JsonObject> aa = api.findClassByName("radiopharmaceuticals", CLASS_ALL);
//
//
//        repo = new RxnormRepository(api, new RxnormRepositoryCallback() {
//            @Override
//            public void successResponse(String message) {
//                //sout("[*] " + message);
//            }
//
//            @Override
//            public void successResponse(Response1 response1) {
//                //sout(response1.properties.toString());
//            }
//
//            @Override
//            public void successResponse(int opCode, JsonObject body) {
//                //sout(opCode + " " + body);
//            }
//
//            @Override
//            public void successResponse(Response0 response0) {
//                repo.next(pd);
//            }
//
//            @Override
//            public void handleThrowable(Throwable tr) {
//                //sout(tr.toString());
//            }
//        });
//        //repo.globalRequest("Xanax");
//
//
//    }

    public void globalRequest(String query) {
        Call<Response0> aa = api.searchRxcui(query, 2, 1);//catalase
        //aa.enqueue(new Task111(0, callback, this));
        aa.enqueue(new Callback<Response0>() {
            @Override
            public void onResponse(@NonNull Call<Response0> call, Response<Response0> response) {
                Response0 response0 = response.body();
                if (callback != null) {
                    callback.successResponse(response0);
                }
            }

            @Override
            public void onFailure(@NonNull Call<Response0> call, Throwable t) {
                if (callback != null) {
                    callback.handleThrowable(t);
                }
            }
        });
    }

    static Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static class Task111 implements Callback<JsonObject> {

        private final int opCode;
        private final RxnormRepositoryCallback callback;
        private final RxnormRepository aaa;


        public Task111(int opCode, RxnormRepositoryCallback callback, RxnormRepository aaa) {
            this.opCode = opCode;
            this.callback = callback;
            this.aaa = aaa;
        }

        @Override
        public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
            switch (opCode) {
//                case 0:
//                    JsonObject body = response.body();
//                    Response0 response0 = gson.fromJson(body.toString(), Response0.class);
//
//                    IdGroup mm = response0.idGroup;
//                    List<String> rxnormId = mm.rxnormId;
//                    pd = rxnormId.get(0);
//
//                    callback.successResponse(opCode, body);
//                    callback.successResponse(pd);
//                    aaa.next(pd);
//                    break;

                case 2:
                    JsonObject body2 = response.body();
                    //sout("@@@@@@@@@@@@@@@@@@@@@@@@@@" + body2.toString().contains("Aceclofenac may decrease"));
                    //callback.successResponse(opCode, body2);
                    break;

                case 3:
                    JsonObject body3 = response.body();
                    //sout("@@@@@@@@@@@@@@@@@@@@@@@@@@" + body3.toString().contains("Aceclofenac may decrease"));
                    //callback.successResponse(opCode, body3);
                    //AttributesResponse aa = gson.fromJson(body3.toString(), AttributesResponse.class);
                    ////sout(gson.toJson(aa));

                    break;

                case 4:
                    JsonObject body4 = response.body();
                    //sout("@@@@@@@@@@@@@@@@@@@@@@@@@@" + body4.toString().contains("Aceclofenac may decrease"));
                    //callback.successResponse(opCode, body4);
                    break;

                case 6:
                    //Interactions.contains("Aceclofenac may decrease")
                    JsonObject body6 = response.body();
                    if (body6 != null) {
                        Response6 aa = gson.fromJson(body6.toString(), Response6.class);

                        callback.successResponse(opCode, body6);
                    }

                    break;

                case 7:
                    JsonObject jsonObject = response.body();//Status
                    //sout("@@@@@@@@@@@@@@@@@@@@@@@@@@" + jsonObject.toString().contains("Aceclofenac may decrease"));
                    //callback.successResponse(opCode, jsonObject);
                    break;

                case 8:
                    JsonObject body8 = response.body();
                    //sout("@@@@@@@@@@@@@@@@@@@@@@@@@@" + body8.toString().contains("Aceclofenac may decrease"));
                    //callback.successResponse(opCode, body8);
                    break;

                case 9:
                    JsonObject body9 = response.body();
                    callback.successResponse(opCode, body9);
                    break;


                case 10:
                    JsonObject body10 = response.body();
                    callback.successResponse(opCode, body10);
                    //InteractionResponse aaa = gson.fromJson(body10.toString(), InteractionResponse.class);
                    ////sout(gson.toJson(aaa));
                    break;

                case 11:
                    JsonObject body11 = response.body();
                    callback.successResponse(opCode, body11);
                    break;


            }

        }

        @Override
        public void onFailure(@NonNull Call<JsonObject> call, @NonNull Throwable t) {

        }
    }

    private void next(String pd) {
//1 [-]
//        Call<Response1> call2 = api.searchRxcuiProp(pd, RX_NAV_CALLER);
//        call2.enqueue(new Task111(1, callback, this));

//        Call<JsonObject> call03 = api.allrelatedextension(pd, RX_NAV_CALLER);
//        call03.enqueue(new Task111(2, callback, this));

//        Call<Response4> call4 = api.allProperties(pd, RX_NAV_CALLER, "Attributes");
//        call4.enqueue(new Task111(3, callback, this));

        Call<JsonObject> call5 = api.ndcproperties(pd, RX_NAV_CALLER);
        call5.enqueue(new Task111(4, callback, this));


//        Call<Response6> call7 = api.interaction(pd, "DrugBank");
//        call7.enqueue(new Task111(6, callback, this));

//        Call<JsonObject> call8 = api.historystatus(pd, RX_NAV_CALLER);
//        call8.enqueue(new Task111(7, callback, this));

        Call<JsonObject> call9 = api.byRxcui(pd);
        call9.enqueue(new Task111(8, callback, this));

        Call<JsonObject> call10 = api.allhistoricalndcs(pd, RX_NAV_CALLER, 2);
        call10.enqueue(new Task111(9, callback, this));

        Call<JsonObject> call11 = api.interaction(pd, RX_NAV_CALLER, "onchigh");
        call11.enqueue(new Task111(10, callback, this));

    }

    //[0]https://rxnav.nlm.nih.gov/REST/rxcui?caller=RxNav&name=catalase
    //[1]https://rxnav.nlm.nih.gov/REST/rxcui/2133/properties.json?caller=RxNav
    //[2]https://rxnav.nlm.nih.gov/REST/rxcui/2133/allrelatedextension?caller=RxNav
    //[3]https://rxnav.nlm.nih.gov/REST/rxcui/2133/allProperties?caller=RxNav&prop=Attributes
    //[4]https://rxnav.nlm.nih.gov/REST/ndcproperties?caller=RxNav&id=2133
    //[5] https://rxnav.nlm.nih.gov/REST/RxTerms/rxcui/198440/allinfo?caller=RxNav

    //[6] https://rxnav.nlm.nih.gov/REST/interaction/interaction.json?caller=RxNav&rxcui=202363&sources=DrugBank
    //    https://rxnav.nlm.nih.gov/REST/interaction/interaction?caller=RxNav&rxcui=2133&sources=DrugBank
    //    https://rxnav.nlm.nih.gov/REST/interaction/interaction?caller=RxNav&rxcui=1186300&sources=DrugBank

    //[7] https://rxnav.nlm.nih.gov/REST/rxcui/202363/historystatus.json?caller=RxNav
    //[8] https://rxnav.nlm.nih.gov/REST/rxclass/class/byRxcui.json?rxcui=2133
    //[9] https://rxnav.nlm.nih.gov/REST/rxcui/2133/allhistoricalndcs?caller=RxNav&history=2
    //[10] https://rxnav.nlm.nih.gov/REST/interaction/interaction?caller=RxNav&rxcui=2133&sources=onchigh


    //@@ https://rxnav.nlm.nih.gov/REST/reformulationConcepts?caller=RxNav
    //@@ https://rxnav.nlm.nih.gov/REST/idtypes.json?caller=RxNav
    //@@ https://rxnav.nlm.nih.gov/REST/displaynames.json?caller=RxNav


    //@@ daylimed
    //https://dailymed.nlm.nih.gov/dailymed/autocomplete.cfm?key=search&returntype=json&term=xanax
    //https://dailymed.nlm.nih.gov/dailymed/search.cfm?labeltype=all&query=xanax


}