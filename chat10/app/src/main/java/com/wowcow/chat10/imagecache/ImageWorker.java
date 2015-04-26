/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wowcow.chat10.imagecache;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.SparseArray;

import com.wowcow.chat10.Utils.Logger;
import com.wowcow.chat10.Utils.VersionUtil;

import java.lang.ref.WeakReference;
import java.util.Hashtable;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


/**
 * This class wraps up completing some arbitrary long running work when loading
 * a bitmap to an ImageView. It handles things like using a memory and disk
 * cache, running the work in a background thread and setting a placeholder
 * image.
 */
public abstract class ImageWorker {
  private static final Logger logger = Logger.getInstance(ImageWorker.class);

  protected ImageCache mImageCache;
  protected ImageCache.ImageCacheParams mImageCacheParams;
  protected Bitmap mLoadingBitmap;
  private boolean mExitTasksEarly = false;
  protected boolean mPauseWork = false;
  private final Object mPauseWorkLock = new Object();
  private final Hashtable<Integer, Bitmap> loadingBitmaps = new Hashtable<Integer, Bitmap>(
      2);

  protected Resources mResources;

  private static final int MESSAGE_CLEAR = 0;
  private static final int MESSAGE_INIT_DISK_CACHE = 1;
  private static final int MESSAGE_FLUSH = 2;
  private static final int MESSAGE_CLOSE = 3;

  private static final ThreadFactory sThreadFactory = new ThreadFactory() {
    private final AtomicInteger mCount = new AtomicInteger(1);

    public Thread newThread(Runnable r) {
      return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
    }
  };

  // Dual thread executor for main AsyncTask
  public static final Executor DUAL_THREAD_EXECUTOR = Executors
      .newFixedThreadPool(5, sThreadFactory);

  protected ImageWorker(Context context) {
    mResources = context.getResources();
  }

  /**
   * Load an image specified by the data parameter into an ImageView (override
   * {@link ImageWorker#processBitmap(Object)} to define the processing logic).
   * A memory and disk cache will be used if an {@link ImageCache} has been set
   * using {@link ImageWorker#addImageCache}. If the image is found in the
   * memory cache, it is set immediately, otherwise an {@link android.os.AsyncTask} will be
   * created to asynchronously load the bitmap.
   *
   * @param data
   *          The URL of the image to download.
   * @param imageView
   *          The ImageView to bind the downloaded image to.
   */
  protected void loadImage(Object data, ImageCacheObject<?> imageCacheObject) {
    loadImage(
        data,
        imageCacheObject,
        mLoadingBitmap);
  }

  /**
   * Load an image specified by the data parameter into an ImageView (override
   * {@link ImageWorker#processBitmap(Object)} to define the processing logic).
   * A memory and disk cache will be used if an {@link ImageCache} has been set
   * using {@link ImageWorker#addImageCache}. If the image is found in the
   * memory cache, it is set immediately, otherwise an {@link android.os.AsyncTask} will be
   * created to asynchronously load the bitmap.
   *
   * @param data
   *          The URL of the image to download.
   * @param imageView
   *          The ImageView to bind the downloaded image to.
   * @param resId
   *          Resource of placeholder bitmap while the image loads.
   */
  protected void loadImage(Object data, ImageCacheObject<?> imageCacheObject, int resId) {
    if (!loadingBitmaps.containsKey(resId)) {
      // Store loading bitmap in a hash table to prevent continual decoding
      loadingBitmaps
          .put(resId, BitmapFactory.decodeResource(mResources, resId));
    }
    loadImage(
        data,
        imageCacheObject,
        loadingBitmaps.get(resId));
  }

