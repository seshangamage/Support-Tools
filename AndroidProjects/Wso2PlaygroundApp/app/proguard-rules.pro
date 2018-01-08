# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/sheshan/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-libraryjar httpcore-4.4.1.jar
#-libraryjar org.apache.http.legacy.jar

-ignorewarnings

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service


-keep public class com.sheshan.wso2telco.plyground.model.EnvironmentDTO
-keep public class com.sheshan.wso2telco.ssl.SimpleSSLSocketFactory
-keep public class playground.wso2telco.sheshan.com.wso2playgroundapp.ProductionTokenRequest