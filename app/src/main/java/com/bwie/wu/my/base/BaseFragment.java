package com.bwie.wu.my.base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * @Auther: 苏青岩
 * @Date: 2019/1/7 14:48:17
 * @Description:
 */

public abstract class BaseFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(bindLayout(),container,false);
    }
    //绑定视图
    protected abstract int bindLayout();



    @Override
    public void onActivityCreated(  Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        initData();
        bindEvent();
    }


    //绑定组件
    protected abstract void initView();
    //操作数据
    protected abstract void initData();
    //设置监听
    protected abstract void bindEvent();

    protected <T extends View > T bindView(int resId){
        return (T) getView().findViewById(resId);
    }
}
