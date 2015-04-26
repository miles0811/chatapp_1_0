package com.wowcow.chat10.Models;

import java.io.Serializable;

public class Paging implements Serializable {

  private static final long serialVersionUID = 1L;

  private int limit;
  private int currentPage;
  private int totalPage;
  private int prev;
  private int next;

  public int getLimit() {
    return limit;
  }

  public void setLimit(int limit) {
    this.limit = limit;
  }

  public int getCurrentPage() {
    return currentPage;
  }

  public void setCurrentPage(int currentPage) {
    this.currentPage = currentPage;
  }

  public int getTotalPage() {
    return totalPage;
  }

  public void setTotalPage(int totalPage) {
    this.totalPage = totalPage;
  }

  public int getPrev() {
    return prev;
  }

  public void setPrev(int prev) {
    this.prev = prev;
  }

  public int getNext() {
    return next;
  }

  public void setNext(int next) {
    this.next = next;
  }

}
