package com.example.attendink;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class Deletestudent extends AppCompatActivity {
    private Button btndelete,btncancel;
    private EditText studid;
    private DatabaseReference databasestudent,dataattendance,olddb;
    private String sid;
    private TextView dispsid,dispname,dispgender,dispdep,dispemail,disppass,dispphone,dispaddress,dispage;

    private static long back_pressed;
    private String studentid,ssid,sname,sgender,spass,semail,sage,saddress,ssection,sprofile,sphone,ssdispstudent;
    private ImageButton backBtn105006;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deletestudent);

        studid=(EditText)findViewById(R.id.studentid);
        btndelete=(Button)findViewById(R.id.btndelete);
        btncancel=(Button)findViewById(R.id.btncancel);
        dispsid=(TextView)findViewById(R.id.dispsid);
        dispname=(TextView)findViewById(R.id.dispname);
        dispgender=(TextView)findViewById(R.id.dispgender);
        dispdep=(TextView)findViewById(R.id.dispdep);
        dispemail=(TextView)findViewById(R.id.dispemail);
        disppass=(TextView)findViewById(R.id.disppass);
        dispphone=(TextView)findViewById(R.id.dispphone);
        dispaddress=(TextView)findViewById(R.id.dispaddress);
        dispage=(TextView)findViewById(R.id.dispage);


        backBtn105006=(ImageButton)findViewById(R.id.backBtn105006);


        databasestudent = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        dataattendance = FirebaseDatabase.getInstance().getReference("Attendance");



        sid=studid.getText().toString();
        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                deleteStudent();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cancelStudent();
            }
        });


        backBtn105006.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void showidstudents(View view)
    {

        dispsid.setText("");
        dispname.setText("");
        dispage.setText("");
        dispaddress.setText("");
        dispdep.setText("");
        dispemail.setText("");
        dispgender.setText("");
        disppass.setText("");
        dispphone.setText("");

        if(!TextUtils.isEmpty(studid.getText().toString())) {

            sid = studid.getText().toString();

            databasestudent.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    if(snapshot.child(sid).exists())
                    {

                        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Users").child("Students");
                        ref1.orderByChild("sid").equalTo(sid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override

                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot dsp:snapshot.getChildren())
                                {
                                    String name = "" + dsp.child("name").getValue();
                                    String sid1 = "" + dsp.child("sid").getValue();
                                    String dep = "" + dsp.child("section").getValue();
                                    String email = "" + dsp.child("email").getValue();
                                  //  String profileimage = "" + dsp.child("profileimage").getValue();
                                    String pass = "" + dsp.child("password").getValue();
                                    String phone = "" + dsp.child("phone").getValue();
                                    String addr = "" + dsp.child("address").getValue();
                                    String age = "" + dsp.child("age").getValue();
                                    String gender = "" + dsp.child("gender").getValue();

                                    dispsid.setText("Sid : "+sid);
                                    dispname.setText("Name : "+name);
                                    dispage.setText("Age : "+age);
                                    dispaddress.setText("Address : "+addr);
                                    dispdep.setText("Section : "+dep);
                                    dispemail.setText("Email : "+email);
                                    dispgender.setText("Gender : "+gender);
                                    disppass.setText("Password : " +pass);
                                    dispphone.setText("Phone : "+phone);


                                    ssid =dispsid.getText().toString();
                                    sname=dispname.getText().toString();
                                    sgender=dispgender.getText().toString();
                                    spass=disppass.getText().toString();
                                    semail=dispemail.getText().toString();
                                    sage=dispage.getText().toString();
                                    saddress=dispaddress.getText().toString();
                                    ssection=dispdep.getText().toString();
                                    sphone=dispphone.getText().toString();


                                    HashMap<String,Object> hashmap= new HashMap<>();
                                    hashmap.put("abemail",""+semail);
                                    hashmap.put("abname",""+sname);
                                    hashmap.put("abphone",""+sphone);
                                    hashmap.put("absid",""+ssid);
                                    hashmap.put("abage",""+sage);
                                    hashmap.put("absection",""+ssection);
                                    hashmap.put("abaddress",""+saddress);
                                    hashmap.put("abpassword", "" +spass);
                                    hashmap.put("abgender", "" +sgender);

                                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("RemovedStudents");
                                    reference.child(sid1).setValue(hashmap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Deletestudent.this, "", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Toast.makeText(Deletestudent.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                    else
                    {
                        Toast.makeText(Deletestudent.this, "Enter the valid student id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Deletestudent.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Student id cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }


    private void deleteStudent()
    {

        databasestudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(sid).exists()) {
                    databasestudent.child(sid).setValue(null);
                    dataattendance.child("date").child(sid).setValue(null);
                    databasestudent.child("uid").setValue(null);
                    Toast.makeText(getApplicationContext(), "Student removed successfully", Toast.LENGTH_SHORT).show();
                    finish();
                }
                else
                {
                    Toast.makeText(Deletestudent.this, "Enter the valid teacher id", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }
            public void cancelStudent()
    {
        studid.setText("");

    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
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