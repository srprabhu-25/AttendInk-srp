package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class starpage extends AppCompatActivity {
Button btnadmin,btnteacher,btnstudent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starpage);
        btnadmin=(Button)findViewById(R.id.btnadmin);
        btnteacher=(Button) findViewById(R.id.btnteacher);
        btnstudent=(Button) findViewById(R.id.btnstudent);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminadd();
            }
        });
        btnstudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentadd();
            }
        });
        btnteacher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacheradd();
            }
        });
    }

    private void teacheradd() {
        Intent intent=new Intent(starpage.this,loginactivityteach.class);
        startActivity(intent);
    }

    private void studentadd() {

        Intent intent=new Intent(starpage.this,LoginActivity.class);

        startActivity(intent);
    }

    private void adminadd() {
        Intent intent=new Intent(starpage.this,adminstart.class);
        startActivity(intent);
    }
}