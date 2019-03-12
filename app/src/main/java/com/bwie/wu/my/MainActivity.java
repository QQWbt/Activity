package com.bwie.wu.my;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ListView;
import android.widget.Toast;

import com.bwie.wu.my.base.BaseActivity;
import com.bwie.wu.my.sql.MyHelper;
import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;

import java.util.List;

public class MainActivity extends BaseActivity {

    private SQLiteDatabase db;
    private MyAdapter adapter;
    private PullToRefreshListView pull;
    private List<JsonBean.DataBean> list;
    private String string = "https://www.apiopen.top/novelApi";
    private Cursor query;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        pull = findViewById (R.id.pull);
        pull.setMode (PullToRefreshBase.Mode.BOTH);
        pull.setScrollingWhileRefreshingEnabled (true);

        MyHelper helper = new MyHelper(MainActivity.this);
        //获取数据库类
        db = helper.getWritableDatabase ();
    }

    @Override
    protected void initData() {
        if(HttpUtils.isNetworkConnected (MainActivity.this)){
            HttpUtils.httpAsynTask (string, new HttpUtils.CallBackString () {
                @Override
                public void getData(String s) {

                    Gson gson = new Gson();
                    JsonBean bean = gson.fromJson(s, JsonBean.class);
                    List<JsonBean.DataBean> list = bean.getData ();

                    adapter = new MyAdapter (MainActivity.this, list);

                    pull.setAdapter(adapter);

                    Toast.makeText (MainActivity.this, "ccc", Toast.LENGTH_SHORT).show ();

                    query = db.query("person", null, null, null, null, null, null);
                    //判断有没第一个数据,如果有就证明有数据
                    if (!query.moveToFirst()) {
                        ContentValues values = new ContentValues();
                        values.put("Title", s);
                        db.insert("person", null, values);
                    }

                }
            });
        }else{
            //如果没网就请求数据库
            Cursor query = db.query("person", null, null, null, null, null, null);
            //如果有第一条数据就查询数据
            if (query.moveToFirst()) {
                String s = "";
                do {
                    s = query.getString(query.getColumnIndex("Title"));
                } while (query.moveToNext());
                Gson gson = new Gson();
                JsonBean bean = gson.fromJson(s, JsonBean.class);
                List<JsonBean.DataBean> list = bean.getData ();
                adapter = new MyAdapter(MainActivity.this, list);
                pull.setAdapter(adapter);

            } else {
                Toast.makeText(MainActivity.this, "没有数据,请打开网络", Toast.LENGTH_LONG).show();
            }
            query.close();
        }
    }

    @Override
    protected void bindEvent() {
        //第五步:设置监听 (这里注意要new OnRefreshListener2  不要错了,
        // 如果只有一个方法就证明你new错了)
        pull.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新调用的方法
                if (HttpUtils.isNetworkConnected(MainActivity.this)) {
                    HttpUtils.httpAsynTask(string, new HttpUtils.CallBackString() {

                        @Override
                        public void getData(String s) {
                            Gson gson = new Gson();
                            JsonBean bean = gson.fromJson(s, JsonBean.class);
                            list = bean.getData ();

                            adapter = new MyAdapter(MainActivity.this, list);
                            pull.setAdapter(adapter);
                            pull.onRefreshComplete();
                        }

                    });
                } else {
                    Toast.makeText(MainActivity.this, "世界上最远的距离不是天涯海角,是没有网络!", Toast.LENGTH_LONG).show();
                    pull.onRefreshComplete();
                }


            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新调用的方法
                if (HttpUtils.isNetworkConnected(MainActivity.this)) {
                    HttpUtils.httpAsynTask(string, new HttpUtils.CallBackString() {

                        @Override
                        public void getData(String s) {
                            Gson gson = new Gson();
                            JsonBean bean = gson.fromJson(s, JsonBean.class);
                            List<JsonBean.DataBean> data = bean.getData();
                            list.addAll(data);
                            adapter.notifyDataSetChanged();
                            pull.onRefreshComplete();
                        }

                    });
                } else {
                    Toast.makeText(MainActivity.this, "世界上最远的距离不是天涯海角,是没有网络!", Toast.LENGTH_LONG).show();
                    pull.onRefreshComplete();
                }


            }

        });

    }
}
