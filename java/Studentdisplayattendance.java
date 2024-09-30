package com.example.attendink;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Studentdisplayattendance extends AppCompatActivity {

   private ListView listView;
    private String sid, teacher_id;

    private static long back_pressed;
    private EditText date;
    private  ArrayList Userlist = new ArrayList<>();
    private ArrayList Studentlist = new ArrayList<>();

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference dbAttendance;
    private DatabaseReference dbStudent = ref.child("Users");
    private String required_date,classes;
    private  Spinner classes12;
    private ImageButton backBtn105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentdisplayattendance);

        listView = (ListView) findViewById(R.id.listsrp);
        date = (EditText) findViewById(R.id.datesrp);
       // Bundle bundle1 = getIntent().getExtras();
      //  sid = bundle1.getString("sid");

        backBtn105=(ImageButton)findViewById(R.id.backBtn105003);

        classes12=(Spinner)findViewById(R.id.spinner25102002);
         classes=classes12.getSelectedItem().toString();


        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    public void viewList(View v) {

        classes=classes12.getSelectedItem().toString();
        Userlist.clear();
        dbStudent = dbStudent.child("Students");
        dbStudent.orderByChild("section").equalTo(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    Userlist.add(dsp.child("sid").getValue().toString());
                }
                display_list(Userlist);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
            }

        });
    }


    public void display_list(final ArrayList userlist) {

        Studentlist.clear();
        required_date = date.getText().toString();
        dbAttendance = ref.child("Attendance");
        Studentlist.add("      SID       "+"Status");
            dbAttendance.child(required_date).addValueEventListener(new ValueEventListener() {
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(Object dsp :userlist)
                {
                    String p1 = snapshot.child(dsp.toString()).child("p1").getValue().toString().substring(0, 1);

                    if((p1.equals("A"))||(p1.equals("P")))
                    {
                        Studentlist.add(snapshot.child(dsp.toString()).getKey().toString() + "                  " + p1 + "    ");

                    }
                }
                list(Studentlist);

            }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(getApplicationContext(), "something went wrong", Toast.LENGTH_LONG).show();
                }

            });




    }
    public void list(ArrayList studentlist){

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, android.R.id.text1, studentlist);
        listView.setAdapter(adapter);


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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