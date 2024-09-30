package com.example.attendink;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
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

public class Teacher_attendance_sheet extends AppCompatActivity {

    private ListView listview;
    private Spinner class_name;
    private String classes;
    private EditText date;
    private ArrayList Userlist =new ArrayList();
    private ArrayList Studentlist=new ArrayList();
    private ImageButton imgdate,backBtn105;
    private Calendar cal;
    private int day;
    private int month;
    private int dyear;


    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();

    DatabaseReference dbAttendance = ref.child("Attendance");
    DatabaseReference dbstudent = ref.child("Users").child("Students");

    String required_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_attendance_sheet);


        listview=(ListView)findViewById(R.id.list51);
        class_name=(Spinner) findViewById(R.id.spinner51);
        date=(EditText) findViewById(R.id.date51);
        imgdate=(ImageButton)findViewById(R.id.DateImageButton51);
        backBtn105=(ImageButton)findViewById(R.id.backBtn105000);

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

                startActivity(new Intent(Teacher_attendance_sheet.this,Teacherlogin.class));
                finish();
            }
        });
    }



    public void viewlist(View view)
    {
        classes=class_name.getSelectedItem().toString();
        Userlist.clear();
        dbstudent.orderByChild("section").equalTo(classes).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dsp : snapshot.getChildren())
                {
                    Userlist.add(dsp.child("sid").getValue().toString());
                    //Userlist.add(dsp.child("sid").child("p1").getValue().toString());

                }
                //  Toast.makeText(getApplicationContext(),Userlist.toString(), Toast.LENGTH_LONG).show();
                display_list(Userlist);


            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();
            }
        });

    }

//    public void onDataset(Calendar view,int year,int month,int day)
//    {
//        date.setText(ConverterDate.ConvertDate(year,month+1,day));
//
//    }

    public void display_list(final ArrayList Userlist) {
        Studentlist.clear();
        required_data = date.getText().toString();


        dbAttendance.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                if (snapshot.child(required_data).exists()) {
                    dbAttendance.child(required_data).addValueEventListener(new ValueEventListener() {

                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {


                            Studentlist.add("Student_id " + "   Status  ");


                            for (Object sid : Userlist) {

                                // String p1 = snapshot.child(sid.toString()).child("p1").getValue().toString().substring(0, 1);

                                Studentlist.add(snapshot.child(sid.toString()).getKey().toString() + "                  " + snapshot.child(sid.toString()).child("p1").getValue().toString().substring(0,1) + "    ");


                            }
                            //Toast.makeText(getApplicationContext(),Studentlist.toString(), Toast.LENGTH_LONG).show();
                            list(Studentlist);

                        }


                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                            Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_LONG).show();

                        }
                    });

                }
                else
                {
                    Toast.makeText(Teacher_attendance_sheet.this, "Enter the correct date ", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    public void list(ArrayList Studentlist) {

        ArrayAdapter<String> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,android.R.id.text1,Studentlist);
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
        public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {
            date.setText(selectedDay + "-" + (selectedMonth + 1) + "-"
                    + selectedYear);


        }
    };


}

