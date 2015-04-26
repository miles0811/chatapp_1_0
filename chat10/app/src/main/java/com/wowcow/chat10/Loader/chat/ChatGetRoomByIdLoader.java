package com.wowcow.chat10.Loader.chat;

import android.content.AsyncTaskLoader;
import android.content.Context;

import com.wowcow.chat10.Loader.BaseAsyncTaskLoader;
import com.wowcow.chat10.Utils.APIAgent;
import com.wowcow.chat10.Utils.APIResponse;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ChatGetRoomByIdLoader extends AsyncTaskLoader<APIResponse> {
  private String accessToken;
  private String room_id;

  public ChatGetRoomByIdLoader(Context context, String accessToken,
      String room_id) {
    super(context);
    this.accessToken = accessToken;
    this.room_id = room_id;
  }

  protected String action() throws Exception {
    return APIAgent.doChatGetRoomById(this.accessToken, this.room_id);
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
