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

public class Termsdisplay extends AppCompatActivity {

    private TextView about;
    private ImageView Iv_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_termsdisplay);

        about=findViewById(R.id.about14);
        Iv_back=findViewById(R.id.IV_back14);

        Iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("Terms").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String aboutdetail=""+snapshot.child("term").getValue();
                about.setText(aboutdetail);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void backpressed() {

        startActivity(new Intent(Termsdisplay.this,Adminlogin.class));
        finish();

    }
}