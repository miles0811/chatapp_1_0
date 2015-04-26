package com.wowcow.chat10.Service;

/**
 * Created by gametree on 15/2/24.
 */

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.wowcow.chat10.Activity.ChatRoomActivity;
import com.wowcow.chat10.R;
import com.wowcow.chat10.Utils.PreferencesUtil;
import com.wowcow.chat10.Consts.Constants;
import com.wowcow.chat10.Consts.IntentAction;

/**
 * GCM相關類別．
 *
 * @author magiclen
 *
 */
public class CookietimeIntentGCM extends IntentService {

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
    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (!extras.isEmpty()) {
            if (GoogleCloudMessaging.MESSAGE_TYPE_SEND_ERROR
                    .equals(messageType)) {
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_DELETED
                    .equals(messageType)) {
            } else if (GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE
                    .equals(messageType)) {

//                Intent i = new Intent(this, MainActivity.class);
//                i.setAction("android.intent.action.MAIN");
//                i.addCategory("android.intent.category.LAUNCHER");
                sendLocalNotification(this,
                        NOTIFICATION_ID,
                        R.drawable.ic_launcher,
                        extras.getString("name"),
                        extras.getString("msg"),
                        extras.getString("key1"),
                        extras.getString("key2"),
                        "CALL ME",
                        //Resources.getSystem().getString(R.string.app_name),
                        true);
            }
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    // ----------類別方法----------
    /**
     * 發出Local端的通知(顯示在通知欄上)
     *
     * @param context
     *            Context
     * @param notifyID
     *            通知ID(重複會被覆蓋)
     * @param drawableSmallIcon
     *            小圖示(用Drawable ID來設定)
     * @param title
     *            標題
     * @param msg
     *            訊息
     * @param info
     *            附加文字
     * @param autoCancel
     *            是否按下後就消失
     */
    public static void sendLocalNotification(Context context, int notifyID,
                                             int drawableSmallIcon, String title, String msg, String key1, String key2, String info,
                                             boolean autoCancel) {

        Log.i("sendLocalNotification", "sendLocalNotification");
        // which activity is running
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ComponentName cn = am.getRunningTasks(1).get(0).topActivity;
        // On Chat Room
        if(cn.getClassName().equals(Constants.CHATROOM_TAG)){
            String CHAT_BROATCAST_STRING = "CHAT_ON_SERVICE_001";
            Intent i = new Intent(CHAT_BROATCAST_STRING);
            i.setAction(IntentAction.UPDATE_CHAT_LIST);
            i.putExtra("data", msg);
            i.putExtra("room_no", key1);
            i.putExtra("nick_name", key2);
            context.sendBroadcast(i);
        }else if(cn.getClassName().equals(Constants.TABACTIVITY_TAG)){

        }else {
            // Wake
            Intent action = new Intent();
            action.setAction(IntentAction.UPDATE_CHATROOM_LIST);
            PreferencesUtil.setChatNotify(context, Integer.parseInt(key1));
            PreferencesUtil.setChatListNotify(context);
//            PreferencesUtil.setUnread(context, key1);
//            PreferencesUtil.setBadgeNum(this, badge + 1,Constants.NOTIFY_TYPE_CHAT);
            context.sendBroadcast(action);


            // redirect Activity
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            Intent intent = new Intent();
            //todo change ChatActivity to MainActivity
            intent.setClass(context, ChatRoomActivity.class);
            intent.putExtra(Constants.CHATROOM, key1);
            intent.putExtra("type", "pending");
            stackBuilder.addParentStack(ChatRoomActivity.class);
            stackBuilder.addNextIntent(intent);
            PendingIntent contentIntent = null;
            //按下後要使用什麼Intent
            contentIntent = stackBuilder.getPendingIntent(0,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            NotificationManager mNotificationManager = (NotificationManager) context
                    .getSystemService(Context.NOTIFICATION_SERVICE);

            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                    context).setSmallIcon(drawableSmallIcon).setContentTitle(title)
                    .setContentText(msg).setAutoCancel(autoCancel)
                    .setContentInfo(info).setDefaults(Notification.DEFAULT_ALL);

            if (msg.length() > 10) {
                mBuilder.setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(msg));
            }
            mBuilder.setContentIntent(contentIntent);
            mNotificationManager.notify(notifyID, mBuilder.build());
        }

    }

    // ----------建構子----------

    public CookietimeIntentGCM() {
        super("CookietimeGCM");
    }
}
