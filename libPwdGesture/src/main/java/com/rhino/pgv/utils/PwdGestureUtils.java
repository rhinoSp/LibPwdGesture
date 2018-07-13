package com.rhino.pgv.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.widget.Button;

import com.rhino.pgv.activity.PwdGestureCreateActivity;
import com.rhino.pgv.activity.PwdGestureInputActivity;
import com.rhino.pgv.view.PwdGestureView;

import java.nio.file.FileAlreadyExistsException;

/**
 * <p>The utils of password gesture.</p>
 *
 * @author LuoLin
 * @since Create on 2018/7/6.
 */
public class PwdGestureUtils {

    public static Build createPwd() {
        return new Build(true, Build.REQUEST_CODE_CREATE_PWD);
    }

    public static Build inputPwd() {
        return new Build(false, Build.REQUEST_CODE_INPUT_PWD).setAutoMatch(true);
    }

    public static void initPwdGestureView(@NonNull PwdGestureView pwdGestureView, @NonNull Bundle extra) {
        pwdGestureView.setRowCount(extra.getInt(Build.KEY_ROW_COUNT));
        pwdGestureView.setColumnCount(extra.getInt(Build.KEY_COLUMN_COUNT));

        pwdGestureView.setNormalOvalStrokeWidth(extra.getInt(Build.KEY_NORMAL_OVAL_STROKE_WIDTH, 3));
        pwdGestureView.setNormalOvalStrokeColor(extra.getInt(Build.KEY_NORMAL_OVAL_STROKE_COLOR, 0xFFAAAAAA));
        pwdGestureView.setNormalOvalSolidColor(extra.getInt(Build.KEY_NORMAL_OVAL_SOLID_COLOR, 0));
        pwdGestureView.setNormalOvalRadius(extra.getInt(Build.KEY_NORMAL_OVAL_RADIUS, 90));

        pwdGestureView.setSelectOvalStrokeWidth(extra.getInt(Build.KEY_SELECT_OVAL_STROKE_WIDTH, 0));
        pwdGestureView.setSelectOvalStrokeColor(extra.getInt(Build.KEY_SELECT_OVAL_STROKE_COLOR, 0));
        pwdGestureView.setSelectOvalSolidColor(extra.getInt(Build.KEY_SELECT_OVAL_SOLID_COLOR, 0xFFAAAAAA));
        pwdGestureView.setSelectOvalRadius(extra.getInt(Build.KEY_SELECT_OVAL_RADIUS, 24));

        pwdGestureView.setShowGestureLine(extra.getBoolean(Build.KEY_SHOW_GESTURE_LINE, true));
        pwdGestureView.setGestureLineColor(extra.getInt(Build.KEY_GESTURE_LINE_COLOR, 0xFF1BBC9B));
        pwdGestureView.setGestureLineWidth(extra.getInt(Build.KEY_GESTURE_LINE_WIDTH, 3));

        pwdGestureView.setAutoMatch(extra.getBoolean(Build.KEY_AUTO_MATCH, false));
        pwdGestureView.setAutoResetDelay(extra.getInt(Build.KEY_AUTO_RESET_DELAY, 1000));
        pwdGestureView.setMatchFailedColor(extra.getInt(Build.KEY_MATCH_FAILED_COLOR, 0xFFFF0000));

        pwdGestureView.setMinSelectCount(extra.getInt(Build.KEY_MIN_SELECT_COUNT, 4));

        pwdGestureView.setRightPassword(extra.getIntArray(Build.KEY_RIGHT_PASSWORD));
    }

    public static class Build {

        public static final int REQUEST_CODE_CREATE_PWD = 1;
        public static final int REQUEST_CODE_INPUT_PWD = 2;

        public static final String KEY_ROW_COUNT = "key_row_count";
        public static final String KEY_COLUMN_COUNT = "key_column_count";

        public static final String KEY_NORMAL_OVAL_STROKE_WIDTH = "key_normal_oval_stroke_width";
        public static final String KEY_NORMAL_OVAL_STROKE_COLOR = "key_normal_oval_stroke_color";
        public static final String KEY_NORMAL_OVAL_SOLID_COLOR = "key_normal_oval_solid_color";
        public static final String KEY_NORMAL_OVAL_RADIUS = "key_normal_oval_radius";

        public static final String KEY_SELECT_OVAL_STROKE_WIDTH = "key_select_oval_stroke_width";
        public static final String KEY_SELECT_OVAL_STROKE_COLOR = "key_select_oval_stroke_color";
        public static final String KEY_SELECT_OVAL_SOLID_COLOR = "key_select_oval_solid_color";
        public static final String KEY_SELECT_OVAL_RADIUS = "key_select_oval_radius";

