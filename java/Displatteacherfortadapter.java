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

public class Displatteacherfortadapter extends RecyclerView.Adapter<Displatteacherfortadapter.ViewHolder> implements Filterable {

    private Context context;
    public List<teachermodel> teachermodelList, filterList;
    private Filterteacherfor filter;

    public Displatteacherfortadapter(Context context, List<teachermodel> teachermodelList) {
        this.context = context;
        this.teachermodelList = teachermodelList;
        this.filterList = teachermodelList;

    }


    @Override
    public Filter getFilter() {
        if (filter == null) {
            filter = new Filterteacherfor(this, (ArrayList<teachermodel>) filterList);
        }
        return filter;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.disprowteacher1, parent, false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        teachermodel teachmodel = teachermodelList.get(position);


        String imageuri = null;
        imageuri = teachmodel.getProfileimage();
        Picasso.get().load(imageuri).into(holder.profileimage123);

        holder.nametext123.setText("Name : " + teachmodel.getName());
        holder.classtext123.setText("Department : " + teachmodel.getSection());
        holder.tidtext123.setText("T ID : " + teachmodel.getTid());
        holder.phonetext123.setText("Phone : " + teachmodel.getPhone());
    }

    @Override
    public int getItemCount() {

        return teachermodelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileimage123;
        TextView nametext123, classtext123, tidtext123, phonetext123;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileimage123 = itemView.findViewById(R.id.teachprofileimage123);
            nametext123 = itemView.findViewById(R.id.teachnametext123);
            classtext123 = itemView.findViewById(R.id.teachclasstext123);
            tidtext123 = itemView.findViewById(R.id.teachtidtext123);
            phonetext123 = itemView.findViewById(R.id.teachphonetext123);


        }
    }
}
