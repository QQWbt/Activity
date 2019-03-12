package com.bwie.wu.my;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * @Auther: 苏青岩
 * @Date: 2019/1/8 10:27:26
 * @Description:
 */
public class MyAdapter extends BaseAdapter {

    private Context context;
    private List<JsonBean.DataBean> list;

    public MyAdapter(Context context, List<JsonBean.DataBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh=null;
        if(convertView==null){
            convertView = View.inflate (context, R.layout.simple_list_item_1, null);
            vh= new ViewHolder ();
            vh.text=convertView.findViewById (R.id.text1);
            convertView.setTag (vh);
        }else{
            vh = (ViewHolder)convertView.getTag ();
        }
        vh.text.setText (list.get (position).getAuthor_name ());
        return convertView;
       /* convertView = View.inflate(context, android.R.layout.simple_list_item_1, null);
        TextView textView = convertView.findViewById(android.R.id.text1);
        textView.setText(list.get(position).getAuthor_name());
        return convertView;*/
    }
    class ViewHolder{
        TextView text;
    }
}
