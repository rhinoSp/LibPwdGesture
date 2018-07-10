package com.rhino.pgv.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * <p>限制执行次数</p>
 *
 * @author LuoLin
 * @since Create on 2018/5/11.
 */
public class LimitTryCountUtils {

    private static final String FILE_NAME = "limit_try_count";
    private static final String KEY_TRY_COUNT = "try.count";
    private static final String KEY_LOCK_TIMESTAMP = "lock.timestamp";
    private static final String KEY_NEXT_TRY_TIMESTAMP = "next.try.timestamp";
    private static final int DEFAULT_MAX_TRY_COUNT = 5;
    private static final long DEFAULT_LIMIT_DURATION = 5 * 60 * 1000;
    private long mLimitDuration = DEFAULT_LIMIT_DURATION;
    private int mMaxTryCount = DEFAULT_MAX_TRY_COUNT;
    private SharedPreferences mSharedPreferences;

    private static LimitTryCountUtils instance;
    public static LimitTryCountUtils getInstance(Context context) {
        if (instance == null) {
            instance = new LimitTryCountUtils(context);
        }
        return instance;
    }

    private LimitTryCountUtils(Context context) {
        this.mSharedPreferences = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
    }

    public void addTryCount(String key) {
        int tryCount = mSharedPreferences.getInt(KEY_TRY_COUNT + "_" + key, 0);
        tryCount++;
        if (tryCount >= mMaxTryCount) {
            long curTimestamp = System.currentTimeMillis();
            long nextTryTimestamp = curTimestamp + mLimitDuration;
            mSharedPreferences.edit().putLong(KEY_LOCK_TIMESTAMP + "_" + key, curTimestamp).apply();
            mSharedPreferences.edit().putLong(KEY_NEXT_TRY_TIMESTAMP + "_" + key, nextTryTimestamp).apply();
        }
        mSharedPreferences.edit().putInt(KEY_TRY_COUNT + "_" + key, tryCount).apply();
    }

    public long limitDuration(String key) {
        long nextTryTimestamp = mSharedPreferences.getLong(KEY_NEXT_TRY_TIMESTAMP + "_" + key, 0);
        long limitTime = nextTryTimestamp - System.currentTimeMillis();
        if (0 < limitTime) {
            mSharedPreferences.edit().putInt(KEY_TRY_COUNT + "_" + key, 0).apply();
        }
        return limitTime;
    }

    public long getLockTimestamp(String key) {
        return mSharedPreferences.getLong(KEY_LOCK_TIMESTAMP + "_" + key, 0);
    }

    public long getLimitDuration() {
        return mLimitDuration;
    }

    public void setLimitDuration(long duration) {
        this.mLimitDuration = duration;
    }

    public int getMaxTryCount() {
        return mMaxTryCount;
    }

    public void setMaxTryCount(int count) {
        this.mMaxTryCount = count;
    }


}
