package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AboutusforTeacher extends AppCompatActivity {


    private TextView about,about1;
    private ImageView Iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutusfor_teacher);

        about=findViewById(R.id.aboutteacher);
        about1=findViewById(R.id.contactteacher);

        Iv_back=findViewById(R.id.IV_backteacher);

        Iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("About").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String aboutdetail=""+snapshot.child("about").getValue();
                about.setText(aboutdetail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("Contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String aboutdetail=""+snapshot.child("cdetail").getValue();
                about1.setText(aboutdetail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void backpressed() {

        startActivity(new Intent(AboutusforTeacher.this,Teacherlogin.class));
        finish();

    }
}