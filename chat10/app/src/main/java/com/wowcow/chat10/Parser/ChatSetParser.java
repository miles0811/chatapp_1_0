package com.wowcow.chat10.Parser;

import com.wowcow.chat10.Models.Chat;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatSetParser extends DataSetParser<Chat> {
  UserSetParser parser = new UserSetParser();

  @Override
  public Chat toData(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    Chat chat = new Chat();
    chat.setId(getString(json, "_id"));
    if(json.has("room"))
      chat.setRoom(getString(json, "room"));

    if (json.has("creator"))
      chat.setCreator(parser.toData(getString(json, "creator")));

    chat.setTime(getLong(json, "time"));
    // String msg = getString(json, "msg");
    chat.setMsg(getString(json, "msg"));

    return chat;
  }

}
