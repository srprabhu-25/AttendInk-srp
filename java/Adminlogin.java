package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Adminlogin extends AppCompatActivity {
    DatabaseReference ref= FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbStudent=ref.child("Users");
    DatabaseReference dbAttendance;
    DatabaseReference dbadmin=ref.child("Users");

    private TextView nameTV1212,dateTV1212,emailTV1212;
    private ImageButton editprofilebutton1212,logoutPrincipal;
    private ImageView profileIV1212;

    private ImageButton dotbuttonhome;

    private FirebaseAuth firebaseAuth;

    Toolbar mtoolbar;
    private static long back_pressed;
    Button addteacher;
    ArrayList studentlist=new ArrayList<>();
    String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        //mtoolbar=(Toolbar)findViewById(R.id.ftoolbar);
       // mtoolbar.setTitle("Admin Dashboard : "+"("+date+")");

        nameTV1212=(TextView) findViewById(R.id.nameTV1212);
       // teidTv1212=(TextView) findViewById(R.id.teidTV1212);
        dateTV1212=(TextView) findViewById(R.id.dateTV1212);
        emailTV1212=(TextView) findViewById(R.id.emailTV1212);
        editprofilebutton1212=(ImageButton) findViewById(R.id.editprofilebutton1212);
        profileIV1212=(ImageView) findViewById(R.id.profileIV1212);

        logoutPrincipal=(ImageButton)findViewById(R.id.logoutprincipal);



        dotbuttonhome=(ImageButton)findViewById(R.id.dotbuttonhome);

        PopupMenu popupMenu=new PopupMenu(Adminlogin.this,dotbuttonhome);
        popupMenu.getMenu().add("About Us");
        popupMenu.getMenu().add("Privacy Policy");
        popupMenu.getMenu().add("Terms and Condition");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="About Us")
                {
                    startActivity(new Intent(Adminlogin.this, Aboutusdisplay.class));
                    finish();
                }
                else if(item.getTitle()=="Privacy Policy")
                {

                    startActivity(new Intent(Adminlogin.this,Privacydisplay.class));
                    finish();
                }
                else if(item.getTitle()=="Terms and Condition")
                {
                    startActivity(new Intent(Adminlogin.this,Termsdisplay.class));
                    finish();
                }
                return true;
            }
        });

        dotbuttonhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupMenu.show();
            }
        });


        firebaseAuth= FirebaseAuth.getInstance();
        checkuser();

        dbStudent=ref.child("Users").child("Students");
        dbAttendance=ref.child("Attendance");




        editprofilebutton1212.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(new Intent(Adminlogin.this,Editadminprofile.class));
            }
        });


    }

    private void checkuser()
    {

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(Adminlogin.this,adminstart.class));
            finish();
        }
        else
        {
            loadMyinfo();
        }

    }

    private void loadMyinfo() {

        dbadmin=dbadmin.child("Principal");
        dbadmin.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp:snapshot.getChildren())
                {
                    String name=""+dsp.child("pname").getValue();
                    String email=""+dsp.child("pemail").getValue();
                    String profile=""+dsp.child("profileimage").getValue();
                 //   String tid=""+dsp.child("sid").getValue();
                    String sec=""+dsp.child("dob").getValue();

                    nameTV1212.setText(name);
                    dateTV1212.setText(sec);
                    emailTV1212.setText(email);
                    //teidTv12.setText(tid);
                    try {
                        Picasso.get().load(profile).placeholder(R.drawable.ic_person_sanju).into(profileIV1212);
                    }
                    catch (Exception e)
                    {
                        profileIV1212.setImageResource(R.drawable.ic_person_sanju);

                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


    public void AddTeacherButton(View view)
    {
        Intent intentadd=new Intent(this,Addteacher.class);
        startActivity(intentadd);
    }
    public void AddStudentButton(View view)
    {
        Intent intent=new Intent(this,Addstudent.class);
        startActivity(intent);
    }
    public void attendanceRecord(View view)
    {
        Intent intent12=new Intent(Adminlogin.this,admin_Attendancesheet.class);
        startActivity(intent12);
    }
    public void RemoveStudentButton(View view)
    {
        Intent intent=new Intent(this,Deletestudent.class);
        startActivity(intent);
    }
    public void RemoveTeacherButton(View view)
    {
        Intent intent=new Intent(this,Deleteteacher.class);
        startActivity(intent);
    }
    public void viewstudent(View view)
    {
        Intent intent=new Intent(this,admindisplay.class);
        startActivity(intent);
    }

    public void viewTeacher(View view)
    {
        Intent intent=new Intent(this,retrieveteacher.class);
        startActivity(intent);
    }

    public void viewabsent(View view)
    {
        Intent intent=new Intent(this,Viewabsent.class);
        startActivity(intent);
    }

//    public void Oldrecord(View view)
//    {
//        startActivity(new Intent(this,Showdelete.class));
//        finish();
//
//    }
    public void CreateAttendance(View view)
    {
        dbStudent.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String p1="-";
//                String p2="-";
//                String p3="-";
//                String p4="-";
//                String p5="-";
//                String p6="-";
                Attendance_sheet attend=new Attendance_sheet(p1);
                //,p2,p3,p4,p5,p6

                for(DataSnapshot dsp : snapshot.getChildren())
                {
                    String sid;
                    sid = dsp.child("sid").getValue().toString();
                    dbAttendance.child(date).child(sid).setValue(attend);
                }
                Toast.makeText(getApplicationContext(),"Successfully Created "+date+" db",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(),"Something wrong",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void logoutPrincipal(View view)
    {
        Intent logoutteacher=new Intent(Adminlogin.this,adminstart.class);

        logoutteacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        firebaseAuth.signOut();
        startActivity(logoutteacher);

    }

    public void onBackPressed()
    {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            super.onBackPressed();
            finish();
            ActivityCompat.finishAffinity(this);
            System.exit(0);
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }
    }

}