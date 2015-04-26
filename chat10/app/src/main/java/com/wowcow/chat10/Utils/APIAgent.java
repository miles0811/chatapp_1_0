package com.wowcow.chat10.Utils;

import android.text.TextUtils;
import android.util.Log;

//import com.wowcow.chat10.Models.Login;
//import com.wowcow.chat10.consts.Constants;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class APIAgent {

    private static final String API_URL = "http://54.178.131.192";
    // 版本號
    private static final String VERSION = "/version";
    // 使用者 User
  /*
   * 建立帳號
   */
    private static final String USER_FAST_CREATE = "/user/fastCreate";
    /*
     * 確認是否有此fb用戶
     */
    private static final String USER_CHECK_FACEBOOK = "/user/checkFacebook";
    /*
     * 以FB來建立帳號
     */
    private static final String USER_FACEBOOK_CREATE = "/user/facebookLogin";
    /*
     * 上傳使用者圖片
     */
    private static final String USER_UPLOAD_IMAGE = "/image/upload";
    /*
     * 取得使用者圖片
     */
    private static final String USER_GET_IMAGES = "/image/getImages";
    /*
     * 刪除使用者圖片
     */
    private static final String USER_DELETE_IMAGE = "/image/deleteImage";
    /*
     * 更換使用者圖片
     */
    private static final String USER_CHANGE_HEAD = "/image/changeHead";
    /*
     * 登入
     */
    private static final String LOGIN = "/do/login";

    /*
     * Register
     */
    private static final String REGISTER = "/auth/register";

    /*
     * 取得全部使用者清單
     */
    private static final String USER_FIND_ALL = "/user/getlist";
    private static final String USER_PRODUCT_FIND_ALL = "/user/getproductlist";
    private static final String USER_FIND = "/user/get";
    private static final String USER_RECEIVER_FIND = "/user/getreceiver";
    private static final String USER_UPDATE = "/user/update";
    private static final String USER_EXCHANGE = "/user/exchange";

    /*
     * 更新GCM REGISTER ID
     */
    private static final String USER_UPDATE_REGISTER = "/user/updateRegister";
    /*
     * 更新自我介紹
     */
    private static final String USER_UPDATE_PROFILE = "/user/updateProfile";
    /*
     * 取得個人資料
     */
    private static final String USER_GET_PROFILE = "/user/getProfile";

    /*
     * 取得點數資料
     */
    private static final String USER_GET_POINT = "/user/getPoints";
    // 聊天室
  /*
   * 創建聊天室
   */
    private static final String CHATROOM_CREATE = "/chatroom/create";

    /*
     * 發送訊息
     */
    private static final String CHATROOM_CHAT = "/chatroom/chat";

    /*
     * 取得訊息
     */
    private static final String CHATROOM_GETCHATS = "/chatroom/getchats";
    /*
     * 取得聊天室列表
     */
    private static final String CHATROOM_GETROOMS = "/chatroom/getrooms";
    /*
     * 取得聊天室資料
     */
    private static final String CHATROOM_GETROOM = "/chatroom/getroombyid";
    // 邀請
  /*
   * 取得邀請清單
   */
    private static final String INVITATION_GET_LIST = "/invitation/getList";
    /*
     * 取得邀請資料 by id
     */
    private static final String INVITATION_GET_BY_ID = "/invitation/getInvitationById";
    /*
     * 發出邀請
     */
    private static final String INVITATION_SEND = "/invitation/invite";
    /*
     * 回應邀請
     */
    private static final String INVITATION_REPLY = "/invitation/reply";
    /*
     * 取得寄送出的邀請函
     */
    private static final String INVITATION_SENDER = "/invitation/getSenders";
    // 儲值
  /*
   * 驗證訂單並且儲值
   */
    private static final String BILLING_CHECK = "/IAB/check";
    /*
     * 去廣告
     */
    private static final String REMOVE_AD = "/IAB/checkRemoveAd";
    /*
     * 使用點數
     */
    private static final String POINTS_USE = "/IAB/usePoint";

    // 競標派對
  /*
   * 建立PARTY
   */
    private static final String PARTY_CREATE = "/party/create";
    /*
     * 取得競標中的派對
     */
    private static final String PARTY_GET_LIST = "/party/getParties";
    /*
     * 取得自己的派對資料
     */
    private static final String PARTY_GET_MY_LIST = "/party/getMyParties";
    /*
     * 由派對ID來取得派對資料
     */
    private static final String PARTY_GET_BY_ID = "/party/getPartyByID";
    /*
     * 競標派對
     */
    private static final String PARTY_BID = "/party/bidParty";
    /*
     * 取得投標最高五人
     */
    private static final String PARTY_GET_PARTICIPATION = "/party/getParticipation";
    /*
     * 選擇派對贏家
     */
    private static final String PARTY_SELECT_WINNER = "/party/selectWinner";
    /*
     * 取得派對圖片路徑
     */
    private static final String PARTY_GET_SHOW_IMAGE = "/party/getShowImage";
    /*
     * 是否有競標人
     */
    private static final String PARTY_HAS_PARTICIPATION = "/party/hasParticipations";

    /*
    購買
     */
    private static final String PURCHASE = "/trade/purchase";
    private static final String PURCHASE_ITEM = "/trade/item";
