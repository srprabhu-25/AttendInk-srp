package com.example.attendink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Oldstudentadapter extends RecyclerView.Adapter<Oldstudentadapter.ViewHolder> implements Filterable {

    private Context context;
    public  List<Oldstudentmodel> oldstudentmodelList,oldfilterlist1;
    private Filteroldstudent oldfilter1;

    public Oldstudentadapter(Context context,List<Oldstudentmodel> oldstudentmodelList) {

        this.context = context;
        this.oldstudentmodelList = oldstudentmodelList;
        this.oldfilterlist1=oldstudentmodelList;
    }

    @Override
    public Filter getFilter() {

        if(oldfilter1==null)
        {
            oldfilter1=new Filteroldstudent(this, (ArrayList<Oldstudentmodel>) oldfilterlist1);

        }
        return oldfilter1;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.oldrowdesign,parent,false);
        return new ViewHolder(v);
        
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Oldstudentmodel oldstudentmodel=oldstudentmodelList.get(position);

        holder.nametext.setText("Name : "+oldstudentmodel.getName());
        holder.coursetext.setText("Class : "+oldstudentmodel.getSection());
        holder.sidtext.setText("S Id : "+oldstudentmodel.getSid());

        String imageUri=null;
        imageUri=oldstudentmodel.getProfileimage();
        Picasso.get().load(imageUri).into(holder.profileimage);
    }

    @Override
    public int getItemCount() {

        return oldstudentmodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        TextView nametext,coursetext,sidtext;


        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            profileimage=itemView.findViewById(R.id.oldprofileimage);
            nametext=itemView.findViewById(R.id.oldnametext);
            coursetext=itemView.findViewById(R.id.oldcoursetext);
            sidtext=itemView.findViewById(R.id.oldsidtext);
        }
    }

}

