package com.wowcow.chat10.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;


import com.wowcow.chat10.Consts.Constants;
import com.wowcow.chat10.Models.Chat;
import com.wowcow.chat10.Models.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class PreferencesUtil {

  private static final String USER_ACCOUNT = "user_account";
  private static final String USER_TOKEN = "accessToken";
    private static final String USER_PASSWORD = "password";
    private static final String USER_NO = "no";
  private static final String USER_REGISTERID = "register_id";
  private static final String PROPERTY_APP_VERSION = "appVersion";
  private static final String LAST_INVITATION = "last_invitation_id";
  private static final String INVITATION_SEND = "invatation_send";
  private static final String INVITATION_RECEIVE = "invitation_receive";
  private static final String USER_NICKNAME = "user_nickname";
    private static final String USER_FROM = "user_from";
    private static final String USER_NAME = "user_name";
  private static final String USER_GENDER = "user_gender";
  private static final String USER_DESC = "user_desc";
  private static final String USER_BIRTHDAY = "user_birthday";
  private static final String USER_CONTELLATION = "user_contellation";
  private static final String USER_AGE = "user_age";
  private static final String USER_IMG = "user_img_path";
  private static final String USER_LOCATE = "user_locate";
    private static final String USER_MONEY = "user_money";
    private static final String USER_COINS = "user_coins";
    private static final String USER_PHOTO = "user_photo";
  private static final String USER_BODY = "user_body";
  private static final String USER_PERSONALIZED = "user_personalized";
  private static final String USER_HOLIDAY = "user_holiday";
  private static final String USER_AFTER_MEET = "user_after_meet";
  private static final String USER_FIRST_PAY = "user_first_pay";
    private static final String USER_LOGIN_TYPE = "user_login_type";
  private static final String ADMOB_VIISBLE_TIME = "admob_visible_time";
  private static final String NEW_NOTIFY_TAG = "new_notify";
  private static final String NOTIFY_CENTER_TAG = "notify";
  private static final String IS_REMOVE_AD = "isAdRemove";
  private static final String CHAT_HISTORY = "chat_history";
  private static final String ROOM_UNREAD = "room_unread";
  private static final String ALL_NOTIFY = "all_notify";
  private static final String CHAT_NOTIFY = "chat_notify";
  private static final String CHAT_LIST_NOTIFY = "chat_list_notify";
  private static final String PURCHASE_NOTIFY = "purchase_notify";
  private static final String PURCHASE_LIST_NOTIFY = "purchase_list_notify";
  private static final String APPRAISE_NOTIFY = "appraise_notify";
  private static final String APPRAISE_LIST_NOTIFY = "appraise_list_notify";


  public static String getUserAccount(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_ACCOUNT, "");
  }

  public static void setUserAccount(Context context, String account) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_ACCOUNT, account).commit();
  }

  public static String getUserToken(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_TOKEN, "");
  }

  public static void setUserToken(Context context, String token) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_TOKEN, token).commit();
  }

    public static String getUserPassword(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(USER_PASSWORD, "");
    }

    public static void setUserPassword(Context context, String password) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_PASSWORD, password).commit();
    }

    public static int getUserNo(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(USER_NO, 0);
    }

    public static void setUserNo(Context context, int no) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(USER_NO, no).commit();
    }

    public static String getUserName(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(USER_NAME, "");
    }

    public static void setUserName(Context context, String nickname) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_NAME, nickname).commit();
    }

  public static String getUserNickName(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_NICKNAME, "");
  }

  public static void setUserNickName(Context context, String nickname) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_NICKNAME, nickname).commit();
  }

    public static String getUserFrom(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(USER_FROM, "");
    }

    public static void setUserFrom(Context context, String nickname) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_FROM, nickname).commit();
    }

  public static String getUserDesc(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_DESC, "");
  }

  public static void setUserDesc(Context context, String desc) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_DESC, desc).commit();
  }

  public static long getUserBirthday(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getLong(USER_BIRTHDAY, 0);
  }

  public static void setUserBirthday(Context context, String birthday) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_BIRTHDAY, birthday).commit();
  }

  public static String getUserConstellation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_CONTELLATION, "");
  }

  public static void setUserGender(Context context, int gender) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putInt(USER_GENDER, gender).commit();
  }

  public static int getUserGender(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getInt(USER_GENDER, 9);
  }

  public static void setUserAge(Context context, int age) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putInt(USER_AGE, age).commit();
  }

  public static int getUserAge(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getInt(USER_AGE, 0);
  }

  public static void setUserConstellation(Context context, String constellation) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_CONTELLATION, constellation).commit();
  }

  public static String getUserBody(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_BODY, "");
  }

  public static void setUserBody(Context context, String body) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_BODY, body).commit();
  }

    public static String getUserLoginType(Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(USER_LOGIN_TYPE, "");
    }

    public static void setUserLoginType(Context context, String login_type) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_LOGIN_TYPE, login_type).commit();
    }

  public static String getIMGPath(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_IMG, "");
  }

  public static void setUserIMGPath(Context context, String path) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_IMG, path).commit();
  }

  public static String getUserPersonalized(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_PERSONALIZED, "");
  }

  public static void setUserPersonalized(Context context, String personalized) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_PERSONALIZED, personalized).commit();
  }

  public static String getUserHoliday(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_HOLIDAY, "");
  }

  public static void setUserHoliday(Context context, String holiday) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_HOLIDAY, holiday).commit();
  }

  public static String getUserLocate(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_LOCATE, "");
  }

  public static void setUserLocate(Context context, String locate) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_LOCATE, locate).commit();
  }

    public static String getUserPhoto(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getString(USER_PHOTO, "");
    }

    public static void setUserPhoto(Context context, String photo) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putString(USER_PHOTO, photo).commit();
    }

    public static int getUserMoney(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(USER_MONEY, 0);
    }

    public static void setUserMoney(Context context, int money) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(USER_MONEY, money).commit();
    }

    public static int getUserCoins(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(USER_COINS, 0);
    }

    public static void setUserCoins(Context context, int coins) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(USER_COINS, coins).commit();
    }

  public static String getUserAfterMeet(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_AFTER_MEET, "");
  }

  public static void setUserAfterMeet(Context context, String after_meet) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_AFTER_MEET, after_meet).commit();
  }

  public static String getUserFirstPay(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_FIRST_PAY, "");
  }

  public static void setUserFirstPay(Context context, String firstPay) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_FIRST_PAY, firstPay).commit();
  }

  public static String getGCMRegisterId(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(USER_REGISTERID, "");
  }

  public static void setGCMRegisterId(Context context, String registerId) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(USER_REGISTERID, registerId).commit();
  }

  public static int getAPPVersion(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getInt(PROPERTY_APP_VERSION, Integer.MIN_VALUE);
  }

    public static int getAllNotify(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(ALL_NOTIFY, 0);
    }

    public static void setAllNotify(Context context, int notify) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(ALL_NOTIFY, notify).commit();
    }

    public static int getChatListNotify(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(CHAT_LIST_NOTIFY, 0);
    }

    public static void setChatListNotify(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        System.out.println("getChatListNotify"+getChatListNotify(context));
        preferences.edit().putInt(CHAT_LIST_NOTIFY, getChatListNotify(context)+1).commit();
    }

    public static void setChatListNotify(Context context, int notify) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(CHAT_LIST_NOTIFY, notify).commit();
    }

    public static int getChatNotify(Context context, int room_no) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(CHAT_NOTIFY+room_no, 0);
    }

    public static void setChatNotify(Context context, int room_no) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(CHAT_NOTIFY+room_no, getChatNotify(context, room_no)+1).commit();
    }
    public static void setChatNotify(Context context, int room_no, int notify) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(CHAT_NOTIFY+room_no, notify).commit();
    }

    public static int getPurchaseListNotify(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(PURCHASE_LIST_NOTIFY, 0);
    }

    public static void setPurchaseListNotify(Context context, int notify) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(PURCHASE_LIST_NOTIFY, notify).commit();
    }

    public static int getAppraiseListNotify(Context context) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        return preferences.getInt(PURCHASE_LIST_NOTIFY, 0);
    }

    public static void setAppraiseListNotify(Context context, int notify) {
        SharedPreferences preferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        preferences.edit().putInt(APPRAISE_LIST_NOTIFY, notify).commit();
    }

  public static void setAPPVersion(Context context, int version) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putInt(PROPERTY_APP_VERSION, version).commit();
  }

  public static String getLastInviation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(LAST_INVITATION, "");
  }

  public static void setLastInvitation(Context context, String id) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(LAST_INVITATION, id).commit();
  }

  public static long getRoomLastTime(Context context, String room_id) {
    SharedPreferences preferences = context.getSharedPreferences(room_id,
        Activity.MODE_PRIVATE);
    // preferences.edit().clear().commit();
    return preferences.getLong(room_id + "_time", -1);
  }

  public static void setRoomLastTime(Context context, String room_id, long time) {
    SharedPreferences preferences = context.getSharedPreferences(room_id,
        Activity.MODE_PRIVATE);
    // preferences.edit().clear().commit();
    preferences.edit().putLong(room_id + "_time", time).commit();
  }

  public static String getPurchaseStatus(Context context, int item_id, String type) {
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    // preferences.edit().clear().commit();
    return preferences.getString(type + Integer.toString(item_id), "");
  }

  public static void setPurchaseStatus(Context context, int item_id, String type) {
    SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
    preferences.edit().putString(type + Integer.toString(item_id), "purchased").commit();
  }

    public static int getChatStatus(Context context, int item_user, String item_type) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        // preferences.edit().clear().commit();
        return preferences.getInt("chat_" + item_type + Integer.toString(item_user), 0);
    }

    public static void setChatStatus(Context context, int item_user, String item_type) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        preferences.edit().putInt("chat_" + item_type + Integer.toString(item_user), 1).commit();
    }

    public static String getRoomChatsUser(Context context, String room_id) {
        SharedPreferences preferences = context.getSharedPreferences("room_user",
                Activity.MODE_PRIVATE);
        // preferences.edit().clear().commit();
        return preferences.getString(room_id+"_user", "");
    }

