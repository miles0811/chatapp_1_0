package com.wowcow.chat10.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wowcow.chat10.R;
import com.wowcow.chat10.Consts.Constants;
import com.wowcow.chat10.imagecache.ImageFetcher;
import com.wowcow.chat10.Models.Chat;
import com.wowcow.chat10.Models.Gender;
import com.wowcow.chat10.Utils.PreferencesUtil;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

@SuppressLint("SimpleDateFormat")
public class ChatAdapter extends BaseAdapter {
  LayoutInflater inflater;
  Context mContext;
  List<Chat> datas;
//  ImageFetcher imageFetcher;
  int bubble_width = 0;
  ImageLoader imageLoader;
  SimpleDateFormat time_sdf, date_sdf;

  public ChatAdapter(Context context, List<Chat> datas){//,ImageFetcher imageFetcher) {
    this.mContext = context;
    this.inflater = LayoutInflater.from(context);
    this.datas = datas;
//    this.imageFetcher = imageFetcher;
    this.time_sdf = new SimpleDateFormat("HH:mm", context.getResources()
        .getConfiguration().locale);
    this.date_sdf = new SimpleDateFormat("yyyy/MM/dd", context.getResources()
        .getConfiguration().locale);
    this.imageLoader = ImageLoader.getInstance();
    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
        context).threadPriority(Thread.NORM_PRIORITY - 2)
        .denyCacheImageMultipleSizesInMemory()
        .discCacheFileNameGenerator(new Md5FileNameGenerator())
//        .tasksProcessingOrder(QueueProcessingType.LIFO).enableLogging().build();
        .tasksProcessingOrder(QueueProcessingType.LIFO).build();
    ImageLoader.getInstance().init(config);
    bubble_width = context.getResources().getDisplayMetrics().widthPixels / 2;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    ChatHolder holder;
    if (convertView == null) {
      convertView = inflater.inflate(R.layout.chat_item, null);
      holder = new ChatHolder();
      holder.layout_date = (RelativeLayout) convertView
          .findViewById(R.id.layout_date);
      holder.layout_left = (RelativeLayout) convertView
          .findViewById(R.id.layout_left);
      holder.layout_right = (RelativeLayout) convertView
          .findViewById(R.id.layout_right);
      holder.date = (TextView) convertView.findViewById(R.id.tv_chat_date);
      holder.left_msg = (TextView) convertView.findViewById(R.id.tv_left_msg);
      holder.right_msg = (TextView) convertView.findViewById(R.id.tv_right_msg);
      holder.left_time = (TextView) convertView.findViewById(R.id.tv_left_time);
      holder.right_time = (TextView) convertView
          .findViewById(R.id.tv_right_time);
      holder.left_image = (ImageView) convertView
          .findViewById(R.id.iv_left_image);
      convertView.setTag(holder);
    } else {
      holder = (ChatHolder) convertView.getTag();
    }
    if (datas == null || datas.get(position).getCreator() == null)
      return convertView;
    if (datas.get(position).getCreator().getAccessToken()
        .equals(PreferencesUtil.getUserToken(mContext))) {
      setVisible(holder, holder.layout_right, position);
    } else {
      setVisible(holder, holder.layout_left, position);
    }
//    holder.left_image.setOnClickListener(new OnClickListener() {
//      @Override
//      public void onClick(View v) {
//        Intent intent = new Intent(mContext, UserProfileActivity.class);
//        intent.setAction(Constants.CHATROOM);
//        intent.putExtra(Constants.USER_DATA, datas.get(position).getCreator());
//        mContext.startActivity(intent);
//      }
//    });
    checkCrossDay(holder, position);
    return convertView;

  }

  private void checkCrossDay(ChatHolder holder, int position) {
    long time = datas.get(position).getTime();

    if (position == 0) {
      holder.layout_date.setVisibility(View.VISIBLE);
      holder.date.setText(date_sdf.format(time));
    } else {
      Calendar date1 = Calendar.getInstance();
      date1.setTimeInMillis(time);
      Calendar date2 = Calendar.getInstance();
      date2.setTimeInMillis(datas.get(position - 1).getTime());
      boolean sameDay = date1.get(Calendar.YEAR) == date2.get(Calendar.YEAR)
          && date1.get(Calendar.DAY_OF_YEAR) == date2.get(Calendar.DAY_OF_YEAR);
      if (!sameDay) {
        holder.layout_date.setVisibility(View.VISIBLE);
        holder.date.setText(date_sdf.format(time));
      } else {
        holder.layout_date.setVisibility(View.GONE);
      }
    }

  }

  private void setVisible(ChatHolder holder, View mView, int position) {
    holder.layout_left.setVisibility(View.GONE);
    holder.layout_right.setVisibility(View.GONE);
    String msg = datas.get(position).getMsg();
    String time = time_sdf.format(datas.get(position).getTime());
    // msg = msg.substring(1, msg.length() - 1);
    String image = datas.get(position).getCreator().getImagePath();
    switch (mView.getId()) {
    case R.id.layout_left:
      holder.left_msg.setMaxWidth(bubble_width);
      holder.layout_left.setVisibility(View.VISIBLE);
      holder.left_time.setText(time);
      holder.left_msg.setText(msg);
      int mGender = datas.get(position).getCreator().getGender() == Gender.MALE
          .ordinal() ? Gender.getDefaultMale() : Gender.getDefaultFemale();
      String mDefault = "drawable://" + mGender;
      String mDrawable = image != null ? Constants.TESTIMAGURL + image
          : mDefault;
      DisplayImageOptions options = new DisplayImageOptions.Builder()
          .showStubImage(mGender).showImageForEmptyUri(mGender)
          .showImageOnFail(mGender).cacheInMemory().cacheOnDisc()
          .displayer(new RoundedBitmapDisplayer(20)).build();
      imageLoader.displayImage(mDrawable, holder.left_image, options, null);
      // this.imageFetcher
      // .loadThumbnailImage(
      // Constants.TESTIMAGURL + image,
      // holder.left_image,
      // datas.get(position).getCreator().getGender() == Gender.MALE
      // .ordinal() ? Gender.getDefaultMale() : Gender
      // .getDefaultFemale());
      break;
    default:
      holder.right_msg.setMaxWidth(bubble_width);
      holder.layout_right.setVisibility(View.VISIBLE);
      holder.right_time.setText(time);
      holder.right_msg.setText(msg);
      break;
    }
  }

  class ChatHolder {
    TextView left_msg, right_msg, left_time, right_time, date;
    ImageView left_image;
    RelativeLayout layout_left, layout_right, layout_date;
  }

  @Override
  public int getCount() {
    return datas.size();
  }

  @Override
  public Object getItem(int position) {
    return datas.get(position);
  }

  @Override
  public long getItemId(int position) {
    return datas.get(position).hashCode();
  }
}
