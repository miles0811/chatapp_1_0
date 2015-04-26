package com.wowcow.chat10.Service;

/**
 * Created by gametree on 15/2/24.
 */

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.AsyncTask;

import com.wowcow.chat10.Utils.PreferencesUtil;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import java.io.IOException;

/**
 * GCM相關類別．
 *
 * @author magiclen
 *
 */
public class CookietimeGCM{

    // ----------類別常數(須自行修改)----------
    /**
     * Google Developers Console 的 Project Number
     */
    //todo modify
    public final static String SENDER_ID = "820582180119";

    // ----------類別列舉----------
    public static enum PlayServicesState {
        SUPPROT, NEED_PLAY_SERVICE, UNSUPPORT;
    }

    public static enum GCMState {
        PLAY_SERVICES_NEED_PLAY_SERVICE, PLAY_SERVICES_UNSUPPORT, NEED_REGISTER, AVAILABLE;
    }

    // ----------類別介面----------
    public static interface CookietimeGCMListener {
        /**
         * GCM註冊結束
         *
         * @param successfull
         *            是否註冊成功
         * @param regID
         *            傳回註冊到的regID
         */
        public void gcmRegistered(boolean successfull, String regID);

        /**
         * GCM註冊成功，將結果寫入App Server
         *
         * @param regID
         *            傳回註冊到的regID
         * @return 是否傳送App Server成功
         */
        public boolean gcmSendRegistrationIdToAppServer(String regID);

    }

    // ----------類別常數----------
    /**
     * 用來當作SharedPreferences的Key.
     */
    private static final String PROPERTY_REG_ID = "registration_id";
    private static final String PROPERTY_APP_VERSION = "appVersion";
    /**
     * 使用MagicLenGCM的Activity可以實作這個ActivityResult號碼
     */
    public final static int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static final int NOTIFICATION_ID = 0;


    // ----------物件變數----------
    private Activity activity;
    private CookietimeGCMListener listener;

    // ----------建構子----------

    public CookietimeGCM(Activity activity) {
        this(activity, null);
    }

    public CookietimeGCM(Activity activity, CookietimeGCMListener listener) {
        this.activity = activity;
        setCookietimeGCMListener(listener);
    }

    // ----------物件方法----------
    /**
     * 取得Activity
     *
     * @return 傳回Activity
     */
    public Activity getActivity() {
        return activity;
    }

    public void setCookietimeGCMListener(CookietimeGCMListener listener) {
        this.listener = listener;
    }

    /**
     * 開始接上GCM
     *
     * @return 傳回GCM狀態
     */
    public GCMState startGCM() {
        return openGCM();
    }

    /**
     * 開始接上GCM
     *
     * @return 傳回GCM狀態
     */
    public GCMState openGCM() {
        switch (checkPlayServices()) {
            case SUPPROT:
                String regid = getRegistrationId();
                if (regid.isEmpty()) {
                    registerInBackground();
                    return GCMState.NEED_REGISTER;
                } else {
                    return GCMState.AVAILABLE;
                }
            case NEED_PLAY_SERVICE:
                return GCMState.PLAY_SERVICES_NEED_PLAY_SERVICE;
            default:
                return GCMState.PLAY_SERVICES_UNSUPPORT;
        }
    }

    public String getRegistrationId() {
        final SharedPreferences prefs = getGCMPreferences();
        String registrationId = prefs.getString(PROPERTY_REG_ID, "");
        if (registrationId.isEmpty()) {
            return "";
        }
        // 檢查程式是否有更新過
        int registeredVersion = prefs.getInt(CookietimeGCM.PROPERTY_APP_VERSION,
                Integer.MIN_VALUE);
        int currentVersion = getAppVersion();
        if (registeredVersion != currentVersion) {
            return "";
        }
        return registrationId;
    }

    public int getAppVersion() {
        try {
            PackageInfo packageInfo = activity.getPackageManager()
                    .getPackageInfo(activity.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            // 不可能會發生
            throw new RuntimeException("Could not get package name: " + e);
        }
    }

    private SharedPreferences getGCMPreferences() {
        return activity.getSharedPreferences(activity.getClass()
                .getSimpleName(), Context.MODE_PRIVATE);
    }

    /**
     * 檢查Google Play Service可用狀態
     *
     * @return 傳回Google Play Service可用狀態
     */
    public PlayServicesState checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity);
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, activity,
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
                return PlayServicesState.NEED_PLAY_SERVICE;
            } else {
                return PlayServicesState.UNSUPPORT;
            }
        }
        return PlayServicesState.SUPPROT;
    }

    /**
     * 在背景註冊GCM
     */
    private void registerInBackground() {
        new AsyncTaskRegister().execute();
    }

    private final class AsyncTaskRegister extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            String regid = "";
            try {
                GoogleCloudMessaging gcm = GoogleCloudMessaging
                        .getInstance(activity);
                regid = gcm.register(SENDER_ID);

                if (regid == null || regid.isEmpty()) {
                    return "";
                }

                // 儲存regID
                storeRegistrationId(regid);

                if (listener != null) {
                    if (!listener.gcmSendRegistrationIdToAppServer(regid)) {
                        storeRegistrationId("");
                        return "";
                    }
                }
            } catch (IOException ex) {

            }
            return regid;
        }

        @Override
        protected void onPostExecute(String msg) {
            if (listener != null) {
                listener.gcmRegistered(!msg.isEmpty(), msg.toString());
            }
        }
    }

    private void storeRegistrationId(String regId) {
        PreferencesUtil.setGCMRegisterId(getActivity(), regId);
//        final SharedPreferences prefs = getGCMPreferences();
//        int appVersion = getAppVersion();
//        SharedPreferences.Editor editor = prefs.edit();
//        editor.putString(PROPERTY_REG_ID, regId);
//        editor.putInt(PROPERTY_APP_VERSION, appVersion);
//        editor.commit();
    }
}
