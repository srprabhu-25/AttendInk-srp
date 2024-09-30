package com.example.attendink;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

public class StudentforAdmin extends AppCompatActivity {

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseStorage mStorage;
    RecyclerView recyclerView1;
    private Studentforadminadapter studentadapter;
    private ArrayList<Studentmodel> studentmodelList;
    private EditText searchProductEt1;
    private ImageButton filterProductbtn1, backBtn105004;
    private TextView filteredproducttv1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentfor_admin);

        searchProductEt1 = (EditText) findViewById(R.id.searchProductEt112);
        filterProductbtn1 = (ImageButton) findViewById(R.id.filterProductbtn112);
        filteredproducttv1 = (TextView) findViewById(R.id.selected112);
        backBtn105004 = (ImageButton) findViewById(R.id.backBtn10500412);


        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference("Users").child("Students");
        mStorage = FirebaseStorage.getInstance();
        recyclerView1 = findViewById(R.id.rv112);
        recyclerView1.setHasFixedSize(true);
        recyclerView1.setLayoutManager(new LinearLayoutManager(this));

        studentmodelList = new ArrayList<Studentmodel>();
        studentadapter = new Studentforadminadapter(StudentforAdmin.this, studentmodelList);
        recyclerView1.setAdapter(studentadapter);


        backBtn105004.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        searchProductEt1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    studentadapter.getFilter().filter(s);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

                Studentmodel studentmodel = snapshot.getValue(Studentmodel.class);
                studentmodelList.add(studentmodel);
                studentadapter.notifyDataSetChanged();

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


        filterProductbtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(StudentforAdmin.this);
                builder.setTitle("Choose the Section")
                        .setItems(Constantsabc.section1, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String selected = Constantsabc.section1[which];
                                filteredproducttv1.setText(selected);
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
        studentmodelList = new ArrayList<>();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        reference1.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        studentmodelList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            Studentmodel studentmodel = ds.getValue(Studentmodel.class);
                            studentmodelList.add(studentmodel);
                        }
                        studentadapter = new Studentforadminadapter(StudentforAdmin.this, studentmodelList);
                        recyclerView1.setAdapter(studentadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

    private void loadfilteredstudents(String selected) {
        studentmodelList = new ArrayList<>();
        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child("Students");
        reference1.orderByChild("section")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        studentmodelList.clear();
                        for (DataSnapshot ds : snapshot.getChildren()) {
                            String studentcategory = "" + ds.child("section").getValue();

                            if (selected.equalsIgnoreCase(studentcategory)) {
                                Studentmodel studentmodel = ds.getValue(Studentmodel.class);
                                studentmodelList.add(studentmodel);
                            }
                        }

                        studentadapter = new Studentforadminadapter(StudentforAdmin.this, studentmodelList);
                        recyclerView1.setAdapter(studentadapter);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
}