//  private static final String PURCHASE_ITEM_PICTURE = "/trade/pictureitem";

    /*
    評價
     */
    private static final String APPRAISE = "/appraise/getuser";
    private static final String APPRAISE_INFO = "/appraise/getinfo";
    private static final String APPRAISE_LIST = "/appraise/getlist";
    private static final String APPRAISE_SET = "/appraise/update";

    /*
    交易
     */
    private static final String TRADE_LIST = "/trade/tradelist";

    /*
    精選自拍
     */
    private static final String PICTURE_FIND_ALL = "/photo/getpicture";
    private static final String PICTURE_FIND = "/picture/get";
    private static final String PICTURE_ADD = "/photo/addpicture";
    private static final String PICTURE_UPDATE = "/photo/updatepicture";
    /*
    旅伴服務
     */
    private static final String TRAVELS_FIND_ALL = "/travel/gettravels";
    private static final String TRAVEL_FIND = "/travel/get";
    private static final String TRAVEL_ADD = "/travel/add";
    private static final String TRAVEL_UPDATE = "/travel/update";

    @SuppressWarnings("unused")
    private static void putValidString(Map<String, String> params, String key,
                                       String value) {
        if (TextUtils.isEmpty(value) == false) {
            params.put(key, value);
        }
    }

    @SuppressWarnings("unused")
    private static void putValidInt(Map<String, String> params, String key,
                                    int value) {
        if (value != 0) {
            params.put(key, String.valueOf(value));
        }
    }

    @SuppressWarnings("unused")
    private static void putValidDouble(Map<String, String> params, String key,
                                       double value) {
        if (value != 0) {
            params.put(key, String.valueOf(value));
        }
    }

    @SuppressWarnings("unused")
    private static void putValidCollection(Map<String, String> params,
                                           String key, Collection<String> value) {
        if (value != null && value.size() > 0) {
            StringBuffer sb = new StringBuffer();
            boolean first = true;
            for (String s : value) {
                if (first) {
                    first = false;
                } else {
                    sb.append(",");
                }
                sb.append(s);
            }

            params.put(key, sb.toString());
        }
    }

    // version
    public static String getVersion() throws Exception {
        return HttpUtil.get(API_URL + VERSION);
    }

    // user

    public static String doUserCreate(String nickname, int gender,
                                      String account, String registerId, String password, String image_name, String login_type) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("nick_name", nickname);
        obj.put("gender", gender);
        obj.put("register_id", registerId);
        obj.put("account", account);
        obj.put("password", password);
        obj.put("photo", image_name);
        obj.put("policy", "1");

        if (login_type.equals("fb")) {
//        System.out.println("==> doUserCreate: "+login_type);
            return HttpUtil.post("http://missdate.cookietime.tw/do/registerfb", obj.toString());
        } else {
            return HttpUtil.post("http://missdate.cookietime.tw/do/register", obj.toString());
        }
    }

    public static String checkFacebookUser(String fb_id) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("fb_id", fb_id);
        return HttpUtil.post(API_URL + USER_CHECK_FACEBOOK, obj.toString());
    }

    public static String doFacebookCreate(String fb_id, String nickname,
                                          int gender, String registerId) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("fb_id", fb_id);
        obj.put("nick_name", nickname);
        obj.put("gender", gender);
        obj.put("registerId", registerId);


        return HttpUtil.post(API_URL + USER_FACEBOOK_CREATE, obj.toString());
    }

    public static String doUserUploadImages(String account, String password, String accessToken,
                                            List<File> images, String type) throws Exception {
        Map<String, String> params = new HashMap<String, String>();
        Map<String, File> fileParams = new HashMap<String, File>();
        params.put("account", account);
        params.put("password", password);
        params.put("access_token", accessToken);
        params.put("type", type + "");
        for (int i = 0; i < images.size(); i++) {
            fileParams.put(i + "", images.get(i));
        }

//    return HttpUtil.multipart(API_URL + USER_UPLOAD_IMAGE, params, fileParams);
//      System.out.println("POST UPLOAD: "+ account+"/"+password+"/"+accessToken);
        return HttpUtil.multipart("http://missdate.cookietime.tw/photo/upload", params, fileParams);
    }

    public static String doUserGetImages(String accessToken) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + USER_GET_IMAGES, obj.toString());
    }

    public static String doUserDeleteImage(String path) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("path", path);
        return HttpUtil.post(API_URL + USER_DELETE_IMAGE, obj.toString());
    }

    public static String doUserChangeHead(String accessToken, String path)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("path", path);
        return HttpUtil.post(API_URL + USER_CHANGE_HEAD, obj.toString());
    }

    public static String login(String account, String pwd, String registerId, String login_type) throws Exception {
        // String params = "/" + account + "/" + pwd;
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", pwd);
        obj.put("register_id", registerId);
        obj.put("login_type", login_type);
        return HttpUtil.post(API_URL + LOGIN, obj.toString());
    }

    public static String register(String gcm_id, String nick_name) throws Exception {
        // String params = "/" + account + "/" + pwd;
//        JSONObject obj = new JSONObject();
//        obj.put("gcm_id", gcm_id);
//        obj.put("nick_name", nick_name);
        List<NameValuePair> nameValuePair = new ArrayList<NameValuePair>(2);
        nameValuePair.add(new BasicNameValuePair("gcm_id", gcm_id));
        nameValuePair.add(new BasicNameValuePair("nick_name", nick_name));
        return HttpUtil.post(API_URL + REGISTER, nameValuePair);
    }

    public static String getAllUserList(String accessToke, int user_no, int gender, String type) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToke);
        obj.put("user_no", user_no);
        obj.put("gender", gender);
        obj.put("type", type);
        return HttpUtil.post(API_URL + USER_FIND_ALL, obj.toString());
    }

    public static String getAllUserProductList(String accessToke, int user_no, int gender) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToke);
        obj.put("user_no", user_no);
        obj.put("gender", gender);
        return HttpUtil.post(API_URL + USER_PRODUCT_FIND_ALL, obj.toString());
    }

