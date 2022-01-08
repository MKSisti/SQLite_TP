package com.tp.sqlite_tp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// DBHelper(DAO)
public class DbHelper extends SQLiteOpenHelper {

// the context to show toasts
    private Context context;
//  specifying the database name and the version
    private static final String DATABASE_NAME = "PeopleManagerDB";
    private static final int DATABASE_VERSION = 1;

//  since we are going to use these names a lot I generated fields for the names
    private final String TABLE_NAME = "people";
    private final String COLUMN_ID = "_id";
    private final String COLUMN_NOM = "nom";
    private final String COLUMN_PRENOM = "prenom";
    private final String COLUMN_AGE = "age";

//  constructor
    public DbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

//  on create generates our tables the first time
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
//      simple sql query to create a people table for us
        String query = "CREATE TABLE " + TABLE_NAME + "\n" +
                "(" +   COLUMN_ID + " varchar NOT NULL PRIMARY KEY,\n" +
                        COLUMN_NOM + " varchar NOT NULL,\n" +
                        COLUMN_PRENOM + " varchar NOT NULL,\n" +
                        COLUMN_AGE + " int NOT NULL);";
//      callign execSQL to execute the sql query
        sqLiteDatabase.execSQL(query);
    }

//  on upgrade is run when the database version changes to update the schema
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
//      we can for example drop the tables and call OnCreate to generate new ones
//      assuming that when we change the database version we did perform some changes on the tables
        String query = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";
        sqLiteDatabase.execSQL(query);
        onCreate(sqLiteDatabase);
    }

//  CRUD operations for Person:
//  Create :
    public void addPerson(String prenom,String nom, int age){
//      get a reference to the SQLite db, Writable because we want to perform an insert
        SQLiteDatabase db = this.getWritableDatabase();
//      content values is like bundle for intent, like an associative array to hold our field values
        ContentValues cv = new ContentValues();
//      I'm using a UUID instead of a simple int here, calling randomUUID() to get a random universal unique id v4
        cv.put(COLUMN_ID,UUID.randomUUID().toString());
//      setting the rest of the parameters passed to the method
        cv.put(COLUMN_PRENOM,prenom);
        cv.put(COLUMN_NOM,nom);
        cv.put(COLUMN_AGE,age);
//      performing the insertion by calling insert and passing the table name a null for nullColumnHack and then finally the contentValues
        long res = db.insert(TABLE_NAME,null,cv);
//      if the insertion was successful we would get a positive number returned, the if statement would check that result and show a toast to the user accordingly
        if(res == -1){
            Toast.makeText(context,"Error Inserting",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Inserted One New Record",Toast.LENGTH_LONG).show();
        }
    }
//  Update :
    public void updatePerson(PersonModel p){
//      get a reference to the SQLite db, Writable because we want to perform an update which also requires a write instance
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//      only updating the visible fields to the user since the id is something the user shouldn't see
        cv.put(COLUMN_PRENOM,p.getPrenom());
        cv.put(COLUMN_NOM,p.getNom());
        cv.put(COLUMN_AGE,p.getAge());
/*
      calling update method and passing in the table name and the content values,
      then specifying the whereClause as _id=? this means that we wanna perform a where clause on the id field and we wanna match it against a dynamic parameter
      the last param is the id we want to match against read from the passed in PersonMode
*/
        long res = db.update(TABLE_NAME,cv,"_id=?",new String[]{p.getId()});
        if(res == -1){
            Toast.makeText(context,"Error Updating",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Updated a Record",Toast.LENGTH_LONG).show();
        }

    }
//  Delete :
    public void deletePerson(String id){
//      same as before we want to change the content of the database so we need a writable instance of out database
        SQLiteDatabase db = this.getWritableDatabase();
//      calling delete method and passing the table name we wanna delete from and specifying the where clause
        long res = db.delete(TABLE_NAME, "_id=?", new String[]{id});

        if (res == -1){
            Toast.makeText(context,"Error Deleting",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(context,"Deleted a Record",Toast.LENGTH_LONG).show();
        }
    }
//  Read :
    public List<PersonModel> getAll(){
//      creating the select all query
        String query = "SELECT * FROM " + TABLE_NAME ;
//      we only need a readable instance of our database since we only want to select
        SQLiteDatabase db = this.getReadableDatabase();
//      creating a cursor just like in standard jdbc, we will use this cursor to loop through the results
        Cursor c ;
//      creating the list we will be returning
        List<PersonModel> lp = new ArrayList<>();
        try {
//          calling rawQuery and passing the query we created to select all, and null for the selection args to get all
            c = db.rawQuery(query,null);

//          checking if we got any rows
            if (c.getCount()>0){
//              if we did get some rows we perform a while loop to go through
//              all the rows and create a new instance of PersonModel to push in our list created above
                while(c.moveToNext()){
                    lp.add(new PersonModel(c.getString(0),c.getString(2),c.getString(1),c.getInt(3)));
                }
            }
        }catch (Exception e){
            throw e;
        }

        return lp;
    }
}
