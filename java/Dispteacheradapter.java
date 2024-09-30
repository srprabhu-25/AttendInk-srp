package com.example.attendink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class Dispteacheradapter extends RecyclerView.Adapter<Dispteacheradapter.ViewHolder> implements Filterable {


    private Context context;
    public List<teachermodel> teachermodelList,filterList;
    private Filterdispteacher filter;

    public Dispteacheradapter(Context context, List<teachermodel> teachermodelList) {
        this.context = context;
        this.teachermodelList = teachermodelList;
        this.filterList=teachermodelList;
    }

    @Override
    public Filter getFilter() {
        if(filter==null)
        {
            filter=new Filterdispteacher(this, (ArrayList<teachermodel>) filterList);
        }
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.disprowteacher,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        teachermodel teachmodel=teachermodelList.get(position);


        String  imageuri=null;
        imageuri=teachmodel.getProfileimage();
        Picasso.get().load(imageuri).into(holder.profileimage123);

        holder.nametext123.setText("Name : "+teachmodel.getName());
        holder.classtext123.setText("Department : "+teachmodel.getSection());
        holder.tidtext123.setText("T ID : "+teachmodel.getTid());
    }


    @Override
    public int getItemCount() {

        return teachermodelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileimage123;
        TextView nametext123,classtext123,tidtext123;

        public ViewHolder(@NonNull View itemView) {

            super(itemView);

            profileimage123=itemView.findViewById(R.id.teachprofileimage);
            nametext123=itemView.findViewById(R.id.teachnametext1);
            classtext123=itemView.findViewById(R.id.teachclasstext1);
            tidtext123=itemView.findViewById(R.id.teachtidtext1);
        }
    }
}