//    public static void setRoomChatsUser(Context context, String room_id,User userData) {
//        SharedPreferences preferences = context.getSharedPreferences("room_user",
//                Activity.MODE_PRIVATE);
//        JSONObject json = null;
//        try {
//            json = new JSONObject();
//            json.put("no", userData.getNo());
//            json.put("account", userData.getAccount());
//            json.put("appr_good", userData.getAppr_good());
//            json.put("appr_awful", userData.getAppr_awful());
//            json.put("location", userData.getLocation());
//            json.put("introduction", userData.getIntroduction());
//            json.put("location", userData.getLocation());
//            json.put("photo", userData.getPhoto());
//            json.put("nick_name", userData.getNick_name());
//            preferences.edit().putString(room_id+"_user", json.toString()).commit();
//            // preferences.edit().clear().commit();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }

    public static String getRoomChats(Context context, String room_id) {
        SharedPreferences preferences = context.getSharedPreferences(room_id,
                Activity.MODE_PRIVATE);
        // preferences.edit().clear().commit();
        return preferences.getString(room_id, "");
    }

    public static void setRoomChats(Context context, String room_id, List<Chat> chats) {
        SharedPreferences preferences = context.getSharedPreferences(room_id,
                Activity.MODE_PRIVATE);
        JSONObject json = null;
        JSONArray root = null;
        try {
            if (!getRoomChats(context, room_id).equalsIgnoreCase("")) {
                json = new JSONObject(getRoomChats(context, room_id));
                root = json.getJSONArray("result");
            } else {
                json = new JSONObject();
                root = new JSONArray();
            }
            for (int i = 0; i < chats.size(); i++) {
                JSONObject obj = new JSONObject();
                JSONObject creatorObj = new JSONObject();
                creatorObj.put("accessToken", chats.get(i).getCreator()
                        .getAccessToken());
                creatorObj.put("nickname", chats.get(i).getCreator().getNickname());
                creatorObj.put("gender", chats.get(i).getCreator().getGender());
                creatorObj.put("imagePath", chats.get(i).getCreator().getImagePath());
                creatorObj.put("nickname", chats.get(i).getCreator().getNickname());
                obj.put("_id", chats.get(i).getId());
                obj.put("room", chats.get(i).getRoom());
                obj.put("creator", creatorObj);
                obj.put("time", chats.get(i).getTime());
                obj.put("msg", chats.get(i).getMsg());
                root.put(obj);
            }
            json.put("result", root);
            preferences.edit().putString(room_id, json.toString()).commit();
            // preferences.edit().clear().commit();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

  public static String getSendInvitation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(INVITATION_SEND, "");
  }

  public static void clearSendInvitation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(INVITATION_SEND, "").commit();
  }

