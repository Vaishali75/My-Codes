package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.hp.quesec.Beans.ChatBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.UriBean;
import com.example.hp.quesec.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UriAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<Uri> list;

    public UriAdapter(Context context, ArrayList<Uri> list)
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
        convertView = inflater.inflate(R.layout.img_row,null);
       ImageView imageView=convertView.findViewById(R.id.imageView);

        Uri uri = (Uri) getItem(position);

        Glide.with(context).load(uri).override(300,240).into(imageView);

       // Glide.with(context).load(Uri.parse(uriBean.getUri())).override(300,240).into(imageView);
//        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("UriBean");
//        reference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                QuestionBean questionBean=dataSnapshot.getValue(QuestionBean.class);
//
//                if(questionBean.getUri()!=null && questionBean.getUri().equals(((QuestionBean) getItem(i)).getUri())) {
//                    Log.d("uri",((QuestionBean) getItem(i)).getUri());
//
//                    //imgquechk.setImageURI(Uri.parse(questionBean.getUri()));
//                    //imgquechk.setImageResource(R.drawable.blackbookmark);
//                    //imgquechk.setImageURI(Uri.parse("https://firebasestorage.googleapis.com/v0/b/quesec-d6d33.appspot.com/o/SQuestionUploads1535001437063.jpg?alt=media&token=48b040d2-a7df-4aa6-8e71-18805c51db55"));
//
//                    //imgquechk.setImageURI(Uri.parse(questionBean.getUri()));
//
//
//                    Log.d("uri", Uri.parse(questionBean.getUri())+"");
//
//                    Glide.with(context).load(Uri.parse(questionBean.getUri())).override(300,240).into(imgquechk);
//                }
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



        return convertView;
    }
}
