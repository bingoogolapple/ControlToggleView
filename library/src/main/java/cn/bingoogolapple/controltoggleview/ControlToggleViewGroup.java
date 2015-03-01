package cn.bingoogolapple.controltoggleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.HashSet;
import java.util.Set;

public class ControlToggleViewGroup extends LinearLayout implements ControlToggleView.BeforeControlToggleViewChangeListener {
    private static final String INSTANCE_STATUS = "instance_status";
    private static final String STATUS_CHECKEDID = "status_checkedId";

    private int mCheckedId = -1;
    /**
     * 保存用户点击后，将要选中的id
     */
    private int mNextCheckedId = mCheckedId;
    private BeforeControlToggleViewChangeListener mBeforeControlToggleViewChangeListener;
    private Set<ControlToggleView> mControlToggleViews = new HashSet<ControlToggleView>();

    public ControlToggleViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ControlToggleViewGroup);
        final int N = typedArray.getIndexCount();
        for (int i = 0; i < N; i++) {
            initAttr(typedArray.getIndex(i), typedArray);
        }
        typedArray.recycle();
    }

    public void initAttr(int attr, TypedArray typedArray) {
        if(attr == R.styleable.ControlToggleViewGroup_ctvg_checkedId) {
            mCheckedId = typedArray.getResourceId(attr, mCheckedId);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        // 由于在构造方法中获取子孩子个数，所以移到这里初始化子孩子
        initControlToggleViews(this);
        check(mCheckedId);
    }

    private void initControlToggleViews(ViewGroup viewGroup) {
        View child;
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            child = viewGroup.getChildAt(i);
            if(child instanceof ViewGroup) {
                initControlToggleViews((ViewGroup) child);
            } else if (child instanceof ControlToggleView) {
                ((ControlToggleView) child).setBeforeChangeListener(this);
                mControlToggleViews.add((ControlToggleView) child);
            }
        }
    }

    public void check(int checkedId) {
        for (ControlToggleView controlToggleView : mControlToggleViews) {
            if (controlToggleView.getId() == checkedId) {
                mCheckedId = checkedId;
                controlToggleView.setChecked(true);
            } else {
                controlToggleView.setChecked(false);
            }
        }
    }

    public void allowAction(){
        check(mNextCheckedId);
    }

    public void setBeforeChangeListener(BeforeControlToggleViewChangeListener beforeControlToggleViewChangeListener) {
        mBeforeControlToggleViewChangeListener = beforeControlToggleViewChangeListener;
    }

    @Override
    public void controlToggleViewBeforeChecked(ControlToggleView controlToggleView) {
        mNextCheckedId = controlToggleView.getId();
        if (mBeforeControlToggleViewChangeListener != null && mNextCheckedId != mCheckedId) {
            mBeforeControlToggleViewChangeListener.controlToggleViewGroupCheckedChanged(this, controlToggleView.getId());
        }
    }

    @Override
    public void controlToggleViewBeforeUnChecked(ControlToggleView controlToggleView) {
    }

    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putParcelable(INSTANCE_STATUS, super.onSaveInstanceState());
        bundle.putInt(STATUS_CHECKEDID, mCheckedId);
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;
            check(bundle.getInt(STATUS_CHECKEDID));
            super.onRestoreInstanceState(bundle.getParcelable(INSTANCE_STATUS));
        } else {
            super.onRestoreInstanceState(state);
        }
    }

    public static interface BeforeControlToggleViewChangeListener {
        public void controlToggleViewGroupCheckedChanged(ControlToggleViewGroup controlToggleViewGroup, int checkedId);
    }
}