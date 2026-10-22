package com.walhalla.pillfinder.fragment.api2

import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.walhalla.lib.Export
import com.walhalla.pillfinder.MpcField
import com.walhalla.pillfinder.fragment.api2.FRepository.RxCallback
import com.walhalla.ui.DLog.d
import java.util.Locale

fun getRxCollection1(db: FirebaseFirestore, callback: RxCallback) {
    val usersList: MutableList<Export?> = ArrayList<Export?>()
    val value = "162".uppercase(Locale.getDefault())
    val query = db.collection(FRepository.COLLECTION_RX) //get all the users
        .limit(3)
        .orderBy(FieldPath.of("mpc", MpcField.IMPRINT.value))
        .startAt("" + '\uf8ff')
        .endAt(value + '\uf8ff') //"" + value + '\uf8ff'    [ok] => ""+'\uf8ff'

    query.get()
        .addOnCompleteListener(OnCompleteListener { task: Task<QuerySnapshot?>? ->
            if (task!!.isSuccessful) {
                if (task.getResult() != null) {
                    for (document in task.getResult()!!) {
                        usersList.add(document.toObject<Export?>(Export::class.java))
                    }
                } else {
                    d("@@@@ Error getting documents." + task.exception)
                }
                d("@@@@" + task.getResult()!!.metadata)
            }
            callback.success(usersList)
        })
}