package com.rhino.pgv.utils;

import android.content.Context;
import android.content.SharedPreferences;


/**
 * <p>The utils of limiting for trying.</p>
 *
 * @author LuoLin
 * @since Create on 2018/5/11.
 */
public class LimitTryCountUtils {

    private static final String FILE_NAME = "limit_try_count";
    private static final String KEY_TRY_COUNT = "key_try_count";
    private static final String KEY_LOCK_TIMESTAMP = "key_lock_timestamp";
    private static final String KEY_NEXT_TRY_TIMESTAMP = "key_next_try_timestamp";
    private static final int DEFAULT_MAX_TRY_COUNT = 5;
    private static final long DEFAULT_LIMIT_DURATION = 5 * 60 * 1000 + 1000;
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

    private String buildKey(String keyPrefix, String key) {
        return keyPrefix + "_" + key;
    }

    public void addTryCount(String key) {
        int tryCount = mSharedPreferences.getInt(buildKey(KEY_TRY_COUNT, key), 0);
        tryCount++;
        if (tryCount >= mMaxTryCount) {
            long curTimestamp = System.currentTimeMillis();
            long nextTryTimestamp = curTimestamp + mLimitDuration;
            mSharedPreferences.edit().putLong(buildKey(KEY_LOCK_TIMESTAMP, key), curTimestamp).apply();
            mSharedPreferences.edit().putLong(buildKey(KEY_NEXT_TRY_TIMESTAMP, key), nextTryTimestamp).apply();
        }
        mSharedPreferences.edit().putInt(buildKey(KEY_TRY_COUNT, key), tryCount).apply();
    }

    public long limitDuration(String key) {
        long nextTryTimestamp = mSharedPreferences.getLong(buildKey(KEY_NEXT_TRY_TIMESTAMP, key), 0);
        long limitTime = nextTryTimestamp - System.currentTimeMillis();
        if (0 < limitTime) {
            mSharedPreferences.edit().putInt(buildKey(KEY_TRY_COUNT, key), 0).apply();
        }
        return limitTime;
    }

    public void reset(String key) {
        mSharedPreferences.edit().putInt(buildKey(KEY_TRY_COUNT, key), 0).apply();
        mSharedPreferences.edit().putLong(buildKey(KEY_LOCK_TIMESTAMP, key), 0).apply();
        mSharedPreferences.edit().putLong(buildKey(KEY_NEXT_TRY_TIMESTAMP, key), 0).apply();
    }

    public long getLockTimestamp(String key) {
        return mSharedPreferences.getLong(buildKey(KEY_LOCK_TIMESTAMP, key), 0);
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
