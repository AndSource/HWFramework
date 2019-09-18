package huawei.android.app;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import huawei.android.widget.HwWidgetUtils;
import huawei.android.widget.loader.ResLoaderUtil;
import java.text.NumberFormat;

public class HwProgressDialog extends AlertDialog {
    public static final int STYLE_HORIZONTAL = 1;
    public static final int STYLE_SPINNER = 0;
    private View mCancel;
    private boolean mHasStarted;
    private int mIncrementBy;
    private int mIncrementSecondaryBy;
    private boolean mIndeterminate;
    private Drawable mIndeterminateDrawable;
    private int mMax;
    private CharSequence mMessage;
    private TextView mMessageView;
    /* access modifiers changed from: private */
    public ProgressBar mProgress;
    private Drawable mProgressDrawable;
    /* access modifiers changed from: private */
    public TextView mProgressNumber;
    /* access modifiers changed from: private */
    public String mProgressNumberFormat;
    /* access modifiers changed from: private */
    public TextView mProgressPercent;
    /* access modifiers changed from: private */
    public NumberFormat mProgressPercentFormat;
    private int mProgressStyle = 0;
    private int mProgressVal;
    private int mSecondaryProgressVal;
    private View mSpace;
    private Handler mViewUpdateHandler;

    public HwProgressDialog(Context context) {
        super(context);
        initFormats();
    }

    public HwProgressDialog(Context context, int theme) {
        super(context, theme);
        initFormats();
    }

    private void initFormats() {
        this.mProgressNumberFormat = "%1d/%2d";
        this.mProgressPercentFormat = NumberFormat.getPercentInstance();
        this.mProgressPercentFormat.setMaximumFractionDigits(0);
    }

    public static HwProgressDialog show(Context context, CharSequence title, CharSequence message) {
        return show(context, title, message, false);
    }

