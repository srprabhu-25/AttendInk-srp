package com.example.attendink;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class LoginActivity extends AppCompatActivity {

    private EditText username, password;
    private String userid, pass;
    private DatabaseReference ref;
    private ProgressDialog progressDialog;
    private Button btnlogin;
    private TextView forgotIV;
    private FirebaseAuth firebaseAuth;

    private static long  back_pressed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        forgotIV = (TextView) findViewById(R.id.txt);

        btnlogin = (Button) findViewById(R.id.btnlogin);

        firebaseAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);




          onchechuser();

        forgotIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, forgotactivity.class));
            }
        });

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginCustomer();
            }
        });

    }


    private void onchechuser() {


        if(firebaseAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(LoginActivity.this,Studentlogin.class));
        }
        else
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginCustomer() {
        userid = username.getText().toString().trim();

        pass = password.getText().toString().trim();


        progressDialog.setTitle("Logging in..");
        progressDialog.show();

        if (TextUtils.isEmpty(userid)) {
            progressDialog.dismiss();
            Toast.makeText(this, " Enter Email..", Toast.LENGTH_SHORT).show();
            return;
        } else if (TextUtils.isEmpty(pass)) {
            progressDialog.dismiss();
            Toast.makeText(this, " Enter Password..", Toast.LENGTH_SHORT).show();
            return;
        }

        firebaseAuth.signInWithEmailAndPassword(userid, pass)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                     public void onSuccess(AuthResult authResult) {

                        makeMeOnline();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

    private void makeMeOnline()
    {

        progressDialog.setMessage("Checking the user.....");


        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child("Students").orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("online", "true");

                for (DataSnapshot dsp : snapshot.getChildren()) {

                    String teachid = "" + dsp.child("sid").getValue();
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
                    ref.child("Students").child(teachid).updateChildren(hashMap)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    checkusertype();

                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                    progressDialog.dismiss();
                                    Toast.makeText(LoginActivity.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();

                                }
                            });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });



    }

    private void checkusertype()
    {

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("Students");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dsp : snapshot.getChildren())
                        {
                            String accounttype=""+dsp.child("accounttype").getValue();
                            if(accounttype.equals("Student"))
                            {
                                progressDialog.dismiss();
                                startActivity(new Intent(LoginActivity.this,Studentlogin.class));
                                finish();
                            }

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