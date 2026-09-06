package com.github.pdfviewer

import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

object HashUtils {
    @JvmStatic
    fun getMD5Hash(filePath: String): String? {
        try {
            val md = MessageDigest.getInstance("MD5")
            md.update(filePath.toByteArray())
            val digest = md.digest()
            val sb = StringBuilder()
            for (b in digest) {
                sb.append(String.format("%02x", b))
            }
            return sb.toString()
        } catch (e: NoSuchAlgorithmException) {
            //DLog.handleException(e);
            return null
        }
    }
}
