

-printmapping
-obfuscationdictionary 'D:\android\GitHub\facebook\proguard\examples\dictionaries\keywords.txt'
-classobfuscationdictionary 'D:\dropbox\dictionary\classobfuscation-compact.txt'
-packageobfuscationdictionary 'D:\dropbox\dictionary\packageobfuscation-compact.txt'
-repackageclasses com
-keepattributes Signature,Annotation,*Annotation*,Signature,Signature,Annotation,*Annotation*,Signature,*Annotation*,Signature,Exceptions,RuntimeVisibleAnnotations,RuntimeInvisibleAnnotations,RuntimeVisibleParameterAnnotations,RuntimeInvisibleParameterAnnotations,EnclosingMethod
-dontnote android.support.**,android.net.http.*,org.apache.commons.codec.**,org.apache.http.**,android.support.**,javax.**,com.android.vending.licensing.ILicensingService
#-dontwarn okhttp3.**,com.google.appengine.**,javax.servlet.**,android.support.**,com.squareup.**,okio.**,org.w3c.dom.**,org.joda.time.**,org.shaded.apache.**,org.ietf.jgss.**,com.google.android.gms.**,okhttp3.**,com.google.appengine.**,javax.servlet.**,android.support.**,com.squareup.**,android.support.**,org.apache.harmony.**,com.tonicsystems.**,org.firebirdsql.**,org.antlr.**,org.apache.**,okio.**,retrofit2.Platform$Java8,com.squareup.picasso.**,com.koushikdutta.http.**,com.google.android.gms.**,javax.management.**,java.lang.management.**,org.apache.log4j.**,org.apache.commons.logging.**,org.slf4j.**,org.json.*,javax.annotation.**,org.codehaus.mojo.animal_sniffer.*,okhttp3.internal.platform.ConscryptPlatform,kotlin.jvm.internal.Lambda,kotlin.jvm.functions.Function1,com.squareup.okhttp.*,rx.**,javax.xml.stream.**,com.google.appengine.**,java.nio.file.**,org.codehaus.**,retrofit2.**,org.codehaus.mojo.**
-printconfiguration config.txt


-keep class okhttp3.* {
    <fields>;
    <methods>;
}

-keep interface  okhttp3.* {
    <fields>;
    <methods>;
}

-keep class com.squareup.** {
    <fields>;
    <methods>;
}

#-keep class android.support.v7.widget.SearchView {
#    <fields>;
#    <methods>;
#}

# Firebase SDK 2.0.0:
-keep class com.firebase.** {
    <fields>;
    <methods>;
}

-keep class org.apache.** {
    <fields>;
    <methods>;
}

# -keep class com.dropbox.core.** {*;}
# -keep class com.dropbox.** {*;}
-keep class com.google.common.** {
    <fields>;
    <methods>;
}

-keep class com.square.picasso.** {
    <fields>;
    <methods>;
}

-keep class javax.servlet.http.** {
    <fields>;
    <methods>;
}

-keep class okio.Okio.** {
    <fields>;
    <methods>;
}

# -keep class com.google.android.gms.auth.** {*;}
# -keep class com.google.android.gms.drive.** {*;}
# GMS
# -keep public class com.google.android.gms.* { public *; }
-keep class com.google.android.gms.** {
    <fields>;
    <methods>;
}

-keep class * extends java.util.ListResourceBundle {
    protected java.lang.Object[][] getContents();
}

-keep public class com.google.android.gms.common.internal.safeparcel.SafeParcelable {
    public static final *** NULL;
}

# butterknife
# Retain generated class which implement Unbinder.
#-keep public class * extends butterknife.Unbinder {
#    public <init>(**,android.view.View);
#}

# Prevent obfuscation of types which use ButterKnife annotations since the simple name
# is used to reflectively look up the generated ViewBinding.
-keep class butterknife.*

-keep class okhttp3.* {
    <fields>;
    <methods>;
}

-keep interface  okhttp3.* {
    <fields>;
    <methods>;
}

-keep class com.squareup.** {
    <fields>;
    <methods>;
}

#-keep class android.support.v7.widget.SearchView {
#    <fields>;
#    <methods>;
#}

