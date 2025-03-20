package com.walhalla;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import com.google.android.material.snackbar.Snackbar;
import com.walhalla.pillfinder.R;
import com.walhalla.pillfinder.fragment.Nlmx.FileWrapper;
import com.walhalla.pillfinder.fragment.Nlmx.NlmRxFragment;

import java.io.File;
import java.lang.reflect.Method;
import java.util.List;

public class FileSystem {

    static String PDF_MIME_TYPE = MimeTypeMap.getSingleton().getMimeTypeFromExtension("pdf");

    public static void openDocument(Activity activity, FileWrapper wrapper) {

//        DLog.d(wrapper.toString());//29 {fileName='1620825170867.pdf', uri=content://media/external/downloads/173}
//        DLog.d(wrapper.uri.getPath());//29 /external/downloads/173
//        DLog.d(wrapper.uri.toString());//29 content://media/external/downloads/173 21->file://
//        DLog.d(PDF_MIME_TYPE);

        View view = activity.findViewById(android.R.id.content);
        Snackbar.make(view, R.string.generated_qr_code_already_exists, Snackbar.LENGTH_LONG)

                .setAction("Open", view2 -> {


                    if (Build.VERSION.SDK_INT >= 24) {
                        try {
                            Method m = StrictMode.class.getMethod("disableDeathOnFileUriExposure");
                            m.invoke(null);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    Intent target = new Intent(Intent.ACTION_VIEW);
                    target.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                    target.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    target.setDataAndType(
                            wrapper.uri, PDF_MIME_TYPE
                    ); // For now there is only type 1 (PDF).
                    Intent chooser = Intent.createChooser(target, activity.getString(R.string.share_data));
                    List<ResolveInfo> infos = activity.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);
                    for (ResolveInfo resolveInfo : infos) {
                        String packageName = resolveInfo.activityInfo.packageName;
                        activity.grantUriPermission(packageName, wrapper.uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    }

                    if (Build.VERSION.SDK_INT == 21) {
                        try {
                            activity.startActivity(target);
                        } catch (ActivityNotFoundException e) {
                            try {
                                Intent myIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wrapper.uri.toString()));
                                activity.startActivity(myIntent);
                            } catch (ActivityNotFoundException e0) {
                                Toast.makeText(activity, R.string.pdf_viewer_not_installed, Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                    else {
                        if (infos.size() > 0) {
                            try {
                                activity.startActivity(chooser);
                            } catch (ActivityNotFoundException e) {
                                Toast.makeText(activity, R.string.pdf_viewer_not_installed, Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(activity, R.string.pdf_viewer_not_installed, Toast.LENGTH_SHORT).show();
                        }
                    }

                })
                .show();
    }

    //29 23 22
    public static Uri streamLoader(@NonNull Activity activity, String fileName) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) //Android 10 level 29 (Android 10).
        {
            //fileUri = FileProvider.getUriForFile(this, getString(R.string.file_provider_authority), file);
            //content://media/external/images/media/147
            long currentTime = System.currentTimeMillis();
            ContentResolver resolver = activity.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf");

            contentValues.put(MediaStore.Video.Media.TITLE, "" + currentTime);
            contentValues.put(MediaStore.Video.Media.DATE_ADDED, currentTime / 1000);
            contentValues.put(MediaStore.Video.Media.DATE_TAKEN, currentTime);

            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS);
            Uri fileUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues);

//            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS);
//            fileUri = resolver.insert(MediaStore.Files.EXTERNAL_CONTENT_URI, contentValues);

            //if (fileUri != null) {
            return fileUri;
            //}
        } else {

            //File file = new File(this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), fileName0);
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), fileName);
            //File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "1.pdf");
            File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
            if (!file.exists()) {

            }

            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
                return FileProvider.getUriForFile(activity, activity.getPackageName() + ".fileprovider", file);
            } else {
                return Uri.fromFile(file);
            }
        }
    }
}
