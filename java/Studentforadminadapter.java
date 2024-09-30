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

public class Studentforadminadapter extends RecyclerView.Adapter<Studentforadminadapter.ViewHolder> implements Filterable {

    private Context context;
    public List<Studentmodel> studentmodelList,filterlist1;
    private Filteradminstudent filter1;

    public Studentforadminadapter(StudentforAdmin studentforAdmin, ArrayList<Studentmodel> studentmodelList) {

        this.context = context;
        this.studentmodelList = studentmodelList;
        this.filterlist1=studentmodelList;

    }

    @Override
    public Filter getFilter() {

        if(filter1==null)
        {
            filter1=new Filteradminstudent(this, (ArrayList<Studentmodel>) filterlist1);
        }
        return filter1;

    }

    @NonNull
    @Override
    public Studentforadminadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull Studentforadminadapter.ViewHolder holder, int position) {

        Studentmodel studentmodel=studentmodelList.get(position);

        // holder.accounttext.setText("Accounttype : "+studentmodel.getAccounttype());
        holder.addresstext.setText("Address : "+studentmodel.getAddress());
        holder.agetext.setText("Age : "+studentmodel.getAge());
        holder.emailtext.setText("Email : "+studentmodel.getEmail());
        holder.gendertext.setText("Gender : "+studentmodel.getGender());
        holder.nametext.setText("Name : "+studentmodel.getName());
        // holder.onlinetext.setText("Online : "+studentmodel.getOnline());
        // holder.passwordtext.setText("Password : "+studentmodel.getPassword());
        holder.phonetext.setText("Phone : "+studentmodel.getPhone());

        String imageUri=null;
        imageUri=studentmodel.getProfileimage();
        Picasso.get().load(imageUri).into(holder.profileimage);

        holder.coursetext.setText("Class : "+studentmodel.getSection());
        holder.sidtext.setText("S Id : "+studentmodel.getSid());

    }

    @Override
    public int getItemCount() {

        return studentmodelList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileimage;
        TextView nametext,gendertext,coursetext,phonetext,emailtext,sidtext,passwordtext,agetext,accounttext,onlinetext,addresstext,uidtext,timestamptext;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileimage=itemView.findViewById(R.id.profileimage);
            nametext=itemView.findViewById(R.id.nametext);
            gendertext=itemView.findViewById(R.id.gendertext);
            coursetext=itemView.findViewById(R.id.coursetext);
            phonetext=itemView.findViewById(R.id.phonetext);
            emailtext=itemView.findViewById(R.id.emailtext);
            sidtext=itemView.findViewById(R.id.sidtext);
            //   passwordtext=itemView.findViewById(R.id.passwordtext);
            agetext=itemView.findViewById(R.id.agetext);
            //accounttext=itemView.findViewById(R.id.accounttext);
            //onlinetext=itemView.findViewById(R.id.onlinetext);
            addresstext=itemView.findViewById(R.id.addresstext);

        }
    }
}
