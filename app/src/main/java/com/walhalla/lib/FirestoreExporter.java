package com.walhalla.lib;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class FirestoreExporter {

    static final String[] data = "a,b,c,d".split(",");


    public static void main(String[] args) {
        FirestoreExporter e = new FirestoreExporter();
        e.make("D:\\walhalla\\PillIdentifier\\rximagesAll.json");

        //e.permutation(0);

//        CircleArray array = new CircleArray(5);
//        for (int i = 0; i < 100; i++) {
//            array.pull("" + i);
//        }
//        int[] input = {10, 20, 30, 40, 50, 0};
//
//        input = ashift(input);
//        input = ashift(input);
//        input = ashift(input);
//        input = ashift(input);
//        input = ashift(input);
//        input = ashift(input);
//        input = ashift(input);


//        String value="abc";
//        //sout(""+'\uf8ff'+ value+'\uf8ff');
    }

    private static int[] ashift(int[] input) {
        //sout(Arrays.toString(input));

        int[] raw = new int[input.length];
        raw[0] = input[input.length - 1];//Set last in first
        for (int i = 1; i < input.length; i++) {
            raw[i] = input[i - 1];
        }
        return raw;
    }

//    private List<int[]> ee() {
//        int[] input = {10, 20, 30, 40, 50, 0};    // input array
//        int k = 5;                             // sequence length
//
//        List<int[]> subsets = new ArrayList<>();
//
//        int[] s = new int[k];                  // here we'll keep indices
//        // pointing to elements in input array
//
//        if (k <= input.length) {
//            // first index sequence: 0, 1, 2, ...
//            for (int i = 0; (s[i] = i) < k - 1; i++);
//            subsets.add(getSubset(input, s));
//            for(;;) {
//                int i;
//                // find position of item that can be incremented
//                for (i = k - 1; i >= 0 && s[i] == input.length - k + i; i--);
//                if (i < 0) {
//                    break;
//                }
//                s[i]++;                    // increment this item
//                for (++i; i < k; i++) {    // fill up remaining items
//                    s[i] = s[i - 1] + 1;
//                }
//                subsets.add(getSubset(input, s));
//            }
//        }
//return subsets;
//    }


    // generate actual subset by index sequence
    int[] getSubset(int[] input, int[] subset) {
        int[] result = new int[subset.length];
        for (int i = 0; i < subset.length; i++)
            result[i] = input[subset[i]];
        return result;
    }


    private void make(String inputFile) {
        Gson gson = new GsonBuilder().create();
        Reader reader = null;
        try {
            reader = new BufferedReader(new FileReader(inputFile));
            Type listType = new TypeToken<List<Export>>() {
            }.getType();
            List<Export> data = gson.fromJson(reader, listType);
            Map<String, JsonObject> hashMap = new HashMap<>();

            Set<String> hashSet = new TreeSet<>();

            for (int i = 0; i < data.size(); i++) {
                ////sout(data.get(i).id.oid);
                JsonObject jsonObject = new JsonParser().parse(gson.toJson(data.get(i))).getAsJsonObject();

                String[] imprints = data.get(i).mpc.imprint.split(";");
                hashSet.add(Arrays.toString(imprints));
                JsonArray jsonArray = new JsonParser().parse(gson.toJson(imprints)).getAsJsonArray();
                jsonObject.add("imp", jsonArray);
                jsonObject.addProperty("attribution", "");

                hashMap.put(data.get(i).id.oid, jsonObject);

            }

            for (String s : hashSet) {
                //sout(s);
            }

            JsonObject jsonObject = new JsonObject();
            JsonElement jsonElement = gson.toJsonTree(hashMap);
            jsonObject.add("rximages", jsonElement);

            ////sout(jsonObject);

            FileWriter fileWriter = new FileWriter(
                    "C:\\Users\\combo\\Desktop\\firestore\\Pillidentifier\\rximagesAll.json");
            fileWriter.write(jsonObject.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private void wrapper(Export export) {
        //JsonObject jsonObject = new JsonObject();
        //JsonObject jsonObject = new JsonParser().parse("{\"name\": \"John\"}").getAsJsonObject();

//        jsonObject.addProperty("rxnavImageSize300", export.rxnavImageSize300);
//
//        //Integer rxnavImageSize300;
//        jsonObject.addProperty("labeler", export.labeler);
//
//        //String labeler;
//        jsonObject.addProperty("nlmImageSize800", export.nlmImageSize800);
//
//        //Integer nlmImageSize800;
//        jsonObject.addProperty("rxcui", export.rxcui);
//
//        //Integer rxcui;
//        jsonObject.addProperty("ingredientsAvailable", export.ingredientsAvailable);
//
//        //Boolean ingredientsAvailable;
//        jsonObject.addProperty("nlmImageSizeFull", export.nlmImageSizeFull);
//
//        //Integer nlmImageSizeFull;
//        jsonObject.addProperty("rxnavImageFileName", export.rxnavImageFileName);
//
//        //String rxnavImageFileName;
//        jsonObject.addProperty("acqDate", export.acqDate);
//
//        //String acqDate;
//        jsonObject.addProperty("nlmImageSize600", export.nlmImageSize600);
//
//        //Integer nlmImageSize600;
//        jsonObject.addProperty("rxnavImageSize120", export.rxnavImageSize120);
//
//        //Integer rxnavImageSize120;
//        jsonObject.addProperty("rxnavImageObjectId", export.rxnavImageObjectId);
//
//        //Integer rxnavImageObjectId;
//        jsonObject.addProperty("deaSchedule", export.deaSchedule);
//
//        //String deaSchedule;
//        jsonObject.addProperty("rxnavImageSize600", export.rxnavImageSize600);
//
//        //Integer rxnavImageSize600;
//        jsonObject.addProperty("rxnavImageSizeFull", export.rxnavImageSizeFull);
//
//        //Integer rxnavImageSizeFull;
//        jsonObject.addProperty("rxnavImageSize1024", export.rxnavImageSize1024);
//
//        //Integer rxnavImageSize1024;
//        jsonObject.addProperty("attribution", export.attribution);
//
//        //String attribution;
//        jsonObject.addProperty("rxnavImageSize", export.rxnavImageSize);
//
//        //Integer rxnavImageSize;
//        jsonObject.addProperty("nlmImageSize120", export.nlmImageSize120);
//
//        //Integer nlmImageSize120;
//        jsonObject.addProperty("nlmImageSize300", export.);
//
//        //Integer nlmImageSize300;
//        jsonObject.addProperty("ndc11", export.);
//
//        //String ndc11;
//        jsonObject.addProperty("nlmImageSize", export.);
//
//        //Integer nlmImageSize;
//        jsonObject.addProperty("part", export.);
//
//        //Integer part;
//        jsonObject.addProperty("ingredients", export.);
//
//        //Ingredients ingredients;
//        jsonObject.addProperty("nlmImageFileName", export.);
//
//        //String nlmImageFileName;
//        jsonObject.addProperty("_id", export.);
//
//        //Id id;
//        jsonObject.addProperty("imageSize", export.);
//
//        //Integer imageSize;
//        jsonObject.addProperty("rxnavImageSize800", export.);
//
//        //Integer rxnavImageSize800;
//        jsonObject.addProperty("mpc", export.);
//
//        //Mpc mpc;
//        jsonObject.addProperty("name", export.);
//
//        //String name;
//        jsonObject.addProperty("nlmImageObjectId", export.);
//
//        //Integer nlmImageObjectId;
    }
}
