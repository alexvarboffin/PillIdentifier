//package com.walhalla.pillfinder;
//
//import android.content.ComponentName;
//import android.content.Context;
//import android.content.Intent;
//import android.net.Uri;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//
//import androidx.test.ext.junit.runners.AndroidJUnit4;
//import androidx.test.filters.SdkSuppress;
//
//
//import androidx.test.platform.app.InstrumentationRegistry;
//import androidx.test.uiautomator.UiDevice;
//import androidx.test.uiautomator.By;
//import androidx.test.uiautomator.UiObject;
//import androidx.test.uiautomator.UiObjectNotFoundException;
//import androidx.test.uiautomator.UiSelector;
//import androidx.test.uiautomator.Until;
//
//
//
//@RunWith(AndroidJUnit4.class)
//@SdkSuppress(minSdkVersion = 18)
//
//public class ExampleInstrumentedTest {
//
//    private static final long TIMEOUT = 500;
//    private UiDevice device;
//
//    @Test
//    public void test() throws UiObjectNotFoundException {
//        // Initialize UiDevice instance
//        device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
//        Context context = InstrumentationRegistry.getInstrumentation().getTargetContext();
//
//
//        Intent intent = context.getPackageManager()
//                .getLaunchIntentForPackage("com.walhalla.pillfindel");
//        if (intent != null) {
//
//
//            intent.setAction(Intent.ACTION_MAIN);
//            intent.setComponent(new ComponentName("com.walhalla.pillfindel",
//                    "com.walhalla.pillfinder.activity.MainActivity"));
//            //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//            //intent.setData(Uri.parse("https://stackoverflow.com/"));
//            // We found the activity now start the activity
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(intent);
//        }else {
//            // Bring user to the market or let them choose an app?
//            intent = new Intent(Intent.ACTION_VIEW);
//            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            intent.setData(Uri.parse("market://details?id=" + "com.walhalla.pillfindel"));
//            context.startActivity(intent);
//        }
//
////        device.wait(Until.hasObject(By.pkg("com.android.chrome").depth(0)), TIMEOUT);
////        UiObject textBox = new UiObject(new UiSelector()
////                .className("android.widget.EditText").instance(0));
////        textBox.setText("0000000000000000");
//
//        //sout("Hello World!");
//    }
//}
