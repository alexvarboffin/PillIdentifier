package com.walhalla.pillfinder.activity;

import android.content.Intent;

import androidx.fragment.app.Fragment;

import com.walhalla.pillfinder.fragment.api2.RxNorm;
import com.walhalla.pillfinder.fragment.main.FragmentMain;

import java.util.HashMap;

public class Demo {

    public static void f1(MainActivity mainActivity) {

        String a = "{\"acqDate\":\"\",\"attribution\":\"\",\"id\":0,\"imageSize\":6715,\"imageUrl\":\"https://pillidentifier-dfb05.web.app/300/00093-0813-01_NLMIMAGE10_D626EB47.jpg\",\"splSetId\":\"\",\"labeler\":\"Teva Pharmaceuticals USA Inc\",\"mpc\":{\"color\":\"ORANGE\",\"imprint\":\"75;mg;93;813\",\"imprintColor\":\"BLACK\",\"imprintType\":\"PRINTED\",\"score\":1,\"shape\":\"CAPSULE\",\"size\":19,\"symbol\":false},\"name\":\"Nortriptyline 75 MG Oral Capsule\",\"ndc11\":\"00093-0813-01\",\"part\":1,\"rxcui\":198047}";
        mainActivity.showMoreInfo(a);

//        HashMap<String, String> map=new HashMap<>();
//        map.put("color", "RED");
//         = FragmentMain.newInstance(map);
//        mainActivity.replaceFragment(a);

//        Fragment aa = RxNorm.newInstance("891522");
//        mainActivity.replaceFragment(aa);

//        Intent intent=new Intent(mainActivity, Main.class);
//        intent.putExtra(Main.KEY_RXNORMID, "891522");
//        mainActivity.startActivity(intent);

    }
}
