package com.rhino.pgv.utils;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import com.rhino.pgv.activity.PwdGestureActivity;
import com.rhino.pgv.view.PwdGestureView;

/**
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureUtils {

    public static Build build() {
        return new Build();
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
        public static final String KEY_RIGHT_PASSWORD = "key_right_password";

        private Intent intent;
        private int requestCode;

        public Build() {
            this.intent = new Intent();
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


        public Build setRightPassword(int[] rightPwd) {
            intent.putExtra(KEY_RIGHT_PASSWORD, rightPwd);
            return this;
        }

        public void show(@NonNull Activity activity) {
            show(activity, PwdGestureActivity.class);
        }

        public void show(@NonNull Activity activity, @NonNull Class<?> cls) {
            intent.setClass(activity, cls);
            activity.startActivityForResult(intent, requestCode);
        }

    }

}
