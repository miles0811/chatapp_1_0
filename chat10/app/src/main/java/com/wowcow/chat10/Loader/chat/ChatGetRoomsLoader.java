package com.wowcow.chat10.Loader.chat;

import android.content.Context;

import com.wowcow.chat10.Loader.BaseAsyncTaskLoader;
import com.wowcow.chat10.Utils.APIAgent;

public class ChatGetRoomsLoader extends BaseAsyncTaskLoader {
  String accessToken;
  public ChatGetRoomsLoader(Context context, String accessToken) {
    super(context);
    this.accessToken = accessToken;
  }

  @Override
  protected String action() throws Exception {
    return APIAgent.doChatGetRooms(accessToken);
  }

}
