package com.wowcow.chat10.Utils;

import android.app.ActionBar;
import android.content.Context;
//import android.support.v7.app.ActionBar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.wowcow.chat10.R;

public class ActionBarUtils {
  ActionBar actionbar;
  View customView;
  Context mContext;

  public ActionBarUtils(Context context, ActionBar actionbar) {
    this.actionbar = actionbar;
    this.mContext = context;
    createBaseView();
  }

  private void createBaseView() {
    actionbar.setCustomView(R.layout.actionbar_base);
    actionbar.setBackgroundDrawable(mContext.getResources().getDrawable(
        R.drawable.bg_corner1));
    customView = actionbar.getCustomView();
    
    actionbar.setDisplayShowHomeEnabled(false);
    actionbar.setDisplayShowTitleEnabled(false);
    actionbar.setDisplayUseLogoEnabled(false);
    actionbar.setDisplayShowCustomEnabled(true);
  }

  public void setTitle(Object resId) {
    if (resId != null)
      ((TextView) customView.findViewById(R.id.ab_title))
          .setText((Integer) resId);
  }

  public void setTitle(String str) {
    if (!str.equals(""))
      ((TextView) customView.findViewById(R.id.ab_title)).setText(str);
  }

  public View getView() {
    return this.customView;
  }

  public Button setButton(int type, String title) {
    Button button = (Button) customView.findViewById(R.id.ab_button);
    button.setVisibility(type);
    button.setText(title);
    return button;
  }

  public Button setButton(int type, String title, int background) {
    Button button = (Button) customView.findViewById(R.id.ab_button);
    button.setVisibility(type);
    button.setText(title);
    button.setBackgroundResource(background);
    return button;
  }

  public Button setNegaButton(int type, String title) {
    Button button = (Button) customView.findViewById(R.id.ab_negativ_button);
    button.setVisibility(type);
    button.setText(title);
    return button;
  }

  public ImageButton setImageButton(int type, int buttonID, int resID) {
    ImageButton button = (ImageButton) customView.findViewById(buttonID);
    button.setVisibility(type);
    button.setImageResource(resID);
    return button;
  }

  public void setTitleColors(int resId) {
    if (resId != 0)
      ((TextView) customView.findViewById(R.id.ab_title)).setTextColor(resId);
  }

  public void setBackground(int resId) {
    actionbar.setBackgroundDrawable(mContext.getResources().getDrawable(resId));
  }

  public void setDivider(int type) {
    customView.findViewById(R.id.ab_divider).setVisibility(type);
  }

  public boolean isSetProfile(Context context) {
    boolean result = true;
    if (PreferencesUtil.getUserDesc(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserLocate(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserBirthday(mContext) == 0)
      result = false;
    if (PreferencesUtil.getUserConstellation(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserAge(mContext) == 0)
      result = false;
    if (PreferencesUtil.getUserBody(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserPersonalized(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserHoliday(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserAfterMeet(mContext).equals(""))
      result = false;
    if (PreferencesUtil.getUserFirstPay(mContext).equals(""))
      result = false;
    return result;
  }
}
