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

public class Displaystudentfortadapter extends RecyclerView.Adapter<Displaystudentfortadapter.Viewholder> implements Filterable {

    private Context context;
    public List<Oldstudentmodel> oldstudentmodelList,oldfilterlist1;
    private Filterdispstudent oldfilter1;


    public Displaystudentfortadapter(Context context,List<Oldstudentmodel> oldstudentmodelList) {

        this.context = context;
        this.oldstudentmodelList = oldstudentmodelList;
        this.oldfilterlist1=oldstudentmodelList;
    }

    @Override
    public Filter getFilter() {
        if(oldfilter1==null)
        {
            oldfilter1=new Filterdispstudent(this, (ArrayList<Oldstudentmodel>) oldfilterlist1);

        }
        return oldfilter1;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.oldrowdesign1,parent,false);
        return new Viewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull Displaystudentfortadapter.Viewholder holder, int position) {


        Oldstudentmodel oldstudentmodel=oldstudentmodelList.get(position);

        holder.nametext.setText("Name : "+oldstudentmodel.getName());
        holder.coursetext.setText("Class : "+oldstudentmodel.getSection());
        holder.sidtext.setText("S Id : "+oldstudentmodel.getSid());
        holder.phonetxt.setText("Phone : "+oldstudentmodel.getPhone());

        String imageUri=null;
        imageUri=oldstudentmodel.getProfileimage();
        Picasso.get().load(imageUri).into(holder.profileimage);

    }

    @Override
    public int getItemCount() {

        return oldstudentmodelList.size();
    }

    public class Viewholder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        TextView nametext,coursetext,sidtext,phonetxt;

        public Viewholder(@NonNull View itemView) {

            super(itemView);

            profileimage=itemView.findViewById(R.id.oldprofileimage12);
            nametext=itemView.findViewById(R.id.oldnametext12);
            coursetext=itemView.findViewById(R.id.oldcoursetext12);
            sidtext=itemView.findViewById(R.id.oldsidtext12);
            phonetxt=itemView.findViewById(R.id.oldphonetext);

        }
    }
}
