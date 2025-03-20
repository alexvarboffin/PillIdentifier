-keep public class com.android.vending.licensing.ILicensingService
-keep class com.halodoc.TestClass

#-printmapping mapping.txt
#-printmapping
-optimizationpasses 2
#-optimizationpasses 5
#-optimizationpasses 30
-printconfiguration full-r8-config.txt

#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

#Other - D:\android\GitHub\facebook\
#D:\\source\\CallRecorder\\app\\


#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontpreverify
#-verbose
#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*

# Allow methods with the same signature, except for the return type,
# to get the same obfuscation name.

#-overloadaggressively



#-mergeinterfacesaggressively
#-dontpreverify
#-optimizations !code/simplification/arithmetic
## Put all obfuscated classes into the nameless root package.
#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-repackageclasses 'con'#res

#-useuniqueclassmembernames
#-keeppackagenames doNotKeepAThing
#
## Allow classes and class members to be made public.
#
#-allowaccessmodification

-obfuscationdictionary "D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt"

#D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt
-classobfuscationdictionary "D:\dropbox\dictionary\classobfuscation-compact.txt"
-packageobfuscationdictionary "D:\dropbox\dictionary\packageobfuscation-compact.txt"


#@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@-flattenpackagehierarchy 'xxx'#xxxxx


#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class com.android.vending.licensing.ILicensingService

#Datamodel
-keep class com.walhalla.lib.datamodel.* { *; }

#-dontnote
# OkHttp and Servlet optional dependencies
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.**

-dontwarn com.google.appengine.**
-dontwarn javax.servlet.**

# Support classes for compatibility with older API versions

-dontwarn android.support.**
-dontnote android.support.**

-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

-keep class android.support.v7.widget.SearchView { *; }

-printconfiguration config.txt

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-assumenosideeffects class android.util.Log{
    public static *** d(...);
    public static *** v(...);
}


#You can remove logging calls with this option in proguard-project.txt:
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String, int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
-dontwarn okio.**

#-dontwarn retrofit2.Platform$Java8
#-dontwarn retrofit.Platform$Java8
#-dontwarn retrofit2.Platform$Java8
#-dontwarn rx.internal.util.**

# -keepclassmembers class com.psyberia.sms_regcom.rest.beans.** {
#   *;
# }

-dontnote android.net.http.*
-dontnote org.apache.commons.codec.**
-dontnote org.apache.http.**

 #Firebase SDK 2.0.0:

-keep class com.firebase.** { *; }


-keep class org.apache.** { *; }

-keepnames class com.fasterxml.jackson.** { *; }
-keepnames class javax.servlet.** { *; }
-keepnames class org.ietf.jgss.** { *; }
-dontwarn org.w3c.dom.**
-dontwarn org.joda.time.**
-dontwarn org.shaded.apache.**
-dontwarn org.ietf.jgss.**


#-keep class com.dropbox.core.** {*;}
#-keep class com.dropbox.** {*;}


-keep class com.google.common.** {*;}
-keep class com.square.picasso.** {*;}
-keep class javax.servlet.http.** {*;}

-keep class okio.Okio.** {*;}


#-keep class com.google.android.gms.auth.** {*;}
#-keep class com.google.android.gms.drive.** {*;}


#GMS
#-keep public class com.google.android.gms.* { public *; }
-keep class com.google.android.gms.** {*;}
-dontwarn com.google.android.gms.**

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

-keepnames @com.google.android.gms.common.annotation.KeepName class *
-keepclassmembernames class * {
    @com.google.android.gms.common.annotation.KeepName *;
}

-keepnames class * implements android.os.Parcelable {
    public static final ** CREATOR;
}


#butterknife
# Retain generated class which implement Unbinder.
#-keep public class * implements butterknife.Unbinder { public <init>(**, android.view.View); }

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*
-keepclasseswithmembernames class * { @butterknife.* <methods>; }
-keepclasseswithmembernames class * { @butterknife.* <fields>; }





###########################################
#-dontnote
# OkHttp and Servlet optional dependencies
-keepattributes Signature
-keepattributes Annotation
-keep class okhttp3.* { *; }
-keep interface okhttp3.* { *; }
-dontwarn okhttp3.**


-dontwarn com.google.appengine.**
-dontwarn javax.servlet.**

# Support classes for compatibility with older API versions

-dontwarn android.support.**
-dontnote android.support.**

-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.**
-keep class com.squareup.** { *; }

#-keep class android.support.v7.widget.SearchView { *; }

-printconfiguration config.txt

#-renamesourcefileattribute SourceFile
#-keepattributes SourceFile,LineNumberTable

-dontwarn android.support.**
-dontwarn org.apache.harmony.**
-dontwarn com.tonicsystems.**
-dontwarn org.firebirdsql.**
-dontwarn org.antlr.**
-dontwarn org.apache.**
-dontwarn okio.**
-dontwarn retrofit2.Platform$Java8
-dontnote javax.**
#=======================[2] Warning error 'class not found fix' =====================
-dontwarn com.squareup.picasso.**
-dontwarn com.koushikdutta.http.**
-dontwarn com.google.android.gms.**
-dontwarn javax.management.**
-dontwarn java.lang.management.**
-dontwarn org.apache.log4j.**
-dontwarn org.apache.commons.logging.**
-dontwarn org.slf4j.**
-dontwarn org.json.*



#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

