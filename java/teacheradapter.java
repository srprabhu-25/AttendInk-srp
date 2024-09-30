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

public class teacheradapter extends RecyclerView.Adapter<teacheradapter.ViewHolder> implements Filterable {


    private Context context;
    public List<teachermodel> teachermodelList,filterList;
    private Filterproduct filter;

    public teacheradapter(Context context, List<teachermodel> teachermodelList) {
        this.context = context;
        this.teachermodelList = teachermodelList;
        this.filterList=teachermodelList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.rowdesignforteacher,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        teachermodel teachmodel=teachermodelList.get(position);

        //holder.accounttext1.setText("Accounttype : "+teachmodel.getAccounttype());
        holder.addresstext1.setText("Address : "+teachmodel.getAddress());
        holder.agetext1.setText("Age : "+teachmodel.getAge());
        holder.emailtext1.setText("Email : "+teachmodel.getEmail());
        holder.gendertext1.setText("Gender : "+teachmodel.getGender());
        holder.nametext1.setText("Name : "+teachmodel.getName());
       // holder.onlinetext1.setText("Online : "+teachmodel.getOnline());
       // holder.passwordtext1.setText("Password : "+teachmodel.getPassword());
        holder.phonetext1.setText("Phone : "+teachmodel.getPhone());

        String  imageuri=null;
        imageuri=teachmodel.getProfileimage();
        Picasso.get().load(imageuri).into(holder.profileimage);

        holder.classtext1.setText("Department : "+teachmodel.getSection());
        holder.tidtext1.setText("T ID : "+teachmodel.getTid());
//        holder.timestamp1.setText("Timestamp : "+teachmodel.getTimestamp());
//        holder.uid1.setText("Uid : "+teachmodel.getUid());


    }

    @Override
    public int getItemCount()
    {
        return teachermodelList.size();

    }


    @Override
    public Filter getFilter()
    {
        if(filter==null)
        {
          filter=new Filterproduct(this, (ArrayList<teachermodel>) filterList);
        }
        return filter;

    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView profileimage;
        TextView nametext1,gendertext1,classtext1,phonetext1,emailtext1,tidtext1,passwordtext1,agetext1,accounttext1,onlinetext1,addresstext1,uid1,timestamp1;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileimage=itemView.findViewById(R.id.profileimage);
            nametext1=itemView.findViewById(R.id.nametext1);
            gendertext1=itemView.findViewById(R.id.gendertext1);
            classtext1=itemView.findViewById(R.id.classtext1);
            phonetext1=itemView.findViewById(R.id.phonetext1);
            emailtext1=itemView.findViewById(R.id.emailtext1);
            tidtext1=itemView.findViewById(R.id.tidtext1);
           // passwordtext1=itemView.findViewById(R.id.passwordtext1);
            agetext1=itemView.findViewById(R.id.agetext1);
            //accounttext1=itemView.findViewById(R.id.accounttext1);
            //onlinetext1=itemView.findViewById(R.id.onlinetext1);
            addresstext1=itemView.findViewById(R.id.addresstext1);
//            uid1=itemView.findViewById(R.id.uid1);
//            timestamp1=itemView.findViewById(R.id.timestamp1);



        }
    }

}