        public static final String KEY_SHOW_GESTURE_LINE = "key_show_gesture_line";
        public static final String KEY_GESTURE_LINE_COLOR = "key_gesture_line_color";
        public static final String KEY_GESTURE_LINE_WIDTH = "key_gesture_line_width";

        public static final String KEY_AUTO_MATCH = "key_auto_match";
        public static final String KEY_AUTO_RESET_DELAY = "key_auto_reset_delay";
        public static final String KEY_MATCH_FAILED_COLOR = "key_match_failed_color";

        public static final String KEY_MIN_SELECT_COUNT = "key_min_select_count";
        public static final String KEY_RIGHT_PASSWORD = "key_right_password";

        public static final String KEY_ACTION_BAR_BACKGROUND_COLOR = "key_action_bar_background_color";
        public static final String KEY_STATUS_BAR_BACKGROUND_COLOR = "key_status_bar_background_color";
        public static final String KEY_TITLE_BACKGROUND_COLOR = "key_title_background_color";
        public static final String KEY_TITLE_TEXT = "key_title_text";
        public static final String KEY_TITLE_BACK_BUTTON_VISIBLE = "key_title_back_button_visible";

        private Intent intent;
        private int requestCode;
        private boolean isCreate;

        public Build(boolean isCreate) {
            this.intent = new Intent();
            this.isCreate = isCreate;
        }

        public Build(boolean isCreate, int requestCode) {
            this.intent = new Intent();
            this.isCreate = isCreate;
            this.requestCode = requestCode;
        }

        /**
         * Set requestCode.
         *
         * @param requestCode requestCode.
         * @return Build
         */
        public Build setRequestCode(int requestCode) {
            this.requestCode = requestCode;
            return this;
        }

        /**
         * Set the count of row.
         *
         * @param rowCount the count of row.
         * @return Build
         */
        public Build setRowCount(int rowCount) {
            intent.putExtra(KEY_ROW_COUNT, rowCount);
            return this;
        }

        /**
         * Set the count of column.
         *
         * @param columnCount the count of column.
         * @return Build
         */
        public Build setColumnCount(int columnCount) {
            intent.putExtra(KEY_COLUMN_COUNT, columnCount);
            return this;
        }

        /**
         * Set the stroke width of normal oval.
         *
         * @param width the stroke width of normal oval.
         * @return Build
         */
        public Build setNormalOvalStrokeWidth(int width) {
            intent.putExtra(KEY_NORMAL_OVAL_STROKE_WIDTH, width);
            return this;
        }

        /**
         * Set the stroke color of normal oval.
         *
         * @param color the stroke color of normal oval.
         * @return Build
         */
        public Build setNormalOvalStrokeColor(@ColorInt int color) {
            intent.putExtra(KEY_NORMAL_OVAL_STROKE_COLOR, color);
            return this;
        }

        /**
         * Set the solid color of normal oval.
         *
         * @param color the solid color of normal oval.
         * @return Build
         */
        public Build setNormalOvalSolidColor(@ColorInt int color) {
            intent.putExtra(KEY_NORMAL_OVAL_SOLID_COLOR, color);
            return this;
        }

        /**
         * Set the radius of of normal oval.
         *
         * @param radius the radius of of normal oval.
         * @return Build
         */
        public Build setNormalOvalRadius(int radius) {
            intent.putExtra(KEY_NORMAL_OVAL_RADIUS, radius);
            return this;
        }

        /**
         * Set the stroke width of select oval.
         *
         * @param width the stroke width of select oval.
         * @return Build
         */
        public Build setSelectOvalStrokeWidth(int width) {
            intent.putExtra(KEY_SELECT_OVAL_STROKE_WIDTH, width);
            return this;
        }

        /**
         * Set the stroke color of select oval.
         *
         * @param color the stroke color of select oval.
         * @return Build
         */
        public Build setSelectOvalStrokeColor(@ColorInt int color) {
            intent.putExtra(KEY_SELECT_OVAL_STROKE_COLOR, color);
            return this;
        }

        /**
         * Set the solid color of select oval.
         *
         * @param color the solid color of select oval.
         * @return Build
         */
        public Build setSelectOvalSolidColor(@ColorInt int color) {
            intent.putExtra(KEY_SELECT_OVAL_SOLID_COLOR, color);
            return this;
        }

