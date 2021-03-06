package huawei.android.widget.pattern;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import com.android.internal.widget.ViewPager;
import huawei.android.widget.loader.ResLoader;
import huawei.android.widget.loader.ResLoaderUtil;

public class HwImageTextIndicator extends View implements ViewPager.OnPageChangeListener {
    private static final int AUTO_PLAY_DELAYMILLIS = 5000;
    private static final float DIVIDER = 2.0f;
    private static final int MIN_COUNT = 2;
    public static final String TAG = "HwImageTextIndicator";
    private static final int UNSELECTED_DOT_PAINT_ALPHA = 76;
    /* access modifiers changed from: private */
    public Handler mAutoPlayHandler;
    private int mBottomMargin;
    private Context mContext;
    private int mCurrentPage;
    private float[] mDotCenterX;
    private float mDotCenterY;
    private int mDotDiameter;
    private float mDotRadius;
    private float mFontHeight;
    private Paint.FontMetrics mFontMetrics;
    private int mGap;
    /* access modifiers changed from: private */
    public boolean mIsAttached;
    /* access modifiers changed from: private */
    public boolean mIsAutoPlay;
    private boolean mIsShowAsDot;
    private ViewPager.OnPageChangeListener mPageChangeListener;
    private int mPageCount;
    /* access modifiers changed from: private */
    public Runnable mRunnable;
    private int mSelectedColour;
    private int mSelectedDotSize;
    private float mSelectedDotX;
    private Paint mSelectedPaint;
    private float mTextBaseLineY;
    private float mTextCenterX;
    private String mTextNum;
    private Paint mTextPaint;
    private int mTopMargin;
    private int mUnselectedColour;
    private Paint mUnselectedPaint;
    /* access modifiers changed from: private */
    public ViewPager mViewPager;

    public HwImageTextIndicator(Context context) {
        this(context, null);
    }

    public HwImageTextIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HwImageTextIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public HwImageTextIndicator(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mRunnable = new Runnable() {
            public void run() {
                if (HwImageTextIndicator.this.mViewPager == null || HwImageTextIndicator.this.mViewPager.getAdapter() == null) {
                    Log.w(HwImageTextIndicator.TAG, "Runnable, mViewPager=" + HwImageTextIndicator.this.mViewPager);
                } else if (2 > HwImageTextIndicator.this.mViewPager.getAdapter().getCount()) {
                    Log.w(HwImageTextIndicator.TAG, "the page count is less than two");
                } else {
                    int currentItem = HwImageTextIndicator.this.mViewPager.getCurrentItem();
                    if (currentItem == HwImageTextIndicator.this.mViewPager.getAdapter().getCount() - 1) {
                        HwImageTextIndicator.this.mViewPager.setCurrentItem(0, false);
                    } else {
                        HwImageTextIndicator.this.mViewPager.setCurrentItem(currentItem + 1, true);
                    }
                    if (HwImageTextIndicator.this.mIsAutoPlay) {
                        HwImageTextIndicator.this.mAutoPlayHandler.postDelayed(HwImageTextIndicator.this.mRunnable, 5000);
                    }
                }
            }
        };
        this.mContext = context;
        this.mIsAutoPlay = false;
        this.mIsShowAsDot = true;
        Resources.Theme theme = ResLoader.getInstance().getTheme(context);
        if (theme != null) {
            TypedArray typedArraySelectDot = theme.obtainStyledAttributes(new int[]{16843818});
            TypedArray typedArrayUnselectDot = theme.obtainStyledAttributes(new int[]{16842800});
            this.mSelectedColour = typedArraySelectDot.getColor(0, 0);
            this.mUnselectedColour = typedArrayUnselectDot.getColor(0, 0);
            typedArraySelectDot.recycle();
            typedArrayUnselectDot.recycle();
        }
        initData();
    }

    private void initData() {
        if (this.mIsShowAsDot) {
            initDotsParam();
        } else {
            initNumericParam();
        }
        if (this.mIsAutoPlay) {
            initHandle();
        }
        addOnAttachStateChangeListener(new View.OnAttachStateChangeListener() {
            public void onViewAttachedToWindow(View v) {
                boolean unused = HwImageTextIndicator.this.mIsAttached = true;
                if (HwImageTextIndicator.this.mIsAutoPlay) {
                    HwImageTextIndicator.this.startAutoPlay();
                }
            }

            public void onViewDetachedFromWindow(View v) {
                boolean unused = HwImageTextIndicator.this.mIsAttached = false;
                if (HwImageTextIndicator.this.mIsAutoPlay) {
                    HwImageTextIndicator.this.stopAutoPlay();
                }
            }
        });
    }

