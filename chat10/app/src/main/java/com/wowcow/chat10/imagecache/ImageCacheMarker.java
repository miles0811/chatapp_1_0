package com.wowcow.chat10.imagecache;

import android.graphics.Bitmap;

import com.wowcow.chat10.imagecache.ImageWorker.ImageCacheObject;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;

public class ImageCacheMarker extends ImageCacheObject<Marker>{
  public ImageCacheMarker(Marker marker) {
    super(marker);
  }
  
  @Override
  public void setImageBitmap(Bitmap bitmap) {
    final Marker marker = this.getReference();
    
    if(marker != null && bitmap != null){
      BitmapDescriptor bd = BitmapDescriptorFactory.fromBitmap(bitmap);
      marker.setIcon(bd);
    }
  }
}