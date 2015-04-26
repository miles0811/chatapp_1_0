package com.wowcow.chat10.Parser;

import com.wowcow.chat10.Models.ApiError;
import com.wowcow.chat10.Models.Paging;

import org.json.JSONException;
import org.json.JSONObject;


public class BaseParser {
  protected static boolean getBoolean(JSONObject json, String key) {
    if (json.has(key)) {
      return json.optBoolean(key, false);
    } else {
      return false;
    }
  }
  
  protected static double getDouble(JSONObject json, String key) {
    if (json.has(key)) {
      return json.optDouble(key, 0);
    } else {
      return 0;
    }
  }
  
  protected static long getLong(JSONObject json, String key){
    if (json.has(key)) {
      return json.optLong(key, 0);
    } else {
      return 0;
    }
  }
  protected static long getDate(JSONObject json, String key){
    if(json.has(key)){
      //TODO string time convert to long
      return 0;
    }else{
      return 0;
    }
    
  }
  protected static float getFloat(JSONObject json, String key){
    if(json.has(key)){
      return (float)json.optDouble(key, 0);
    }else{
      return 0;
    }
  }
  protected static int getInt(JSONObject json, String key) {
    if (json.has(key)) {
      return json.optInt(key, 0);
    } else {
      return 0;
    }
  }
  
  protected static String getString(JSONObject json, String key) {
    if (json.has(key)) {
      if (json.isNull(key) == false) {
        return json.optString(key, "");
      }
    }
  
    return null;
  }
  
  public ApiError toError(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    ApiError error = null;
    
    if(json.has("error")){
      error = new ApiError();
      
      JSONObject errorJson = json.getJSONObject("error");
      
      error.setMessage(getString(errorJson, "message"));
      error.setType(getString(errorJson, "type"));
      error.setCode(getInt(errorJson, "code"));
      
    }
    
    return error;
  }
  
  public boolean toSuccess(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    json = json.getJSONObject("result");
    return getBoolean(json, "Success");
  }
  

  public Paging toPaging(String jsonString) throws JSONException {
    JSONObject pagingJson = new JSONObject(jsonString);
    Paging paging = new Paging();
    paging.setLimit(getInt(pagingJson, "limit"));
    paging.setCurrentPage(getInt(pagingJson, "current_page"));
    paging.setTotalPage(getInt(pagingJson, "total_page"));
    paging.setPrev(getInt(pagingJson, "prev"));
    paging.setNext(getInt(pagingJson, "next"));
    
    return paging;
  }
}
