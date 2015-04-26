package com.wowcow.chat10.Activity;

import android.app.Fragment;
import android.app.LoaderManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.wowcow.chat10.R;
import com.wowcow.chat10.Adapter.ChatAdapter;
import com.wowcow.chat10.Consts.Constants;
import com.wowcow.chat10.Consts.IntentAction;
import com.wowcow.chat10.imagecache.ImageFetcher;
import com.wowcow.chat10.Loader.chat.ChatGetChatsLoader;
import com.wowcow.chat10.Loader.chat.ChatGetRoomByIdLoader;
import com.wowcow.chat10.Loader.chat.ChatSendMsgLoader;
import com.wowcow.chat10.Models.ApiError;
import com.wowcow.chat10.Models.Chat;
import com.wowcow.chat10.Models.ChatRoom;
import com.wowcow.chat10.Models.User;
import com.wowcow.chat10.Parser.ChatRoomParser;
import com.wowcow.chat10.Parser.ChatSetParser;
import com.wowcow.chat10.Utils.APIResponse;
import com.wowcow.chat10.Utils.PreferencesUtil;
import com.wowcow.chat10.Utils.RandomGenerator;
import com.wowcow.chat10.Utils.ReceiverHelper;
import com.google.android.gms.ads.AdView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class ChatRoomActivity extends Fragment {
  ChatRoom room;
  TextView tv_receiver;
  Button btn_send;
  EditText et_message;
  Context mContext;
  List<Chat> datas;
  ChatAdapter adapter;
//  AdUtils adTool;
  AdView adView;
  ListView listview;
  ReceiverHelper receiver;
  ImageFetcher imageFetcher;
//  ActionBarUtils barTool;
//  ImageFetcherHelper imageFetcherHelper;
  ProgressDialog pd;
    View v;
    View root;
    LoaderManager loaderManager;

//    @Override
//    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
//        setContentView(R.layout.activity_chatroom);
//        mContext = this;
//        init();
//        return super.onCreateView(parent, name, context, attrs);
//    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = View.inflate(getActivity(), R.layout.activity_chatroom, container);
        loaderManager = getLoaderManager();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
  public void onStart() {
    // TODO Auto-generated method stub
    super.onStart();
//    EasyTracker.getInstance(this).activityStart(this);
  }
  @Override
  public void onStop() {
    // TODO Auto-generated method stub
    super.onStop();
//    EasyTracker.getInstance(this).activityStop(this);  // Add this method.
  }

//  @Override
//  protected void onNewIntent(Intent intent) {
//    super.onNewIntent(intent);
//  }

  private void init() {
    datas = new ArrayList<Chat>();
//    this.imageFetcherHelper = new ImageFetcherHelper(this);
//    this.imageFetcher = this.imageFetcherHelper.createImageFetcher();
    this.pd = new ProgressDialog(mContext);
    this.pd.setMessage(getString(R.string.text_progress_waiting));
    listview = (ListView) v.findViewById(R.id.listview);
//    adTool = new AdUtils(mContext, getWindow().getDecorView());
//    adView = adTool.setAdView(R.id.adview);
//    this.barTool = new ActionBarUtils(this, getActionBar());
//    this.barTool.setBackground(R.drawable.bg_corner2);
//    this.barTool.setTitleColors(getResources().getColor(R.color.white));

    //開啟聊天室
    if (getArguments().getSerializable(Constants.CHATROOM) != null) {
      room = (ChatRoom) getArguments().getSerializable(Constants.CHATROOM);
      // set history
      if (room.getCreator().getUser_token().equals(PreferencesUtil.getUserToken(mContext))) {
//        PreferencesUtil.setChatHistory(mContext, room.getReceiver(),room.getId());
      }else {
//        PreferencesUtil.setChatHistory(mContext, room.getCreator(),room.getId());
      }
      setRoomData();
    //已經在聊天室
    } else if (getArguments().getString(Constants.CHATROOM) != null) {
      Bundle bundle = new Bundle();
      bundle.putString(Constants.CHATROOM,getArguments().getString(Constants.CHATROOM));
        loaderManager.restartLoader(RandomGenerator.genLodaerId(), bundle, getRoomByIdLoaderCallbacks);
      pd.show();
    }
  }

  @Override
  public void onDestroy() {
//    imageFetcherHelper.handleOnDestroy();
    if (receiver != null)
      receiver.unregister(getActivity());
    super.onDestroy();
  }

    public void onResume() {
//    imageFetcherHelper.handleOnResume();
//    adTool.setRequest();

    super.onResume();
  };

  @Override
  public void onPause() {
//    imageFetcherHelper.handleOnPause();
    super.onPause();
  };

  private void setRoomData() {

    room.setTime(PreferencesUtil.getRoomLastTime(getActivity(), room.getId()));

//    this.barTool.setTitle(room.getReceiver().getAccessToken()
//        .equals(PreferencesUtil.getUserToken(mContext)) ? room.getCreator()
//        .getNickname() : room.getReceiver().getNickname());
    btn_send = (Button) v.findViewById(R.id.btn_send);
    et_message = (EditText) v.findViewById(R.id.et_msg);

    // listener
    btn_send.setOnClickListener(sendLisenter);

    // register recevier
    receiver = new ReceiverHelper();
    receiver.register(getActivity(), new BroadcastReceiver() {

      @Override
      public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras().getBundle("data");
        String nickname = bundle.getString("key2").split(":")[0];
          //todo change check user no
        User mCreator = room.getCreator();
        User mReceiver = room.getReceiver();
        if (nickname.equals(mCreator.getNick_name())
            || nickname.equals(mReceiver.getNick_name())) {
            loaderManager.restartLoader(RandomGenerator.genLodaerId(), null, getChatsLoaderCallbacks);
        } else {
          // TODO 不是此聊天室 推到Android的通知中心
        }

      }
    }, IntentAction.UPDATE_CHAT_LIST);
    // set histroy chat
    ChatSetParser parser = new ChatSetParser();
    try {
      datas = parser.toDataSet(
          PreferencesUtil.getRoomChats(mContext, room.getId())).getData();
      adapter = new ChatAdapter(mContext, datas);//, this.imageFetcher);
      listview.setAdapter(adapter);
      listview.setSelection(datas.size() - 1);
    } catch (JSONException e) {
      e.printStackTrace();
    }
      loaderManager.restartLoader(RandomGenerator.genLodaerId(), null, getChatsLoaderCallbacks);
  }

  OnClickListener sendLisenter = new OnClickListener() {
    @Override
    public void onClick(View v) {

      if (!et_message.getText().toString().equals("")) {

        Bundle bundle = new Bundle();
        bundle.putString("msg", et_message.getText().toString());
        bundle.putString("accessToken", PreferencesUtil.getUserToken(mContext));
        bundle.putString("room_id", room.getId());
        loaderManager.restartLoader(RandomGenerator.genLodaerId(), bundle, sendMsgLoaderCallbacks);
        et_message.setText("");

        /*
         * new AsyncTask() {
         * 
         * @Override protected String doInBackground(Object... params) { String
         * msg = ""; try { Bundle data = new Bundle();
         * data.putString("my_message", "Hello World");
         * data.putString("my_action",
         * "com.google.android.gcm.demo.app.ECHO_NOW"); String id =
         * Integer.toString(new AtomicInteger().incrementAndGet());
         * gcm.send(Constants.SENDER_ID + "@gcm.googleapis.com", id, data); msg
         * = "Sent message"; } catch (IOException ex) { msg = "Error :" +
         * ex.getMessage(); } return msg; }
         * 
         * protected void onPostExecute(String msg) { } }.execute(null, null,
         * null);
         */
      }
    }
  };

    private LoaderManager.LoaderCallbacks<APIResponse> sendMsgLoaderCallbacks = new LoaderManager.LoaderCallbacks<APIResponse>() {
        ChatSetParser parser = new ChatSetParser();
        @Override
        public android.content.Loader<APIResponse> onCreateLoader(int i, Bundle bundle) {
            String accessToken = bundle.getString("accessToken");
            String room_id = bundle.getString("room_id");
            String msg = bundle.getString("msg");
            return new ChatSendMsgLoader(mContext, accessToken, room_id, msg);
        }

        @Override
        public void onLoadFinished(android.content.Loader<APIResponse> loader, APIResponse data) {
            getLoaderManager().destroyLoader(loader.getId());
            if (data.getException() == null) {
                try {
                    ApiError error = parser.toError(data.getResult());
                    if (error == null) {
                        if (parser.toSuccess(data.getResult())) {
                            //todo in fragment use getLoaderManager
                            loaderManager.restartLoader(RandomGenerator.genLodaerId(), null, getChatsLoaderCallbacks);
                        }
                    } else {
                        // toast error but server side didn't write it
                    }
                } catch (Exception e) {

                }
            } else {

            }
        }

        @Override
        public void onLoaderReset(android.content.Loader loader) {

        }
    };


  private LoaderManager.LoaderCallbacks<APIResponse> getChatsLoaderCallbacks = new LoaderManager.LoaderCallbacks<APIResponse>() {
    ChatSetParser parser = new ChatSetParser();

    @Override
    public android.content.Loader<APIResponse> onCreateLoader(int id, Bundle bundle) {
      String chat_id = null;
      if (room.getTime() == -1) {
        room.setTime(0);
      }
      if (datas.size() != 0) {
        chat_id = datas.get(datas.size() - 1).getId();
      }

      return new ChatGetChatsLoader(mContext,
          PreferencesUtil.getUserToken(mContext), room.getId(), room.getTime(),
          chat_id);
    }

    @Override
    public void onLoadFinished(android.content.Loader<APIResponse> loader, APIResponse data) {
      getLoaderManager().destroyLoader(loader.getId());
      if (data.getException() == null) {
        try {
          ApiError error = parser.toError(data.getResult());
          if (error == null) {
            List<Chat> list = parser.toDataSet(data.getResult()).getData();
            Collections.reverse(list);
            datas.addAll(list);
            PreferencesUtil.setRoomLastTime(mContext, room.getId(),
                new Date().getTime());
            PreferencesUtil.setRoomChats(mContext, room.getId(), list);
            if (adapter == null) {
              adapter = new ChatAdapter(mContext, datas);//, imageFetcher);
              listview.setAdapter(adapter);
            } else {
              adapter.notifyDataSetChanged();
            }
            listview.setSelection(adapter.getCount() - 1);
            int badge = PreferencesUtil.getBadgeNum(mContext,
                Constants.NOTIFY_TYPE_CHAT)
                - PreferencesUtil.getUnread(mContext, room.getId());
            Intent action = new Intent();
            action.setAction(IntentAction.UPDATE_CHATROOM_LIST);
            PreferencesUtil.setBadgeNum(mContext, badge,
                Constants.NOTIFY_TYPE_CHAT);
            PreferencesUtil.setUnread(mContext, room.getId(), 0);
            getActivity().sendBroadcast(action);
          } else {
            Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG)
                .show();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {

      }
    }

    @Override
    public void onLoaderReset(android.content.Loader<APIResponse> arg0) {
    }
  };
  private android.app.LoaderManager.LoaderCallbacks<APIResponse> getRoomByIdLoaderCallbacks = new LoaderManager.LoaderCallbacks<APIResponse>() {
    ChatRoomParser parser = new ChatRoomParser();

    @Override
    public android.content.Loader<APIResponse> onCreateLoader(int id, Bundle bundle) {
      String room_id = bundle.getString(Constants.CHATROOM);

      return new ChatGetRoomByIdLoader(mContext,
          PreferencesUtil.getUserToken(mContext), room_id);
    }

    @Override
    public void onLoadFinished(android.content.Loader<APIResponse> loader, APIResponse data) {
      if (pd != null)
        pd.dismiss();
      getLoaderManager().destroyLoader(loader.getId());
      if (data.getException() == null) {
        try {
          ApiError error = parser.toError(data.getResult());
          if (error == null) {
            room = parser.toData(data.getResult());
            // set history
            if (room.getCreator().getUser_token().equals(PreferencesUtil.getUserToken(mContext))) {
//                PreferencesUtil.setChatHistory(mContext, room.getReceiver(), room.getId());
            }else {
//                PreferencesUtil.setChatHistory(mContext, room.getCreator(),room.getId());
            }

            setRoomData();
          } else {
            Toast.makeText(mContext, error.getMessage(), Toast.LENGTH_LONG)
                .show();
//            finish();
          }
        } catch (Exception e) {
          e.printStackTrace();
        }
      } else {
        Toast.makeText(mContext, data.getException().getMessage(),
                Toast.LENGTH_LONG).show();
//        finish();
      }
    }

    @Override
    public void onLoaderReset(android.content.Loader<APIResponse> arg0) {
    }
  };

}
