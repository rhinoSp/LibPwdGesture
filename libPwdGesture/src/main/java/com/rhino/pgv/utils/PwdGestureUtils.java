package com.rhino.pgv.utils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.rhino.pgv.activity.PwdGestureCreateActivity;
import com.rhino.pgv.activity.PwdGestureInputActivity;
import com.rhino.pgv.view.PwdGestureView;

/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureUtils {

    public static Build create() {
        return new Build(true);
    }

    public static Build input() {
        return new Build(false);
    }

    public static void initPwdGestureView(@NonNull PwdGestureView pwdGestureView, @NonNull Bundle extra) {
        pwdGestureView.setRowCount(extra.getInt(Build.KEY_ROW_COUNT));
        pwdGestureView.setColumnCount(extra.getInt(Build.KEY_COLUMN_COUNT));

        pwdGestureView.setCircleColor(extra.getInt(Build.KEY_CIRCLE_COLOR));
        pwdGestureView.setCircleLineWidth(extra.getInt(Build.KEY_CIRCLE_LINE_WIDTH));
        pwdGestureView.setCircleRadius(extra.getInt(Build.KEY_CIRCLE_RADIUS));

        pwdGestureView.setCircleSelectColor(extra.getInt(Build.KEY_CIRCLE_SELECT_COLOR));
        pwdGestureView.setCircleSelectRadius(extra.getInt(Build.KEY_CIRCLE_SELECT_RADIUS));

        pwdGestureView.setLineColor(extra.getInt(Build.KEY_LINE_COLOR));
        pwdGestureView.setLineWidth(extra.getInt(Build.KEY_LINE_WIDTH));
        pwdGestureView.setShowLine(extra.getBoolean(Build.KEY_SHOW_LINE, true));

        pwdGestureView.setAutoMatch(extra.getBoolean(Build.KEY_AUTO_MATCH));
        pwdGestureView.setMinPointCount(extra.getInt(Build.KEY_MIN_POINT_COUNT));

        pwdGestureView.setRightPassword(extra.getIntArray(Build.KEY_RIGHT_PASSWORD));
    }

    public static class Build {

        public static final String KEY_ROW_COUNT = "key_row_count";
        public static final String KEY_COLUMN_COUNT = "key_column_count";
        public static final String KEY_CIRCLE_COLOR = "key_circle_color";
        public static final String KEY_CIRCLE_LINE_WIDTH = "key_circle_line_width";
        public static final String KEY_CIRCLE_RADIUS = "key_circle_radius";
        public static final String KEY_CIRCLE_SELECT_COLOR = "key_circle_select_color";
        public static final String KEY_CIRCLE_SELECT_RADIUS = "key_circle_select_radius";
        public static final String KEY_LINE_COLOR = "key_line_color";
        public static final String KEY_LINE_WIDTH = "key_line_width";
        public static final String KEY_SHOW_LINE = "key_show_line";
        public static final String KEY_AUTO_MATCH = "key_auto_match";
        public static final String KEY_MIN_POINT_COUNT = "key_min_point_count";
        public static final String KEY_RIGHT_PASSWORD = "key_right_password";

        private Intent intent;
        private int requestCode;
        private boolean isCreate;

        public Build(boolean isCreate) {
            this.intent = new Intent();
            this.isCreate = isCreate;
        }

        public Build setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        public Build setRowCount(int rowCount) {
            intent.putExtra(KEY_ROW_COUNT, rowCount);
            return this;
        }

        public Build setColumnCount(int rowCount) {
            intent.putExtra(KEY_COLUMN_COUNT, rowCount);
            return this;
        }

        public Build setCircleColor(@ColorInt int color) {
            intent.putExtra(KEY_CIRCLE_COLOR, color);
            return this;
        }

        public Build setCircleLineWidth(int width) {
            intent.putExtra(KEY_CIRCLE_LINE_WIDTH, width);
            return this;
        }

        public Build setCircleRadius(int radius) {
            intent.putExtra(KEY_CIRCLE_RADIUS, radius);
            return this;
        }

        public Build setCircleSelectColor(@ColorInt int color) {
            intent.putExtra(KEY_CIRCLE_SELECT_COLOR, color);
            return this;
        }

        public Build setCircleSelectRadius(int radius) {
            intent.putExtra(KEY_CIRCLE_SELECT_RADIUS, radius);
            return this;
        }

        public Build setLineColor(@ColorInt int color) {
            intent.putExtra(KEY_LINE_COLOR, color);
            return this;
        }

        public Build setLineWidth(int width) {
            intent.putExtra(KEY_LINE_WIDTH, width);
            return this;
        }

        public Build setShowLine(boolean showLine) {
            intent.putExtra(KEY_SHOW_LINE, showLine);
            return this;
        }

        public Build setAutoMatch(boolean autoMatch) {
            intent.putExtra(KEY_AUTO_MATCH, autoMatch);
            return this;
        }

        public Build setMinPointCount(int count) {
            intent.putExtra(KEY_MIN_POINT_COUNT, count);
            return this;
        }

        public Build setRightPassword(int[] rightPwd) {
            intent.putExtra(KEY_RIGHT_PASSWORD, rightPwd);
            return this;
        }

        public void show(@NonNull Activity activity) {
            if (isCreate) {
                show(activity, PwdGestureCreateActivity.class);
            } else {
                show(activity, PwdGestureInputActivity.class);
            }
        }

        public void show(@NonNull Activity activity, @NonNull Class<?> cls) {
            intent.setClass(activity, cls);
            activity.startActivityForResult(intent, requestCode);
        }

    }

}