##---------------Begin: proguard configuration common for all Android apps ----------


#-dontusemixedcaseclassnames
#-dontskipnonpubliclibraryclasses
#-dontskipnonpubliclibraryclassmembers
#-dontpreverify
#-verbose
#-dump class_files.txt
#-printseeds seeds.txt
#-printusage unused.txt


#-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#======================= END_Warning error 'class not found fix' =====================
#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
-dontnote com.android.vending.licensing.ILicensingService

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * implements java.io.Serializable {
static final long serialVersionUID;
private static final java.io.ObjectStreamField[] serialPersistentFields;
private void writeObject(java.io.ObjectOutputStream);
private void readObject(java.io.ObjectInputStream);
java.lang.Object writeReplace();
java.lang.Object readResolve();
}

# Preserve all native method names and the names of their classes.
-keepclasseswithmembernames class * {
native <methods>;
}

-keepclasseswithmembernames class * {
public <init>(android.content.Context, android.util.AttributeSet);
}

-keepclasseswithmembernames class * {
public <init>(android.content.Context, android.util.AttributeSet, int);
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
public static <fields>;
}

# Preserve the special static methods that are required in all enumeration classes.
-keepclassmembers enum * {
public static **[] values();
public static ** valueOf(java.lang.String);
}

-keep public class * {
public protected *;
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}


#-keep class com.** { *; }
#-keep class javax.** { *; }
#
#-keep class org.** { *; }
#-keep class twitter4j.** { *; }

 -keepclassmembers class com.walhalla.domen.rest.** {
   *;
 }
 -keepclassmembers class gov.nih.nlm.model.** {
   *;
 }

 #okhttp3.pro
 # JSR 305 annotations are for embedding nullability information.
 -dontwarn javax.annotation.**

 # A resource is loaded with a relative path so the package of this class must be preserved.
 -keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

 # Animal Sniffer compileOnly dependency to ensure APIs are compatible with older versions of Java.
 -dontwarn org.codehaus.mojo.animal_sniffer.*

 # OkHttp platform used only on JVM and when Conscrypt dependency is available.
 -dontwarn okhttp3.internal.platform.ConscryptPlatform



 -keep public class com.google.firebase.analytics.FirebaseAnalytics {
     public *;
 }

 -keep public class com.google.android.gms.measurement.AppMeasurement {
     public *;
 }

-keep public class kotlin.reflect.jvm.internal.impl.builtins.* { public *; }
-dontwarn kotlin.jvm.internal.Lambda
-dontwarn kotlin.jvm.functions.Function1

# Retrofit
-keep class com.google.gson.** { *; }
-keep class com.google.inject.** { *; }
-keep class org.apache.http.** { *; }
-keep class org.apache.james.mime4j.** { *; }
-keep class javax.inject.** { *; }
-keep class javax.xml.stream.** { *; }
-keep class retrofit.** { *; }
-keep class com.google.appengine.** { *; }
-keepattributes *Annotation*
-keepattributes Signature
-dontwarn com.squareup.okhttp.*
-dontwarn rx.**
-dontwarn javax.xml.stream.**
-dontwarn com.google.appengine.**
-dontwarn java.nio.file.**
-dontwarn org.codehaus.**

-dontwarn retrofit2.**
-dontwarn org.codehaus.mojo.**
-keep class retrofit2.** { *; }
-keepattributes Exceptions
-keepattributes RuntimeVisibleAnnotations
-keepattributes RuntimeInvisibleAnnotations
-keepattributes RuntimeVisibleParameterAnnotations
-keepattributes RuntimeInvisibleParameterAnnotations

-keepattributes EnclosingMethod
-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}
-keepclasseswithmembers interface * {
    @retrofit2.* <methods>;
}

# proguard configuration for iText

-keep class org.spongycastle.** { *; }
-dontwarn org.spongycastle.**

-keep class com.itextpdf.** { *; }

-keep class javax.xml.crypto.dsig.** { *; }
-dontwarn javax.xml.crypto.dsig.**

-keep class org.apache.jcp.xml.dsig.internal.dom.** { *; }
-dontwarn org.apache.jcp.xml.dsig.internal.dom.**

-keep class javax.xml.crypto.dom.** { *; }
-dontwarn javax.xml.crypto.dom.**

-keep class org.apache.xml.security.utils.** { *; }
-dontwarn org.apache.xml.security.utils.**

-keep class javax.xml.crypto.XMLStructure
-dontwarn javax.xml.crypto.XMLStructure

-keep public class com.itextpdf.text.pdf.BaseFont
#-repackageclasses ''
# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn java.lang.reflect.AnnotatedType
-dontwarn javax.xml.crypto.XMLCryptoContext
-dontwarn org.jspecify.annotations.NullMarked
-include "C:\android\proguard\okhttp3.pro"


# Please add these rules to your existing keep rules in order to suppress warnings.
# This is generated automatically by the Android Gradle plugin.
-dontwarn com.squareup.okhttp.Cache
-dontwarn com.squareup.okhttp.CacheControl$Builder
-dontwarn com.squareup.okhttp.CacheControl
-dontwarn com.squareup.okhttp.Call
-dontwarn com.squareup.okhttp.OkHttpClient
-dontwarn com.squareup.okhttp.Request$Builder
-dontwarn com.squareup.okhttp.Request
-dontwarn com.squareup.okhttp.Response
-dontwarn com.squareup.okhttp.ResponseBody