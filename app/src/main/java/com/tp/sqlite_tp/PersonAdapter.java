package com.tp.sqlite_tp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//  a custom adapter to display person data in the list
public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.CustomViewHolder> {
//  the context and the list of people that we want to display
    Context context;
    List<PersonModel> people;
//  the constructor
    public PersonAdapter(Context context, List<PersonModel> people) {
        this.context = context;
        this.people = people;
    }
//  extending RecyclerView.Adapter forces us to override OnCreateViewHolder nad onBindViewHolder
//  we create an inflater from the context and we inflate it with the custom row that will be used to display the data in the list
    @NonNull
    @Override
    public CustomViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.row_layout,parent,false);
        return new CustomViewHolder(view);
    }
//  this will be called for every single row
    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder holder, int position) {
//      we set the text in the custom row layout to the data read from the list of people
        holder.prenomTxt.setText(String.valueOf(people.get(position).getPrenom()));
        holder.nomTxt.setText(String.valueOf(people.get(position).getNom()));
        holder.ageTxt.setText(String.valueOf(people.get(position).getAge()));
//      we add a click listener to open the Update Activity and we pass in the person clicked
        holder.rowLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,UpdateActivity.class);
                intent.putExtra("person", (Serializable) people.get(position));

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }
//  getting references to the elements in our custom holder for row
    public class CustomViewHolder extends RecyclerView.ViewHolder {

        TextView prenomTxt, nomTxt, ageTxt;
        LinearLayout rowLayout;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            prenomTxt = itemView.findViewById(R.id.prenom_txt);
            nomTxt = itemView.findViewById(R.id.nom_txt);
            ageTxt = itemView.findViewById(R.id.age_txt);
            rowLayout = itemView.findViewById(R.id.row);

        }
    }
}
