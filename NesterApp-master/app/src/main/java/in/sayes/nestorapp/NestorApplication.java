package in.sayes.nestorapp;


import android.app.Application;
import android.os.Handler;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import in.sayes.nestorapp.chat.helper.NativeLoader;
import in.sayes.nestorapp.network.LruBitmapCache;

/**
 * Created by sourav on 22/04/16.
 * Project : NesterApp , Package Name : in.sayes.nesterapp
 * Copyright : Sourav Bhattacharya eMail: sav.accharya@gmail.com
 */
public class NestorApplication extends Application {


    private static final String TAG = NestorApplication.class.getSimpleName();

    private static NestorApplication appInstance;
    private RequestQueue mRequestQueue;

    /**
     * ImageLoader from Volley to load images asynchronously
     */
    private ImageLoader mImageLoader;

    /**
     * LRU cache to cache bitmaps from server
     */
    private LruBitmapCache mLruBitmapCache;
    public static volatile Handler applicationHandler = null;



    public static synchronized NestorApplication getInstance() {

        if (null == appInstance) {
            appInstance = new NestorApplication();
        }
        return appInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        appInstance = this;
//Chat module
        applicationHandler = new Handler(getInstance().getMainLooper());

        NativeLoader.initNativeLibs(NestorApplication.getInstance());

    }


    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public LruBitmapCache getLruBitmapCache() {
        if (mLruBitmapCache == null)
            mLruBitmapCache = new LruBitmapCache();
        return this.mLruBitmapCache;
    }

    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            getLruBitmapCache();
            mImageLoader = new ImageLoader(this.mRequestQueue, mLruBitmapCache);
        }

        return this.mImageLoader;
    }



}