        /**
         * Set the radius of of select oval.
         *
         * @param radius the radius of of select oval.
         * @return Build
         */
        public Build setSelectOvalRadius(int radius) {
            intent.putExtra(KEY_SELECT_OVAL_RADIUS, radius);
            return this;
        }

        /**
         * Set show gesture line.
         *
         * @param show True show.
         * @return Build
         */
        public Build setShowGestureLine(boolean show) {
            intent.putExtra(KEY_SHOW_GESTURE_LINE, show);
            return this;
        }

        /**
         * Set the width of gesture line.
         *
         * @param width the color of gesture line.
         * @return Build
         */
        public Build setGestureLineWidth(int width) {
            intent.putExtra(KEY_GESTURE_LINE_WIDTH, width);
            return this;
        }

        /**
         * Set the color of gesture line.
         *
         * @param color the color of gesture line.
         * @return Build
         */
        public Build setGestureLineColor(@ColorInt int color) {
            intent.putExtra(KEY_GESTURE_LINE_COLOR, color);
            return this;
        }

        /**
         * Set whether auto match.
         *
         * @param autoMatch True show.
         * @return Build
         */
        public Build setAutoMatch(boolean autoMatch) {
            intent.putExtra(KEY_AUTO_MATCH, autoMatch);
            return this;
        }

        /**
         * Set delay (in milliseconds) of reset when gesture not match.
         *
         * @param delay The delay (in milliseconds) of reset when gesture not match.
         * @return Build
         */
        public Build setAutoResetDelay(int delay) {
            intent.putExtra(KEY_AUTO_RESET_DELAY, delay);
            return this;
        }

        /**
         * Set the color when match failed.
         *
         * @param color the color when match failed.
         * @return Build
         */
        public Build setMatchFailedColor(@ColorInt int color) {
            intent.putExtra(KEY_MATCH_FAILED_COLOR, color);
            return this;
        }

        /**
         * Set the min select count.
         *
         * @param count the min select count.
         * @return Build
         */
        public Build setMinSelectCount(int count) {
            intent.putExtra(KEY_MIN_SELECT_COUNT, count);
            return this;
        }

        /**
         * Set right password.
         *
         * @param rightPwd the right password.
         * @return Build
         */
        public Build setRightPassword(int[] rightPwd) {
            intent.putExtra(KEY_RIGHT_PASSWORD, rightPwd);
            return this;
        }

        /**
         * Set the background color of actionbar.
         *
         * @param color the background color of actionbar.
         * @return Build
         */
        public Build setActionBarBackgroundColor(@ColorInt int color) {
            intent.putExtra(KEY_ACTION_BAR_BACKGROUND_COLOR, color);
            return this;
        }

        /**
         * Set the background color of status bar.
         *
         * @param color the background color of status bar.
         * @return Build
         */
        public Build setStatusBarBackgroundColor(@ColorInt int color) {
            intent.putExtra(KEY_STATUS_BAR_BACKGROUND_COLOR, color);
            return this;
        }

        /**
         * Set the background color of title.
         *
         * @param color the background color of title.
         * @return Build
         */
        public Build setTitleBackgroundColor(@ColorInt int color) {
            intent.putExtra(KEY_TITLE_BACKGROUND_COLOR, color);
            return this;
        }

        /**
         * Set the text of title.
         *
         * @param title the text of title.
         * @return Build
         */
        public Build setTitle(String title) {
            intent.putExtra(KEY_TITLE_TEXT, title);
            return this;
        }

        /**
         * Set the visible of title back button.
         *
         * @param visible the visible of title back button.
         * @return Build
         */
        public Build setTitleBackButtonVisible(boolean visible) {
            intent.putExtra(KEY_TITLE_BACK_BUTTON_VISIBLE, visible);
            return this;
        }

        /**
         * Show the gesture activity，default dest activity is {@link PwdGestureCreateActivity} or defalut {@link PwdGestureInputActivity}
         *
         * @param activity Activity.
         */
        public void show(@NonNull Activity activity) {
            if (isCreate) {
                show(activity, PwdGestureCreateActivity.class);
            } else {
                show(activity, PwdGestureInputActivity.class);
            }
        }

        /**
         * Show the gesture activity.
         *
         * @param activity Activity.
         * @param cls      the dest activity.
         */
        public void show(@NonNull Activity activity, @NonNull Class<?> cls) {
            intent.setClass(activity, cls);
            activity.startActivityForResult(intent, requestCode);
        }
    }

}
