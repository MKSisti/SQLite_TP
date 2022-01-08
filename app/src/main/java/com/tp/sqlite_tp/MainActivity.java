package com.tp.sqlite_tp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {
//  an instance of the DAO
    DbHelper dbh;

    List<PersonModel> pl;

    RecyclerView recyclerView;
    FloatingActionButton add_btn;
    TextView hiddenText;

    PersonAdapter personAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//      instantiating the databaseHelper
        dbh = new DbHelper(MainActivity.this);
//      calling getAll to select *
        pl = dbh.getAll();


//      references
        recyclerView = findViewById(R.id.RecycleView);
        add_btn = findViewById(R.id.Add_btn);
        hiddenText = findViewById(R.id.hiddenTxt);

//      Custom Person Adapter
        personAdapter = new PersonAdapter(MainActivity.this,pl);
        recyclerView.setAdapter(personAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
//      if no records in db display a message
        if (pl.size()==0){
            hiddenText.setVisibility(View.VISIBLE);
        }
    }

//  calling recreate() on restart to update the list on the main activity when we come back from the update Activity or create Activity
    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }
//  button click event
    public void OpenCreate(View view) {
//      start the create activity
        Intent intent = new Intent(MainActivity.this,CreateActivity.class);
        startActivity(intent);

    }
}