package com.tp.sqlite_tp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class CreateActivity extends AppCompatActivity {
//  instance of dbHelper to perform the create
    private DbHelper dbh;
//  declaring the three fields
    EditText _prenom, _nom, _age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        dbh = new DbHelper(CreateActivity.this);
//      references
        _prenom = findViewById(R.id.EdtPrenom);
        _nom = findViewById(R.id.EdtNom);
        _age = findViewById(R.id.EdtAge);


    }
//  the create button click event
    public void DoCreate(View view) {
//      check if the fields are populated
        if (_prenom.getText().length()>0 && _nom.getText().length()>0 && Integer.parseInt( _age.getText().toString())>0){
//          calling addPerson from the database Helper (DAO) and finishing the activity to return to the mainActivity
            dbh.addPerson(_prenom.getText().toString(),_nom.getText().toString(),Integer.parseInt(_age.getText().toString()));
            finishAfterTransition();
        }
    }

}