  /**
   * Load an image specified by the data parameter into an ImageView (override
   * {@link ImageWorker#processBitmap(Object)} to define the processing logic).
   * A memory and disk cache will be used if an {@link ImageCache} has been set
   * using {@link ImageWorker#addImageCache}. If the image is found in the
   * memory cache, it is set immediately, otherwise an {@link android.os.AsyncTask} will be
   * created to asynchronously load the bitmap.
   *
   * @param data
   *          The URL of the image to download.
   * @param imageView
   *          The ImageView to bind the downloaded image to.
   */
  @SuppressLint("NewApi")
  protected void loadImage(Object data, ImageCacheObject<?> imageCacheObject, Bitmap loadingBitmap) {
    if (data == null) {
      return;
    }

    logger.d("loadImage --------------------------- "+ String.valueOf(data)+" "+imageCacheObject.objectHash);

    Bitmap bitmap = null;

    if (mImageCache != null) {
      bitmap = mImageCache.getBitmapFromMemCache(String.valueOf(data));
    }

    if (bitmap != null) {
      // Bitmap found in memory cache
      imageCacheObject.setImageBitmap(bitmap);
    } else if (cancelPotentialWork(data, imageCacheObject)) {
      final BitmapWorkerTask task = new BitmapWorkerTask(imageCacheObject);
      imageCacheObject.setBitmapWorkerTask(task);
      imageCacheObject.setImageBitmap(loadingBitmap);

      if (VersionUtil.hasHoneycomb()) {
        // On HC+ we execute on a dual thread executor. There really isn't much
        // extra
        // benefit to having a really large pool of threads. Having more than
        // one will
        // likely benefit network bottlenecks though.
        task.executeOnExecutor(DUAL_THREAD_EXECUTOR, data);
      } else {
        // Otherwise pre-HC the default is a thread pool executor (not ideal,
        // serial
        // execution or a smaller number of threads would be better).
        task.execute(data);
      }
    }
  }

  /**
   * Set placeholder bitmap that shows when the the background thread is
   * running.
   *
   * @param bitmap
   */
  public void setLoadingImage(Bitmap bitmap) {
    mLoadingBitmap = bitmap;
  }

  /**
   * Set placeholder bitmap that shows when the the background thread is
   * running.
   *
   * @param resId
   */
  public void setLoadingImage(int resId) {
    mLoadingBitmap = BitmapFactory.decodeResource(mResources, resId);
  }

  /**
   * Adds an {@link ImageCache} to this worker in the background (to prevent
   * disk access on UI thread).
   *
   * @param fragmentManager
   *          The FragmentManager to initialize and add the cache
   * @param cacheParams
   *          The cache parameters to use
   */
  public void addImageCache(FragmentManager fragmentManager,
      ImageCache.ImageCacheParams cacheParams) {
    mImageCacheParams = cacheParams;
    setImageCache(ImageCache.findOrCreateCache(fragmentManager,
        mImageCacheParams));
    new CacheAsyncTask().execute(MESSAGE_INIT_DISK_CACHE);
  }

  /**
   * Adds an {@link ImageCache} to this worker in the background (to prevent
   * disk access on UI thread) using default cache parameters.
   *
   * @param fragmentActivity
   *          The FragmentActivity to initialize and add the cache
   */
  public void addImageCache(FragmentActivity fragmentActivity) {
    addImageCache(fragmentActivity.getSupportFragmentManager(),
        new ImageCache.ImageCacheParams(fragmentActivity));
  }

  /**
   * Sets the {@link ImageCache} object to use with this ImageWorker. Usually
   * you will not need to call this directly, instead use
   * {@link ImageWorker#addImageCache} which will create and add the
   * {@link ImageCache} object in a background thread (to ensure no disk access
   * on the main/UI thread).
   *
   * @param imageCache
   */
  public void setImageCache(ImageCache imageCache) {
    mImageCache = imageCache;
  }

  /**
   * Setting this to true will signal the working tasks to exit processing at
   * the next chance. This helps finish up pending work when the activity is no
   * longer in the foreground and completing the tasks is no longer useful.
   *
   * @param exitTasksEarly
   */
  public void setExitTasksEarly(boolean exitTasksEarly) {
    mExitTasksEarly = exitTasksEarly;
    setPauseWork(false);
  }

  /**
   * Subclasses should override this to define any processing or work that must
   * happen to produce the final bitmap. This will be executed in a background
   * thread and be long running. For example, you could resize a large bitmap
   * here, or pull down an image from the network.
   *
   * @param data
   *          The data to identify which image to process, as provided by
   *          {@link ImageWorker#loadImage(Object, android.widget.ImageView)}
   * @return The processed bitmap
   */
  protected abstract Bitmap processBitmap(Object data);

