package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);

        firebaseAuth = FirebaseAuth.getInstance();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser user=firebaseAuth.getCurrentUser();
//                if(user==null)
//                {
//                    startActivity(new Intent(SplashActivity.this,starpage.class));
//                    finish();
//
//                }
//                else
//                {
//                    checkusertype();
//
//                }

            startActivity(new Intent(SplashActivity.this,starpage.class));
            finish();
                
            }
        }, 1000);

    }

//    private void checkusertype() {
//
//        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users");
//        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
//                .addListenerForSingleValueEvent(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//
//                        for(DataSnapshot dsp : snapshot.getChildren())
//                        {
//                            String accounttype=""+dsp.child("accounttype").getValue();
//                            if(accounttype.equals("Principal"))
//                            {
//                                startActivity(new Intent(SplashActivity.this,Adminlogin.class));
//                                finish();
//                            }
//                            else if(accounttype.equals("Students"))
//                            {
//                                startActivity(new Intent(SplashActivity.this,Studentlogin.class));
//                                finish();
//                            }
//                            else
//                            {
//                                startActivity(new Intent(SplashActivity.this,Teacherlogin.class));
//                                finish();
//                            }
//
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError error) {
//
//                    }
//                });
//
//
//    }


}
