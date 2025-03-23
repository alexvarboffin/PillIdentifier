package com.github.pdfviewer;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtils {
    public static String getMD5Hash(String filePath) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(filePath.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            //DLog.handleException(e);
            return null;
        }
    }
}
