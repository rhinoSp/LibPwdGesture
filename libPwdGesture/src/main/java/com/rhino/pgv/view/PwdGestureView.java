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
 *Follow this example:
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
 *          app:pgv_row_count="3"
 *          app:pgv_circle_line_width="2dp"
 *          app:pgv_circle_radius="40dp"
 *          app:pgv_circle_color="#FFAAAAAA"
 *          app:pgv_line_width="2dp"
 *          app:pgv_line_color="#FF008888"
 *          app:pgv_circle_select_radius="10dp"
 *          app:pgv_circle_select_color="#FF008888"
 *          app:pgv_show_line="true""/&gt
 *
 *&lt;/RelativeLayout&gt
 *</pre>
 * @author LuoLin
 * @since Created on 2017/9/29.
 **/
public class PwdGestureView extends View {

    private static final int DEFAULT_COLUMN_COUNT = 3;
    private static final int DEFAULT_ROW_COUNT = 3;
    private static final int DEFAULT_CIRCLE_WIDTH = 2;  //dp
    private static final int DEFAULT_CIRCLE_RADIUS = 40;  //dp
    private static final int DEFAULT_CIRCLE_COLOR = 0xFF888888;
    private static final int DEFAULT_LINE_WIDTH = 2; //dp
    private static final int DEFAULT_LINE_COLOR = 0xFF008888;
    private static final int DEFAULT_CIRCLE_SELECT_RADIUS = 8;  //dp
    private static final int DEFAULT_CIRCLE_SELECT_COLOR = 0xFF008888;
    private static final int DEFAULT_INPUT_ERROR_COLOR = 0xFFFF0000;
    private static final boolean DEFAULT_SHOW_LINE = true;
    private static final int DEFAULT_MIN_POINT_COUNT = 4;
    private int mColumnCount = DEFAULT_COLUMN_COUNT;
    private int mRowCount = DEFAULT_ROW_COUNT;
    private int mCircleLineWidth = DEFAULT_CIRCLE_WIDTH;
    private float mCircleRadius = DEFAULT_CIRCLE_RADIUS;
    private int mCircleColor = DEFAULT_CIRCLE_COLOR;
    private int mLineWidth = DEFAULT_LINE_WIDTH;
    private int mLineColor = DEFAULT_LINE_COLOR;
    private float mCircleSelectRadius = DEFAULT_CIRCLE_SELECT_RADIUS;
    private int mCircleSelectColor = DEFAULT_CIRCLE_SELECT_COLOR;
    private int mInputErrorColor = DEFAULT_INPUT_ERROR_COLOR;
    private boolean mIsShowLine = DEFAULT_SHOW_LINE;
    private int mMinPointCount = DEFAULT_MIN_POINT_COUNT;

    private int mViewHeight;
    private int mViewWidth;
    private Paint mCirclePaint;
    private Paint mCircleSelectPaint;
    private Paint mLinePaint;
    private List<RectF> mRectFPointList = new ArrayList<>();
    private List<RectF> mRectFSelectPointList = new ArrayList<>();
    private List<Integer> mRightPassword = new ArrayList<>();
    private List<Integer> mInputPassword = new ArrayList<>();
    private float mLastTouchX = 0;
    private float mLastTouchY = 0;
    private boolean mIsMoved = false;

    private static int STATUS_NONE = 1;
    private static int STATUS_INPUT_RIGHT = 2;
    private static int STATUS_INPUT_ERROR = 3;
    private int mInputStatus = STATUS_NONE;
    private boolean mIsAutoMatch = true;

