package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Adminhome extends AppCompatActivity {

    private ImageButton dotbuttonhome,logoutbtn;
    private static long back_pressed;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminhome);

        firebaseAuth=FirebaseAuth.getInstance();
        dotbuttonhome=(ImageButton)findViewById(R.id.dotbuttonhome);
        logoutbtn=(ImageButton)findViewById(R.id.backBtnhome);

        PopupMenu popupMenu=new PopupMenu(Adminhome.this,dotbuttonhome);
        popupMenu.getMenu().add("About Us");
        popupMenu.getMenu().add("Contact Us");
        popupMenu.getMenu().add("Privacy Policy");
        popupMenu.getMenu().add("Terms and Condition");

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(item.getTitle()=="About Us")
                {
                    startActivity(new Intent(Adminhome.this, Aboutus.class));
                    finish();
                }
                else if(item.getTitle()=="Contact Us")
                {
                    startActivity(new Intent(Adminhome.this,AdminContactDetails.class));
                    finish();
                }
                else if(item.getTitle()=="Privacy Policy")
                {

                    startActivity(new Intent(Adminhome.this,AdminPrivacyPolicy.class));
                    finish();
                }
                else if(item.getTitle()=="Terms and Condition")
                {
                    startActivity(new Intent(Adminhome.this,AdminTermsCondition.class));
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

        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent logoutteacher=new Intent(Adminhome.this,adminstart.class);

                logoutteacher.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                firebaseAuth.signOut();
                startActivity(logoutteacher);
            }
        });
        
    }

    public void Old_record(View view)
    {
        startActivity(new Intent(this,Showdelete.class));
        finish();

    }

    public void view_student(View view)
    {
        Intent intent=new Intent(this,StudentforAdmin.class);
        startActivity(intent);
    }

    public void view_Teacher(View view)
    {
        Intent intent=new Intent(this,retrieveteacher.class);
        startActivity(intent);
    }

    public void Addprincipal(View view)
    {
        Intent intent=new Intent(this,adminregister.class);
        startActivity(intent);

    }

    public void removeprinci(View view)
    {
       // firebaseAuth=FirebaseAuth.getInstance();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("Principal");
        ref.child("Principal").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.exists()) {
                    ref.setValue(null);
                    Toast.makeText(Adminhome.this, "Principal removed successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Adminhome.this, "Principal does not exists ", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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