  /**
   * Cancels any pending work attached to the provided ImageView.
   * 
   * @param imageView
   */
  public static void cancelWork(ImageCacheObject<?> imageCacheObject) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageCacheObject);
    if (bitmapWorkerTask != null) {
      bitmapWorkerTask.cancel(true);
      logger.d("cancelWork - cancelled work for " + bitmapWorkerTask.data);
    }
  }

  /**
   * Returns true if the current work has been canceled or if there was no work
   * in progress on this image view. Returns false if the work in progress deals
   * with the same data. The work is not stopped in that case.
   */
  public static boolean cancelPotentialWork(Object data, ImageCacheObject<?> imageCacheObject) {
    final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageCacheObject);

    if (bitmapWorkerTask != null) {
      final Object bitmapData = bitmapWorkerTask.data;
      if (bitmapData == null || bitmapData.equals(data)==false) {
        bitmapWorkerTask.cancel(true);
        logger.d("cancelPotentialWork - cancelled work for " + data);
      } else {
        // The same work is already in progress.
        return false;
      }
    }
    
    return true;
  }
  
  /**
   * @param imageCacheObject
   *          Any imageObject
   * @return Retrieve the currently active work task (if any) associated with
   *         this imageView. null if there is no such task.
   */
  private static BitmapWorkerTask getBitmapWorkerTask(ImageCacheObject<?> imageCacheObject) {
    if (imageCacheObject != null){
      if(imageCacheObject.getReference() != null) {
        return imageCacheObject.getBitmapWorkerTask();
      }
    }
    return null;
  }

  /**
   * The actual AsyncTask that will asynchronously process the image.
   */
  private class BitmapWorkerTask extends AsyncTask<Object, Void, Bitmap> {
    private Object data;
    private final ImageCacheObject<?> imageCacheObject;

    public BitmapWorkerTask(ImageCacheObject<?> imageCacheObject) {
      this.imageCacheObject = imageCacheObject;
    }

    /**
     * Background processing.
     */
    @Override
    protected Bitmap doInBackground(Object... params) {
      logger.d("doInBackground - starting work");

      data = params[0];
      final String dataString = String.valueOf(data);
      Bitmap bitmap = null;

      // Wait here if work is paused and the task is not cancelled
      synchronized (mPauseWorkLock) {
        while (mPauseWork && !isCancelled()) {
          try {
            mPauseWorkLock.wait();
          } catch (InterruptedException e) {
          }
        }
      }
      
      // If the image cache is available and this task has not been cancelled by
      // another
      // thread and the ImageView that was originally bound to this task is
      // still bound back
      // to this task and our "exit early" flag is not set then try and fetch
      // the bitmap from
      // the cache
      if (mImageCache != null && !isCancelled()
          && getAttachedImageObject() != null && !mExitTasksEarly) {
        bitmap = mImageCache.getBitmapFromDiskCache(dataString);
        logger.d("doInBackground - getBitmapFromDiskCache");
      }

      // If the bitmap was not found in the cache and this task has not been
      // cancelled by
      // another thread and the ImageView that was originally bound to this task
      // is still
      // bound back to this task and our "exit early" flag is not set, then call
      // the main
      // process method (as implemented by a subclass)
      if (bitmap == null && !isCancelled() && getAttachedImageObject() != null
          && !mExitTasksEarly) {
        bitmap = processBitmap(params[0]);
        logger.d("doInBackground - processBitmap");
      }

      // If the bitmap was processed and the image cache is available, then add
      // the processed
      // bitmap to the cache for future use. Note we don't check if the task was
      // cancelled
      // here, if it was, and the thread is still running, we may as well add
      // the processed
      // bitmap to our cache as it might be used again in the future
      if (bitmap != null && mImageCache != null) {
        mImageCache.addBitmapToCache(dataString, bitmap);
      }

      logger.d("doInBackground - finished work");
      return bitmap;
    }

    /**
     * Once the image is processed, associates it to the imageView
     */
    @Override
    protected void onPostExecute(Bitmap bitmap) {
      // if cancel was called on this task or the "exit early" flag is set then
      // we're done
      if (isCancelled() || mExitTasksEarly) {
        bitmap = null;
      }
      
      final ImageCacheObject<?> imageObject = getAttachedImageObject();
      if (bitmap != null && imageObject != null) {
        logger.d("onPostExecute - setting bitmap");
        imageObject.setImageBitmap(bitmap);
        imageObject.removeBitmapWorkerTask();
      }
    }

    @Override
    protected void onCancelled() {
      super.onCancelled();
      this.imageCacheObject.removeBitmapWorkerTask();
      synchronized (mPauseWorkLock) {
        mPauseWorkLock.notifyAll();
      }
    }
    
    /**
     * Returns the imageCacheObject associated with this task as long as the
     * ImageView's task still points to this task as well. Returns null
     * otherwise.
     */
    private ImageCacheObject<?> getAttachedImageObject() {
      final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(this.imageCacheObject);

      if (this == bitmapWorkerTask) {
        return this.imageCacheObject;
      }

      return null;
    }
  }
  
  public static abstract class ImageCacheObject<T> {
    private final WeakReference<T> imageReference;
    private final int objectHash;
    private static final Object lock = new Object();
    private static SparseArray<WeakReference<BitmapWorkerTask>> bitmapWorkerTaskReference;
    
    static{
      bitmapWorkerTaskReference = new SparseArray<WeakReference<BitmapWorkerTask>>();
    }
    
    public ImageCacheObject(T t){
      this.objectHash = t.hashCode();
      this.imageReference = new WeakReference<T>(t);
    }
    
    protected abstract void setImageBitmap(Bitmap bitmap);
    
    protected T getReference(){
      return this.imageReference.get();
    }
    
    private void removeBitmapWorkerTask(){
      synchronized(lock){
        bitmapWorkerTaskReference.remove(objectHash);
      }
    }
    
    private void setBitmapWorkerTask(BitmapWorkerTask bitmapWorkerTask){
      synchronized(lock){
        bitmapWorkerTaskReference.append(
            objectHash,
            new WeakReference<BitmapWorkerTask>(bitmapWorkerTask));
      }
    }
    
    private BitmapWorkerTask getBitmapWorkerTask() {
      synchronized(lock){
        if(bitmapWorkerTaskReference.indexOfKey(objectHash) >= 0){
          return bitmapWorkerTaskReference.get(objectHash).get();
        }
        return null;
      }
    }
  }
  
  /**
   * Pause any ongoing background work. This can be used as a temporary measure
   * to improve performance. For example background work could be paused when a
   * ListView or GridView is being scrolled using a
   * {@link android.widget.AbsListView.OnScrollListener} to keep scrolling
   * smooth.
   * <p>
   * If work is paused, be sure setPauseWork(false) is called again before your
   * fragment or activity is destroyed (for example during
   * {@link android.app.Activity#onPause()}), or there is a risk the background
   * thread will never finish.
   */
  public void setPauseWork(boolean pauseWork) {
    synchronized (mPauseWorkLock) {
      mPauseWork = pauseWork;
      if (!mPauseWork) {
        mPauseWorkLock.notifyAll();
      }
    }
  }

  protected class CacheAsyncTask extends AsyncTask<Object, Void, Void> {

    @Override
    protected Void doInBackground(Object... params) {
      switch ((Integer) params[0]) {
      case MESSAGE_CLEAR:
        clearCacheInternal();
        break;
      case MESSAGE_INIT_DISK_CACHE:
        initDiskCacheInternal();
        break;
      case MESSAGE_FLUSH:
        flushCacheInternal();
        break;
      case MESSAGE_CLOSE:
        closeCacheInternal();
        break;
      }
      return null;
    }
  }

  protected void initDiskCacheInternal() {
    if (mImageCache != null) {
      mImageCache.initDiskCache();
    }
  }

  protected void clearCacheInternal() {
    if (mImageCache != null) {
      mImageCache.clearCache();
    }
  }

  protected void flushCacheInternal() {
    if (mImageCache != null) {
      mImageCache.flush();
    }
  }

  protected void closeCacheInternal() {
    if (mImageCache != null) {
      mImageCache.close();
      mImageCache = null;
    }
  }

  public void clearCache() {
    new CacheAsyncTask().execute(MESSAGE_CLEAR);
  }

  public void flushCache() {
    new CacheAsyncTask().execute(MESSAGE_FLUSH);
  }

  public void closeCache() {
    new CacheAsyncTask().execute(MESSAGE_CLOSE);
  }
}
