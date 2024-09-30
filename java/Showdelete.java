package com.example.attendink;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Showdelete extends AppCompatActivity {


    private TextView tabstudentsIV,tabteachersIV;
    private RelativeLayout toolstudent,toolteacher;
    private RecyclerView removeteachrv1,removestudentrv1;


    private ArrayList<Removeteachmodel> removeteachmodelslist;
    private Removeteachadapter removeteachadapter;

    private static long back_pressed;
    private ArrayList<Removedstudentmodel> removestudentmodelslist;
    private Removestudentadapter removestudentadapter;
    private ImageButton backBtn105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showdelete);
        tabstudentsIV=findViewById(R.id.tabstudentsIV);
        tabteachersIV=findViewById(R.id.tabteachersIV);

        toolstudent=findViewById(R.id.toolstudent);
        toolteacher=findViewById(R.id.toolteacher);

        removeteachrv1=findViewById(R.id.removeteachrv1);
        removestudentrv1=findViewById(R.id.removestudentrv1);

        backBtn105=(ImageButton)findViewById(R.id.backBtn1050013);



        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        tabstudentsIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showstudentsUI();
            }
        });

        tabteachersIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showTerachersUI();
            }
        });
    }



    private void showTerachersUI() {

        toolteacher.setVisibility(View.VISIBLE);
        toolstudent.setVisibility(View.GONE);

        tabteachersIV.setTextColor(getResources().getColor(R.color.black));
        tabteachersIV.setBackgroundResource(R.drawable.shape_004);

        tabstudentsIV.setTextColor(getResources().getColor(R.color.white));
        tabstudentsIV.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        loadallteachers();


    }

    private void loadallteachers() {

        removeteachmodelslist=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("RemovedTeachers");
        reference.orderByChild("absection").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                removeteachmodelslist.clear();
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    Removeteachmodel removeteachmodel=dsp.getValue(Removeteachmodel.class);
                    removeteachmodelslist.add(removeteachmodel);

                }
                removeteachadapter=new Removeteachadapter(Showdelete.this,removeteachmodelslist);
                removeteachrv1.setAdapter(removeteachadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void showstudentsUI() {

        toolteacher.setVisibility(View.GONE);
        toolstudent.setVisibility(View.VISIBLE);

        tabteachersIV.setTextColor(getResources().getColor(R.color.white));
        tabteachersIV.setBackgroundColor(getResources().getColor(android.R.color.transparent));

        tabstudentsIV.setTextColor(getResources().getColor(R.color.black));
        tabstudentsIV.setBackgroundResource(R.drawable.shape_004);


        loadallstudents();
    }

    private void loadallstudents() {

        removestudentmodelslist=new ArrayList<>();
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("RemovedStudents");
        reference.orderByChild("absection").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                removestudentmodelslist.clear();
                for (DataSnapshot dsp : snapshot.getChildren())
                {
                    Removedstudentmodel removedstudentmodel=dsp.getValue(Removedstudentmodel.class);
                    removestudentmodelslist.add(removedstudentmodel);

                }
                removestudentadapter=new Removestudentadapter(Showdelete.this,removestudentmodelslist);
                removestudentrv1.setAdapter(removestudentadapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

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