package com.bwie.wu.my.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;

public abstract class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(bindLayout());
        initView();//先找控件
        initData();//给控件设置数据(位置不要写反了!!!)
        bindEvent();

    }

    //绑定布局的方法
    public abstract int bindLayout();

    //绑定组件的方法
    protected abstract void initView();

    //操作数据的方法(请求网络)
    protected abstract void initData();

    //设置监听的方法
    protected abstract void bindEvent();

    //找id 的
//    protected <T extends View> T bindView(int resId) {
//        return (T) findViewById(resId);
//    }
    //生命周期

    protected <T extends View> T bindView(int resid) {
        return findViewById(resid);
    }
}