package com.wowcow.chat10.Parser;

import com.wowcow.chat10.Models.ChatRoom;

import org.json.JSONException;
import org.json.JSONObject;

public class ChatRoomParser extends DataSetParser<ChatRoom> {
  UserSetParser parser = new UserSetParser();
  @Override
  public ChatRoom toData(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    if (json.has("result"))
      json = json.getJSONObject("result");
    ChatRoom room = new ChatRoom();
    room.setId(getString(json, "_id"));
    room.setCreator(parser.toData(getString(json, "creator")));
    room.setReceiver(parser.toData(getString(json, "receiver")));
    room.setLockState(getBoolean(json, "state"));
    room.setLast_text(getString(json, "last_text"));
    room.setTime(getLong(json, "last_time"));

    return room;
  }

}
