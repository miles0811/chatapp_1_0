package com.wowcow.chat10.Models;

import java.io.Serializable;
import java.util.List;

public class DataSet<T> implements Serializable {
  private static final long serialVersionUID = 1L;

  private List<T> data;
  private Paging paging;

  public List<T> getData() {
    return data;
  }

  public void setData(List<T> data) {
    this.data = data;
  }

  public Paging getPaging() {
    return paging;
  }

  public void setPaging(Paging paging) {
    this.paging = paging;
  }

}