//  public static void setSendInvitation(Context context, Invitation invitation) {
//    SharedPreferences preferences = PreferenceManager
//        .getDefaultSharedPreferences(context);
//    JSONObject json = null;
//    JSONArray root = null;
//    try {
//      if (!getSendInvitation(context).equalsIgnoreCase("")) {
//        json = new JSONObject(getSendInvitation(context));
//        root = json.getJSONArray("result");
//      } else {
//        json = new JSONObject();
//        root = new JSONArray();
//      }
//      JSONObject obj = new JSONObject();
//      obj.put("_id", invitation.getId());
//      obj.put("inviteType", invitation.getInviteType());
//      obj.put("place", invitation.getPlace());
//      obj.put("doWhat", invitation.getDoWhat());
//      obj.put("desc", invitation.getDesc());
//      obj.put("time", invitation.getTime());
//      obj.put("createTime", invitation.getCreateTime());
//      obj.put("from", invitation.getFrom());
//      obj.put("to", invitation.getTo());
//
//      root.put(obj);
//      json.put("result", root);
//      preferences.edit().putString(INVITATION_SEND, json.toString()).commit();
//    } catch (JSONException e) {
//      e.printStackTrace();
//    }
//  }

  public static String getReceiveInvitation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getString(INVITATION_RECEIVE, "");
  }

  public static void setReceiveInvitation(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putString(INVITATION_RECEIVE, "").commit();
  }
