package com.wowcow.chat10.Models;

import java.io.Serializable;

public class ApiError implements Serializable {

  private static final long serialVersionUID = 1L;

  private String message;
  private String type;
  private int code;

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

}