//    public static String getUserList(Login login) throws Exception {
//        JSONObject obj = new JSONObject();
//        asLogin(obj, login);
//        return HttpUtil.post(API_URL + USER_FIND, obj.toString());
//    }
//    public static String getUser(Login login) throws Exception {
//        JSONObject obj = new JSONObject();
//        asLogin(obj, login);
//        return HttpUtil.post(API_URL + USER_FIND, obj.toString());
//    }

//    public static String userExchange(Login login, String user_name, String user_phone, String item_name, String amount) throws Exception {
//        JSONObject obj = new JSONObject();
//        asLogin(obj, login);
//        obj.put("user_name", user_name);
//        obj.put("user_phone", user_phone);
//        obj.put("item_name", item_name);
//        obj.put("amount", amount);
//        return HttpUtil.post(API_URL + USER_EXCHANGE, obj.toString());
//    }
//
//    public static String getReceiverUser(Login login, int user_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        asLogin(obj, login);
//        obj.put("user_no", user_no);
//        return HttpUtil.post(API_URL + USER_RECEIVER_FIND, obj.toString());
//    }
//
//    public static String updateUser(Login login, int user_no, String info_date, String info_location, String info_introduction, String image_name) throws Exception {
//        JSONObject obj = new JSONObject();
//        asLogin(obj, login);
//        obj.put("birthday", info_date);
//        obj.put("location", info_location);
//        obj.put("introduction", info_introduction);
//        obj.put("image_name", image_name);
//        return HttpUtil.post(API_URL + USER_UPDATE, obj.toString());
//    }
//
//
//    public static String addTravelItem(Login login, String purchase, String type, String title, String photo, String desc, String msg, String destination, int pay, String end_at) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("purchase", purchase);
//        obj.put("type", type);
//        obj.put("title", title);
//        obj.put("photo", photo);
//        obj.put("desc", desc);
//        obj.put("msg", msg);
//        obj.put("location", destination);
//        obj.put("pay", pay);
//        obj.put("end_at", end_at);
//        return HttpUtil.post(API_URL + TRAVEL_ADD, obj.toString());
//    }

