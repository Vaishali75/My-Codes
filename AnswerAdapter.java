package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hp.quesec.Beans.AnswerBean;

import com.example.hp.quesec.Beans.BookmarkBean;
import com.example.hp.quesec.Beans.LikeBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.zip.Inflater;

import static android.content.Context.MODE_PRIVATE;

public class AnswerAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<AnswerBean> list;
    int count=0;
    private  AnswerBean answerBean;
private DatabaseReference databaseReference;
private String userId;


    public AnswerAdapter(Context context, ArrayList<AnswerBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() { return list.size(); }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        view= inflater.inflate(R.layout.answer_row,null);
        TextView key=(TextView)view.findViewById(R.id.answerID);
        TextView answer=(TextView) view.findViewById(R.id.answer);

        TextView postedby=(TextView) view.findViewById(R.id.postedBy);
        TextView postdate=(TextView) view.findViewById(R.id.postDate);
        final ImageView likeimg=view.findViewById(R.id.likeimg);
        TextView liketxt=view.findViewById(R.id.liketxt);

        SharedPreferences sharedPreferences = context.getSharedPreferences("mydb",MODE_PRIVATE);
        userId=sharedPreferences.getString("keyUserId","");

         answerBean=(AnswerBean) getItem(i);
        answer.setText(answerBean.getAnswer());
        key.setText(answerBean.getAnswerID());
        postedby.setText("Posted By : "+answerBean.getPostedBy());
        postdate.setText("Posted On : "+new Date(answerBean.getPostDate()));

        DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Like");
        databaseReference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                LikeBean likeBean=dataSnapshot.getValue(LikeBean.class);
                if(((AnswerBean)getItem(i)).getAnswerID().equals(likeBean.getAnsid()))
                {
                    likeimg.setImageResource(R.drawable.ic_favorite_black_24dp);
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        likeimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                if (((ImageView)v).getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.ic_favorite_black_24dp).getConstantState())
                {
                    ((ImageView)v).setImageResource(R.drawable.like);
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    databaseReference=database.getReference("Like");
                    databaseReference.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            LikeBean likeBean=dataSnapshot.getValue(LikeBean.class);

                            if((((AnswerBean) getItem(i)).getAnswerID()).equals(likeBean.getAnsid()))
                            {
                                databaseReference.child(likeBean.getLikeid()).removeValue();
                            }

                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {


                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                }
                else if (((ImageView)v).getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.like).getConstantState())
                {

                    ((ImageView)v).setImageResource(R.drawable.ic_favorite_black_24dp);

                    LikeBean likeBean = new LikeBean();
                    likeBean.setAnsid(((AnswerBean) getItem(i)).getAnswerID());
                    likeBean.setUserid(userId);

                    DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Like");
                    likeBean.setLikeid(reference.push().getKey());

                    reference.child(likeBean.getLikeid()).setValue(likeBean);



                }
            }
        });

        //liketxt.setText(count+"Likes");
        return view;
    }
}
