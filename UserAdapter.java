package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.quesec.Beans.UriBean;
import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.R;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class UserAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<UserBean> list;

    public UserAdapter(Context context, ArrayList<UserBean> list)
    {
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
        convertView = inflater.inflate(R.layout.user_row,null);
        ImageView imgperson=convertView.findViewById(R.id.imgperson);
       TextView username=convertView.findViewById(R.id.username);
       TextView statustxt=convertView.findViewById(R.id.statustxt);
       TextView userid=convertView.findViewById(R.id.userid);
       UserBean userBean=(UserBean) getItem(position);


//         SharedPreferences sharedPreferences = context.getSharedPreferences("mydb",MODE_PRIVATE);
//        String id=sharedPreferences.getString("keyUserId","");
        username.setText(userBean.getName());
        userid.setText(userBean.getId());
        statustxt.setText(userBean.getStatus());
        if(userBean.getUseruri()!=null)
            Glide.with(context).load(Uri.parse(userBean.getUseruri())).override(300,240).into(imgperson);

        return convertView;
    }
}
