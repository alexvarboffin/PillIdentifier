package com.walhalla.pillfinder.activity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.walhalla.lib.Export
import com.walhalla.pillfinder.fragment.api2.FRepository
import com.walhalla.pillfinder.fragment.api2.FRepository.RxCallback
import com.walhalla.ui.DLog.d

class Debug : AppCompatActivity() {
    private val TAG = "@@@"


    override fun onCreate(savedInstanceState: Bundle?) {
        //setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState)

        val db = FirebaseFirestore.getInstance()
        val repository = FRepository()
        FRepository.getRxCollection1(db, object : RxCallback {
            override fun success(usersList: List<Export>) {
                if (usersList.isEmpty()) {
                    d("@@@@@@@@@@@ EMPTY @@@@@@@@@")
                }
                for (export in usersList) {
                    //DLog.d(export.mpc.color + " " + export.mpc.score+"");
                    d("@@" + export.mpc.imprint + " " + export.ingredients.active.toString())
                }
            }

            override fun error() {
            }
        })

        //twst1(db);
    }

    private fun twst1(db: FirebaseFirestore) {
        // Create a new user with a first and last name
        val user: MutableMap<String, Any> = HashMap()
        user["first"] = "Ada"
        user["last"] = "Lovelace"
        user["born"] = 1815

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(
                    TAG,
                    "DocumentSnapshot added with ID: " + documentReference.id
                )
            }
            .addOnFailureListener { e ->
                Log.w(
                    TAG,
                    "Error adding document",
                    e
                )
            }
    } //    Not Work
    //                .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, ORANGE")
    //            .whereEqualTo(FieldPath.of("mpc", "color"), "WHITE, GREEN")
}
