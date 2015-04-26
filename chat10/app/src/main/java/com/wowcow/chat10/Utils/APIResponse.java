package com.wowcow.chat10.Utils;

public class APIResponse {
  private String result;
  private Exception exception;
  
  public String getResult() {
    return result;
  }
  public void setResult(String result) {
    this.result = result;
  }
  public Exception getException() {
    return exception;
  }
  public void setException(Exception exception) {
    this.exception = exception;
  }
}