    private OnGestureFinishedListener mOnGestureFinishedListener = null;

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
        drawGridCirclePoint(canvas);
        drawSelectCircle(canvas);
        if (mIsShowLine) {
            drawLine(canvas);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                getParent().requestDisallowInterceptTouchEvent(true);
                checkAddSelectPoint(event.getX(), event.getY());
                resetData();
                postInvalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                mIsMoved = true;
                checkAddSelectPoint(event.getX(), event.getY());
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                mIsMoved = false;
                getParent().requestDisallowInterceptTouchEvent(false);
                postInvalidate();
                if (!mRectFSelectPointList.isEmpty()) {
                    boolean inputRight = mInputPassword.toString().equals(mRightPassword.toString());
                    if (null != mOnGestureFinishedListener) {
                        mOnGestureFinishedListener.onGestureFinished(this, inputRight);
                    }
                    if (mIsAutoMatch) {
                        mInputStatus = inputRight ? STATUS_INPUT_RIGHT : STATUS_INPUT_ERROR;
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                reset();
                            }
                        }, 1000);
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
            mCircleLineWidth = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_circle_line_width,
                    dip2px(DEFAULT_CIRCLE_WIDTH));
            mCircleRadius = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_circle_radius,
                    dip2px(DEFAULT_CIRCLE_RADIUS));
            mCircleColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_circle_color,
                    DEFAULT_CIRCLE_COLOR);
            mLineWidth = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_line_width,
                    dip2px(DEFAULT_LINE_WIDTH));
            mLineColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_line_color,
                    DEFAULT_LINE_COLOR);
            mCircleSelectRadius = typedArray.getDimensionPixelOffset(R.styleable.PwdGestureView_pgv_circle_select_radius,
                    dip2px(DEFAULT_CIRCLE_SELECT_RADIUS));
            mCircleSelectColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_circle_select_color,
                    DEFAULT_CIRCLE_SELECT_COLOR);
            mInputErrorColor = typedArray.getColor(R.styleable.PwdGestureView_pgv_input_error_color,
                    DEFAULT_INPUT_ERROR_COLOR);
            mIsShowLine = typedArray.getBoolean(R.styleable.PwdGestureView_pgv_show_line,
                    DEFAULT_SHOW_LINE);
            typedArray.recycle();
        }
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStyle(Paint.Style.STROKE);
        mCirclePaint.setColor(mCircleColor);

        mCircleSelectPaint = new Paint();
        mCircleSelectPaint.setAntiAlias(true);
        mCircleSelectPaint.setStyle(Paint.Style.FILL);
        mCircleSelectPaint.setColor(mCircleSelectColor);

        mLinePaint = new Paint();
        mLinePaint.setAntiAlias(true);
        mLinePaint.setStyle(Paint.Style.FILL);
        mLinePaint.setColor(mLineColor);
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
     * Draw the grid circle point.
     *
     * @param canvas Canvas
     */
    private void drawGridCirclePoint(Canvas canvas) {
        mCirclePaint.setStrokeWidth(mCircleLineWidth);
        for (int i = 0; i < mRectFPointList.size(); i++) {
            RectF rectF = mRectFPointList.get(i);
            if (STATUS_INPUT_ERROR == mInputStatus) {
                mCirclePaint.setColor(mInputErrorColor);
            } else {
                mCirclePaint.setColor(isSelected(rectF) ? mCircleSelectColor : mCircleColor);
            }
            canvas.drawCircle(mRectFPointList.get(i).centerX(), mRectFPointList.get(i).centerY(), mCircleRadius, mCirclePaint);
        }
    }

    /**
     * Draw the circle which select.
     *
     * @param canvas Canvas
     */
    private void drawSelectCircle(Canvas canvas) {
        if (mRectFSelectPointList.isEmpty()) {
            return;
        }
        for (int i = 0; i < mRectFSelectPointList.size(); i++) {
            if (STATUS_INPUT_ERROR == mInputStatus) {
                mCircleSelectPaint.setColor(mInputErrorColor);
            } else {
                mCircleSelectPaint.setColor(mCircleSelectColor);
            }
            canvas.drawCircle(mRectFSelectPointList.get(i).centerX(), mRectFSelectPointList.get(i).centerY(), mCircleSelectRadius, mCircleSelectPaint);
        }
    }

    /**
     * Draw line between select points.
     *
     * @param canvas Canvas
     */
    private void drawLine(Canvas canvas) {
        if (mRectFSelectPointList.isEmpty()) {
            return;
        }
        if (STATUS_INPUT_ERROR == mInputStatus) {
            mLinePaint.setColor(mInputErrorColor);
        } else {
            mLinePaint.setColor(mLineColor);
        }
        mLinePaint.setStrokeWidth(mLineWidth);
        int count = mRectFSelectPointList.size();
        for (int i = 1; i < count; i++) {
            RectF rectLast = mRectFSelectPointList.get(i - 1);
            RectF rect = mRectFSelectPointList.get(i);
            drawLineBetweenCircle(rectLast.centerX(), rectLast.centerY(), rect.centerX(), rect.centerY(), mCircleRadius, canvas, false);
        }

        if (mIsMoved) {
            RectF rectEnd = mRectFSelectPointList.get(count - 1);
            drawLineBetweenCircle(rectEnd.centerX(), rectEnd.centerY(), mLastTouchX, mLastTouchY, mCircleRadius, canvas, true);
        }
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
            canvas.drawLine(xPoint1, yPoint1, x2, y2, mLinePaint);
        } else {
            canvas.drawLine(xPoint1, yPoint1, xPoint2, yPoint2, mLinePaint);
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
            if (getDistance(rect.centerX(), rect.centerY(), x, y) <= mCircleRadius) {
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
     * dp convert to px.
     *
     * @param dpValue the dp value
     * @return the px value
     */
    private int dip2px(float dpValue) {
        final float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
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
        mInputStatus = STATUS_NONE;
    }

    /**
     * Reset data and invalidate.
     */
    public void reset() {
        resetData();
        postInvalidate();
    }

    /**
     * Show error of input.
     */
    public void showInputError() {
        mInputStatus = STATUS_INPUT_ERROR;
        postInvalidate();
    }

    /**
     * Set the count of column.
     *
     * @param columnCount the count of column
     */
    public void setColumnCount(int columnCount) {
        this.mColumnCount = columnCount <= 0 ? DEFAULT_COLUMN_COUNT : columnCount;
    }

    /**
     * Set the count of row.
     *
     * @param rowCount the count of row
     */
    public void setRowCount(int rowCount) {
        this.mRowCount = rowCount <= 0 ? DEFAULT_ROW_COUNT : rowCount;
    }

    /**
     * Set the width of circle line.
     *
     * @param width the width of circle line(dp)
     */
    public void setCircleLineWidth(int width) {
        this.mCircleLineWidth = width <= 0 ? dip2px(DEFAULT_CIRCLE_WIDTH) : dip2px(width);
    }

    /**
     * Set the radius of circle line.
     *
     * @param radius the radius of circle line(dp)
     */
    public void setCircleRadius(int radius) {
        this.mCircleRadius = radius <= 0 ? dip2px(DEFAULT_CIRCLE_RADIUS) : dip2px(radius);
    }

    /**
     * Set the color of circle.
     *
     * @param color the color of circle
     */
    public void setCircleColor(@ColorInt int color) {
        this.mCircleColor = color == 0 ? DEFAULT_CIRCLE_COLOR : color;
    }

    /**
     * Set the width of line.
     *
     * @param width the color of line(dp)
     */
    public void setLineWidth(int width) {
        this.mLineWidth = width <= 0 ? dip2px(DEFAULT_LINE_WIDTH) : dip2px(width);
    }

    /**
     * Set the color of line.
     *
     * @param color the color of line
     */
    public void setLineColor(@ColorInt int color) {
        this.mLineColor = color == 0 ? DEFAULT_LINE_COLOR : color;
    }

    /**
     * Set the radius of select circle.
     *
     * @param radius the radius of select circle(dp)
     */
    public void setCircleSelectRadius(float radius) {
        this.mCircleSelectRadius = radius <= 0 ? dip2px(DEFAULT_CIRCLE_SELECT_RADIUS) : dip2px(radius);
    }

    /**
     * Set the color of select circle.
     *
     * @param color the color of select circle
     */
    public void setCircleSelectColor(@ColorInt int color) {
        this.mCircleSelectColor = color == 0 ? DEFAULT_CIRCLE_SELECT_COLOR : color;
    }

    /**
     * Set whether show line.
     *
     * @param show True show
     */
    public void setShowLine(boolean show) {
        this.mIsShowLine = show;
        postInvalidate();
    }

    /**
     * Set whether auto match.
     *
     * @param autoMatch True show
     */
    public void setAutoMatch(boolean autoMatch) {
        this.mIsAutoMatch = autoMatch;
    }

    /**
     * Set the min point count.
     * @param count the min point count
     */
    public void setMinPointCount(int count) {
        this.mMinPointCount = 0 >= count ? DEFAULT_MIN_POINT_COUNT : count;
    }

    /**
     * Get the min point count.
     * @return the min point count
     */
    public int getMinPointCount() {
        return mMinPointCount;
    }

    /**
     * Set right password.
     *
     * @param realPwd the right password
     */
    public boolean setRightPassword(List<Integer> realPwd) {
        this.mRightPassword.clear();
        if (null != realPwd) {
            this.mRightPassword.addAll(realPwd);
        }
        return true;
    }

    /**
     * Set right password.
     *
     * @param rightPwd the right password
     */
    public boolean setRightPassword(int[] rightPwd) {
        mRightPassword = new ArrayList<>();
        if (null != rightPwd) {
            for (int p : rightPwd) {
                mRightPassword.add(p);
            }
        }
        return true;
    }

    /**
     * Get right password.
     *
     * @return the right password
     */
    @NonNull
    public List<Integer> getRightPassword() {
        return mRightPassword;
    }

    /**
     * Get right password.
     *
     * @return the right password
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
     * @return the input password
     */
    @NonNull
    public List<Integer> getInputPassword() {
        return mInputPassword;
    }

    /**
     * Register a callback to be invoked when gesture finished.
     *
     * @param listener the callback to call when gesture finished
     */
    public void setOnGestureFinishedListener(OnGestureFinishedListener listener) {
        this.mOnGestureFinishedListener = listener;
    }

    public interface OnGestureFinishedListener {
        /**
         * Call when gesture finished.
         * @param view PwdGestureView
         * @param right right or error
         */
        void onGestureFinished(PwdGestureView view, boolean right);
    }

}
