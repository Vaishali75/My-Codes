package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.R;

import java.util.ArrayList;

public class QueImgAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<QuestionBean> list;

    public QueImgAdapter(Context context, ArrayList<QuestionBean> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =inflater.inflate(R.layout.que_img_row,null);
        ImageView queimg=convertView.findViewById(R.id.queimg);


        return convertView;
    }
}
