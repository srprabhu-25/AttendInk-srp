package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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

public class Aboutus extends AppCompatActivity {

    private ImageView Iv_back;
    private EditText about;
    private Button Ok,clear;
    private FirebaseAuth firebaseAuth;
    private String aboutdetail;
    private TextView txtdetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);


       // firebaseAuth = FirebaseAuth.getInstance();

        Ok=(Button) findViewById(R.id.ok);
        clear=findViewById(R.id.clear);
        about=(EditText) findViewById(R.id.about);
        Iv_back=findViewById(R.id.IV_back);
        txtdetail=findViewById(R.id.txtdetail);

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

    }

    private void backpressed() {

        startActivity(new Intent(Aboutus.this,Adminhome.class));
        finish();

    }


    private void inputData() {

        aboutdetail=about.getText().toString().trim();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("about",""+aboutdetail);

        DatabaseReference reference=FirebaseDatabase.getInstance().getReference("Admin");
        reference.child("About").setValue(hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {

                Toast.makeText(Aboutus.this, "About us detail save successfully", Toast.LENGTH_SHORT).show();

            }
        })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Aboutus.this, "Details are not entered", Toast.LENGTH_SHORT).show();
                    }
                });

    }

}