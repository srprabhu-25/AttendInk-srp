package com.example.attendink;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;


public class Sendsmstostudent extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    RecyclerView recyclerView1;
    private absentsend absentadapter;
    private ArrayList<Studentmodel> studentmodelList;

    private static final int SEND_SMS_CODE=600;
    String [] smspermission;

    private static long back_pressed;
    private ImageButton backBtn105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sendsmstostudent);

        backBtn105=(ImageButton)findViewById(R.id.backBtn1050012);


        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
      //  messagepermission=new String[]{Manifest.permission.SEND_SMS,Manifest.permission.WRITE_EXTERNAL_STORAGE};

        Bundle bundle=getIntent().getExtras();
        ArrayList<String> arrayList=bundle.getStringArrayList("absent");



        smspermission = new String[]{Manifest.permission.SEND_SMS};

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS)
                == PackageManager.PERMISSION_GRANTED) {

            // Permission has already been granted

        }
        else
        {

            // Permission is not granted
            // Ask for permision
            ActivityCompat.requestPermissions(this, smspermission, 100);

        }


//        Intent in=getIntent();
//        String absentid=in.getExtras().getString("absent");

       // Toast.makeText(this, absentid, Toast.LENGTH_SHORT).show();

        mDatabase=FirebaseDatabase.getInstance();
        mRef=mDatabase.getReference("Users").child("Students");
        mStorage=FirebaseStorage.getInstance();
        recyclerView1=findViewById(R.id.recyclerviewsendsms);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        studentmodelList=new ArrayList<Studentmodel>();
        absentadapter=new absentsend(Sendsmstostudent.this,studentmodelList);
        recyclerView1.setAdapter(absentadapter);

        for(Object item : arrayList) {
            mRef.orderByChild("sid").equalTo(item.toString()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                    Studentmodel studentmodel = snapshot.getValue(Studentmodel.class);
                    studentmodelList.add(studentmodel);
                    absentadapter.notifyDataSetChanged();

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot snapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
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
            }
            else
            {
                Toast.makeText(getApplicationContext(), "sms permissions are neccessary ", Toast.LENGTH_SHORT).show();
            }
        }
    }

//    public void onBackPressed()
//    {
//        if (back_pressed + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//            ActivityCompat.finishAffinity(this);
//            System.exit(0);
//        } else {
//            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
//            back_pressed = System.currentTimeMillis();
//        }
//    }

}