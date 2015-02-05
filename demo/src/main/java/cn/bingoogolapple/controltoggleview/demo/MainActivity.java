package cn.bingoogolapple.controltoggleview.demo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import cn.bingoogolapple.controltoggleview.ControlToggleView;
import cn.bingoogolapple.loonannotation.Loon;
import cn.bingoogolapple.loonannotation.LoonLayout;
import cn.bingoogolapple.loonannotation.LoonView;

@LoonLayout(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements ControlToggleView.BeforeControlToggleViewChangeListener {
    @LoonView(R.id.ctv_main_left)
    private ControlToggleView mLeftCtv;
    @LoonView(R.id.ctv_main_center)
    private ControlToggleView mCenterCtv;
    @LoonView(R.id.ctv_main_right)
    private ControlToggleView mRightCtv;
    @LoonView(R.id.ctv_main_apple)
    private ControlToggleView mAppleCtv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Loon.injectView2Activity(this);

        mCenterCtv.setBackgroundResIds(R.drawable.selector_center_checked, R.drawable.selector_center_unchecked);
        mRightCtv.setTextColorResIds(R.color.selector_checked, R.color.selector_unchecked);

        setListener();
    }

    private void setListener() {
        mLeftCtv.setBeforeChangeListener(this);
        mCenterCtv.setBeforeChangeListener(this);
        mRightCtv.setBeforeChangeListener(this);
        mAppleCtv.setBeforeChangeListener(this);
    }

    @Override
    public void beforeControlToggleViewChecked(ControlToggleView controlToggleView) {
        switch (controlToggleView.getId()) {
            case R.id.ctv_main_left:
                break;
            case R.id.ctv_main_center:
                controlToggleView.setChecked(true);
                mLeftCtv.setChecked(false);
                mRightCtv.setChecked(false);
                break;
            case R.id.ctv_main_right:
                controlToggleView.setChecked(true);
                mLeftCtv.setChecked(false);
                mCenterCtv.setChecked(false);
                break;
            case R.id.ctv_main_apple:
                controlToggleView.setChecked(true);
                break;
        }
    }

    @Override
    public void beforeControlToggleViewUnChecked(ControlToggleView controlToggleView) {
        switch (controlToggleView.getId()) {
            case R.id.ctv_main_apple:
                controlToggleView.setChecked(false);
                break;
        }
    }
}