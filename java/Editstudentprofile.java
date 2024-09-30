package com.example.attendink;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class Editstudentprofile extends AppCompatActivity {

    private ImageButton backBtn105;
    private ImageView profileIv105;
    private TextView nameEt105, sidEt105, genderEt105, depEt105;
    private EditText phoneEt105, addrEt105, ageEt105, emailEt105, passEt105;
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
        setContentView(R.layout.activity_editstudentprofile);

        backBtn105 = (ImageButton) findViewById(R.id.backBtn105);
        profileIv105 = (ImageView) findViewById(R.id.imgteacher105);
        updateBtn105=(Button)findViewById(R.id.updateBtn105);

        nameEt105= findViewById(R.id.newteacher105);
        sidEt105 = findViewById(R.id.teacherid105);
        genderEt105 = findViewById(R.id.gender105);
        depEt105 = findViewById(R.id.department105);

        phoneEt105 = findViewById(R.id.phoneno105);
        addrEt105 = findViewById(R.id.address105);
        ageEt105 = findViewById(R.id.age105);
        emailEt105 = findViewById(R.id.emailid105);
        passEt105 = findViewById(R.id.password105);

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


    private void checkUser() {

        FirebaseUser user = firebaseAuth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            finish();

        } else
        {
            loadMyinfo();
        }

    }


    private void loadMyinfo()
    {
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference("Users").child("Students");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        for(DataSnapshot dsp:snapshot.getChildren())
                        {
                            String name=""+dsp.child("name").getValue();
                            String sid = "" + dsp.child("sid").getValue();
                            String dep = "" + dsp.child("section").getValue();
                            String email = "" + dsp.child("email").getValue();
                            String profileimage = "" + dsp.child("profileimage").getValue();
                            String pass = "" + dsp.child("password").getValue();
                            String phone = "" + dsp.child("phone").getValue();
                            String addr = "" + dsp.child("address").getValue();
                            String age = "" + dsp.child("age").getValue();
                            String gender = "" + dsp.child("gender").getValue();

                            nameEt105.setText(name);
                            sidEt105.setText(sid);
                            depEt105.setText(dep);
                            emailEt105.setText(email);
                            passEt105.setText(pass);
                            phoneEt105.setText(phone);
                            addrEt105.setText(addr);
                            ageEt105.setText(age);
                            genderEt105.setText(gender);

                            try {
                                Picasso.get().load(profileimage).placeholder(R.drawable.ic_person_sanju).into(profileIv105);

                            } catch (Exception e) {
                                profileIv105.setImageResource(R.drawable.ic_person_sanju);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

    }


    private String name105, sid105, gender105, depart105, phone105, addr105, email105, pass105, age105;

    private void inputdata() {

        name105 = nameEt105.getText().toString().trim();
        sid105 = sidEt105.getText().toString().trim();
        gender105 = genderEt105.getText().toString();
        depart105 = depEt105.getText().toString();
        phone105 = phoneEt105.getText().toString();
        addr105 = addrEt105.getText().toString();
        email105 = emailEt105.getText().toString();
        pass105 = passEt105.getText().toString();
        age105 = ageEt105.getText().toString();
        updateProfile();
    }

    private void updateProfile() {

        progressDialog.setMessage("updating profile");
        progressDialog.show();

        if(image_uri==null)
        {

            HashMap<String, Object> hashMap = new HashMap<>();
            hashMap.put("email", "" + email105);
            hashMap.put("name", "" + name105);
            hashMap.put("sid", "" + sid105);
            hashMap.put("phone", "" + phone105);
            hashMap.put("age", "" + age105);
            hashMap.put("section", "" + depart105);
            hashMap.put("address", "" + addr105);
            hashMap.put("password", "" + pass105);
            hashMap.put("gender", "" + gender105);

            DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("Students");
            ref.child(sid105).updateChildren(hashMap)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressDialog.dismiss();
                            Toast.makeText(Editstudentprofile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(Editstudentprofile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });

        }
        else
        {
            String filePathname="profile_images/" +""+firebaseAuth.getUid();
            StorageReference storageReference= FirebaseStorage.getInstance().getReference(filePathname);
            storageReference.putFile(image_uri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                            while(!uriTask.isSuccessful());
                            Uri downloadImageUri=uriTask.getResult();

                            if(uriTask.isSuccessful())
                            {

                                HashMap<String, Object> hashMap = new HashMap<>();
                                hashMap.put("email", "" + email105);
                                hashMap.put("name", "" + name105);
                                hashMap.put("sid", "" + sid105);
                                hashMap.put("phone", "" + phone105);
                                hashMap.put("age", "" + age105);
                                hashMap.put("section", "" + depart105);
                                hashMap.put("address", "" + addr105);
                                hashMap.put("password", "" + pass105);
                                hashMap.put("gender", "" + gender105);
                                hashMap.put("profileimage",""+downloadImageUri);

                                DatabaseReference ref=FirebaseDatabase.getInstance().getReference("Users").child("Students");
                                ref.child(sid105).updateChildren(hashMap)
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Editstudentprofile.this, "Profile updated successfully", Toast.LENGTH_SHORT).show();
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                progressDialog.dismiss();
                                                Toast.makeText(Editstudentprofile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) { progressDialog.dismiss();
                            Toast.makeText(Editstudentprofile.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
        }

    }


    private void showImagePickDialog()
    {
        String[] options = {"Camera", "Gallery"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Image :")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which == 0) {
                            if (checkCamerapermissions()) {
                                pickFromcamera();
                            } else {
                                requestCamerapermission();
                            }
                        } else {
                            if (checkStoragepermission()) {
                                pickFromgallery();
                            } else {
                                requestStoragepermission();
                            }
                        }
                    }
                })
                .show();

    }


    private void requestStoragepermission() {

        ActivityCompat.requestPermissions(this, storagePermission, STORAGE_REQUEST_CODE);

    }


    private void pickFromgallery() {

        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_GALLERY_CODE);
    }


    private boolean checkStoragepermission() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result;
    }


    private void requestCamerapermission() {

        ActivityCompat.requestPermissions(this, cameraPermission, CAMERA_REQUEST_CODE);

    }


    private void pickFromcamera() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, "Temp_Image Title");
        contentValues.put(MediaStore.Images.Media.DESCRIPTION, "Temp_image Description");

        image_uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, image_uri);
        startActivityForResult(intent, IMAGE_PICK_CAMERA_CODE);
    }


    private boolean checkCamerapermissions() {

        boolean result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == (PackageManager.PERMISSION_GRANTED);
        boolean result1 = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == (PackageManager.PERMISSION_GRANTED);
        return result && result1;

    }



    public void onRequestPermissionresults(int requestcode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestcode) {
            case CAMERA_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storageAccepted = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted && storageAccepted) {
                        pickFromcamera();
                    } else {
                        Toast.makeText(this, "Camera permissions are neccessary ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;
            case STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    boolean storageAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (storageAccepted) {
                        pickFromcamera();
                    } else {
                        Toast.makeText(this, "Storage permission are required ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            break;

        }
        super.onRequestPermissionsResult(requestcode, permissions, grantResults);
    }


    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == IMAGE_PICK_GALLERY_CODE) {
                image_uri = data.getData();
                profileIv105.setImageURI(image_uri);
            } else if (requestCode == IMAGE_PICK_CAMERA_CODE) {
                profileIv105.setImageURI(image_uri);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
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