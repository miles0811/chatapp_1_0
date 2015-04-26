package com.wowcow.chat10.Parser;

import com.wowcow.chat10.Models.User;

import org.json.JSONException;
import org.json.JSONObject;

public class UserSetParser extends DataSetParser<User> {

  @Override
  public User toData(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    if (json.has("result"))
      json = json.getJSONObject("result");
    User user = new User();
    if (json.has("user_token"))
        user.setUser_token(getString(json, "user_token"));
    else
        user.setUser_token(getString(json, "user_token"));

    user.setUser_no(getInt(json, "no"));
    user.setUser_name(getString(json, "user_name"));
      user.setNick_name(getString(json, "nick_name"));
      user.setFrom(getString(json, "from"));
      user.setLogin_type(getString(json, "login_type"));
      user.setGcm_id(getString(json, "gcm_id"));

    return user;
  }

}
