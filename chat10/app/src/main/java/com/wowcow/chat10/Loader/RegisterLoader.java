package com.wowcow.chat10.Loader;

import android.content.Context;

import com.wowcow.chat10.Utils.APIAgent;

public class RegisterLoader extends BaseAsyncTaskLoader {
  String gcm_id;
  String nick_name;

  public RegisterLoader(Context context, String gcm_id, String nick_name) {
    super(context);
    this.gcm_id = gcm_id;
      this.nick_name = nick_name;
  }

  @Override
  protected String action() throws Exception {
    return APIAgent.register(this.gcm_id, this.nick_name);
  }

}
