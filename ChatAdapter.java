package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Beans.ChatBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.R;

import java.util.ArrayList;
import java.util.Date;

public class ChatAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<ChatBean> list;

    public ChatAdapter(Context context, ArrayList<ChatBean> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.chat_row,null);
        TextView msg=(TextView)convertView.findViewById(R.id.msgtxtrow);

        ChatBean chatBean=(ChatBean) getItem(position);
        msg.setText(chatBean.getChatmsg());

        LinearLayout ll = (LinearLayout) convertView.findViewById(R.id.ll);

        String senderid = context.getSharedPreferences("mydb",Context.MODE_PRIVATE).getString("keyUserId","");

        if (senderid.equals(list.get(position).getSenderId()))
        {
            ll.setGravity(Gravity.RIGHT);
        }
        else
        {
            ll.setGravity(Gravity.LEFT);
        }


        return convertView;


    }
}