//    public static String deleteTravelItem(Login login, int item_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("item_no", item_no);
//        obj.put("type", "delete");
//        return HttpUtil.post(API_URL + TRAVEL_UPDATE, obj.toString());
//    }
//
//    public static String addPictureItem(Login login, String purchase, String photo, String desc, String end_at) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("purchase", purchase);
//        obj.put("photo", photo);
//        obj.put("desc", desc);
//        obj.put("end_at", end_at);
//        return HttpUtil.post(API_URL + PICTURE_ADD, obj.toString());
//    }
//
//    public static String deletePictureItem(Login login, int item_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("item_no", item_no);
//        obj.put("type", "delete");
//        return HttpUtil.post(API_URL + PICTURE_UPDATE, obj.toString());
//    }
//
//    public static String getItemPictureList(Login login, int item_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("item_no", item_no);
//        return HttpUtil.post(API_URL + PICTURE_FIND, obj.toString());
//    }
//
//    public static String getAllPictureList(Login login, int user_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("user_no", user_no);
//        return HttpUtil.post(API_URL + PICTURE_FIND_ALL, obj.toString());
//    }
//    public static String getTravelsList(Login login, int user_no) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("user_no", user_no);
//        return HttpUtil.post(API_URL + TRAVELS_FIND_ALL, obj.toString());
//    }

    public static String getAppraiseList(String account, String password, String accessToke, String status) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", accessToke);
        obj.put("status", status);
        Log.i("test", "getAppraiseList");
        return HttpUtil.post(API_URL + APPRAISE_LIST, obj.toString());
    }

    public static String getAppraise(String account, String password, String accessToke, int user_no, int item_no, String item_type) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", accessToke);
        obj.put("status", "done");
        obj.put("user_no", user_no);
        obj.put("item_no", item_no);
        obj.put("item_type", item_type);
        return HttpUtil.post(API_URL + APPRAISE, obj.toString());
    }

    public static String getDefaultAppraise(String account, String password, String accessToke, int user_no) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", accessToke);
        obj.put("user_no", user_no);
        return HttpUtil.post(API_URL + APPRAISE_INFO, obj.toString());
    }

