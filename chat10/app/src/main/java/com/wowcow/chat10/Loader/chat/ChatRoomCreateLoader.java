package com.wowcow.chat10.Loader.chat;

import android.content.Context;

import com.wowcow.chat10.Loader.BaseAsyncTaskLoader;
import com.wowcow.chat10.Utils.APIAgent;

public class ChatRoomCreateLoader extends BaseAsyncTaskLoader {
  private String accessToken;
  private String toAccount;

  public ChatRoomCreateLoader(Context context, String accessToken,
      String toAccount) {
    super(context);
    this.accessToken = accessToken;
    this.toAccount = toAccount;
  }

  @Override
  protected String action() throws Exception {
//    return APIAgent.doChatRoomCreate(this.accessToken, this.toAccount);
      return "test";
  }

}
