package com.wowcow.chat10.Models;

import com.wowcow.chat10.R;

public enum Gender {
  MALE, FEMALE;
  public static int getValue(Gender gender) {
    return gender.ordinal();
  }

  public static int getDefaultMale() {
    return R.drawable.ic_default_male;
  }

  public static int getDefaultFemale() {
    return R.drawable.ic_default_female;
  }

  public static int convertInt(String gender) {
    if (gender.equals("female"))
      return 1;
    else
      return 0;
  }

}
