package com.example.hp.quesec.Activities;


import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.hp.quesec.Beans.QuestionBean;
import com.example.hp.quesec.Beans.UserBean;
import com.example.hp.quesec.Fragments.AllQuestionFragment;
import com.example.hp.quesec.Fragments.AskQuestionsFragment;
import com.example.hp.quesec.Fragments.BookmarkFragment;
import com.example.hp.quesec.Fragments.ChatFragment;
import com.example.hp.quesec.Fragments.MyProfileFragment;
import com.example.hp.quesec.Fragments.MyQuestionFragment;
import com.example.hp.quesec.Fragments.RateFragment;
import com.example.hp.quesec.Fragments.SingleQuestionFragment;
import com.example.hp.quesec.Fragments.UnansweredFragment;
import com.example.hp.quesec.Fragments.UserListFragment;
import com.example.hp.quesec.MainActivity;
import com.example.hp.quesec.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,AskQuestionsFragment.OnFragmentInteractionListener,
        AllQuestionFragment.OnFragmentInteractionListener,SingleQuestionFragment.OnFragmentInteractionListener,
        UnansweredFragment.OnFragmentInteractionListener,UserListFragment.OnFragmentInteractionListener,
        ChatFragment.OnFragmentInteractionListener,MyQuestionFragment.OnFragmentInteractionListener,
        RateFragment.OnFragmentInteractionListener,MyProfileFragment.OnFragmentInteractionListener,
        BookmarkFragment.OnFragmentInteractionListener

{
    Bundle bundle;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView dashtextname,textView;
    private String categoryid,categoryname,spcategoryid,spcategoryname,id;
    private TextView quote,quotetxt;
   private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                        .setAction("Action", null).show();
//
//                dispatchFrag(new AskQuestionsFragment());
//            }
//        });

        sharedPreferences = getSharedPreferences("mydb",MODE_PRIVATE);
        String email = sharedPreferences.getString("keyemail","");
        String pass = sharedPreferences.getString("keypass","");
          id=sharedPreferences.getString("keyUserId","");

        if (email.isEmpty() || pass.isEmpty())
        {
            startActivity(new Intent(getBaseContext(), MainActivity.class));
        }

      quote=findViewById(R.id.quote);

        quotetxt=findViewById(R.id.quotetxt);



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerview = navigationView.getHeaderView(0);
        dashtextname=headerview.findViewById(R.id.dashtextname);
        textView=headerview.findViewById(R.id.textView);
        imageView=headerview.findViewById(R.id.imageView);
       // dashtextname.setText(name);
      //  textView.setText(email);
        UserBean userBean=new UserBean();





        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Users");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                UserBean ub=dataSnapshot.getValue(UserBean.class);
                if(ub.getId().equals(id) && ub.getUseruri()!=null)
                {
                    dashtextname.setText(ub.getName());
                    textView.setText(ub.getStatus());
                    Glide.with(getBaseContext()).load(Uri.parse(ub.getUseruri())).into(imageView);
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



        Intent it=getIntent();
        categoryid=it.getStringExtra("keycategoryIda");
        categoryname=it.getStringExtra("keycategoryNamea");

        spcategoryid=sharedPreferences.getString("keycategoryIda","");
        spcategoryname=sharedPreferences.getString("keycategoryNamea","");

       /* Toast.makeText(Home.this,"categoryid:"+categoryid,Toast.LENGTH_LONG).show();
        Toast.makeText(Home.this,"categoryname:"+categoryname,Toast.LENGTH_LONG).show();
        Toast.makeText(Home.this,"categoryid:"+spcategoryid,Toast.LENGTH_LONG).show();
        Toast.makeText(Home.this,"categoryname:"+spcategoryname,Toast.LENGTH_LONG).show();
*/
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIdasp",spcategoryid);
        editor.putString("keycategoryNameasp",spcategoryname);
        editor.commit();

        dispatchFrag(new AllQuestionFragment());

        Calendar cal = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE MMM dd yyyy");
        //Toast.makeText(this, dateFormat.format(cal.getTime()), Toast.LENGTH_SHORT).show();

        if(dateFormat.format(cal.getTime()).equals("Thu Aug 23 2018"))
        {
            quotetxt.setText("It's not about perfect. It's about effort. And when you bring that effort every single day, that's where transformation happens. That's how change occurs.");
        }

        else if(dateFormat.format(cal.getTime()).equals("Fri Aug 24 2018"))

        {
            quotetxt.setText("Take care of your body. It's the only place you have to live.");
        }


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            startActivity(new Intent(Home.this,Categories.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id == R.id.askqusetion)
        {
            bundle=new Bundle();
            bundle.putString("keyquestype","ask");
            dispatchFrag(new AskQuestionsFragment());

        }

       else if (id == R.id.allquestions) {
            // Handle the camera action
            bundle = new Bundle();
            bundle.putString("keyquestype","all");
            dispatchFrag(new AllQuestionFragment());

        } else if (id == R.id.myquestions) {
            bundle = new Bundle();
            bundle.putString("keyquestype","my");
            dispatchFrag(new MyQuestionFragment());

        } else if (id == R.id.unanswered) {
            dispatchFrag(new UnansweredFragment());

        } else if (id == R.id.myprofile) {
            dispatchFrag(new MyProfileFragment());

        }
        else if (id == R.id.rate)
        {

     dispatchFrag(new RateFragment());
        }
        else if (id == R.id.logout)
        {
        startActivity(new Intent(Home.this,LogIn.class));

        }
        else if (id == R.id.users) {
            dispatchFrag(new UserListFragment());

        }
        else if(id==R.id.bookmark)
        {
            dispatchFrag(new BookmarkFragment());
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void dispatchFrag(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.homeframe,fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
