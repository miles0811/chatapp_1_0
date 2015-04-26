package com.wowcow.chat10.Utils;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class Logger {
  private static final Map<String, Logger> INSTANCES = new HashMap<String, Logger>();
  private static boolean enable = true;
   
  private String tag;
  private Logger(String tag){
    this.tag = tag;
  }
   
  public static synchronized Logger getInstance(Class<?> clazz) {
    String _tag = clazz.getName();
    Logger _logger = null;
    if(INSTANCES.containsKey(_tag)){
      _logger = INSTANCES.get(_tag);
    }else{
      _logger =  new Logger(_tag);
      INSTANCES.put(_tag, _logger);
    }
  
   return _logger;
  }
  
  public String getTag() {
    return tag;
  }

  public void v(String message){
    if(enable){
      Log.v(this.tag, message);
    }
  }
   
  public void v(String message, Throwable t){
    if(enable){
      Log.v(this.tag, message, t);
    }
  }
   
  public void d(String message){
    if(enable){
      Log.d(this.tag, message);
    }
  }
   
  public void d(String message, Throwable t){
    if(enable){
      Log.d(this.tag, message, t);
    }
  }
   
  public void i(String message){
    if(enable){
      Log.i(this.tag, message);
    }
  }
   
  public void i(String message, Throwable t){
    if(enable){
      Log.i(this.tag, message, t);
    }
  }
   
  public void w(String message){
    if(enable){
      Log.w(this.tag, message);
    }
  }
   
  public void w(String message, Throwable t){
    if(enable){
      Log.w(this.tag, message, t);
    }
  }
   
  public void e(String message){
    if(enable){
      Log.e(this.tag, message);
    }
  }
   
  public void e(String message, Throwable t){
    if(enable){
      Log.e(this.tag, message, t);
    }
  }
}