//    public static String setAppraise(String appr_content, Login login, int appr_no, String appraise_type) throws Exception {
//        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
//        obj.put("content", appr_content);
//        obj.put("appr_no", appr_no);
//        obj.put("type", appraise_type);
//        return HttpUtil.post(API_URL + APPRAISE_SET, obj.toString());
//    }

    public static String getTradeList(String account, String password, String accessToke) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", accessToke);
        return HttpUtil.post(API_URL + TRADE_LIST, obj.toString());
    }

    public static String getTrvel(String account, String password, String accessToke, int item_no) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", accessToke);
        obj.put("item_no", item_no);
        return HttpUtil.post(API_URL + TRAVEL_FIND, obj.toString());
    }

    public static String doUserUpdateRegister(String accessToken,
                                              String registerId, String ip) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("registerId", registerId);
        obj.put("ip", ip);
        return HttpUtil.post(API_URL + USER_UPDATE_REGISTER, obj.toString());
    }

    public static String doUserUpdateProfile(String accessToken, int type,
                                             String data) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("type", type);
        obj.put("data", data);
        return HttpUtil.post(API_URL + USER_UPDATE_PROFILE, obj.toString());
    }

    public static String doUserGetProfile(String accessToken) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + USER_GET_PROFILE, obj.toString());
    }

    public static String doUserGetPoints(String accessToken) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + USER_GET_POINT, obj.toString());
    }

    public static String doChatRoomCreate(String token, int toUserNo, int item_no)
            throws Exception {
        JSONObject obj = new JSONObject();
//    obj = asLogin(obj, login);
        obj.put("token", token);
        obj.put("to_user_no", toUserNo);
        obj.put("item_no", item_no);
        return HttpUtil.post(API_URL + CHATROOM_CREATE, obj.toString());
    }

    public static String doChatRoomChat(String token, String room_no, String msg) throws Exception {
        JSONObject obj = new JSONObject();
//    obj = asLogin(obj, login);
        obj.put("token", token);
        obj.put("room_no", room_no);
        obj.put("msg", msg);
        return HttpUtil.post(API_URL + CHATROOM_CHAT, obj.toString());
    }

    public static String doChatGetChats(String token, String room_no, long time, String chat_id) throws Exception {
        JSONObject obj = new JSONObject();
//    obj = asLogin(obj, login);
        obj.put("token", token);
        obj.put("room_no", room_no);
        obj.put("time", time);
        obj.put("chat_id", chat_id);
        return HttpUtil.post(API_URL + CHATROOM_GETCHATS, obj.toString());
    }

    public static String doChatGetRooms(String token) throws Exception {
        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
        obj.put("token", token);
        return HttpUtil.post(API_URL + CHATROOM_GETROOMS, obj.toString());
    }

    public static String doChatGetRoomById(String token, String room_no)
            throws Exception {
        JSONObject obj = new JSONObject();
//        obj = asLogin(obj, login);
        obj.put("token", token);
        obj.put("room_no", room_no);
        return HttpUtil.post(API_URL + CHATROOM_GETROOM, obj.toString());
    }

    public static String doInvitationGetList(String accessToken, String last_id)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("last_id", last_id);

        return HttpUtil.post(API_URL + INVITATION_GET_LIST, obj.toString());
    }

    public static String doInvitationGetById(String accessToken,
                                             String invitation_id) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("invitation_id", invitation_id);

        return HttpUtil.post(API_URL + INVITATION_GET_BY_ID, obj.toString());
    }

    public static String doInvitationReply(String accessToken, String invite_id,
                                           int reply) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("invite_id", invite_id);
        obj.put("decide", reply);
        return HttpUtil.post(API_URL + INVITATION_REPLY, obj.toString());
    }

    public static String getInvitationSenders(String accessToken)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + INVITATION_SENDER, obj.toString());
    }

    public static String doInvitationSend(String accessToken, String to,
                                          int inviteType, String place, long time, String doWhat, String desc)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("to", to);
        obj.put("inviteType", inviteType);
        obj.put("place", place);
        obj.put("time", time);
        obj.put("doWhat", doWhat);
        obj.put("desc", desc);

        return HttpUtil.post(API_URL + INVITATION_SEND, obj.toString());
    }

    public static String doBillingCheck(String accessToken, String purchaseData,
                                        String signature) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("purchaseData", purchaseData);
        obj.put("signature", signature);
        // Log.v(Constants.TAG, "post output : " + obj.toString());
        return HttpUtil.post(API_URL + BILLING_CHECK, obj.toString());
    }

    public static String doRemoveAd(String accessToken, String purchaseData,
                                    String signature) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("purchaseData", purchaseData);
        obj.put("signature", signature);
        // Log.v(Constants.TAG, "post output : " + obj.toString());
        return HttpUtil.post(API_URL + REMOVE_AD, obj.toString());
    }

    public static String doPointsUse(String accessToken, int points)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("points", points);
        return HttpUtil.post(API_URL + POINTS_USE, obj.toString());
    }

