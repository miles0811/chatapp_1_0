package com.wowcow.chat10.Loader.chat;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Loader;

import com.wowcow.chat10.Loader.BaseAsyncTaskLoader;
import com.wowcow.chat10.Utils.APIAgent;
import com.wowcow.chat10.Utils.APIResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ChatSendMsgLoader extends AsyncTaskLoader<APIResponse> {
  String accessToken, room_id, msg;

  public ChatSendMsgLoader(Context context, String accessToken, String room_id,
      String msg) {
    super(context);
    this.accessToken = accessToken;
    this.room_id = room_id;
    this.msg = msg;
  }

  protected String action() throws Exception {
    return APIAgent.doChatRoomChat(this.accessToken, this.room_id, this.msg);
  }

    public APIResponse loadInBackground() {
        APIResponse response = new APIResponse();
        try {
            response.setResult(this.action());

        } catch (Exception e) {
            response.setException(e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
        }
        return response;
    }

}
