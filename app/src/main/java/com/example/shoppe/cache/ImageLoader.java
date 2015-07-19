package com.example.shoppe.cache;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.Display;
import android.widget.ImageView;

import com.example.shoppe.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by nitingup on 7/18/15.
 */
public class ImageLoader {

    MemoryCache mMemoryCache = new MemoryCache();

    private Map<ImageView, String> mImageViews = Collections.synchronizedMap(new WeakHashMap<ImageView, String>());

    ExecutorService mExecutorService;
    Context mContext;

    public ImageLoader(Context context) {
        mExecutorService = Executors.newFixedThreadPool(5);
        mContext = context;
    }

    int mDefaultPic = R.mipmap.ic_tab_photo;
    Display mCurrentDisplay = null;

    public void displayImage(String url, ImageView imageView, boolean h, Display currentDisplay) {

        mImageViews.put(imageView, url);
        this.mCurrentDisplay = currentDisplay;
        Bitmap bitmap = mMemoryCache.get(url);
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            queuePhoto(url, imageView, h);
            imageView.setImageResource(mDefaultPic);
        }
    }

    private void queuePhoto(String url, ImageView imageView, boolean h) {
        PhotoToLoad p = new PhotoToLoad(url, imageView);
        mExecutorService.submit(new PhotosLoader(p, h));
    }

    private Bitmap getBitmap(String url, boolean h) {
        try {
            URL webUrl = new URL(url);
            Bitmap image = BitmapFactory.decodeStream(webUrl.openConnection().getInputStream());
            return image;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //Task for the queue
    private class PhotoToLoad {
        public String url;
        public ImageView imageView;

        public PhotoToLoad(String u, ImageView i) {
            url = u;
            imageView = i;
        }
    }

    class PhotosLoader implements Runnable {
        PhotoToLoad photoToLoad;
        boolean highlighter = false;

        PhotosLoader(PhotoToLoad photoToLoad, boolean h) {
            this.photoToLoad = photoToLoad;
            this.highlighter = h;
        }

        @Override
        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            Bitmap bmp = getBitmap(photoToLoad.url, highlighter);
            mMemoryCache.put(photoToLoad.url, bmp);
            if (imageViewReused(photoToLoad)) {
                return;
            }
            BitmapDisplayer bd = new BitmapDisplayer(bmp, photoToLoad, highlighter);
            Activity a = (Activity) photoToLoad.imageView.getContext();
            a.runOnUiThread(bd);
        }
    }

    boolean imageViewReused(PhotoToLoad photoToLoad) {
        String tag = mImageViews.get(photoToLoad.imageView);
        if (tag == null || !tag.equals(photoToLoad.url))
            return true;
        return false;
    }

    //Used to display bitmap in the UI thread
    class BitmapDisplayer implements Runnable {
        Bitmap bitmap;
        PhotoToLoad photoToLoad;
        boolean highlight = false;

        public BitmapDisplayer(Bitmap b, PhotoToLoad p, boolean h) {
            bitmap = b;
            photoToLoad = p;
            highlight = h;
        }

        public void run() {
            if (imageViewReused(photoToLoad))
                return;
            if (bitmap != null) {
                photoToLoad.imageView.setImageBitmap(bitmap);
            } else
                photoToLoad.imageView.setImageResource(mDefaultPic);
        }
    }

    public void clearCache() {
        mMemoryCache.clear();
    }
}
