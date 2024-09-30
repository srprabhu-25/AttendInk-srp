package com.example.attendink;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
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
import java.util.Calendar;

public class Viewabsent extends AppCompatActivity {

    ListView listview;
    Spinner class_name;
    String classes,ch;
    EditText date;
    ArrayList Userlist =new ArrayList();
    ArrayList Absentlist=new ArrayList();
    ImageButton imgdate;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;
    private static long back_pressed;

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    DatabaseReference dbAttendance=ref.child("Attendance");;
    DatabaseReference dbstudent=ref.child("Users").child("Students");
    String required_data;
    private ImageButton backBtn105;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewabsent);

        listview=(ListView)findViewById(R.id.list123);
        class_name=(Spinner) findViewById(R.id.spinner512);
        date=(EditText) findViewById(R.id.date12);
        imgdate=(ImageButton)findViewById(R.id.DateImageButton12);
        backBtn105=(ImageButton)findViewById(R.id.backBtn1050016);


        classes=class_name.getSelectedItem().toString();
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        dyear = cal.get(Calendar.YEAR);

        imgdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(0);
            }
        });

        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



    }

    public void viewabsent(View view)
    {
        classes=class_name.getSelectedItem().toString();
        Userlist.clear();
        dbstudent.orderByChild("section").equalTo(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp:snapshot.getChildren())
                {
                    Userlist.add(dsp.child("sid").getValue().toString());
                }
                displayabsent(Userlist);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void displayabsent(ArrayList Userlist) {
        Absentlist.clear();

        //  ch="A";
        //  + p1 + "         "
        // + "  Status "

        required_data = date.getText().toString();

        dbAttendance.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(required_data).exists()) {

                    dbAttendance.child(required_data).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {

                            Absentlist.add("Student id");

                            for (Object sid : Userlist) {

                                String p1 = snapshot.child(sid.toString()).child("p1").getValue().toString().substring(0, 1);

                                if (p1.equals("A")) {

                                    Absentlist.add(snapshot.child(sid.toString()).getKey().toString() + "             ");
                                } else {
                                    Toast.makeText(Viewabsent.this, "No student is absent", Toast.LENGTH_SHORT).show();
                                }

                            }
                            list(Absentlist);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
                        }
                    });

                } else {
                    Toast.makeText(Viewabsent.this, "Enter the date in correct format", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

            private void list(ArrayList Absentlist)
    {
        ArrayAdapter<String> adapter=new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,android.R.id.text1,Absentlist);
        listview.setAdapter(adapter);
    }


    public boolean onOptionsItemSelected(MenuItem item)
    {
        if(item.getItemId() == android.R.id.home)
        {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        return new DatePickerDialog(this, datePickerListener, dyear, month, day);
    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {
            date.setText(selectedDay + "-" + (selectedMonth + 1) + "-" + selectedYear);
        }
    };

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
