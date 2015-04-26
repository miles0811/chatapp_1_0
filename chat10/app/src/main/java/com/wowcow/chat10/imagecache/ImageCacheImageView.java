package com.wowcow.chat10.imagecache;

import android.graphics.Bitmap;
import android.widget.ImageView;

import com.wowcow.chat10.imagecache.ImageWorker.ImageCacheObject;

public class ImageCacheImageView extends ImageCacheObject<ImageView>{
  public ImageCacheImageView(ImageView t) {
    super(t);
  }

  @Override
  public void setImageBitmap(Bitmap bitmap) {
    final ImageView imageView = this.getReference();
    
    if(imageView != null){
      imageView.setImageBitmap(bitmap);
    }
  }
}
