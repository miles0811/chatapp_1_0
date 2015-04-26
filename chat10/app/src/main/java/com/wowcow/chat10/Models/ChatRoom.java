package com.wowcow.chat10.Models;

import android.content.Context;

import com.wowcow.chat10.Utils.PreferencesUtil;

import java.io.Serializable;

public class ChatRoom implements Serializable {
  private static final long serialVersionUID = 1L;
  private String id;
  private User creator;
  private User receiver;
  private boolean lockState;
  private String last_text;
  private long time;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public User getCreator() {
    return creator;
  }

  public void setCreator(User creator) {
    this.creator = creator;
  }

  public User getReceiver() {
    return receiver;
  }

  public void setReceiver(User receiver) {
    this.receiver = receiver;
  }

  public boolean isLockState() {
    return lockState;
  }

  public void setLockState(boolean lockState) {
    this.lockState = lockState;
  }

  public long getTime() {
    return time;
  }

  public void setTime(long time) {
    this.time = time;
  }

  public String getLast_text() {
    return last_text;
  }

  public void setLast_text(String last_text) {
    this.last_text = last_text;
  }

  public String toRoomName(Context context) {
    if (PreferencesUtil.getUserToken(context).equals(
        getCreator().getAccessToken()))
      return getReceiver().getNickname();
    else
      return getCreator().getNickname();
  }

}
