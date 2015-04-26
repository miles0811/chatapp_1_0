package com.wowcow.chat10.Utils;

public class RandomGenerator {
  /**
   * @return a integer for loader id(10000~30000)
   */
  public static int genLodaerId(){
    return (int)(Math.random() * 20000) + 10000;
  }
}
