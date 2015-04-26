package com.wowcow.chat10.Utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.IntentFilter;

import java.util.ArrayList;
import java.util.List;

public class ReceiverHelper {

  private List<BroadcastReceiver> receivers;
  
  public ReceiverHelper(){
    this.receivers = new ArrayList<BroadcastReceiver>();

  }
  
  public void register(Context context, BroadcastReceiver receiver, String... actions){
    IntentFilter filter = new IntentFilter();
    for(String action:actions){
      filter.addAction(action);
    }
    
    context.registerReceiver(
        receiver,
        filter);

    this.receivers.add(receiver);
  }
  
  public void unregister(Context context){
    for(BroadcastReceiver r:this.receivers){
      context.unregisterReceiver(r);
    }
    
    this.receivers.clear();
  }
}