//
//  public static void setReceiveInvitation(Context context,
//      List<Invitation> invitations) {
//    SharedPreferences preferences = PreferenceManager
//        .getDefaultSharedPreferences(context);
//    JSONObject json = null;
//    JSONArray root = null;
//    try {
//      if (!getReceiveInvitation(context).equalsIgnoreCase("")) {
//        json = new JSONObject(getReceiveInvitation(context));
//        root = json.getJSONArray("result");
//      } else {
//        json = new JSONObject();
//        root = new JSONArray();
//      }
//      for (int i = 0; i < invitations.size(); i++) {
//        JSONObject obj = new JSONObject();
//        obj.put("_id", invitations.get(i).getId());
//        obj.put("inviteType", invitations.get(i).getInviteType());
//        obj.put("place", invitations.get(i).getPlace());
//        obj.put("doWhat", invitations.get(i).getDoWhat());
//        obj.put("time", invitations.get(i).getTime());
//        obj.put("createTime", invitations.get(i).getCreateTime());
//        obj.put("from", invitations.get(i).getFrom());
//        obj.put("desc", invitations.get(i).getDesc());
//        obj.put("to", invitations.get(i).getTo());
//
//        root.put(obj);
//      }
//      json.put("result", root);
//      preferences.edit().putString(INVITATION_RECEIVE, json.toString())
//          .commit();
//    } catch (JSONException e) {
//      e.printStackTrace();
//    }
//  }

