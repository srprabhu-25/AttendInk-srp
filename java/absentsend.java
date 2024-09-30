package com.example.attendink;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.gsm.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class absentsend  extends RecyclerView.Adapter<absentsend.ViewHolder>
    {
        private  Context context;
        public List<Studentmodel> studentmodelList;
        public String[] smspermission;



        public absentsend(Context context,List<Studentmodel> studentmodelList)
        {
            this.context = context;
            this.studentmodelList = studentmodelList;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder (@NonNull ViewGroup parent,int viewType){
            View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_absentsend,parent,false);
        return new ViewHolder(v);
    }

        @Override
        public void onBindViewHolder (@NonNull ViewHolder holder,int position){

            Studentmodel studentmodel=studentmodelList.get(position);

        holder.nametext1240.setText(""+studentmodel.getName());
        holder.sidtext1240.setText(""+studentmodel.getSid());
        holder.phonetext1240.setText(""+studentmodel.getPhone());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                smspermission = new String[]{Manifest.permission.SEND_SMS};

                if (ContextCompat.checkSelfPermission(context.getApplicationContext(), Manifest.permission.SEND_SMS)
                        == PackageManager.PERMISSION_GRANTED) {

                    // Permission has already been granted
                    sendmessage();
                }



            }

            public void onRequestPermissionResult(int requestCode,@NonNull String[] permissions,@NonNull int[] grantResults)
            {

                if(grantResults.length>0)
                {
                    boolean smsaccepted=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                    //boolean storageAccepted=grantResults[1]==PackageManager.PERMISSION_GRANTED;
                    if(smsaccepted)
                    {
                        sendmessage();
                    }
                    else
                    {
                        Toast.makeText(context.getApplicationContext(), "sms permissions are neccessary ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
                private void  sendmessage()
                {


                    String absentname = holder.nametext1240.getText().toString();
                    String absentsid = holder.sidtext1240.getText().toString();
                    String absentphone = holder.phonetext1240.getText().toString();

                    Toast.makeText(context, absentphone, Toast.LENGTH_SHORT).show();
                    String sms = "Hello Sir/Mam,\n Mr."+ absentname + " Whoose roll no  " + absentsid + " is not attended the class today\n\n" +
                            "     -Sri Bhuvanendra College,Karkala.";

                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(absentphone, null, sms, null, null);

                    Toast.makeText(context, "Message sent successfully ", Toast.LENGTH_SHORT).show();


                    //  e.printStackTrace();
                    //Toast.makeText(context, "Failed to send message ", Toast.LENGTH_SHORT).show();


                }


            });

    }


        @Override
        public int getItemCount () {
            return  studentmodelList.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView nametext1240,sidtext1240,phonetext1240;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            nametext1240=itemView.findViewById(R.id.nametext1240);
            sidtext1240=itemView.findViewById(R.id.sidtext1240);
            phonetext1240=itemView.findViewById(R.id.phonetext1240);


        }
        }
    }