    private void initNumericParam() {
        int textSize = ResLoaderUtil.getDimensionPixelSize(this.mContext, "hwpattern_image_text_indicator_emui_master_body_2_dp");
        int textColor = ResLoaderUtil.getColor(this.mContext, "emui_gray_2");
        this.mTopMargin = ResLoaderUtil.getDimensionPixelSize(this.mContext, "margin_l");
        this.mBottomMargin = ResLoaderUtil.getDimensionPixelSize(this.mContext, "margin_l");
        this.mTextPaint = new Paint(1);
        this.mTextPaint.setColor(textColor);
        this.mTextPaint.setTextSize((float) textSize);
        this.mTextPaint.setTextAlign(Paint.Align.CENTER);
        this.mFontMetrics = this.mTextPaint.getFontMetrics();
        this.mFontHeight = this.mFontMetrics.bottom - this.mFontMetrics.top;
    }

    private void initDotsParam() {
        this.mDotDiameter = ResLoaderUtil.getDimensionPixelSize(this.mContext, "hwpattern_image_text_indicator_default_dot_size");
        this.mGap = ResLoaderUtil.getDimensionPixelSize(this.mContext, "hwpattern_image_text_indicator_default_gap");
        this.mSelectedDotSize = ResLoaderUtil.getDimensionPixelSize(this.mContext, "hwpattern_image_text_indicator_default_selected_dot_size");
        this.mTopMargin = ResLoaderUtil.getDimensionPixelSize(this.mContext, "margin_m");
        this.mBottomMargin = ResLoaderUtil.getDimensionPixelSize(this.mContext, "margin_m");
        this.mDotRadius = ((float) this.mDotDiameter) / DIVIDER;
        this.mUnselectedPaint = new Paint(1);
        this.mUnselectedPaint.setColor(this.mUnselectedColour);
        this.mUnselectedPaint.setAlpha(UNSELECTED_DOT_PAINT_ALPHA);
        this.mSelectedPaint = new Paint(1);
        this.mSelectedPaint.setColor(this.mSelectedColour);
    }

    private void initHandle() {
        this.mAutoPlayHandler = new Handler();
    }

    /* access modifiers changed from: package-private */
    public void setViewPager(ViewPager viewPager) {
        if (viewPager == null || viewPager.getAdapter() == null) {
            Log.w(TAG, "setViewPage, viewPager = " + viewPager);
            return;
        }
        this.mViewPager = viewPager;
        setPageCount(this.mViewPager.getAdapter().getCount());
        viewPager.getAdapter().registerDataSetObserver(new DataSetObserver() {
            public void onChanged() {
                HwImageTextIndicator.this.setPageCount(HwImageTextIndicator.this.mViewPager.getAdapter().getCount());
            }
        });
        viewPager.setOnPageChangeListener(this);
        setCurrentPageImmediate();
    }

    /* access modifiers changed from: package-private */
    public void startAutoPlay() {
        this.mIsAutoPlay = true;
        if (this.mAutoPlayHandler == null) {
            initHandle();
        }
        this.mAutoPlayHandler.removeCallbacks(this.mRunnable);
        this.mAutoPlayHandler.postDelayed(this.mRunnable, 5000);
    }

    /* access modifiers changed from: package-private */
    public void stopAutoPlay() {
        this.mIsAutoPlay = false;
        if (this.mAutoPlayHandler != null) {
            this.mAutoPlayHandler.removeCallbacks(this.mRunnable);
        }
        this.mAutoPlayHandler = null;
    }

