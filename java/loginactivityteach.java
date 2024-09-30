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

public class loginactivityteach extends AppCompatActivity {

    private EditText username1, password1;
    private String userid1, pass1;
    private DatabaseReference ref;
    private ProgressDialog mDialog;
    private Button btnlogin1;
    private TextView forgotIV1;
    private FirebaseAuth firebaseAuth;
    private Bundle basket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginactivityteach);




        username1 = (EditText) findViewById(R.id.username1);
        password1 = (EditText) findViewById(R.id.password1);
        forgotIV1 = (TextView) findViewById(R.id.txt12);
        btnlogin1 = (Button) findViewById(R.id.btnlogin1);

        firebaseAuth = FirebaseAuth.getInstance();

        mDialog = new ProgressDialog(this);
        mDialog.setTitle("Please wait...");
        mDialog.setCanceledOnTouchOutside(false);


        oncheckuser();

        forgotIV1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(loginactivityteach.this, forgotactivity.class));
            }
        });

        btnlogin1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginuser();

            }
        });
    }

    private void oncheckuser() {


        if(firebaseAuth.getCurrentUser() !=null)
        {
            startActivity(new Intent(loginactivityteach.this,Teacherlogin.class));

        }
        else
        {
            Toast.makeText(this, "", Toast.LENGTH_SHORT).show();
        }

    }

    private void loginuser() {
        userid1 = username1.getText().toString().trim();
        pass1 = password1.getText().toString().trim();

        if (TextUtils.isEmpty(userid1)) {
            Toast.makeText(this, "Enter email.....  ", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(pass1)) {
            Toast.makeText(this, "Enter password.....", Toast.LENGTH_SHORT).show();
            return;
        }

        mDialog.setMessage("Logging in ....");
        mDialog.show();

        basket = new Bundle();
        basket.putString("message", userid1);

        firebaseAuth.signInWithEmailAndPassword(userid1,pass1)
                .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {

                        makeMeOnline();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        mDialog.dismiss();
                        Toast.makeText(loginactivityteach.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                    }
                });

    }

    private void makeMeOnline() {

        mDialog.setMessage("Checking the user.....");



        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users");
        ref.child("Teachers").orderByChild("uid").equalTo(firebaseAuth.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {


                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("online", "true");

                for (DataSnapshot dsp : snapshot.getChildren()) {

                    String teachid=""+dsp.child("tid").getValue();

                DatabaseReference ref1 = FirebaseDatabase.getInstance().getReference("Users");
                ref1.child("Teachers").child(teachid).updateChildren(hashMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                checkusertype();

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {

                                mDialog.dismiss();
                                Toast.makeText(loginactivityteach.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();


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

        DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("Teachers");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dsp : snapshot.getChildren())
                        {
                            String accounttype=""+dsp.child("accounttype").getValue();
                            if(accounttype.equals("Teacher"))
                            {
                                mDialog.dismiss();
                                Intent intent=new Intent(loginactivityteach.this,Teacherlogin.class);
                                intent.putExtras(basket);
                                startActivity(intent);
                                 finish();

                            }

                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }


}
