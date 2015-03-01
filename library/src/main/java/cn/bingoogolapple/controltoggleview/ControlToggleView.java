package cn.bingoogolapple.controltoggleview;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.widget.TextView;

public class ControlToggleView extends TextView {
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_CHECKED = "status_checked";
    private int mCheckedBackgroundResId;
    private int mUnCheckedBackgroundResId;
    private ColorStateList mCheckedColorStateList;
    private ColorStateList mUnCheckedColorStateList;
    private boolean mChecked = false;
    private BeforeControlToggleViewChangeListener mBeforeControlToggleViewChangeListener;

    public ControlToggleView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ControlToggleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs);
        setChecked(mChecked);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlToggleView);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    public void initAttr(int attr, TypedArray typedArray) {
        if (attr == R.styleable.ControlToggleView_ctv_checkedBackground) {
            mCheckedBackgroundResId = typedArray.getResourceId(attr, android.R.color.white);
        } else if (attr == R.styleable.ControlToggleView_ctv_uncheckedBackground) {
            mUnCheckedBackgroundResId = typedArray.getResourceId(attr, android.R.color.white);
        } else if (attr == R.styleable.ControlToggleView_ctv_checked) {
            mChecked = typedArray.getBoolean(attr, false);
        } else if (attr == R.styleable.ControlToggleView_ctv_checkedTextColor) {
            mCheckedColorStateList = typedArray.getColorStateList(attr);
        } else if (attr == R.styleable.ControlToggleView_ctv_uncheckedTextColor) {
            mUnCheckedColorStateList = typedArray.getColorStateList(attr);
        }
    }

    public void setChecked(boolean checked) {
        if (checked) {
            mChecked = true;
            setBackgroundResource(mCheckedBackgroundResId);
            if (mCheckedColorStateList != null) {
                setTextColor(mCheckedColorStateList);
            }
        } else {
            mChecked = false;
            setBackgroundResource(mUnCheckedBackgroundResId);
            if (mUnCheckedColorStateList != null) {
                setTextColor(mUnCheckedColorStateList);
            }
        }
    }

    public void toggle() {
        setChecked(!mChecked);
    }

    @Override
    public boolean performClick() {
        if (mBeforeControlToggleViewChangeListener != null) {
            if (mChecked) {
                mBeforeControlToggleViewChangeListener.controlToggleViewBeforeUnChecked(this);
            } else {
                mBeforeControlToggleViewChangeListener.controlToggleViewBeforeChecked(this);
            }
        }
        return super.performClick();
    }

    public boolean isChecked() {
        return mChecked;
    }

    public void setBackgroundResIds(int checkedBackgroundResId, int unCheckedBackgroundResId) {
        mCheckedBackgroundResId = checkedBackgroundResId;
        mUnCheckedBackgroundResId = unCheckedBackgroundResId;
        setChecked(mChecked);
    }

    public void setTextColorResIds(int checkedTextColorResId, int unCheckedTextColorResId) {
        mCheckedColorStateList = getResources().getColorStateList(checkedTextColorResId);
        mUnCheckedColorStateList = getResources().getColorStateList(unCheckedTextColorResId);
        setChecked(mChecked);
    }

    public void setBeforeChangeListener(BeforeControlToggleViewChangeListener beforeControlToggleViewChangeListener) {
        mBeforeControlToggleViewChangeListener = beforeControlToggleViewChangeListener;
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putBoolean(STATUS_CHECKED, mChecked);
        return bundle;
    }


    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            setChecked(bundle.getBoolean(STATUS_CHECKED));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
        } else {
            super.onRestoreInstanceState(state);
        }
    }


    public static interface BeforeControlToggleViewChangeListener {
        public void controlToggleViewBeforeChecked(ControlToggleView controlToggleView);

        public void controlToggleViewBeforeUnChecked(ControlToggleView controlToggleView);
    }
}