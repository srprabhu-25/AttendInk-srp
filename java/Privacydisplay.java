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

public class Privacydisplay extends AppCompatActivity {

    private TextView about;
    private ImageView Iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacydisplay);



        about=findViewById(R.id.about12);
        Iv_back=findViewById(R.id.IV_back12);

        Iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("Privacy").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String aboutdetail=""+snapshot.child("policy").getValue();
                about.setText(aboutdetail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void backpressed() {

        startActivity(new Intent(Privacydisplay.this,Adminlogin.class));
        finish();

    }
}