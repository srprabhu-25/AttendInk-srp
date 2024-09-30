package com.example.attendink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Removestudentadapter extends RecyclerView.Adapter<Removestudentadapter.Viewholder>
{
    private Context context;
    public ArrayList<Removedstudentmodel> removedstudentmodellist;


    public Removestudentadapter(Context context, ArrayList<Removedstudentmodel> removedstudentmodellist) {

        this.context = context;
        this.removedstudentmodellist = removedstudentmodellist;
    }
    @NonNull
    @Override
    public Removestudentadapter.Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_removeteach,parent,false);
        return new Viewholder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull Removestudentadapter.Viewholder holder, int position) {




        Removedstudentmodel removedstudentmodel=removedstudentmodellist.get(position);

        holder.readdresstext1.setText(removedstudentmodel.getAbaddress());
        holder.reagetext1.setText(removedstudentmodel.getAbage());
        holder.reemailtext1.setText(removedstudentmodel.getAbemail());
        holder.regendertext1.setText(removedstudentmodel.getAbgender());
        holder.renametext1.setText(removedstudentmodel.getAbname());
        holder.rephonetext1.setText(removedstudentmodel.getAbphone());

//        String  imageuri=null;
//        imageuri=teachmodel.getProfileimage();
//        Picasso.get().load(imageuri).into(holder.profileimage);

        holder.reclasstext1.setText(removedstudentmodel.getAbsection());
        holder.retidtext1.setText(removedstudentmodel.getAbsid());
    }

    @Override
    public int getItemCount() {

        return removedstudentmodellist.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {
        TextView renametext1,retidtext1,reclasstext1,regendertext1,reemailtext1,reagetext1,rephonetext1,readdresstext1;


        public Viewholder(@NonNull View itemView) {
            super(itemView);

            renametext1=itemView.findViewById(R.id.renametext1);
            retidtext1=itemView.findViewById(R.id.retidtext1);
            reclasstext1=itemView.findViewById(R.id.reclasstext1);
            regendertext1=itemView.findViewById(R.id.regendertext1);
            reemailtext1=itemView.findViewById(R.id.reemailtext1);
            reagetext1=itemView.findViewById(R.id.reagetext1);
            rephonetext1=itemView.findViewById(R.id.rephonetext1);
            readdresstext1=itemView.findViewById(R.id.readdresstext1);
        }
    }
}
