package com.rhino.pgv.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


import com.rhino.pgv.R;

import java.util.ArrayList;
import java.util.List;


/**
 * <p>The custom gesture password view.</p>
 * Follow this example:
 *
 * <pre class="prettyprint">
 * &lt;?xml version="1.0" encoding="utf-8"?&gt</br>
 * &lt;RelativeLayout
 *      xmlns:android="http://schemas.android.com/apk/res/android"
 *      xmlns:app="http://schemas.android.com/apk/res-auto"
 *      android:layout_width="match_parent"
 *      android:layout_height="match_parent"&gt
 *
 *      &lt;com.rhino.pwdgestureview.view.PwdGestureView
 *          android:id="@+id/PwdGestureView"
 *          android:layout_width="300dp"
 *          android:layout_height="300dp"
 *          android:background="#1A000000"
 *          app:pgv_column_count="3"
 *          app:pgv_row_count="3"/&gt
 *
 *&lt;/RelativeLayout&gt
 *</pre>
 * @author LuoLin
 * @since Created on 2017/9/29.
 **/
public class PwdGestureView extends View {

    private static final int DEFAULT_COLUMN_COUNT = 3;
    private static final int DEFAULT_ROW_COUNT = 3;

    private static final int DEFAULT_NORMAL_OVAL_STROKE_WIDTH = 3;
    private static final int DEFAULT_NORMAL_OVAL_STROKE_COLOR = 0x1A000000;
    private static final int DEFAULT_NORMAL_OVAL_STROKE_SELECT_COLOR = 0xFF1BBC9B;
    private static final int DEFAULT_NORMAL_OVAL_STROKE_ERROR_COLOR = 0xFFFF0000;
    private static final int DEFAULT_NORMAL_OVAL_SOLID_COLOR = 0x00000000;
    private static final int DEFAULT_NORMAL_OVAL_SOLID_SELECT_COLOR = 0x00000000;
    private static final int DEFAULT_NORMAL_OVAL_SOLID_ERROR_COLOR = 0x00000000;
    private static final int DEFAULT_NORMAL_OVAL_RADIUS = 90;
    private static final int DEFAULT_SELECT_OVAL_STROKE_WIDTH = 3;
    private static final int DEFAULT_SELECT_OVAL_STROKE_COLOR = 0xFF1BBC9B;
    private static final int DEFAULT_SELECT_OVAL_STROKE_ERROR_COLOR = 0xFFFF0000;
    private static final int DEFAULT_SELECT_OVAL_SOLID_COLOR = 0xFF1BBC9B;
    private static final int DEFAULT_SELECT_OVAL_SOLID_ERROR_COLOR = 0xFFFF0000;
    private static final int DEFAULT_SELECT_OVAL_RADIUS = 24;
    private static final boolean DEFAULT_SHOW_GESTURE_LINE = true;
    private static final int DEFAULT_GESTURE_LINE_WIDTH = 3;
    private static final int DEFAULT_GESTURE_LINE_COLOR = 0xFF1BBC9B;
    private static final int DEFAULT_GESTURE_LINE_ERROR_COLOR = 0xFFFF0000;
    private static final boolean DEFAULT_AUTO_MATCH = false;
    private static final int DEFAULT_AUTO_RESET_DELAY = 1000;
    private static final int DEFAULT_MIN_SELECT_COUNT = 4;
    private int mColumnCount;
    private int mRowCount;
    private int mNormalOvalStrokeWidth;
    private int mNormalOvalStrokeColor;
    private int mNormalOvalStrokeSelectColor;
    private int mNormalOvalStrokeErrorColor;
    private int mNormalOvalSolidColor;
    private int mNormalOvalSolidSelectColor;
    private int mNormalOvalSolidErrorColor;
    private float mNormalOvalRadius;
    private int mSelectOvalStrokeWidth;
    private int mSelectOvalStrokeColor;
    private int mSelectOvalStrokeErrorColor;
    private int mSelectOvalSolidColor;
    private int mSelectOvalSolidErrorColor;
    private float mSelectOvalRadius;
    private boolean mShowGestureLine;
    private int mGestureLineWidth;
    private int mGestureLineColor;
    private int mGestureLineErrorColor;
    private boolean mAutoMatch;
    private int mAutoResetDelay;
    private int mMinSelectCount;