//  public static void setNotifyData(Context context, NotifyItem item) {
//    SharedPreferences preferences = context.getSharedPreferences(
//        NOTIFY_CENTER_TAG, Activity.MODE_PRIVATE);
//    JSONObject json = null;
//    JSONArray root = null;
//    try {
//      if (!getNotifyData(context).equalsIgnoreCase("")) {
//        json = new JSONObject(getNotifyData(context));
//        root = json.getJSONArray("result");
//      } else {
//        json = new JSONObject();
//        root = new JSONArray();
//      }
//      JSONObject obj = new JSONObject();
//      obj.put("title", item.getTitle());
//      obj.put("time", item.getTime());
//      obj.put("content", item.getContent());
//      obj.put("id", item.getId());
//      obj.put("type", item.getType());
//      root.put(obj);
//      json.put("result", root);
//      preferences.edit().putString(NOTIFY_CENTER_TAG, json.toString()).commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//  }

  public static String getNotifyData(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(
        NOTIFY_CENTER_TAG, Activity.MODE_PRIVATE);
    return preferences.getString(NOTIFY_CENTER_TAG, "");
  }

  public static void clearNotifyData(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(
        NOTIFY_CENTER_TAG, Activity.MODE_PRIVATE);
    preferences.edit().clear().commit();
  }

  public static long getADMobTime(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getLong(ADMOB_VIISBLE_TIME, 0);
  }

  public static void setADMobTime(Context context, long time) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putLong(ADMOB_VIISBLE_TIME, time).commit();
  }

  public static boolean getNewNotify(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getBoolean(NEW_NOTIFY_TAG, false);
  }

  public static void setNewNotify(Context context, boolean change) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putBoolean(NEW_NOTIFY_TAG, change).commit();
  }

  public static void clearPreference(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().clear().commit();
    preferences = context.getSharedPreferences(CHAT_HISTORY,
        Activity.MODE_PRIVATE);
    preferences.edit().clear().commit();
  }

  public static void setAdRemove(Context context, boolean isRemove) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    preferences.edit().putBoolean(IS_REMOVE_AD, isRemove).commit();
  }

  public static boolean isAdRemove(Context context) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    return preferences.getBoolean(IS_REMOVE_AD, false);
  }

  public static void setBadgeNum(Context context, int number, int type) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    Editor editor = preferences.edit();
    switch (type) {
    case Constants.NOTIFY_TYPE_CHAT:
      editor.putInt(Constants.BADGE_CHAT, number);
      break;
    case Constants.NOTIFY_TYPE_INVITATION:
      editor.putInt(Constants.BADGE_INVITATION, number);
      break;
    case Constants.NOTIFY_TYPE_PARTY:
      editor.putInt(Constants.BADGE_PARTY, number);
      break;
    }
    editor.commit();
  }

  public static int getBadgeNum(Context context, int type) {
    SharedPreferences preferences = PreferenceManager
        .getDefaultSharedPreferences(context);
    int result = 0;
    switch (type) {
    case Constants.NOTIFY_TYPE_CHAT:
      result = preferences.getInt(Constants.BADGE_CHAT, 0);
      break;
    case Constants.NOTIFY_TYPE_INVITATION:
      result = preferences.getInt(Constants.BADGE_INVITATION, 0);
      break;
    case Constants.NOTIFY_TYPE_PARTY:
      result = preferences.getInt(Constants.BADGE_PARTY, 0);
      break;
    }
    return result;
  }

