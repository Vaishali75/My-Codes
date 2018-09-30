package com.example.hp.quesec.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.quesec.Fragments.AllQuestionFragment;
import com.example.hp.quesec.Fragments.AskQuestionsFragment;
import com.example.hp.quesec.R;

public class Categories extends AppCompatActivity {
    private TextView technology,social,economical,political,career,arts,entertainment,cultural,religious,sports;
    private CardView cardtech,cardsocial,cardeco,cardpoli,cardcareer,cardarts,cardenter,cardcul,cardreligious,cardsports;
private  String categoryId,categoryName;
private SharedPreferences sharedPreferences;
private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

technology=findViewById(R.id.technology);
social=findViewById(R.id.social);
economical=findViewById(R.id.economical);
political=findViewById(R.id.political);
career=findViewById(R.id.career);
arts=findViewById(R.id.arts);
entertainment=findViewById(R.id.entertainment);
cultural=findViewById(R.id.cultural);
religious=findViewById(R.id.religious);
sports=findViewById(R.id.sports);

cardtech=findViewById(R.id.cardtech);
cardarts=findViewById(R.id.cardarts);
cardcareer=findViewById(R.id.cardcareer);
cardeco=findViewById(R.id.cardeco);
cardenter=findViewById(R.id.cardenter);
cardreligious=findViewById(R.id.cardreligious);
cardcul=findViewById(R.id.cardcul);
cardsports=findViewById(R.id.cardsports);
cardpoli=findViewById(R.id.cardpoli);
cardsocial=findViewById(R.id.cardsocial);


cardtech.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v)
    {

//        Bundle b=new Bundle();
//        b.putString("keycategoryId","techid");
//        b.putString("keycategoryName","Technology");
//        AllQuestionFragment allQuestionFragment=new AllQuestionFragment();
//        allQuestionFragment.setArguments(b);
//        AskQuestionsFragment askQuestionsFragment =new AskQuestionsFragment();

        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","techid");
        editor.putString("keycategoryNamea","Technology");
        editor.commit();

    Intent it=new Intent(Categories.this,Home.class);
    it.putExtra("keycategoryIda","techid");
    it.putExtra("keycategoryNamea","Technology");
    startActivity(it);


    }
});

cardsocial.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

//        Bundle b=new Bundle();
//        b.putString("keycategoryId","socialid");
//        b.putString("keycategoryName","Social");

        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","socialid");
        editor.putString("keycategoryNamea","Social");
        editor.commit();
        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","socialid");
        it.putExtra("keycategoryNamea","Social");
        startActivity(it);


    }
});

cardpoli.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

//        Bundle b=new Bundle();
//        b.putString("keycategoryId","poliid");
//        b.putString("keycategoryName","Political");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","poliid");
        editor.putString("keycategoryNamea","Political");
        editor.commit();

        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","poliid");
        it.putExtra("keycategoryNamea","Political");
        startActivity(it);

    }
});

cardsports.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","sportsid");
//        b.putString("keycategoryName","Sports");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","sportsid");
        editor.putString("keycategoryNamea","Sports");
        editor.commit();
        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","sportsid");
        it.putExtra("keycategoryNamea","Sports");
        startActivity(it);
    }
});

cardcul.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","culid");
//        b.putString("keycategoryName","Cultural");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","culid");
        editor.putString("keycategoryNamea","Cultural");
        editor.commit();
        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","culid");
        it.putExtra("keycategoryNamea","Cultural");
        startActivity(it);
    }
});

cardreligious.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","religiousid");
//        b.putString("keycategoryName","Religious");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","religiousid");
        editor.putString("keycategoryNamea","Religious");
        editor.commit();

        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","religiousid");
        it.putExtra("keycategoryNamea","Religious");
        startActivity(it);
    }
});

cardenter.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","enterid");
//        b.putString("keycategoryName","Entertainment");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","enterid");
        editor.putString("keycategoryNamea","Entertainment");
        editor.commit();

        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","enterid");
        it.putExtra("keycategoryNamea","Entertainment");
        startActivity(it);
    }
});

cardeco.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","ecoid");
//        b.putString("keycategoryName","Economical");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","ecoid");
        editor.putString("keycategoryNamea","Economical");
        editor.commit();

        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","ecoid");
        it.putExtra("keycategoryNamea","Economical");
        startActivity(it);
    }
});
cardcareer.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","careerid");
//        b.putString("keycategoryName","Career");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","careerid");
        editor.putString("keycategoryNamea","Career");
        editor.commit();
        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","careerid");
        it.putExtra("keycategoryNamea","Career");
        //Toast.makeText(Categories.this,"career",)
        startActivity(it);
    }
});

cardarts.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
//        Bundle b=new Bundle();
//        b.putString("keycategoryId","artsid");
//        b.putString("keycategoryName","Arts");
        editor=getSharedPreferences("mydb",MODE_PRIVATE).edit();
        editor.putString("keycategoryIda","artsid");
        editor.putString("keycategoryNamea","Arts");
        editor.commit();

        Intent it=new Intent(Categories.this,Home.class);
        it.putExtra("keycategoryIda","artsid");
        it.putExtra("keycategoryNamea","Arts");
        startActivity(it);
    }
});

    }
}
