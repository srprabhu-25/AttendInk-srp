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

public class Deleteteacher extends AppCompatActivity {

    DatabaseReference databaseteacher;
    private String tid;
    private EditText Tid;
    private Button btndelete,btncancel;
    private static long back_pressed;

    private TextView dispsid12,dispname12,dispgender12,dispdep12,dispemail12,disppass12,dispphone12,dispaddress12,dispage12,dispuid12;

    private String suid12,ssid12,sname12,sgender12,spass12,semail12,sage12,saddress12,ssection12,sprofile12,sphone12,ssdispstudent12;
    private ImageButton backBtn105007;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deleteteacher);

        Tid=(EditText)findViewById(R.id.teacherid);
        btndelete=(Button)findViewById(R.id.btndelete);
        btncancel=(Button)findViewById(R.id.btncancel);
        dispsid12=(TextView)findViewById(R.id.dispsid12);
        dispname12=(TextView)findViewById(R.id.dispname12);
        dispgender12=(TextView)findViewById(R.id.dispgender12);
        dispdep12=(TextView)findViewById(R.id.dispdep12);
        dispemail12=(TextView)findViewById(R.id.dispemail12);
        disppass12=(TextView)findViewById(R.id.disppass12);
        dispphone12=(TextView)findViewById(R.id.dispphone12);
        dispaddress12=(TextView)findViewById(R.id.dispaddress12);
        dispage12=(TextView)findViewById(R.id.dispage12);
        dispuid12=findViewById(R.id.dispuid12);

        backBtn105007=(ImageButton)findViewById(R.id.backBtn105007);


        databaseteacher= FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
        //dataattendance = FirebaseDatabase.getInstance().getReference("Attendance");


        tid=Tid.getText().toString();

        btndelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                deleteTeacher();
            }
        });

        btncancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                cancelTeacher();
            }
        });

        backBtn105007.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }


    public void showidteachers(View view) {


        dispsid12.setText("");
        dispname12.setText("");
        dispage12.setText("");
        dispaddress12.setText("");
        dispdep12.setText("");
        dispemail12.setText("");
        dispgender12.setText("");
        disppass12.setText("");
        dispphone12.setText("");

        if (!TextUtils.isEmpty(Tid.getText().toString())) {

            tid = Tid.getText().toString();

            databaseteacher.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {


                    if(snapshot.child(tid).exists())
                    {

                        DatabaseReference ref1=FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
                        ref1.orderByChild("tid").equalTo(tid).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override

                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                for(DataSnapshot dsp:snapshot.getChildren())
                                {
                                    String name = "" + dsp.child("name").getValue();
                                    String sid1 = "" + dsp.child("tid").getValue();
                                    String dep = "" + dsp.child("section").getValue();
                                    String email = "" + dsp.child("email").getValue();
                                    //  String profileimage = "" + dsp.child("profileimage").getValue();
                                    String pass = "" + dsp.child("password").getValue();
                                    String phone = "" + dsp.child("phone").getValue();
                                    String addr = "" + dsp.child("address").getValue();
                                    String age = "" + dsp.child("age").getValue();
                                    String gender = "" + dsp.child("gender").getValue();
                                    String uid=""+dsp.child("uid").getValue();

                                    dispsid12.setText("Tid : "+tid);
                                    dispname12.setText("Name : "+name);
                                    dispage12.setText("Age : "+age);
                                    dispaddress12.setText("Address : "+addr);
                                    dispdep12.setText("Department : "+dep);
                                    dispemail12.setText("Email : "+email);
                                    dispgender12.setText("Gender : "+gender);
                                    disppass12.setText("Password : " +pass);
                                    dispphone12.setText("Phone : "+phone);
                                    dispuid12.setText("Uid : "+uid);


                                    ssid12 =dispsid12.getText().toString();
                                    sname12=dispname12.getText().toString();
                                    sgender12=dispgender12.getText().toString();
                                    spass12=disppass12.getText().toString();
                                    semail12=dispemail12.getText().toString();
                                    sage12=dispage12.getText().toString();
                                    saddress12=dispaddress12.getText().toString();
                                    ssection12=dispdep12.getText().toString();
                                    sphone12=dispphone12.getText().toString();
                                    suid12=dispuid12.getText().toString();


                                    HashMap<String,Object> hashmap= new HashMap<>();
                                    hashmap.put("abemail",""+semail12);
                                    hashmap.put("abname",""+sname12);
                                    hashmap.put("abphone",""+sphone12);
                                    hashmap.put("absid",""+ssid12);
                                    hashmap.put("abage",""+sage12);
                                    hashmap.put("absection",""+ssection12);
                                    hashmap.put("abaddress",""+saddress12);
                                    hashmap.put("abpassword", "" +spass12);
                                    hashmap.put("abgender", "" +sgender12);
                                    hashmap.put("uid",""+suid12);

                                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("RemovedTeachers");
                                    reference.child(sid1).setValue(hashmap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Toast.makeText(Deleteteacher.this, "", Toast.LENGTH_SHORT).show();
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {

                                                    Toast.makeText(Deleteteacher.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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
                        Toast.makeText(Deleteteacher.this, "Enter the valid teacher id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Deleteteacher.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else
        {
            Toast.makeText(getApplicationContext(), "Teacher id cannot be empty ", Toast.LENGTH_SHORT).show();
        }
    }


    public void deleteTeacher() {
        databaseteacher.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.child(tid).exists())
                    {
                        databaseteacher.child(tid).setValue(null);
                        databaseteacher.child("uid").setValue(null);
                        Toast.makeText(getApplicationContext(), "Teacher removed successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(Deleteteacher.this, "Enter the valid teacher id", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(Deleteteacher.this, "Error", Toast.LENGTH_SHORT).show();
                }
            });

        }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId()==android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void cancelTeacher()
    {
        Tid.setText("");
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