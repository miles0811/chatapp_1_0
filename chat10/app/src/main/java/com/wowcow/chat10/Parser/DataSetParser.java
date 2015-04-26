package com.wowcow.chat10.Parser;

import com.wowcow.chat10.Models.DataSet;
import com.wowcow.chat10.Models.Paging;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public abstract class DataSetParser<T> extends BaseParser {
  public abstract T toData(String jsonString) throws JSONException;
  
  public DataSet<T> toDataSet(String jsonString) throws JSONException {
    JSONObject json = new JSONObject(jsonString);
    DataSet<T> dataSet = new DataSet<T>();
    
    dataSet.setData(toList(json, "result"));
    dataSet.setPaging(
        json.has("paging")?
            this.toPaging(json.getString("paging")):
            new Paging());
    
    return dataSet;
  }
  
  protected final List<T> toList(JSONObject json, String key) throws JSONException {
    List<T> data = new ArrayList<T>();
    
    if(json.has(key)){
      JSONArray dataJson = json.getJSONArray(key);
      for(int i=0;i<dataJson.length();i++){
        data.add(toData(dataJson.getString(i)));
      }
    }
    
    return data;
  }

}