    public void setOnPageChangeListener(ViewPager.OnPageChangeListener onPageChangeListener) {
        this.mPageChangeListener = onPageChangeListener;
    }

    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageScrolled(position, positionOffset, positionOffsetPixels);
        }
    }

    public void onPageSelected(int position) {
        if (this.mIsAttached) {
            setSelectedPage(position);
        } else {
            setCurrentPageImmediate();
        }
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageSelected(position);
        }
    }

    public void onPageScrollStateChanged(int state) {
        if (this.mPageChangeListener != null) {
            this.mPageChangeListener.onPageScrollStateChanged(state);
        }
    }

    /* access modifiers changed from: private */
    public void setPageCount(int pages) {
        this.mPageCount = pages;
        calculatePositions();
        requestLayout();
        invalidate();
    }

    private void calculatePositions() {
        if (this.mIsShowAsDot) {
            calculateDotPositions();
        } else {
            calculateNumPosition();
        }
    }

    private void calculateDotPositions() {
        int left = getPaddingLeft();
        int top = getPaddingTop();
        float startLeft = ((float) left) + (((float) (((getWidth() - getPaddingRight()) - left) - getDesiredWidth())) / DIVIDER) + (((float) this.mSelectedDotSize) / DIVIDER);
        this.mDotCenterX = new float[this.mPageCount];
        for (int i = 0; i < this.mPageCount; i++) {
            this.mDotCenterX[i] = ((float) ((this.mDotDiameter + this.mGap) * i)) + startLeft;
        }
        this.mDotCenterY = ((float) top) + (((float) ((this.mSelectedDotSize + this.mTopMargin) + this.mBottomMargin)) / DIVIDER);
        setCurrentPageImmediate();
    }

    private void calculateNumPosition() {
        int left = getPaddingLeft();
        this.mTextCenterX = ((float) left) + (((float) ((getWidth() - getPaddingRight()) - left)) / DIVIDER);
        this.mTextBaseLineY = ((((float) getPaddingTop()) + (((this.mFontHeight + ((float) this.mTopMargin)) + ((float) this.mBottomMargin)) / DIVIDER)) + (this.mFontHeight / DIVIDER)) - this.mFontMetrics.bottom;
        setCurrentPageImmediate();
    }

    private void setCurrentPageImmediate() {
        float f;
        this.mCurrentPage = this.mViewPager != null ? this.mViewPager.getCurrentItem() : 0;
        if (!this.mIsShowAsDot) {
            if (isRtlLocale()) {
                this.mTextNum = this.mPageCount + "/" + (this.mCurrentPage + 1);
            } else {
                this.mTextNum = (this.mCurrentPage + 1) + "/" + this.mPageCount;
            }
            return;
        }
        if (this.mPageCount > 0) {
            if (isRtlLocale()) {
                f = this.mDotCenterX[(this.mPageCount - 1) - this.mCurrentPage];
            } else {
                f = this.mDotCenterX[this.mCurrentPage];
            }
            this.mSelectedDotX = f;
        }
    }

    /* access modifiers changed from: protected */
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int specSize;
        int desiredHeight = getDesiredHeight();
        int mode = View.MeasureSpec.getMode(heightMeasureSpec);
        if (mode != Integer.MIN_VALUE) {
            specSize = mode != 1073741824 ? desiredHeight : View.MeasureSpec.getSize(heightMeasureSpec);
        } else {
            int specSize2 = View.MeasureSpec.getSize(heightMeasureSpec);
            specSize = desiredHeight < specSize2 ? desiredHeight : specSize2;
        }
        setMeasuredDimension(widthMeasureSpec, specSize);
        calculatePositions();
    }

    /* access modifiers changed from: protected */
    public void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        setMeasuredDimension(width, height);
        calculatePositions();
    }

    private int getDesiredHeight() {
        int distanceDx = this.mTopMargin + this.mBottomMargin;
        return getPaddingTop() + (this.mIsShowAsDot ? this.mSelectedDotSize : (int) (this.mFontHeight + 0.5f)) + distanceDx + getPaddingBottom();
    }

    private int getDesiredWidth() {
        return (this.mPageCount * this.mDotDiameter) + (this.mSelectedDotSize - this.mDotDiameter) + ((this.mPageCount - 1) * this.mGap);
    }

    /* access modifiers changed from: protected */
    public void onDraw(Canvas canvas) {
        if (this.mIsShowAsDot) {
            drawUnselected(canvas);
            drawSelected(canvas);
            return;
        }
        drawNumText(canvas);
    }

    private void drawNumText(Canvas canvas) {
        if (canvas == null) {
            Log.w(TAG, "drawNumText, the canvas is null.");
        } else {
            canvas.drawText(this.mTextNum, this.mTextCenterX, this.mTextBaseLineY, this.mTextPaint);
        }
    }

    private void drawUnselected(Canvas canvas) {
        if (canvas == null) {
            Log.w(TAG, "drawUnselected, the canvas is null.");
            return;
        }
        for (int page = 0; page < this.mPageCount; page++) {
            canvas.drawCircle(this.mDotCenterX[page], this.mDotCenterY, this.mDotRadius, this.mUnselectedPaint);
        }
    }

    private void drawSelected(Canvas canvas) {
        if (canvas == null) {
            Log.w(TAG, "drawSelected, the canvas is null.");
            return;
        }
        if (this.mPageCount > 0) {
            canvas.drawCircle(this.mSelectedDotX, this.mDotCenterY, ((float) this.mSelectedDotSize) / DIVIDER, this.mSelectedPaint);
        }
    }

    private void setSelectedPage(int now) {
        if (now == this.mCurrentPage || this.mPageCount == 0) {
            Log.w(TAG, "setSelectedPage : mCurrentPage = " + this.mCurrentPage + ", now = " + now + ", mPageCount = " + this.mPageCount);
            return;
        }
        this.mCurrentPage = now;
        setCurrentPageImmediate();
        invalidate();
    }

    /* access modifiers changed from: package-private */
    public void setDotsColor(int selectedColor, int unselectedColor) {
        this.mSelectedColour = selectedColor;
        this.mUnselectedColour = unselectedColor;
        initDotsParam();
        invalidate();
    }
}
