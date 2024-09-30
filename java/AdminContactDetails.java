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

public class AdminContactDetails extends AppCompatActivity {

    private ImageView Iv_back;
    private EditText about;
    private Button Ok,clear;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_contact_details);


        firebaseAuth = FirebaseAuth.getInstance();

        Ok=findViewById(R.id.contactok);
        clear=findViewById(R.id.contactclear);
        about=findViewById(R.id.contact);
        Iv_back=findViewById(R.id.IV_backcontact);
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
        ref.child("Contact").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String aboutdetail=""+snapshot.child("cdetail").getValue();
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
        hashMap.put("cdetail", "" +aboutdetail);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Admin");
        ref.child("Contact").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(AdminContactDetails.this, "Details are Recorded", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });

    }

    private void backpressed() {

        startActivity(new Intent(AdminContactDetails.this,Adminhome.class));
        finish();

    }
}