package com.example.attendink;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class Editstudent_bottom extends AppCompatActivity {


    private ImageButton backBtn105;
    private ImageView profileIv105;
    private TextView nameEt105, sidEt105, genderEt105,phoneEt105, addrEt105, ageEt105, emailEt105, passEt105 ;
    private EditText depEt105;
    private Button updateBtn105;

    private static long back_pressed;

    private static final int CAMERA_REQUEST_CODE = 200;
    private static final int STORAGE_REQUEST_CODE = 300;

    private static final int IMAGE_PICK_GALLERY_CODE = 400;
    private static final int IMAGE_PICK_CAMERA_CODE = 400;

    private Uri image_uri;
    private String[] cameraPermission;
    private String[] storagePermission;

    private FirebaseAuth firebaseAuth;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editstudent_bottom);

        backBtn105 = (ImageButton) findViewById(R.id.backBtn105);
        profileIv105 = (ImageView) findViewById(R.id.imgteacher105);
        updateBtn105=(Button)findViewById(R.id.updateBtn105);

        nameEt105= findViewById(R.id.newteacher1050);
        sidEt105 = findViewById(R.id.teacherid1050);
        genderEt105 = findViewById(R.id.gender1050);
        depEt105 = findViewById(R.id.department1050);

        phoneEt105 = findViewById(R.id.phoneno1050);
        addrEt105 = findViewById(R.id.address1050);
        ageEt105 = findViewById(R.id.age1050);
        emailEt105 = findViewById(R.id.emailid1050);
        passEt105 = findViewById(R.id.password1050);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please wait");
        progressDialog.setCanceledOnTouchOutside(false);

        firebaseAuth = FirebaseAuth.getInstance();
        checkUser();


        profileIv105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showImagePickDialog();
            }


        });

        backBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });

        updateBtn105.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                inputdata();

            }
        });

    }


}