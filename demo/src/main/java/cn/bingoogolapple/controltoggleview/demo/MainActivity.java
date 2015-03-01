package cn.bingoogolapple.controltoggleview.demo;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import cn.bingoogolapple.bgaannotation.BGAA;
import cn.bingoogolapple.bgaannotation.BGAALayout;
import cn.bingoogolapple.bgaannotation.BGAAView;
import cn.bingoogolapple.controltoggleview.ControlToggleView;
import cn.bingoogolapple.controltoggleview.ControlToggleViewGroup;

@BGAALayout(R.layout.activity_main)
public class MainActivity extends ActionBarActivity implements ControlToggleView.BeforeControlToggleViewChangeListener, ControlToggleViewGroup.BeforeControlToggleViewChangeListener {
    @BGAAView(R.id.ctv_main_apple)
    private ControlToggleView mAppleCtv;

    @BGAAView(R.id.ctv_main_background)
    private ControlToggleView mBackgroundCtv;
    @BGAAView(R.id.ctv_main_textColor)
    private ControlToggleView mTextColorCtv;

    @BGAAView(R.id.ctvg_main_vertical)
    private ControlToggleViewGroup mVerticalCtvg;
    @BGAAView(R.id.ctvg_main_horizontal)
    private ControlToggleViewGroup mHorizontalCtvg;

    private Dialog mLoadingDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BGAA.injectView2Activity(this);

        mBackgroundCtv.setBackgroundResIds(R.drawable.selector_center_checked, R.drawable.selector_center_unchecked);
        mTextColorCtv.setTextColorResIds(R.color.selector_checked, R.color.selector_unchecked);

        setListener();
    }

    private void setListener() {
        mVerticalCtvg.setBeforeChangeListener(this);
        mHorizontalCtvg.setBeforeChangeListener(this);
        mAppleCtv.setBeforeChangeListener(this);
    }

    @Override
    public void controlToggleViewBeforeChecked(ControlToggleView controlToggleView) {
        switch (controlToggleView.getId()) {
            case R.id.ctv_main_apple:
                controlToggleView.setChecked(true);
//                controlToggleView.toggle();
                break;
        }
    }

    @Override
    public void controlToggleViewBeforeUnChecked(ControlToggleView controlToggleView) {
        switch (controlToggleView.getId()) {
            case R.id.ctv_main_apple:
                controlToggleView.setChecked(false);
                break;
        }
    }

    @Override
    public void controlToggleViewGroupCheckedChanged(final ControlToggleViewGroup controlToggleViewGroup, int checkedId) {
        switch (checkedId) {
            case R.id.ctv_main_a:
                // 根据id来选中某个选项
                controlToggleViewGroup.check(checkedId);
                Toast.makeText(this,"A",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctv_main_b:
                // ControlToggleViewGroup中已经保存了刚才点击的按钮的id，调用allowAction方法即可
                controlToggleViewGroup.allowAction();
                Toast.makeText(this,"B",Toast.LENGTH_SHORT).show();
                break;
            case R.id.ctv_main_c:
                // 不执行任何操作，那么点击C按钮后没有任何反应
                break;
            case R.id.ctv_main_d:
                controlToggleViewGroup.allowAction();
                break;
            case R.id.ctv_main_e:
                controlToggleViewGroup.allowAction();
                break;
            case R.id.ctv_main_f:
                mLoadingDialog = ProgressDialog.show(this, null, "正在执行XXX操作");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        controlToggleViewGroup.allowAction();
                        mLoadingDialog.dismiss();
                    }
                },3000);
                break;
            case R.id.ctv_main_g:
                controlToggleViewGroup.allowAction();
                break;
        }
    }
}