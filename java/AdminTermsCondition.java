package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class AdminTermsCondition extends AppCompatActivity {


    private ImageView Iv_back;
    private EditText about;
    private Button Ok,clear;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_terms_condition);


        firebaseAuth = FirebaseAuth.getInstance();

        Ok=findViewById(R.id.termsok);
        clear=findViewById(R.id.termsclear);
        about=findViewById(R.id.terms);
        Iv_back=findViewById(R.id.IV_backterms);
        Iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backpressed();
            }
        });
        Ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inputData();
            }
        });
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                about.setText("");
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

    private String aboutdetail;
    private void inputData() {
        aboutdetail=about.getText().toString().trim();
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("term", "" +aboutdetail);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("Terms").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminTermsCondition.this, "Details are Recorded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }
    private void backpressed() {

        startActivity(new Intent(AdminTermsCondition.this,Adminhome.class));
        finish();

    }
}