//  public static String doPartyCreate(String accessToken, int category,
//      int start_price, String language, String allowedTalk, String hopeContact,
//      String travel_type, String travel_time, String travel_place)
//      throws Exception {
//    JSONObject obj = new JSONObject();
//    obj.put("access_token", accessToken);
//    obj.put("category", category);
//    obj.put("start_price", start_price);
//    obj.put("language", language);
//    obj.put("allowedTalk", allowedTalk);
//    obj.put("hopeContact", hopeContact);
//    if (category == Constants.PARTY_CATEGORY_TRAVEL_TAG) {
//      obj.put("travel_type", travel_type);
//      obj.put("travel_time", travel_time);
//      obj.put("travel_place", travel_place);
//    }
//
//    return HttpUtil.post(API_URL + PARTY_CREATE, obj.toString());
//  }

    public static String doPartyGetList(String accessToken) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + PARTY_GET_LIST, obj.toString());
    }

    public static String doPartyGetMyList(String accessToken) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        return HttpUtil.post(API_URL + PARTY_GET_MY_LIST, obj.toString());
    }

    public static String doPartyGetByID(String accessToken, String b_id)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("b_id", b_id);
        return HttpUtil.post(API_URL + PARTY_GET_BY_ID, obj.toString());
    }

    public static String doPartyBid(String accessToken, String b_id, int price)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("b_id", b_id);
        obj.put("price", price);
        return HttpUtil.post(API_URL + PARTY_BID, obj.toString());
    }

    public static String doPartyGetParticipation(String accessToken, String b_id)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("b_id", b_id);
        return HttpUtil.post(API_URL + PARTY_GET_PARTICIPATION, obj.toString());
    }

    public static String doPartySelectWinner(String accessToken, String b_id,
                                             String u_id) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("b_id", b_id);
        obj.put("u_id", u_id);
        return HttpUtil.post(API_URL + PARTY_SELECT_WINNER, obj.toString());
    }

    public static String doPartyGetImage(String accessToken, int category)
            throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("access_token", accessToken);
        obj.put("category", category);
        return HttpUtil.post(API_URL + PARTY_GET_SHOW_IMAGE, obj.toString());
    }

    public static String hasParticipations(String b_id) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("b_id", b_id);
        return HttpUtil.post(API_URL + PARTY_HAS_PARTICIPATION, obj.toString());
    }

    public static String doPurchaseItem(String account, String password, String registerId, Integer item_no, String item_type, int item_user) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", registerId);
        obj.put("item_no", item_no);
        obj.put("item_type", item_type);
        obj.put("item_user", item_user);
        return HttpUtil.post(API_URL + PURCHASE_ITEM, obj.toString());
    }

    public static String doPurchaseMoney(String account, String password, String registerId, String amount) throws Exception {
        JSONObject obj = new JSONObject();
        obj.put("account", account);
        obj.put("password", password);
        obj.put("access_token", registerId);
        obj.put("amount", amount);
        return HttpUtil.post(API_URL + PURCHASE, obj.toString());
    }

//  public static JSONObject asLogin(JSONObject json, Login login) throws Exception {
//      json.put("account", login.getAccount());
//      json.put("password", login.getPassword());
//      json.put("access_token", login.getAccess_token());
//
//      return json;
//  }
}