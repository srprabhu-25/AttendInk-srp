package com.example.attendink;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Studentadapter extends RecyclerView.Adapter<Studentadapter.ViewHolder> implements Filterable {


    //public ArrayList<Oldstudentmodel> oldstudentmodelList;
    private Context context;
    public  List<Studentmodel> studentmodelList,filterlist1;
    private Filterstudent filter1;

    public Studentadapter(Context context, List<Studentmodel> studentmodelList) {
        this.context = context;
        this.studentmodelList = studentmodelList;
        this.filterlist1=studentmodelList;

    }



    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.singlerowdesign,parent,false);
        return new ViewHolder(v);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


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
//        holder.timestamptext.setText("Timestamp  : "+studentmodel.getTimestamp());
//        holder.uidtext.setText("Uid : "+studentmodel.getUid());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                detailsbottomsheet(studentmodel);
            }
        });
    }

    private void detailsbottomsheet(Studentmodel studentmodel) {

        BottomSheetDialog bottomSheetDialog=new BottomSheetDialog(context);

        View view=LayoutInflater.from(context).inflate(R.layout.bottom_s_student,null);

        bottomSheetDialog.setContentView(view);


        ImageButton editbsstudent=view.findViewById(R.id.editbsstudent);
        ImageButton backbsstudent=view.findViewById(R.id.backbsstudent);
        ImageView bsprofielstudent=view.findViewById(R.id.bsprofielstudent);


        TextView bsnametext=view.findViewById(R.id.bsnametext);
        TextView bsgendertext=view.findViewById(R.id.bsgendertext);
        TextView  bscoursetext=view.findViewById(R.id.bscoursetext);
        TextView bsphonetext=view.findViewById(R.id.bsphonetext);
        TextView bsemailtext=view.findViewById(R.id.bsemailtext);
        TextView bssidtext=view.findViewById(R.id.bssidtext);
        TextView bsagetext=view.findViewById(R.id.bsagetext);
        TextView bsaddresstext=view.findViewById(R.id.bsaddresstext);


        String sid=studentmodel.getSid();
        String name=studentmodel.getName();
        String gender=studentmodel.getGender();
        String course=studentmodel.getSection();
        String phone=studentmodel.getPhone();
        String email=studentmodel.getEmail();
        String age=studentmodel.getAge();
        String address=studentmodel.getAddress();

        String imageUri=null;
        imageUri=studentmodel.getProfileimage();
        Picasso.get().load(imageUri).into(bsprofielstudent);

         bsnametext.setText(name);
         bsgendertext.setText(gender);
         bscoursetext.setText(course);
         bsphonetext.setText(phone);
         bsemailtext.setText(email);
         bssidtext.setText(sid);
         bsagetext.setText(age);
         bsaddresstext.setText(address);


        bottomSheetDialog.show();

        editbsstudent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 Intent in=new Intent(context,Editstudent_bottom.class);
                 in.putExtra("sid",sid);
                 context.startActivity(in);

             }
         });

         backbsstudent.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 bottomSheetDialog.dismiss();
             }
         });
    }

    @Override
    public int getItemCount()
    {
        return studentmodelList.size();

    }

    @Override
    public Filter getFilter() {

        if(filter1==null)
        {
            filter1=new Filterstudent(this, (ArrayList<Studentmodel>) filterlist1);
        }
        return filter1;
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
//            uidtext=itemView.findViewById(R.id.uidtext);
//            timestamptext=itemView.findViewById(R.id.timestamptext);

        }
    }
}
