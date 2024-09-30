package com.example.attendink;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class forgotactivity extends AppCompatActivity {

    private ImageButton backbtn;
    private EditText emailEt;
    private Button recoverbtn;

    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    private static long back_pressed;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotactivity);

        backbtn=(ImageButton) findViewById(R.id.backbtn);
        recoverbtn=(Button)findViewById(R.id.recobtn);
       emailEt=(EditText)findViewById(R.id.emailid);

       firebaseAuth=FirebaseAuth.getInstance();
       progressDialog=new ProgressDialog(this);
       progressDialog.setTitle("Please Wait .....");
       progressDialog.setCanceledOnTouchOutside(false);

       backbtn.setOnClickListener((v) -> {
           onBackPressed();});

           recoverbtn.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   recoverPassword();
               }
           });


    }

    private  String email;
    private void recoverPassword() {
        email=emailEt.getText().toString().trim();
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            Toast.makeText(this, "Invalid Email.....  ", Toast.LENGTH_SHORT).show();
            return;
        }
        progressDialog.setMessage("Sending instructions to reset password.....");
        progressDialog.show();

        firebaseAuth.sendPasswordResetEmail(email)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        progressDialog.dismiss();
                        Toast.makeText(forgotactivity.this, "Password reset instructions sent to your email", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progressDialog.dismiss();
                        Toast.makeText(forgotactivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }

//    public void onBackPressed()
//    {
//        if (back_pressed + 2000 > System.currentTimeMillis()) {
//            super.onBackPressed();
//            finish();
//            ActivityCompat.finishAffinity(this);
//            System.exit(0);
//        } else {
//            Toast.makeText(getBaseContext(), "Press once again to exit", Toast.LENGTH_SHORT).show();
//            back_pressed = System.currentTimeMillis();
//        }
//    }
}
