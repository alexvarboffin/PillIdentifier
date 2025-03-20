package com.walhalla.pillfinder

import android.app.Activity
import android.content.pm.PackageManager
import android.os.Build
import androidx.fragment.app.Fragment
import java.util.Arrays

class PermissionUtil private constructor() {
    @Synchronized
    fun requestPermission(activity: Activity, vararg permissions: String?): Boolean {
        return requestPermission(
            null, activity,
            REQUEST_CODE_PERMISSION_DEFAULT, Arrays.asList(*permissions)
        )
    }

    //    public synchronized boolean requestPermission(Fragment fragment, String... permissions) {
    //        return requestPermission(fragment, null, REQUEST_CODE_PERMISSION_DEFAULT, Arrays.asList(permissions));
    //    }
    @Synchronized
    fun requestPermission(
        activity: Activity, requestCode: Int,
        vararg permissions: String?
    ): Boolean {
        return requestPermission(null, activity, requestCode, Arrays.asList(*permissions))
    }

    @Synchronized
    fun requestPermission(
        fragment: Fragment?,
        requestCode: Int,
        vararg permissions: String?
    ): Boolean {
        return requestPermission(fragment, null, requestCode, Arrays.asList(*permissions))
    }

    private fun requestPermission(
        fragment: Fragment?, activity: Activity?,
        requestCode: Int, permissions: MutableList<String>
    ): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }

        val permissionsNotTaken: ArrayList<String> = ArrayList()

        for (i in permissions.indices) {
            activity?.let {
                if (!isAllowed(it, permissions[i])) {
                    permissionsNotTaken.add(permissions[i])
                }
            }

        }
        if (permissionsNotTaken.isEmpty()) {
            return true
        }
        val array = arrayOfNulls<String>(permissionsNotTaken.size)
        if (fragment == null) {
            activity?.requestPermissions(permissionsNotTaken.toArray(array), requestCode)
        } else {
            fragment.requestPermissions(permissionsNotTaken.toArray(array), requestCode)
        }
        return false
    }

    fun isAllowed(activity: Activity, permission: String?): Boolean {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true
        }
        return activity.checkSelfPermission(permission!!) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        const val REQUEST_CODE_PERMISSION_DEFAULT: Int = 1
        private var sInstance: PermissionUtil? = null

        @JvmStatic
        fun on(): PermissionUtil {
            if (sInstance == null) {
                sInstance = PermissionUtil()
            }

            return sInstance!!
        }
    }
}