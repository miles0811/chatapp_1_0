package com.wowcow.chat10.Utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;

public class VersionUtil {
  public static boolean hasFroyo() {
    // Can use static final constants like FROYO, declared in later versions
    // of the OS since they are inlined at compile time. This is guaranteed
    // behavior.
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
  }

  public static boolean hasGingerbread() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
  }

  public static boolean hasHoneycomb() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
  }

  public static boolean hasHoneycombMR1() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
  }

  public static boolean hasICS() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH;
  }

  public static boolean hasJellyBean() {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
  }
  public static String getCurrentVersionName(Context context) {
    
    PackageManager packageManager = context.getPackageManager();
    String packageName = context.getPackageName();
   
    try {
        PackageInfo info = packageManager.getPackageInfo(packageName, 0);
        return info.versionName;
    } catch (NameNotFoundException e) {
        return  "";
    }
}
}
