 package com.tp.sqlite_tp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;


 public class UpdateActivity extends AppCompatActivity {
//  instance of dbHelper to perform the update and delete
     private DbHelper dbh;
//  declaring the three fields and a PersonModel for later use
     EditText updtPrenom,updtNom,updtAge;
     PersonModel person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

//        refs
        updtPrenom = findViewById(R.id.updtPrenom);
        updtNom = findViewById(R.id.updtNom);
        updtAge = findViewById(R.id.updtAge);

//      reading data passed from the parent activity and assigning it to the corresponding fields
        try {
            person = (PersonModel) getIntent().getSerializableExtra("person");
            updtPrenom.setText(person.getPrenom());
            updtNom.setText(person.getNom());
            updtAge.setText(String.valueOf(person.getAge()));

        }catch (Exception e){
            Log.e("Update Activity",e.getMessage());
        }
//      instantiating the database helper
        dbh = new DbHelper(UpdateActivity.this);



    }
//  update button click event
     public void DoUpdate(View view) {
//       checking the fields for valid data
         if (updtAge.getText().length()>0&&updtNom.getText().length()>0&&updtPrenom.getText().length()>0){
             try {
//               calling updatePerson and passing a personModel object created from the data in the fields
//               ( note that the id is coming from the intent since it is not updatable )
                 dbh.updatePerson(new PersonModel(person.getId(), updtPrenom.getText().toString(),
                         updtNom.getText().toString(), Integer.parseInt(updtAge.getText().toString())));
             }catch (Exception e){
                 Log.e("Update act ",e.getMessage());
             }finally {
//               we call finish and go back to the main activity
                 finishAfterTransition();
             }

         }
     }

//  delete button click event calls openDialog
     public void DoDelete(View view) {
        OpenDialog();
     }
//  opens a dialog concerning the deletion
     private void OpenDialog(){
//       creating the alert dialog
         AlertDialog.Builder builder = new AlertDialog.Builder(this);
         builder.setTitle("Deleting "+person.getNom());
         builder.setMessage("Are you sure you want to delete ??");
         builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
//           the confirmation click event calls deletePerson and finishes the activity
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {
                 dbh.deletePerson(person.getId());
                 finish();
             }
         });
         builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
//           the cancel click event does nothing, it only closes the dialog
             @Override
             public void onClick(DialogInterface dialogInterface, int i) {

             }
         });
         builder.create().show();

     }
 }