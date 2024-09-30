package com.example.attendink;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Removeteachadapter extends RecyclerView.Adapter<Removeteachadapter.ViewHolder>{



        private Context context;
    public ArrayList<Removeteachmodel> removeteachmodelslist;


    public Removeteachadapter(Context context, ArrayList<Removeteachmodel> removeteachmodelslist) {

        this.context = context;
        this.removeteachmodelslist = removeteachmodelslist;
    }
    @NonNull
    @Override
    public Removeteachadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.row_removeteach,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Removeteachadapter.ViewHolder holder, int position) {


        Removeteachmodel removeteachmodel=removeteachmodelslist.get(position);

        holder.readdresstext1.setText(removeteachmodel.getAbaddress());
        holder.reagetext1.setText(removeteachmodel.getAbage());
        holder.reemailtext1.setText(removeteachmodel.getAbemail());
        holder.regendertext1.setText(removeteachmodel.getAbgender());
        holder.renametext1.setText(removeteachmodel.getAbname());
        holder.rephonetext1.setText(removeteachmodel.getAbphone());

//        String  imageuri=null;
//        imageuri=teachmodel.getProfileimage();
//        Picasso.get().load(imageuri).into(holder.profileimage);

        holder.reclasstext1.setText(removeteachmodel.getAbsection());
        holder.retidtext1.setText(removeteachmodel.getAbsid());

    }

    @Override
    public int getItemCount() {
        return removeteachmodelslist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView renametext1,retidtext1,reclasstext1,regendertext1,reemailtext1,reagetext1,rephonetext1,readdresstext1;

        public ViewHolder(@NonNull View itemView) {
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
