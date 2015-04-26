package com.wowcow.chat10.Consts;

/**
 * Created by gametree on 15/1/15.
 */
public class Constants {
    // ============ Deposit =======================
    public static final String PKEY = "4d494942496a414e42676b71686b6947397730424151454641414f43415138414d49494243674b43415145416c6733662f4f556d59636e526379544d54476a46562f4c4a426872646f41504f75456d6e61683266305579356e6a4e697335746f637a6e506e4d67366a6d44552f31615544662b6a4736416a54576630784d736a726b42365044633752343673634a757773466f543763317065435067564468657a5979713455703469753831584554417a453337532f416146652f4951713461734e62554c715348767438474931367655347535596845665630587177474163434147704b3174556a623231445a38754a324436374531336e6e4a586b6b414a4f504c51473145362b7248744c6b76454c663172564e676c76643638554767374877485030493361714249746e4d746241414a37485635743750472f50512b4d704a69727263663976424772393131595671426f5534747574384c626e62524a4f6237497062565a7a317437525277764861575374574d6345434d64506c7935645149444150f35f";
    //=============================================
    public static final String TESTURL = "http://api.cookietime.tw:2014";
    public static final String TESTIMAGURL = "http://img.cookietime.tw/";
    public static final String IMAGEURL = "http://cdn.cookietime.tw/photo/";
    public static final String SENDER_ID = "204394515304";//"820582180119";
    public static final String TABACTIVITY_TAG = "com.cookietime.callme.activity.TabActivity";
    public static final String CHATROOM_TAG = "com.cookietime.callme.Activity.ChatRoomActivity";
    public static final String CHATROOM_LIST_TAG = "com.cookietime.callme.fragment.ChatRoomListFragment";
    public static final String TAG = "com.cookietime.callme";
    public static final String FACEBOOK_PHOTO_URL = "https://graph.facebook.com/%s/picture";
    public static final boolean TEST_MODE = false;
    // ===========================================================
    // ========ACTIVITY CODE==============================
    public static final int ACT_TRAVEL = 103;

    // ========constants name==============================
    public static final String USER_DATA = "user";
    public static final String APPRAISE_DATA = "appraise";
    public static final String PICTURE_DATA = "picture";
    public static final String TRAVEL_DATA = "travel";
    public static final String UPLOAD_FILE = "file";
    public static final String IMAGE_TAG = "image";
    public static final String TO_ACCOUNT = "toAccount";
    public static final String CHATROOM = "chatroom";
    public static final String OPEN_CHATROOM = "open_chatroom";
    public static final String INVITATION = "invitation";
    // ========profile update type===============
    public static final String PROFILE_TYPE_TAG = "type";
    public static final String PROFILE_DATA_TAG = "data";
    public static final int PROFILE_MALE_TAG = 0;
    public static final int PROFILE_FEMALE_TAG = 1;
    public static final int PROFILE_TYPE_DESC = 0;
    public static final int PROFILE_TYPE_NICKNAME = 1;
    public static final int PROFILE_TYPE_LOCATE = 2;
    public static final int PROFILE_TYPE_BIRTHDAY = 3;
    public static final int PROFILE_TYPE_CONSTELLATION = 4;
    public static final int PROFILE_TYPE_AGE = 5;
    public static final int PROFILE_TYPE_BODY = 6;
    public static final int PROFILE_TYPE_PERSONALIZED = 7;
    public static final int PROFILE_TYPE_HOLIDAY = 8;
    public static final int PROFILE_TYPE_AFTER_MEET = 9;
    public static final int PROFILE_TYPE_FIRST_PAY = 10;
    public static final int PROFILE_TYPE_ACCOUNT = 11;
    // ========party constants ============================
    public static final String PARTY_TYPE_TAG = "type";
    public static final String PARTY_DATA_TAG = "data";
    public static final String PARTY_START_PRICE = "start_price";
    public static final int PARTY_TYPE_CATEGORY = 0;
    public static final int PARTY_TYPE_LANGUAGE = 1;
    public static final int PARTY_TYPE_ALLOWED_TALK = 2;
    public static final int PARTY_TYPE_HOPE_CONTACT_DEEP = 3;
    public static final int PARTY_TYPE_TRAVEL = 4;
    public static final int PARTY_TYPE_TRAVEL_TIME = 5;
    public static final int PARTY_TYPE_TRAVEL_PLACE = 6;
    // ========constants upload img type ===================
    public static final int IMAGE_TYPE_PROFILE = 1;
    public static final int IMAGE_TYPE_OTHERS = 2;
    public static final int PARTY_CATEGORY_NORMAL_TAG = 3;
    public static final int PARTY_CATEGORY_TRAVEL_TAG = 4;
    // ========product item ================================
    public static final String ITEM_DATING_TAG = "dating_data";
    public static final String ITEM_DATING_100 = "items_100";
    public static final String ITEM_DATING_300 = "items_300";
    public static final String ITEM_DATING_500 = "items_500";
    public static final String ITEM_DATING_1000 = "items_1000";
    public static final String ITEM_DATING_3000 = "items_3000";
    public static final String ITEM_REMOVE_AD = "items_remove_ad";
    // ======== invitation constants ======================
    public static final int INVITATION_REPLY_NONE = 0;
    public static final int INVITATION_REPLY_ACCEPT = 1;
    public static final int INVITATION_REPLY_REJECT = 2;
    public static final String INVITATION_TYPE_TAG = "type";
    public static final String INVITATION_DATA_TAG = "data";
    // ======== drawer list ===============================
    public static final int DRAWER_NOTIFICATION_TAG = 0;
    public static final int DRAWER_STORE_TAG = 1;
    public static final int DRAWER_CHAT_HISTORY_TAG = 2;
    // ========else========================================
    public static final long ONE_DAY_TIME = 1000 * 60 * 60 * 24;
    public static final int NOTIFY_TYPE_CHAT = 1;
    public static final int NOTIFY_TYPE_INVITATION = 2;
    public static final int NOTIFY_TYPE_PARTY = 3;
    public static final String BADGE_CHAT = "badge_chat";
    public static final String BADGE_INVITATION = "badge_invitation";
    public static final String BADGE_PARTY = "badge_party";
    public static final char[] ACCOUNT_LEGAL_INPUT = { 'a', 'b', 'c', 'd', 'e', 'f',
            'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
            'u', 'v', 'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H',
            'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V',
            'W', 'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
}

