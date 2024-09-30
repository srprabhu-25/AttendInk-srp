package com.example.attendink;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;

public class Olddisplaystudent extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    RecyclerView recyclerView12;
    private Oldstudentadapter oldstudentadapter;
    private ArrayList<Oldstudentmodel> oldstudentmodelList;
    private EditText searchProductEt12;
    private ImageButton filterProductbtn12;
    private TextView filteredproducttv12;

    private static long back_pressed;
    private ImageButton backBtn1050011;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_olddisplaystudent);


        //searchProductEt12 = (EditText) findViewById(R.id.searchProductEt12);
        filterProductbtn12 = (ImageButton) findViewById(R.id.filterProductbtn12);
        filteredproducttv12 = (TextView) findViewById(R.id.selected12);
        backBtn1050011=(ImageButton)findViewById(R.id.backBtn1050011);


        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child("Students");
        mStorage = FirebaseStorage.getInstance();
        recyclerView12 = findViewById(R.id.rv12);
        recyclerView12.setHasFixedSize(true);
        recyclerView12.setLayoutManager(new LinearLayoutManager(this));

        oldstudentmodelList = new ArrayList<Oldstudentmodel>();
        oldstudentadapter = new Oldstudentadapter(Olddisplaystudent.this, oldstudentmodelList);
        recyclerView12.setAdapter(oldstudentadapter);

        // loadAllstudents();

        backBtn1050011.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
                //startActivity(new Intent(Olddisplaystudent.this,Teacherlogin.class));
               // finish();
            }
        });

        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Oldstudentmodel oldstudentmodel = snapshot.getValue(Oldstudentmodel.class);
                oldstudentmodelList.add(oldstudentmodel);
                oldstudentadapter.notifyDataSetChanged();

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        filterProductbtn12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Olddisplaystudent.this);
                builder.setTitle("Choose the Section")
                        .setItems(Constantsabc.section1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected = Constantsabc.section1[which];
                                filteredproducttv12.setText(selected);
                                if (selected.equals("All")) {
                                    loadAllstudents();
                                } else {
                                    loadfilteredstudents(selected);

                                }

                            }
                        })
                        .show();


            }
        });
    }


    private void loadAllstudents() {
        //child("sid").child("name")
        oldstudentmodelList = new ArrayList<>();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        reference1.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        oldstudentmodelList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Oldstudentmodel oldstudentmodel = ds.getValue(Oldstudentmodel.class);
                            oldstudentmodelList.add(oldstudentmodel);
                        }
                        oldstudentadapter = new Oldstudentadapter(Olddisplaystudent.this, oldstudentmodelList);
                        recyclerView12.setAdapter(oldstudentadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadfilteredstudents(String selected) {
        oldstudentmodelList = new ArrayList<>();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        reference1.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        oldstudentmodelList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String studentcategory = "" + ds.child("section").getValue();

                            if (selected.equalsIgnoreCase(studentcategory)) {
                                Oldstudentmodel oldstudentmodel = ds.getValue(Oldstudentmodel.class);
                                oldstudentmodelList.add(oldstudentmodel);
                            }
                        }

                        oldstudentadapter = new Oldstudentadapter(Olddisplaystudent.this, oldstudentmodelList);
                        recyclerView12.setAdapter(oldstudentadapter);

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