//  public static void setChatHistory(Context context, User user, String room_id) {
//    //離開程式以後, 資料還在 cache
//    SharedPreferences preferences = context.getSharedPreferences(CHAT_HISTORY,
//        Activity.MODE_PRIVATE);
//    JSONObject json = null;
//    JSONObject root = null;
//    try {
//      if (!getChatHistory(context).equalsIgnoreCase("")) {
//        root = new JSONObject(getChatHistory(context));
//        json = root.has(user.getAccessToken()) ? root.getJSONObject(user
//            .getAccessToken()) : new JSONObject();
//      } else {
//        root = new JSONObject();
//        json = new JSONObject();
//      }
//      if (root.has(user.getAccessToken()))
//        json.remove(user.getAccessToken());
//      json.put("id", user.getAccessToken());
//      json.put("room_id", room_id);
//      json.put("nickname", user.getNick_name());
//      json.put("time", System.currentTimeMillis());
//      root.put(user.getAccessToken(), json);
//      preferences.edit().putString(CHAT_HISTORY, root.toString()).commit();
//    } catch (Exception e) {
//      e.printStackTrace();
//    }
//
//  }

  public static String getChatHistory(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(CHAT_HISTORY,
        Activity.MODE_PRIVATE);
    return preferences.getString(CHAT_HISTORY, "");
  }

  public static void clearChatHistory(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(CHAT_HISTORY,
        Activity.MODE_PRIVATE);
    preferences.edit().clear().commit();
  }

  public static int getUnread(Context context, String room_id) {
    SharedPreferences preferences = context.getSharedPreferences(ROOM_UNREAD,
        Activity.MODE_PRIVATE);
    return preferences.getInt(room_id, 0);
  }

  public static void setUnread(Context context, String room_id) {
    SharedPreferences preferences = context.getSharedPreferences(ROOM_UNREAD,
        Activity.MODE_PRIVATE);
    preferences.edit().putInt(room_id, getUnread(context, room_id) + 1)
        .commit();
  }

  public static void setUnread(Context context, String room_id, int count) {
    SharedPreferences preferences = context.getSharedPreferences(ROOM_UNREAD,
        Activity.MODE_PRIVATE);
    preferences.edit().putInt(room_id, count).commit();
  }

  public static void clearUnreadCount(Context context) {
    SharedPreferences preferences = context.getSharedPreferences(ROOM_UNREAD,
        Activity.MODE_PRIVATE);
    preferences.edit().clear().commit();
  }

  public static void clearAll(Context context) {
    clearChatHistory(context);
    clearNotifyData(context);
    clearPreference(context);
    clearUnreadCount(context);

  }
  public static void setProfile(Context mContext, User user) {
      PreferencesUtil.setUserNo(mContext, user.getUser_no());
      PreferencesUtil.setUserName(mContext, user.getUser_name());
      PreferencesUtil.setUserToken(mContext, user.getUser_token());
      PreferencesUtil.setUserNickName(mContext, user.getNick_name());
      PreferencesUtil.setUserFrom(mContext, user.getFrom());
      PreferencesUtil.setUserLoginType(mContext, user.getLogin_type());
      PreferencesUtil.setGCMRegisterId(mContext, user.getGcm_id());
  }

//  public static void updateProfile(Context mContext, User user){
//      PreferencesUtil.setUserNo(mContext, user.getNo());
//      PreferencesUtil.setUserToken(mContext, user.getAccessToken());
//      PreferencesUtil.setUserNickName(mContext, user.getNick_name());
//      PreferencesUtil.setUserGender(mContext, user.getGender());
//      PreferencesUtil.setUserAccount(mContext, user.getAccount());
//      PreferencesUtil.setUserDesc(mContext, user.getIntroduction());
//      PreferencesUtil.setUserBirthday(mContext, user.getBirthday());
//      PreferencesUtil.setUserLocate(mContext, user.getLocation());
//
//      PreferencesUtil.setUserMoney(mContext, user.getMoney());
//      PreferencesUtil.setUserCoins(mContext, user.getCoins());
//      PreferencesUtil.setUserPhoto(mContext, user.getPhoto());
//  }

  public static void clearProfile(Context mContext){
      SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(mContext);
      preferences.edit().clear().commit();
  }
}
