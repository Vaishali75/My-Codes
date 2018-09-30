package com.example.hp.quesec.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.quesec.Activities.LogIn;
import com.example.hp.quesec.Beans.AnswerBean;
import com.example.hp.quesec.Beans.BookmarkBean;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.imgclass;
import com.example.hp.quesec.Fragments.AllQuestionFragment;
import com.example.hp.quesec.Fragments.BookmarkFragment;
import com.example.hp.quesec.MainActivity;
import com.example.hp.quesec.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class QuestionAdapter extends BaseAdapter
{
    private Context context ;
    private ArrayList<QuestionBean> list;
    private QuestionBean q;
   // private ArrayList<QuestionBean> list1;
    private ImageView imgquechk;
    private String fetchuri,userId;
    private DatabaseReference databaseReference;
    private String countkey;
    private ArrayList<String> listbmk;
    public QuestionAdapter(Context context, ArrayList<QuestionBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount()
    {return list.size();}

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
        LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        view = inflater.inflate(R.layout.question_row,null);
        TextView key=(TextView)view.findViewById(R.id.questionID);
        TextView title=(TextView) view.findViewById(R.id.questionTitle);
        TextView description=(TextView) view.findViewById(R.id.questionDescription);
        TextView postedby=(TextView) view.findViewById(R.id.postedBy);
        TextView postdate=(TextView) view.findViewById(R.id.postDate);
        TextView counttxt=view.findViewById(R.id.counttxt);
        final LinearLayout queimglayout=view.findViewById(R.id.queimglayout);
        final ImageView queimg=view.findViewById(R.id.queimg);

        final ImageView bookmark=view.findViewById(R.id.bookmark);
        imgquechk=view.findViewById(R.id.imgquechk);
        q=(QuestionBean) getItem(i);
        title.setText(q.getTitle());
        key.setText(q.getQueId());
        description.setText(q.getDescription());
        postedby.setText("Posted By : "+q.getUsername());
        postdate.setText("Posted On : "+q.getPostdate());
        SharedPreferences sharedPreferences = context.getSharedPreferences("mydb",MODE_PRIVATE);
        userId=sharedPreferences.getString("keyUserId","");

        counttxt.setText(((QuestionBean) getItem(i)).getAnscount()+"");


        final String queId=q.getQueId();
        final String userid=q.getUserId();
        final String username=q.getUsername();
        final String categoryid=q.getCategoryId();
        final String categoryname=q.getCategoryname();
        final String descriptiondb=q.getDescription();
        final String titledb=q.getTitle();
        final Date postdatedb=q.getPostdate();
       // fetchuri=q.getUri();

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Questions");
       reference.addChildEventListener(new ChildEventListener() {
           @Override
           public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
               QuestionBean questionBean=dataSnapshot.getValue(QuestionBean.class);

               if(questionBean.getUri()!=null && questionBean.getUri().equals(((QuestionBean) getItem(i)).getUri())) {

                   for(String uri1 : questionBean.getUri())
                   {
                       ImageView image=new ImageView(context);

                       image.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
                       image.setMaxHeight(20);
                       image.setMaxWidth(20);
                       Glide.with(context).load(uri1).into(image);
                       queimglayout.addView(image);
                   }

//                   Glide.with(context).load(Uri.parse(questionBean.getUri())).override(300,240).into(imgquechk);
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


      final DatabaseReference databaseReference1=FirebaseDatabase.getInstance().getReference("Questions");
      databaseReference1.addChildEventListener(new ChildEventListener() {
          @Override
          public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
              QuestionBean qubean=dataSnapshot.getValue(QuestionBean.class);
//              if(((QuestionBean)getItem(i)).getQueId().equals(bookmarkBean.getQueID()))
//              {
//                  bookmark.setImageResource(R.drawable.blackbookmark);
//              }
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

       bookmark.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v)
           {


               if (((ImageView)v).getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.blackbookmark).getConstantState())
               {
                   ((ImageView)v).setImageResource(R.drawable.uncolouredbookmark);

                   Toast.makeText(context, "firebase delete code", Toast.LENGTH_SHORT).show();

                   DatabaseReference dref1=FirebaseDatabase.getInstance().getReference("Questions");
                   dref1.addValueEventListener(new ValueEventListener() {
                       @Override
                       public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           QuestionBean questionBean=dataSnapshot.getValue(QuestionBean.class);
                           QuestionBean qb=new QuestionBean();

                           if((((QuestionBean) getItem(i)).getQueId()).equals(questionBean.getQueId()) && questionBean.getBmk()!=null )
                           {
                               // Toast.makeText(context, "delete called...", Toast.LENGTH_SHORT).show();
                               ArrayList<String> delete= questionBean.getBmk();
                               Iterator itr=delete.iterator();
                               while(itr.hasNext())
                               {
                                   if(userId.equals(itr.next()))
                                   {
                                       Toast.makeText(context, "true", Toast.LENGTH_SHORT).show();
                                       itr.remove();
                                   }
                               }




                               qb.setTitle(questionBean.getTitle());
                               qb.setAnscount(questionBean.getAnscount());
                               qb.setUserId(questionBean.getUserId());
                               qb.setDescription(questionBean.getDescription());
                               qb.setCategoryId(questionBean.getCategoryId());
                               qb.setCategoryname(questionBean.getCategoryname());
                               qb.setPostdate(questionBean.getPostdate());
                               qb.setUsername(questionBean.getUsername());
                               qb.setUri(questionBean.getUri());
                               qb.setQueId(questionBean.getQueId());
                               qb.setBmk(delete);
                               DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions").child(questionBean.getQueId());
                               reference.setValue(qb);

                           }
                       }

                       @Override
                       public void onCancelled(@NonNull DatabaseError databaseError) {

                       }
                   });
               }
               else if (((ImageView)v).getDrawable().getConstantState() == context.getResources().getDrawable(R.drawable.uncolouredbookmark).getConstantState())
               {
                  // Log.d("qid",((QuestionBean) getItem(i)).getTitle());

                   ((ImageView)v).setImageResource(R.drawable.blackbookmark);

                   Toast.makeText(context, "add bookmark", Toast.LENGTH_SHORT).show();



//                   if (qb.getBmk().add(userId)) {
//                       qb.setBmk(q.getBmk());
//                   }




                   DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Questions");
                   ref.addChildEventListener(new ChildEventListener() {
                       @Override
                       public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                           QuestionBean quesbean=dataSnapshot.getValue(QuestionBean.class);
                           if(quesbean.getQueId().equals(((QuestionBean) getItem(i)).getQueId()))
                           {

                               QuestionBean qb=new QuestionBean();


                               qb.setTitle(quesbean.getTitle());
                               qb.setAnscount(quesbean.getAnscount());
                               qb.setUserId(quesbean.getUserId());
                               qb.setDescription(quesbean.getDescription());
                               qb.setCategoryId(quesbean.getCategoryId());
                               qb.setCategoryname(quesbean.getCategoryname());
                               qb.setPostdate(quesbean.getPostdate());
                               qb.setUsername(quesbean.getUsername());
                               qb.setUri(quesbean.getUri());
                               qb.setQueId(quesbean.getQueId());


                               ArrayList<String> l;
                               l = quesbean.getBmk();

                               if (l!=null)
                               {
                                   if (l.add(userId))
                                   {
                                       qb.setBmk(l);
                                       DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions").child(quesbean.getQueId());
                                       reference.setValue(qb);

                                   }

                               }
                               else
                               {
                                   l = new ArrayList<>();
                                   if (l.add(userId))
                                   {
                                       qb.setBmk(l);
                                       DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions").child(quesbean.getQueId());
                                       reference.setValue(qb);

                                   }

                               }



                       /*if (l.add(userId))
                       {
                       qb.setBmk(l);
                           DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Questions").child(qb.getQueId());
                           reference.setValue(qb);

                       }*/





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
           }
       });

        return view;
    }
}
