package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.Date;

public class Teacherlogin extends AppCompatActivity implements AdapterView.OnItemSelectedListener{

    private  String item;
    String message,teacherids;
    Bundle bundle1;

    private ImageButton dotbuttonhome;


    private TextView nameTV,teidTv,depTV,emailTV;
    private ImageButton editprofilebutton101010;
    private ImageView profileIV;

    FirebaseAuth firebaseAuth;
    private static long back_pressed;
   String date=new SimpleDateFormat("dd-MM-yyyy").format(new Date());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacherlogin);


         //bundle1 = getIntent().getExtras();
      //  message = bundle1.getString("message");

        nameTV=(TextView) findViewById(R.id.nameTV);
        teidTv=(TextView) findViewById(R.id.teidTV);
        depTV=(TextView) findViewById(R.id.depTV);
        emailTV=(TextView) findViewById(R.id.emailTV);
        editprofilebutton101010=(ImageButton) findViewById(R.id.editprofilebutton101010);
        profileIV=(ImageView) findViewById(R.id.profileIV);


        dotbuttonhome=(ImageButton)findViewById(R.id.dotbuttonteacher);

        PopupMenu popupMenu=new PopupMenu(Teacherlogin.this,dotbuttonhome);
        popupMenu.getMenu().add("About Us");
        popupMenu.getMenu().add("Privacy Policy");
        popupMenu.getMenu().add("Terms and Condition");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="About Us")
                {
                    startActivity(new Intent(Teacherlogin.this, AboutusforTeacher.class));
                    finish();
                }
                else if(item.getTitle()=="Privacy Policy")
                {

                    startActivity(new Intent(Teacherlogin.this,PrivacyforTeacher.class));
                    finish();
                }
                else if(item.getTitle()=="Terms and Condition")
                {
                    startActivity(new Intent(Teacherlogin.this,Termsforteacher.class));
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

        firebaseAuth=FirebaseAuth.getInstance();
        checkUser();


        editprofilebutton101010.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(new Intent(Teacherlogin.this,Editteacherprofile.class));

            }
        });
    }

    private void checkUser()
    {
        FirebaseUser User=firebaseAuth.getCurrentUser();
        if(User==null)
        {
            startActivity(new Intent(Teacherlogin.this,loginactivityteach.class));
            finish();
        }
        else
        {
            loadMyinfo();
        }
    }

    private void loadMyinfo()
    {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp:snapshot.getChildren())
                {
                    String name=""+dsp.child("name").getValue();
                    String email=""+dsp.child("email").getValue();
                    String profile=""+dsp.child("profileimage").getValue();
                    String tid=""+dsp.child("tid").getValue();
                    String dep=""+dsp.child("section").getValue();


                    nameTV.setText(name);
                    depTV.setText(dep);
                    emailTV.setText(email);
                    teidTv.setText(tid);
                    try
                        {
                            Picasso.get().load(profile).placeholder(R.drawable.ic_person_sanju).into(profileIV);
                        }
                    catch (Exception e)
                    {
                        profileIV.setImageResource(R.drawable.ic_person_sanju);
                    }
                }

                teacherids=nameTV.getText().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void takeAttendanceButton(View view)
    {

        //message = bundle1.getString("message");
        Bundle basket=new Bundle();
        //basket.putString("class_selected",item);
        basket.putString("tid",teacherids);

        Intent intent = new Intent(this,Takeattendance.class);
        intent.putExtras(basket);
        startActivity(intent);

    }

    public void  previous_records(View view){


        startActivity(new Intent(this, Teacher_attendance_sheet.class));
     finish();
    }


    public void ViewStudentlistButton(View view)
    {

        startActivity(new Intent(Teacherlogin.this,displaystudentforteacher.class));
        finish();

    }

    public  void Viewteacher12(View view)
    {
        startActivity(new Intent(Teacherlogin.this,Displayteacherforteacher.class));
        finish();
    }
    public void logoutTeacher(View view)
    {
        Intent logoutteacher=new Intent(Teacherlogin.this,loginactivityteach.class);

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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        item = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}