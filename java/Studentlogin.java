package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
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

public class Studentlogin extends AppCompatActivity {

    private  String item;
    String message;
    Bundle bundle1;

    private ImageButton dotbuttonhome;


    public TextView nameTV12,teidTv12,secTV12,emailTV12;
    private ImageButton editprofilebutton12;
    private ImageView profileIV12;
    public String sstud,sstud12;
public String name,tid,email,profile,sec;
   private FirebaseAuth firebaseAuth;
   private String date = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
  // FirebaseAuth firebaseAuth;

   DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("Students");
   private static long back_pressed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentlogin);

        Bundle bundle=getIntent().getExtras();
        //message=bundle.getString("message");

        nameTV12=(TextView) findViewById(R.id.nameTV12);
        teidTv12=(TextView) findViewById(R.id.teidTV12);
        secTV12=(TextView) findViewById(R.id.secTV12);
        emailTV12=(TextView) findViewById(R.id.emailTV12);
        editprofilebutton12=(ImageButton) findViewById(R.id.editprofilebutton12);
        profileIV12=(ImageView) findViewById(R.id.profileIV12);


        dotbuttonhome=(ImageButton)findViewById(R.id.dotbuttonstudent);

        PopupMenu popupMenu=new PopupMenu(Studentlogin.this,dotbuttonhome);
        popupMenu.getMenu().add("About Us");
        popupMenu.getMenu().add("Privacy Policy");
        popupMenu.getMenu().add("Terms and Condition");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="About Us")
                {
                    startActivity(new Intent(Studentlogin.this, AboutusforStudent.class));
                    finish();
                }
                else if(item.getTitle()=="Privacy Policy")
                {

                    startActivity(new Intent(Studentlogin.this,Privacyforstudent.class));
                    finish();
                }
                else if(item.getTitle()=="Terms and Condition")
                {
                    startActivity(new Intent(Studentlogin.this,Termsforstudent.class));
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
        checkuser();


        editprofilebutton12.setOnClickListener(new android.view.View.OnClickListener() {
            @Override
            public void onClick(android.view.View v) {
                startActivity(new Intent(Studentlogin.this,Editstudentprofile.class));
            }
        });
    }

    private void checkuser()
    {

        FirebaseUser user=firebaseAuth.getCurrentUser();
        if(user==null)
        {
            startActivity(new Intent(Studentlogin.this,LoginActivity.class));
            finish();
        }
        else
        {
            loadMyinfo();
        }

    }


    public void loadMyinfo()
    {

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("Students");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dsp:snapshot.getChildren())
                {
                     name=""+dsp.child("name").getValue();
                     email=""+dsp.child("email").getValue();
                     profile=""+dsp.child("profileimage").getValue();
                     tid=""+dsp.child("sid").getValue();
                     sec=""+dsp.child("section").getValue();

                    nameTV12.setText(name);
                    secTV12.setText(sec);
                    emailTV12.setText(email);
                    teidTv12.setText(tid);
                    try {
                        Picasso.get().load(profile).placeholder(R.drawable.ic_person_sanju).into(profileIV12);
                    }
                    catch (Exception e)
                    {
                        profileIV12.setImageResource(R.drawable.ic_person_sanju);

                    }


                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        sstud=teidTv12.getText().toString();
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

    public void ViewAttendanceButton(android.view.View view) {


                   sstud12=teidTv12.getText().toString();
                    Intent intent = new Intent(getApplicationContext(), student_attendance_sheet.class);
                    intent.putExtra("basket",sstud12);
                    startActivity(intent);


    }

    public void viewstudent11(android.view.View view)
    {

        Intent intent = new Intent(getApplicationContext(), Olddisplaystudent.class);
        // intent.putExtras(basket);
        startActivity(intent);

    }
    public void viewTeacher11(android.view.View view)
    {

        Intent intent = new Intent(getApplicationContext(), Dispteacherforstudent.class);
        // intent.putExtras(basket);
        startActivity(intent);
    }

    public void logoutStudent(android.view.View view) {
        Intent logoutStudent=new Intent(Studentlogin.this,LoginActivity.class);

        logoutStudent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        firebaseAuth.signOut();
        startActivity(logoutStudent);

    }


}