    public static HwProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate) {
        return show(context, title, message, indeterminate, false, null);
    }

    public static HwProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable) {
        return show(context, title, message, indeterminate, cancelable, null);
    }

    public static HwProgressDialog show(Context context, CharSequence title, CharSequence message, boolean indeterminate, boolean cancelable, DialogInterface.OnCancelListener cancelListener) {
        HwProgressDialog dialog = new HwProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setIndeterminate(indeterminate);
        dialog.setCancelable(cancelable);
        dialog.setOnCancelListener(cancelListener);
        dialog.show();
        return dialog;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        View view;
        Context context = getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        Resources resources = context.getResources();
        TypedArray a = getContext().obtainStyledAttributes(null, HwWidgetUtils.getResourceDeclareStyleableIntArray("android", "AlertDialog"), resources.getIdentifier("alertDialogStyle", "attr", "android"), 0);
        if (this.mProgressStyle == 1) {
            this.mViewUpdateHandler = new Handler() {
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int progress = HwProgressDialog.this.mProgress.getProgress();
                    int max = HwProgressDialog.this.mProgress.getMax();
                    if (!HwWidgetUtils.isHwTheme(HwProgressDialog.this.getContext())) {
                        if (HwProgressDialog.this.mProgressNumberFormat != null) {
                            String format = HwProgressDialog.this.mProgressNumberFormat;
                            HwProgressDialog.this.mProgressNumber.setText(String.format(format, new Object[]{Integer.valueOf(progress), Integer.valueOf(max)}));
                        } else {
                            HwProgressDialog.this.mProgressNumber.setText("");
                        }
                    }
                    if (HwProgressDialog.this.mProgressPercentFormat != null) {
                        SpannableString tmp = new SpannableString(HwProgressDialog.this.mProgressPercentFormat.format(((double) progress) / ((double) max)));
                        tmp.setSpan(new StyleSpan(1), 0, tmp.length(), 33);
                        HwProgressDialog.this.mProgressPercent.setText(tmp);
                        return;
                    }
                    HwProgressDialog.this.mProgressPercent.setText("");
                }
            };
            if (HwWidgetUtils.isHwTheme(getContext())) {
                view = inflater.inflate(34013221, null);
                this.mMessageView = (TextView) view.findViewById(34603129);
                this.mProgress = (ProgressBar) view.findViewById(34603130);
                this.mProgressPercent = (TextView) view.findViewById(34603131);
                this.mCancel = view.findViewById(34603132);
                this.mSpace = view.findViewById(34603133);
                view.setTag(this);
            } else {
                view = inflater.inflate(a.getResourceId(HwWidgetUtils.getResourceDeclareStyleableIndex("com.android.internal", "AlertDialog_horizontalProgressLayout"), resources.getIdentifier("alert_dialog_progress", ResLoaderUtil.LAYOUT, "android")), null);
                this.mProgress = (ProgressBar) view.findViewById(resources.getIdentifier("progress", ResLoaderUtil.ID, "android"));
                this.mProgressNumber = (TextView) view.findViewById(resources.getIdentifier("progress_number", ResLoaderUtil.ID, "android"));
                this.mProgressPercent = (TextView) view.findViewById(resources.getIdentifier("progress_percent", ResLoaderUtil.ID, "android"));
            }
            setView(view);
        } else {
            View view2 = inflater.inflate(a.getResourceId(HwWidgetUtils.getResourceDeclareStyleableIndex("com.android.internal", "AlertDialog_progressLayout"), resources.getIdentifier("progress_dialog", ResLoaderUtil.LAYOUT, "android")), null);
            this.mProgress = (ProgressBar) view2.findViewById(resources.getIdentifier("progress", ResLoaderUtil.ID, "android"));
            this.mMessageView = (TextView) view2.findViewById(resources.getIdentifier("message", ResLoaderUtil.ID, "android"));
            setView(view2);
        }
        a.recycle();
        if (this.mMax > 0) {
            setMax(this.mMax);
        }
        if (this.mProgressVal > 0) {
            setProgress(this.mProgressVal);
        }
        if (this.mSecondaryProgressVal > 0) {
            setSecondaryProgress(this.mSecondaryProgressVal);
        }
        if (this.mIncrementBy > 0) {
            incrementProgressBy(this.mIncrementBy);
        }
        if (this.mIncrementSecondaryBy > 0) {
            incrementSecondaryProgressBy(this.mIncrementSecondaryBy);
        }
        if (this.mProgressDrawable != null) {
            setProgressDrawable(this.mProgressDrawable);
        }
        if (this.mIndeterminateDrawable != null) {
            setIndeterminateDrawable(this.mIndeterminateDrawable);
        }
        if (this.mMessage != null) {
            setMessage(this.mMessage);
        }
        setIndeterminate(this.mIndeterminate);
        onProgressChanged();
        super.onCreate(savedInstanceState);
    }

    public void onStart() {
        super.onStart();
        if (containsButtons()) {
            this.mSpace.setVisibility(8);
        }
        this.mHasStarted = true;
    }

    /* access modifiers changed from: protected */
    public void onStop() {
        super.onStop();
        this.mHasStarted = false;
    }

    public void setProgress(int value) {
        if (this.mHasStarted) {
            this.mProgress.setProgress(value);
            onProgressChanged();
            return;
        }
        this.mProgressVal = value;
    }

    public void setSecondaryProgress(int secondaryProgress) {
        if (this.mProgress != null) {
            this.mProgress.setSecondaryProgress(secondaryProgress);
            onProgressChanged();
            return;
        }
        this.mSecondaryProgressVal = secondaryProgress;
    }

    public int getProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getProgress();
        }
        return this.mProgressVal;
    }

    public int getSecondaryProgress() {
        if (this.mProgress != null) {
            return this.mProgress.getSecondaryProgress();
        }
        return this.mSecondaryProgressVal;
    }

    public int getMax() {
        if (this.mProgress != null) {
            return this.mProgress.getMax();
        }
        return this.mMax;
    }

    public void setMax(int max) {
        if (this.mProgress != null) {
            this.mProgress.setMax(max);
            onProgressChanged();
            return;
        }
        this.mMax = max;
    }

    public void incrementProgressBy(int diff) {
        if (this.mProgress != null) {
            this.mProgress.incrementProgressBy(diff);
            onProgressChanged();
            return;
        }
        this.mIncrementBy += diff;
    }

    public void incrementSecondaryProgressBy(int diff) {
        if (this.mProgress != null) {
            this.mProgress.incrementSecondaryProgressBy(diff);
            onProgressChanged();
            return;
        }
        this.mIncrementSecondaryBy += diff;
    }

    public void setProgressDrawable(Drawable d) {
        if (this.mProgress != null) {
            this.mProgress.setProgressDrawable(d);
        } else {
            this.mProgressDrawable = d;
        }
    }

    public void setIndeterminateDrawable(Drawable d) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminateDrawable(d);
        } else {
            this.mIndeterminateDrawable = d;
        }
    }

    public void setIndeterminate(boolean indeterminate) {
        if (this.mProgress != null) {
            this.mProgress.setIndeterminate(indeterminate);
        } else {
            this.mIndeterminate = indeterminate;
        }
    }

    public boolean isIndeterminate() {
        if (this.mProgress != null) {
            return this.mProgress.isIndeterminate();
        }
        return this.mIndeterminate;
    }

    public void setMessage(CharSequence message) {
        if (this.mProgress == null) {
            this.mMessage = message;
        } else if (this.mProgressStyle != 1) {
            this.mMessageView.setText(message);
        } else if (HwWidgetUtils.isHwTheme(getContext())) {
            this.mMessageView.setText(message);
        } else {
            super.setMessage(message);
        }
    }

    public void setProgressStyle(int style) {
        this.mProgressStyle = style;
    }

    public int getProgressStyle() {
        return this.mProgressStyle;
    }

    public View getCancelButton() {
        return this.mCancel;
    }

    public void disableCancelButton() {
        this.mCancel.setVisibility(8);
    }

    public void setProgressNumberFormat(String format) {
        this.mProgressNumberFormat = format;
        onProgressChanged();
    }

    public void setProgressPercentFormat(NumberFormat format) {
        this.mProgressPercentFormat = format;
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (this.mProgressStyle == 1 && this.mViewUpdateHandler != null && !this.mViewUpdateHandler.hasMessages(0)) {
            this.mViewUpdateHandler.sendEmptyMessage(0);
        }
    }
}
