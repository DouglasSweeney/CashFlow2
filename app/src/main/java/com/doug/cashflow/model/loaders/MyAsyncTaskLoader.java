package com.doug.cashflow.model.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by Doug on 5/16/2017.
 *
 * www.concretepage.com (good example)
 *      Select "Android" button &
 *      Scroll downwards & click on "Android ASyncTaskLoader Example with ListView & Base adapter"
 *
 */

public abstract class MyAsyncTaskLoader<T> extends AsyncTaskLoader<T> {
    private static final AtomicInteger sCurrentUniqueId = new AtomicInteger(0);
    private T mData;
    public boolean hasResult = false;

    public static int getNewUniqueLoaderId() {
        return sCurrentUniqueId.getAndIncrement();
    }

    public MyAsyncTaskLoader(final Context context) {
        super(context);
        onContentChanged();
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (hasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final T data) {
        mData = data;
        hasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (hasResult) {
            onReleaseResources(mData);
            mData = null;
            hasResult = false;
        }
    }

    protected void onReleaseResources(T data) {
        //nothing to do.
    }

    public T getResult() {
        return mData;
    }
}