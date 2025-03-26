package com.walhalla.pillfinder.fragment.api2;

import android.text.SpannableStringBuilder;
import android.view.View;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FieldPath;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.walhalla.Util;
import com.walhalla.lib.Export;
import com.walhalla.pillfinder.Constants;
import com.walhalla.pillfinder.MpcField;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.fragment.main.FragmentMain;
import com.walhalla.ui.DLog;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import gov.nih.nlm.model.ModelObject;
import gov.nih.nlm.model.NlmRxImage;
import gov.nih.nlm.model.ReplyStatus;

public class FRepository {

    private static final String COLLECTION_RX = "rximages";

//    public void getRxCollection1(FirebaseFirestore db, RxCallback callback) {
//        List<Export> usersList = new ArrayList<>();
//
//
//        //colorFilter.add("WHITE, ORANGE");colorFilter.add("WHITE, GREEN");//Success
//        List<String> list2 = new ArrayList<>();
//        list2.add("fluoxetine hydrochloride 10 mg");
//
//
//        Query query = db.collection(COLLECTION_RX) //get all the users
//                .limit(15)
//                .whereArrayContains(FieldPath.of("ingredients", "active"),"fluoxetine%");
//
//        query.get()
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        if (task.getResult() != null) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                usersList.add(document.toObject(Export.class));
//                            }
//                        } else {
//                            DLog.d("@@@@ Error getting documents." + task.getException());
//                        }
//                        DLog.d("@@@@" + task.getResult().getMetadata());
//                    }
//
//                    callback.success(usersList);
//                });
//    }


    public interface RxCallback {

        void success(List<Export> usersList);

        void error();
    }

    public FRepository() {
    }

    public void test0(FirebaseFirestore db, List<String> colorFilter, RxCallback callback) {
        List<Export> usersList = new ArrayList<>();

        CollectionReference collection = db.collection(COLLECTION_RX);
        Query query = collection.limit(3);

//                .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, ORANGE")
//                .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, GREEN")
        if (colorFilter != null) {
            query = query.whereIn(FieldPath.of("mpc", "color"), colorFilter);
        }
        query = query.whereEqualTo(FieldPath.of("mpc", "score"), 1);
//                .whereIn(FieldPath.of("mpc", "color"), list2)

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usersList.add(document.toObject(Export.class));
                            }
                        } else {
                            DLog.d("@@@@ Error getting documents." + task.getException());
                        }
                        DLog.d("@@@@" + task.getResult().getMetadata());
                    }

                    callback.success(usersList);
                });
    }

    public static void getRxCollection1(FirebaseFirestore db, RxCallback callback) {
        List<Export> usersList = new ArrayList<>();
        String value = "162".toUpperCase();
        Query query = db.collection(COLLECTION_RX) //get all the users
                .limit(3)
                .orderBy(FieldPath.of("mpc", MpcField.IMPRINT.value))
                .startAt(""+'\uf8ff')
                .endAt(value+'\uf8ff');//"" + value + '\uf8ff'    [ok] => ""+'\uf8ff'

        query.get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usersList.add(document.toObject(Export.class));
                            }
                        } else {
                            DLog.d("@@@@ Error getting documents." + task.getException());
                        }
                        DLog.d("@@@@" + task.getResult().getMetadata());
                    }

                    callback.success(usersList);
                });
    }

    public static void getRxCollection(FirebaseFirestore db, RxCallback callback) {

        List<Export> usersList = new ArrayList<>();


        //colorFilter.add("WHITE, ORANGE");colorFilter.add("WHITE, GREEN");//Success
        List<String> colorFilter = new ArrayList<>();
        colorFilter.add("WHITE, GREEN");
        colorFilter.add("GREEN, WHITE");


        Query query = db.collection(COLLECTION_RX) //get all the users
                .limit(15)
//                .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, ORANGE")
//                .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, GREEN")
                .whereIn(FieldPath.of("mpc", "color"), colorFilter);

        query.whereEqualTo(FieldPath.of("mpc", "score"), 1)
//                .whereIn(FieldPath.of("mpc", "color"), list2)

                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        if (task.getResult() != null) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usersList.add(document.toObject(Export.class));
                            }
                        } else {
                            DLog.d("@@@@ Error getting documents." + task.getException());
                        }
                        DLog.d("@@@@" + task.getResult().getMetadata());
                    }

                    callback.success(usersList);
                });
    }

}
