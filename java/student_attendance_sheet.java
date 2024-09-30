package com.example.attendink;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class student_attendance_sheet extends AppCompatActivity {

    public static int count = 0, P = 0, A = 0;
    float average = (float) 0.0;
    private TextView studtext;
    private String avg, p1, p2, p3, p4, p5, p6, p7, p8;
    private String sstud;
    private ArrayList dates = new ArrayList<>();
    ;
    private DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference dbAttendance;
    private ListView studentlist;
    private FirebaseAuth firebaseAuth;
    TextView txtcount;
    private ImageButton backBtn105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_attendance_sheet);

        dbAttendance = ref.child("Attendance");
        studtext = (TextView) findViewById(R.id.studtext);
        txtcount = (TextView) findViewById(R.id.countdisp);

        backBtn105=(ImageButton)findViewById(R.id.backBtn1050014);

        studentlist = (ListView) findViewById(R.id.studlist);

        Intent in = getIntent();
        sstud = in.getExtras().getString("basket");

        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        studtext.setText(sstud);

        // Toast.makeText(this, sstud, Toast.LENGTH_SHORT).show();

        count = 0;
        P = 0;
        A = 0;

        dates.clear();
        dates.add("       Date          " + "  p1  ");


        dbAttendance.orderByChild(sstud).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dsp : snapshot.getChildren()) {
                    // sstud=bundle.getString("student");

                    String p1 = dsp.child(sstud.toString()).child("p1").getValue().toString().substring(0, 1);

                    dates.add(dsp.getKey() + "          " +dsp.child(sstud.toString()).child("p1").getValue()+"            "); //add result into array list


                     // p1 =dsp.child(sstud.toString()).child("p1").getKey().toString().substring(0,1);


                    // Toast.makeText(getApplicationContext(),dsp.child(sstud).child("p1").getValue().toString(),Toast.LENGTH_LONG).show();
                    //||p2.equals("P")||p3.equals("P")||p4.equals("P")||p5.equals("P")||p6.equals("P")||p7.equals("P")||p8.equals("P")

                    if (p1.equals("P")) {

                        P++;
                        count++;

                    }

                    //||p2.equals("A")||p3.equals("A")||p4.equals("A")||p5.equals("A")||p6.equals("A")||p7.equals("A")||p8.equals("A")

                    if (p1.equals("A")) {
                        A++;
                        count++;


                    }

                    txtcount.setText(" Present : " + P + "\n Absent : " + A + "\n      Total Attendance : " + count);

                }

                list(dates);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }


        });


    }

    private void list(ArrayList dates) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, dates);
        studentlist.setAdapter(adapter);
    }
    }