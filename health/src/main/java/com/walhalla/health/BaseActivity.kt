package com.walhalla.health

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "@@@: " + javaClass.simpleName)
    }

    companion object {
        private const val TAG = "@@@"
    }
}
