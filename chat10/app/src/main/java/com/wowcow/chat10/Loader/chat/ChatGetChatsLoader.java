package com.wowcow.chat10.Loader.chat;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wowcow.chat10.Loader.BaseAsyncTaskLoader;
import com.wowcow.chat10.Utils.APIAgent;
import com.wowcow.chat10.Utils.APIResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ChatGetChatsLoader extends AsyncTaskLoader<APIResponse> {
  String accessToken;
  String room_id;
  long time;
  String chat_id;
  public ChatGetChatsLoader(Context context, String accessToken, String room_id, long time, String chat_id) {
    super(context);
    this.accessToken = accessToken;
    this.room_id = room_id;
    this.time = time;
    this.chat_id = chat_id;
  }

//  @Override
  protected String action() throws Exception {
    // TODO Auto-generated method stub
    return APIAgent.doChatGetChats(this.accessToken, this.room_id, this.time, this.chat_id);
  }
    @Override
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
