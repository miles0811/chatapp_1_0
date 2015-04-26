package com.wowcow.chat10.Service;

/**
 * Created by gametree on 15/2/24.
 */

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * 接收來自GCM的訊息
 *
 * @author magiclen
 *
 */
public class GcmBroadcastReceiver extends WakefulBroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

            ComponentName comp = new ComponentName(context.getPackageName(),
                    CookietimeIntentGCM.class.getName());
            startWakefulService(context, (intent.setComponent(comp)));
        setResultCode(Activity.RESULT_OK);

    }

}