    private static final int MATCH_STATUS_NONE = 1;
    private static final int MATCH_STATUS_MATCH_SUCCESS = 2;
    private static final int MATCH_STATUS_MATCH_FAILED = 3;
    private int mMatchStatus = MATCH_STATUS_NONE;

    private int mViewHeight;
    private int mViewWidth;
    private Paint mNormalOvalPaint;
    private Paint mSelectOvalPaint;
    private Paint mGestureLinePaint;
    private List<RectF> mRectFPointList = new ArrayList<>();
    private List<RectF> mRectFSelectPointList = new ArrayList<>();
    private float mLastTouchX = 0;
    private float mLastTouchY = 0;
    private boolean mMoving = false;

    private List<Integer> mRightPassword = new ArrayList<>();
    private List<Integer> mInputPassword = new ArrayList<>();
    private OnGestureChangedListener mOnGestureChangedListener = null;

    private Runnable mAutoResetRunnable = new Runnable() {
        @Override
        public void run() {
            reset();
        }
    };

    public PwdGestureView(Context context) {
        this(context, null);
    }

    public PwdGestureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PwdGestureView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mViewWidth = widthSize;
        } else {
            mViewWidth = getWidth();
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mViewHeight = heightSize;
        } else {
            mViewHeight = getHeight();
        }
        initView(mViewWidth, mViewHeight);
        setMeasuredDimension(mViewWidth, mViewHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawNormalOval(canvas);
        drawSelectOval(canvas);
        if (mShowGestureLine) {
            drawGestureLine(canvas);
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (!isEnabled()) {
            return true;
        }
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                removeCallbacks(mAutoResetRunnable);
                getParent().requestDisallowInterceptTouchEvent(true);
                checkAddSelectPoint(event.getX(), event.getY());
                if (!mRectFSelectPointList.isEmpty() && null != mOnGestureChangedListener) {
                    mOnGestureChangedListener.onGestureStarted(this);
                }
                resetData();
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mMoving = true;
                if (checkAddSelectPoint(event.getX(), event.getY())) {
                    if (null != mOnGestureChangedListener) {
                        mOnGestureChangedListener.onGestureMoving(this);
                    }
                }
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
                mMoving = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                postInvalidate();
                if (!mRectFSelectPointList.isEmpty()) {
                    boolean inputRight = mInputPassword.toString().equals(mRightPassword.toString());
                    if (null != mOnGestureChangedListener) {
                        mOnGestureChangedListener.onGestureFinished(this, inputRight);
                    }
                    if (mAutoMatch) {
                        mMatchStatus = inputRight ? MATCH_STATUS_MATCH_SUCCESS : MATCH_STATUS_MATCH_FAILED;
                        postDelayed(mAutoResetRunnable, mAutoResetDelay);
                    }
                }
                return false;
            default:
                break;
        }
        return true;
    }

    private void init(Context context, AttributeSet attrs) {
        if (null != attrs) {
            TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PwdGestureView);
            mColumnCount = typedArray.getInt(R.styleable.PwdGestureView_pgv_column_count,
                    DEFAULT_COLUMN_COUNT);
            mRowCount = typedArray.getInt(R.styleable.PwdGestureView_pgv_row_count,
                    DEFAULT_ROW_COUNT);
            mNormalOvalStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_normal_oval_stroke_width,
                    DEFAULT_NORMAL_OVAL_STROKE_WIDTH);
            mNormalOvalStrokeColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_stroke_color,
                    DEFAULT_NORMAL_OVAL_STROKE_COLOR);
            mNormalOvalStrokeSelectColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_stroke_select_color,
                    DEFAULT_NORMAL_OVAL_STROKE_SELECT_COLOR);
            mNormalOvalStrokeErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_stroke_error_color,
                    DEFAULT_NORMAL_OVAL_STROKE_ERROR_COLOR);
            mNormalOvalSolidColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_solid_color,
                    DEFAULT_NORMAL_OVAL_SOLID_COLOR);
            mNormalOvalSolidSelectColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_solid_select_color,
                    DEFAULT_NORMAL_OVAL_SOLID_SELECT_COLOR);
            mNormalOvalSolidErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_normal_oval_solid_error_color,
                    DEFAULT_NORMAL_OVAL_SOLID_ERROR_COLOR);
            mNormalOvalRadius = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_normal_oval_radius,
                    DEFAULT_NORMAL_OVAL_RADIUS);
            mSelectOvalStrokeWidth = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_select_oval_stroke_width,
                    DEFAULT_SELECT_OVAL_STROKE_WIDTH);
            mSelectOvalStrokeColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_select_oval_stroke_color,
                    DEFAULT_SELECT_OVAL_STROKE_COLOR);
            mSelectOvalStrokeErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_select_oval_stroke_error_color,
                    DEFAULT_SELECT_OVAL_STROKE_ERROR_COLOR);
            mSelectOvalSolidColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_select_oval_solid_color,
                    DEFAULT_SELECT_OVAL_SOLID_COLOR);
            mSelectOvalSolidErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_select_oval_solid_error_color,
                    DEFAULT_SELECT_OVAL_SOLID_ERROR_COLOR);
            mSelectOvalRadius = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_select_oval_radius,
                    DEFAULT_SELECT_OVAL_RADIUS);
            mShowGestureLine = typedArray.getBoolean(R.styleable.PwdGestureView_pgv_show_gesture_line,
                    DEFAULT_SHOW_GESTURE_LINE);
            mGestureLineWidth = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_line_width,
                    DEFAULT_GESTURE_LINE_WIDTH);
            mGestureLineColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_line_color,
                    DEFAULT_GESTURE_LINE_COLOR);
            mGestureLineErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_line_error_color,
                    DEFAULT_GESTURE_LINE_ERROR_COLOR);
            mAutoMatch = typedArray.getBoolean(R.styleable.PwdGestureView_pgv_auto_match,
                    DEFAULT_AUTO_MATCH);
            mAutoResetDelay = typedArray.getInt(R.styleable.PwdGestureView_pgv_auto_reset_delay,
                    DEFAULT_AUTO_RESET_DELAY);
            mMinSelectCount = typedArray.getInt(R.styleable.PwdGestureView_pgv_min_select_count,
                    DEFAULT_MIN_SELECT_COUNT);
            typedArray.recycle();
        }
        mNormalOvalPaint = new Paint();
        mNormalOvalPaint.setAntiAlias(true);

        mSelectOvalPaint = new Paint();
        mSelectOvalPaint.setAntiAlias(true);

        mGestureLinePaint = new Paint();
        mGestureLinePaint.setAntiAlias(true);
        mGestureLinePaint.setStyle(Paint.Style.FILL);
        mGestureLinePaint.setColor(mGestureLineColor);
    }

    /**
     * Init view size.
     **/
    private void initView(int width, int height) {
        if (0 >= width || 0 >= height) {
            return;
        }
        mRectFPointList.clear();

        float circleContainerWidth;
        float circleContainerHeight;
        float circleContainerSize;
        float circleMarginLeft;
        float circleMarginTop;

        circleContainerWidth = width / mColumnCount;
        circleContainerHeight = height / mRowCount;

        circleContainerSize = circleContainerWidth > circleContainerHeight ? circleContainerHeight : circleContainerWidth;

        circleMarginLeft = Math.abs((width - circleContainerSize * mColumnCount) / 2.0f);
        circleMarginTop = Math.abs((height - circleContainerSize * mRowCount) / 2.0f);

        for (int row = 0; row < mRowCount; row++) {
            for (int column = 0; column < mColumnCount; column++) {
                RectF circleRect = new RectF();
                circleRect.left = column * circleContainerSize + circleMarginLeft;
                circleRect.right = (column + 1) * circleContainerSize + circleMarginLeft;
                circleRect.top = row * circleContainerSize + circleMarginTop;
                circleRect.bottom = (row + 1) * circleContainerSize + circleMarginTop;
                mRectFPointList.add(circleRect);
            }
        }
    }

    /**
     * Draw the normal oval.
     *
     * @param canvas Canvas
     */
    private void drawNormalOval(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mRectFPointList.size(); i++) {
            mNormalOvalPaint.setStyle(Paint.Style.STROKE);
            mNormalOvalPaint.setStrokeWidth(mNormalOvalStrokeWidth);
            if (isSelected(mRectFPointList.get(i))) {
                if (MATCH_STATUS_MATCH_FAILED == mMatchStatus) {
                    mNormalOvalPaint.setColor(mNormalOvalStrokeErrorColor);
                } else {
                    mNormalOvalPaint.setColor(mNormalOvalStrokeSelectColor);
                }
            } else {
                mNormalOvalPaint.setColor(mNormalOvalStrokeColor);
            }
            canvas.drawCircle(mRectFPointList.get(i).centerX(), mRectFPointList.get(i).centerY(),
                    mNormalOvalRadius - mNormalOvalStrokeWidth / 2.0f, mNormalOvalPaint);

            mNormalOvalPaint.setStyle(Paint.Style.FILL);
            if (isSelected(mRectFPointList.get(i))) {
                if (MATCH_STATUS_MATCH_FAILED == mMatchStatus) {
                    mNormalOvalPaint.setColor(mNormalOvalSolidErrorColor);
                } else {
                    mNormalOvalPaint.setColor(mNormalOvalSolidSelectColor);
                }
            } else {
                mNormalOvalPaint.setColor(mNormalOvalSolidColor);
            }
            canvas.drawCircle(mRectFPointList.get(i).centerX(), mRectFPointList.get(i).centerY(),
                    mNormalOvalRadius - mNormalOvalStrokeWidth, mNormalOvalPaint);
        }
        canvas.restore();
    }

    /**
     * Draw the select oval.
     *
     * @param canvas Canvas
     */
    private void drawSelectOval(Canvas canvas) {
        canvas.save();
        if (mRectFSelectPointList.isEmpty()) {
            return;
        }
        for (int i = 0; i < mRectFSelectPointList.size(); i++) {
            mSelectOvalPaint.setStyle(Paint.Style.STROKE);
            mSelectOvalPaint.setStrokeWidth(mSelectOvalStrokeWidth);
            if (MATCH_STATUS_MATCH_FAILED == mMatchStatus) {
                mSelectOvalPaint.setColor(mSelectOvalStrokeErrorColor);
            } else {
                mSelectOvalPaint.setColor(mSelectOvalStrokeColor);
            }
            canvas.drawCircle(mRectFSelectPointList.get(i).centerX(), mRectFSelectPointList.get(i).centerY(),
                    mSelectOvalRadius - mSelectOvalStrokeWidth / 2.0f, mSelectOvalPaint);

            mSelectOvalPaint.setStyle(Paint.Style.FILL);
            if (MATCH_STATUS_MATCH_FAILED == mMatchStatus) {
                mSelectOvalPaint.setColor(mSelectOvalSolidErrorColor);
            } else {
                mSelectOvalPaint.setColor(mSelectOvalSolidColor);
            }
            canvas.drawCircle(mRectFSelectPointList.get(i).centerX(), mRectFSelectPointList.get(i).centerY(),
                    mSelectOvalRadius - mSelectOvalStrokeWidth, mSelectOvalPaint);
        }
        canvas.restore();
    }

    /**
     * Draw gesture line.
     *
     * @param canvas Canvas
     */
    private void drawGestureLine(Canvas canvas) {
        canvas.save();
        if (mRectFSelectPointList.isEmpty()) {
            return;
        }
        if (MATCH_STATUS_MATCH_FAILED == mMatchStatus) {
            mGestureLinePaint.setColor(mGestureLineErrorColor);
        } else {
            mGestureLinePaint.setColor(mGestureLineColor);
        }
        mGestureLinePaint.setStrokeWidth(mGestureLineWidth);
        float mOuterOvalRadius = Math.max(mNormalOvalRadius, mSelectOvalRadius);
        int count = mRectFSelectPointList.size();
        for (int i = 1; i < count; i++) {
            RectF rectLast = mRectFSelectPointList.get(i - 1);
            RectF rect = mRectFSelectPointList.get(i);
            drawLineBetweenCircle(rectLast.centerX(), rectLast.centerY(), rect.centerX(), rect.centerY(), mOuterOvalRadius, canvas, false);
        }

        if (mMoving) {
            RectF rectEnd = mRectFSelectPointList.get(count - 1);
            drawLineBetweenCircle(rectEnd.centerX(), rectEnd.centerY(), mLastTouchX, mLastTouchY, mOuterOvalRadius, canvas, true);
        }
        canvas.restore();
    }

    /**
     * Draw line between to circle.
     */
    private void drawLineBetweenCircle(float x1, float y1, float x2, float y2, float radius, Canvas canvas, boolean isLast) {
        float x = Math.abs(x1 - x2);
        float y = Math.abs(y1 - y2);
        double z = Math.sqrt(x * x + y * y);
        double angle = Math.asin(y / z);
        if (z <= radius) {
            return;
        }
        float distanceX = (float) (radius * Math.cos(angle));
        float distanceY = (float) (radius * Math.sin(angle));
        float xPoint1 = x1 < x2 ? x1 + distanceX : x1 - distanceX;
        float yPoint1 = y1 < y2 ? y1 + distanceY : y1 - distanceY;
        float xPoint2 = x1 < x2 ? x2 - distanceX : x2 + distanceX;
        float yPoint2 = y1 < y2 ? y2 - distanceY : y2 + distanceY;
        if (isLast) {
            canvas.drawLine(xPoint1, yPoint1, x2, y2, mGestureLinePaint);
        } else {
            canvas.drawLine(xPoint1, yPoint1, xPoint2, yPoint2, mGestureLinePaint);
        }
    }

    /**
     * Check whether add select.
     *
     * @param x the x touched
     * @param y the y touched
     * @return True added
     */
    private boolean checkAddSelectPoint(float x, float y) {
        mLastTouchX = x;
        mLastTouchY = y;
        for (int i = 0; i < mRectFPointList.size(); i++) {
            RectF rect = mRectFPointList.get(i);
            float mOuterOvalRadius = Math.max(mNormalOvalRadius, mSelectOvalRadius);
            if (mOuterOvalRadius < DEFAULT_NORMAL_OVAL_RADIUS) {
                mOuterOvalRadius = DEFAULT_NORMAL_OVAL_RADIUS;
            }
            if (getDistance(rect.centerX(), rect.centerY(), x, y) <= mOuterOvalRadius) {
                // valid distance
                if (!mRectFSelectPointList.contains(rect)) {
                    mRectFSelectPointList.add(rect);
                    mInputPassword.add(i + 1);
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /**
     * Return whether selected.
     *
     * @param rectF RectF
     * @return true selected, false unselected
     */
    private boolean isSelected(RectF rectF) {
        for (int j = 0; j < mRectFSelectPointList.size(); j++) {
            if (rectF.equals(mRectFSelectPointList.get(j))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the distance of tow point.
     */
    private double getDistance(float x, float y, float x1, float y1) {
        double distanceX = Math.abs(x - x1);
        double distanceY = Math.abs(y - y1);
        return Math.sqrt(distanceX * distanceX + distanceY * distanceY);
    }

    /**
     * Reset data.
     */
    private void resetData() {
        mRectFSelectPointList.clear();
        mInputPassword.clear();
        mMatchStatus = MATCH_STATUS_NONE;
    }

    /**
     * Reset data and invalidate.
     */
    public void reset() {
        resetData();
        postInvalidate();
    }

    /**
     * Show match failed view.
     */
    public void showMatchFailedView() {
        mMatchStatus = MATCH_STATUS_MATCH_FAILED;
        postInvalidate();
    }

    /**
     * Set the count of row.
     *
     * @param rowCount the count of row.
     */
    public void setRowCount(int rowCount) {
        this.mRowCount = rowCount <= 0 ? DEFAULT_ROW_COUNT : rowCount;
    }

    /**
     * Set the count of column.
     *
     * @param columnCount the count of column.
     */
    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount <= 0 ? DEFAULT_COLUMN_COUNT : columnCount;
    }

    /**
     * Set the stroke width of normal oval.
     *
     * @param width the stroke width of normal oval.
     */
    public void setNormalOvalStrokeWidth(int width) {
        this.mNormalOvalStrokeWidth = width;
    }

    /**
     * Set the stroke color of normal oval.
     *
     * @param color the stroke color of normal oval.
     */
    public void setNormalOvalStrokeColor(@ColorInt int color) {
        this.mNormalOvalStrokeColor = color;
    }

    /**
     * Set the stroke color of normal oval when selected.
     *
     * @param color the stroke color of normal oval.
     */
    public void setNormalOvalStrokeSelectColor(@ColorInt int color) {
        this.mNormalOvalStrokeSelectColor = color;
    }

    /**
     * Set the stroke color of normal oval when match error.
     *
     * @param color the stroke color of normal oval.
     */
    public void setNormalOvalStrokeErrorColor(@ColorInt int color) {
        this.mNormalOvalStrokeErrorColor = color;
    }

    /**
     * Set the solid color of normal oval.
     *
     * @param color the solid color of normal oval.
     */
    public void setNormalOvalSolidColor(@ColorInt int color) {
        this.mNormalOvalSolidColor = color;
    }

    /**
     * Set the solid color of normal oval when selected.
     *
     * @param color the solid color of normal oval.
     */
    public void setNormalOvalSolidSelectColor(@ColorInt int color) {
        this.mNormalOvalSolidSelectColor = color;
    }

    /**
     * Set the solid color of normal oval when match error.
     *
     * @param color the solid color of normal oval.
     */
    public void setNormalOvalSolidErrorColor(@ColorInt int color) {
        this.mNormalOvalSolidErrorColor = color;
    }

    /**
     * Set the radius of of normal oval.
     *
     * @param radius the radius of of normal oval.
     */
    public void setNormalOvalRadius(int radius) {
        this.mNormalOvalRadius = radius;
    }

    /**
     * Set the stroke width of select oval.
     *
     * @param width the stroke width of select oval.
     */
    public void setSelectOvalStrokeWidth(int width) {
        this.mSelectOvalStrokeWidth = width;
    }

    /**
     * Set the stroke color of select oval.
     *
     * @param color the stroke color of select oval.
     */
    public void setSelectOvalStrokeColor(@ColorInt int color) {
        this.mSelectOvalStrokeColor = color;
    }

    /**
     * Set the stroke color of select oval when match error.
     *
     * @param color the stroke color of select oval.
     */
    public void setSelectOvalStrokeErrorColor(@ColorInt int color) {
        this.mSelectOvalStrokeErrorColor = color;
    }

    /**
     * Set the solid color of select oval.
     *
     * @param color the solid color of select oval.
     */
    public void setSelectOvalSolidColor(@ColorInt int color) {
        this.mSelectOvalSolidColor = color;
    }

    /**
     * Set the solid color of select oval when match error.
     *
     * @param color the solid color of select oval.
     */
    public void setSelectOvalSolidErrorColor(@ColorInt int color) {
        this.mSelectOvalSolidErrorColor = color;
    }

    /**
     * Set the radius of of select oval.
     *
     * @param radius the radius of of select oval.
     */
    public void setSelectOvalRadius(float radius) {
        this.mSelectOvalRadius = radius;
    }

    /**
     * Set show gesture line.
     *
     * @param show True show.
     */
    public void setShowGestureLine(boolean show) {
        this.mShowGestureLine = show;
        postInvalidate();
    }

    /**
     * Set the width of gesture line.
     *
     * @param width the color of gesture line.
     */
    public void setGestureLineWidth(int width) {
        this.mGestureLineWidth = width;
    }

    /**
     * Set the color of gesture line.
     *
     * @param color the color of gesture line.
     */
    public void setGestureLineColor(@ColorInt int color) {
        this.mGestureLineColor = color;
    }

    /**
     * Set the color of gesture line when match error.
     *
     * @param color the color of gesture line.
     */
    public void setGestureLineErrorColor(@ColorInt int color) {
        this.mGestureLineErrorColor = color;
    }

    /**
     * Set whether auto match.
     *
     * @param autoMatch True show.
     */
    public void setAutoMatch(boolean autoMatch) {
        this.mAutoMatch = autoMatch;
    }

    /**
     * Set delay (in milliseconds) of reset when gesture not match.
     *
     * @param delay The delay (in milliseconds) of reset when gesture not match.
     */
    public void setAutoResetDelay(int delay) {
        this.mAutoResetDelay = delay;
    }

    /**
     * Set the min select count.
     *
     * @param count the min select count.
     */
    public void setMinSelectCount(int count) {
        this.mMinSelectCount = count;
    }

    /**
     * Get the min select count.
     *
     * @return the min select count.
     */
    public int getMinSelectCount() {
        return mMinSelectCount;
    }

    /**
     * Set right password.
     *
     * @param rightPwd the right password.
     */
    public void setRightPassword(List<Integer> rightPwd) {
        this.mRightPassword.clear();
        if (null != rightPwd) {
            this.mRightPassword.addAll(rightPwd);
        }
    }

    /**
     * Set right password.
     *
     * @param rightPwd the right password.
     */
    public void setRightPassword(int[] rightPwd) {
        mRightPassword = new ArrayList<>();
        if (null != rightPwd) {
            for (int p : rightPwd) {
                mRightPassword.add(p);
            }
        }
    }

    /**
     * Get right password.
     *
     * @return the right password.
     */
    @NonNull
    public List<Integer> getRightPassword() {
        return mRightPassword;
    }

    /**
     * Get right password.
     *
     * @return the right password.
     */
    @NonNull
    public int[] getRightPasswordArray() {
        int[] pwd = new int[mRightPassword.size()];
        for (int i = 0; i < pwd.length; i++) {
            pwd[i] = mRightPassword.get(i);
        }
        return pwd;
    }

    /**
     * Get input password.
     *
     * @return the input password.
     */
    @NonNull
    public List<Integer> getInputPassword() {
        return mInputPassword;
    }

    /**
     * Register a callback to be invoked when gesture changed.
     *
     * @param listener the callback to call when gesture finished.
     */
    public void setOnGestureChangedListener(OnGestureChangedListener listener) {
        this.mOnGestureChangedListener = listener;
    }

    public interface OnGestureChangedListener {
        /**
         * Invoked when gesture started.
         *
         * @param view PwdGestureView.
         */
        void onGestureStarted(PwdGestureView view);

        /**
         * Invoked when gesture moving.
         *
         * @param view PwdGestureView.
         */
        void onGestureMoving(PwdGestureView view);

        /**
         * Invoked when gesture finished.
         *
         * @param view  PwdGestureView.
         * @param right right or error.
         */
        void onGestureFinished(PwdGestureView view, boolean right);

    }

}