# Explicitly preserve all serialization members. The Serializable interface
# is only a marker interface, so it wouldn't save them.
-keepclassmembers class * extends java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# Preserve static fields of inner classes of R classes that might be accessed
# through introspection.
-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# -keep class com.** { *; }
# -keep class javax.** { *; }
# 
# -keep class org.** { *; }
# -keep class twitter4j.** { *; }
-keepclassmembers class com.walhalla.domen.rest.** {
    <fields>;
    <methods>;
}

-keep public class com.google.firebase.analytics.FirebaseAnalytics {
    public <fields>;
    public <methods>;
}

-keep public class com.google.android.gms.measurement.AppMeasurement {
    public <fields>;
    public <methods>;
}

-keep public class kotlin.reflect.jvm.internal.impl.builtins.* {
    public <fields>;
    public <methods>;
}

# Retrofit
-keep class com.google.gson.** {
    <fields>;
    <methods>;
}

-keep class com.google.inject.** {
    <fields>;
    <methods>;
}

-keep class org.apache.http.** {
    <fields>;
    <methods>;
}

-keep class org.apache.james.mime4j.** {
    <fields>;
    <methods>;
}

-keep class javax.inject.** {
    <fields>;
    <methods>;
}

-keep class javax.xml.stream.** {
    <fields>;
    <methods>;
}

-keep class retrofit.** {
    <fields>;
    <methods>;
}

-keep class com.google.appengine.** {
    <fields>;
    <methods>;
}

-keep class retrofit2.** {
    <fields>;
    <methods>;
}

-keepclasseswithmembers class * {
    @retrofit2.http.*
    <methods>;
}

-keepclasseswithmembers interface  * {
    @retrofit2.*
    <methods>;
}

-keep,allowshrinking class com.fasterxml.jackson.** {
    <fields>;
    <methods>;
}

-keep,allowshrinking class javax.servlet.** {
    <fields>;
    <methods>;
}

-keep,allowshrinking class org.ietf.jgss.** {
    <fields>;
    <methods>;
}

-keep,allowshrinking @com.google.android.gms.common.annotation.KeepName class *

-keepclassmembers,allowshrinking class * {
    @com.google.android.gms.common.annotation.KeepName
    <fields>;
    @com.google.android.gms.common.annotation.KeepName
    <methods>;
}

-keep,allowshrinking class * extends android.os.Parcelable {
    public static final ** CREATOR;
}

-keepclasseswithmembers,allowshrinking class * {
    @butterknife.*
    <methods>;
}

-keepclasseswithmembers,allowshrinking class * {
    @butterknife.*
    <fields>;
}

-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet);
}

-keepclasseswithmembers,allowshrinking class * {
    public <init>(android.content.Context,android.util.AttributeSet,int);
}

# A resource is loaded with a relative path so the package of this class must be preserved.
-keep,allowshrinking class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# Keep - Library. Keep all public and protected classes, fields, and methods.
-keep public class * {
    public protected <fields>;
    public protected <methods>;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

# Remove debugging - Throwable_printStackTrace calls. Remove all invocations of
# Throwable.printStackTrace().
-assumenosideeffects public class java.lang.Throwable {
    public void printStackTrace();
}

# Remove debugging - Thread_dumpStack calls. Remove all invocations of
# Thread.dumpStack().
-assumenosideeffects public class java.lang.Thread {
    public static void dumpStack();
}

# Remove debugging - All logging API calls. Remove all invocations of the
# logging API whose return values are not used.
-assumenosideeffects public class java.util.logging.* {
    <methods>;
}

# Remove debugging - All Log4j API calls. Remove all invocations of the
# Log4j API whose return values are not used.
-assumenosideeffects public class org.apache.log4j.** {
    <methods>;
}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

-assumenosideeffects class android.util.Log {
    public static *** d(...);
    public static *** v(...);
}

# You can remove logging calls with this option in proguard-project.txt:
-assumenosideeffects class android.util.Log {
    public static boolean isLoggable(java.lang.String,int);
    public static int v(...);
    public static int i(...);
    public static int w(...);
    public static int d(...);
    public static int e(...);
}
