package huawei.android.graphics.drawable;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import huawei.android.widget.loader.ResLoader;
import huawei.android.widget.loader.ResLoaderUtil;
import java.util.Locale;

public class HwEventBadge extends Drawable {
    private static final int BADGE_RECTANGLE_NUM = 10;
    private static final int DEFAULT_BADGE_MAX_NUM = 99;
    private static final float FLOAT_HALF_DIVISOR = 2.0f;
    private static final int WIDTH_FACTOR_INT = 2;
    private Drawable mBackgroundDrawable;
    private int mBadgeCount = 0;
    private float mBaseLine;
    private Context mContext;
    private String mCountText;
    private TextPaint mPaint;
    private float mTextStartX;

    public HwEventBadge(Context context) {
        this.mContext = context;
        this.mPaint = new TextPaint();
        this.mPaint.setColor(ResLoaderUtil.getColor(context, "emui_text_primary_inverse"));
        this.mPaint.setAntiAlias(true);
        this.mPaint.setFilterBitmap(true);
        this.mPaint.setTextSize((float) ResLoaderUtil.getDimensionPixelSize(context, "emui_text_size_caption"));
        refresh();
    }

    private void refresh() {
        this.mBackgroundDrawable = ResLoader.getInstance().getResources(this.mContext).getDrawable(ResLoaderUtil.getDrawableId(this.mContext, "badge_bg"), null);
        this.mBackgroundDrawable.setTint(ResLoaderUtil.getColor(this.mContext, "emui_functional_red"));
    }

    @Override // android.graphics.drawable.Drawable
    public void setBounds(Rect bounds) {
        super.setBounds(bounds);
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            Rect backRect = drawable.getBounds();
            if (bounds.width() < backRect.width() || bounds.height() < backRect.height()) {
                super.setBounds(backRect);
            }
        }
    }

    public void setBadgeCount(int count) {
        setBadgeCount(count, DEFAULT_BADGE_MAX_NUM);
    }

    public void setBadgeCount(int count, int maxNumber) {
        if (this.mContext != null) {
            this.mBadgeCount = count;
            if (this.mBadgeCount <= maxNumber) {
                this.mCountText = String.format(Locale.getDefault(), "%d", Integer.valueOf(this.mBadgeCount));
            } else {
                this.mCountText = String.format(Locale.getDefault(), "%d+", Integer.valueOf(maxNumber));
            }
            calcBadgeLocation();
        }
    }

    public String getBadgeText() {
        return this.mCountText;
    }

    private void calcBadgeLocation() {
        int backgroundWidth;
        int backgroundWidth2;
        float width = this.mPaint.measureText(this.mCountText);
        float height = this.mPaint.descent() - this.mPaint.ascent();
        int i = this.mBadgeCount;
        if (i == 0) {
            backgroundWidth2 = ResLoaderUtil.getDimensionPixelSize(this.mContext, "badge_dot_size");
            backgroundWidth = backgroundWidth2;
        } else if (i < BADGE_RECTANGLE_NUM) {
            backgroundWidth2 = ResLoaderUtil.getDimensionPixelSize(this.mContext, "badge_height");
            backgroundWidth = backgroundWidth2;
        } else {
            int backgroundWidth3 = (int) ((((float) ResLoaderUtil.getDimensionPixelSize(this.mContext, "badge_margin")) * FLOAT_HALF_DIVISOR) + width);
            backgroundWidth = ResLoaderUtil.getDimensionPixelSize(this.mContext, "badge_height");
            backgroundWidth2 = backgroundWidth3;
        }
        this.mTextStartX = (((float) backgroundWidth2) - width) / FLOAT_HALF_DIVISOR;
        this.mBaseLine = ((((float) backgroundWidth) - height) / FLOAT_HALF_DIVISOR) - this.mPaint.ascent();
        setBounds(0, 0, backgroundWidth2, backgroundWidth);
        this.mBackgroundDrawable.setBounds(0, 0, backgroundWidth2, backgroundWidth);
        invalidateSelf();
    }

    public void setBackgoundColor(int badgeColor) {
        this.mBackgroundDrawable.setTint(badgeColor);
        invalidateSelf();
    }

    public void setTextColor(int textColor) {
        if (this.mPaint.getColor() != textColor) {
            this.mPaint.setColor(textColor);
            invalidateSelf();
        }
    }

    @Override // android.graphics.drawable.Drawable
    public void draw(Canvas canvas) {
        Drawable drawable = this.mBackgroundDrawable;
        if (drawable != null) {
            drawable.draw(canvas);
            if (this.mBadgeCount != 0) {
                canvas.drawText(this.mCountText, this.mTextStartX, this.mBaseLine, this.mPaint);
            }
        }
    }

    @Override // android.graphics.drawable.Drawable
    public int getOpacity() {
        return 0;
    }

    @Override // android.graphics.drawable.Drawable
    public void setAlpha(int alpha) {
    }

    @Override // android.graphics.drawable.Drawable
    public void setColorFilter(ColorFilter colorFilter) {
    }
}
