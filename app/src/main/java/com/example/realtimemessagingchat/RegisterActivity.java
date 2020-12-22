/**
 * Info about this package doing something for package-info.java file.
 */
package com.example.realtimemessagingchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import com.example.realtimemessagingchat.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;

public class RegisterActivity extends AppCompatActivity {
    /**
    *comment.
    */
    private FirebaseAuth auth;
    /**
     *comment.
     */
    private DatabaseReference reference;
    /**
     *comment.
     */
    private EditText username;
    /**
     *comment.
     */
    private EditText email;
    /**
     *comment.
     */
    private EditText password;
    /**
     *comment.
     */
    private Button register;
    /**
     *comment.
     */
    private ImageView imageView;
    /**
     *comment.
     */
    private Uri uri;
    /**
     *comment.
     */
    private String image;
    /**
     *comment.
     */
    private StorageReference storageRef;
    /**
     *comment.
     */
    private User userc;
    /**
     *comment.
     */
    private FirebaseStorage storage;
    /**
     *comment.
     */
    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth = FirebaseAuth.getInstance();
        imageView = findViewById(R.id.imageview);
        username = findViewById(R.id.editText_username);
        email = findViewById(R.id.editText_email);
        password = findViewById(R.id.editText_password);
        register = findViewById(R.id.btn_rgs);
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

        storage = FirebaseStorage.getInstance();
        storageRef = storage.getReference();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String usernames = username.getText().toString();
                String emails = email.getText().toString();
                String passwords = password.getText().toString();
                if (!TextUtils.isEmpty(usernames) && !TextUtils.isEmpty(emails)
                         && !TextUtils.isEmpty(passwords)) {
                    if (passwords.length() > 8) {
                      setRegister(v, usernames, emails, passwords);
                    } else {
                        Toast.makeText(RegisterActivity.this,
                                "يجب أن تكون كلمة السر 8 أحرف أو أكثر",
                                Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this,
                            "كل الخانات مطلوبة!!!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                imageViewclick(v);
            }
        });

    }

    /**
     *comment.
     */
    public void imageViewclick(final View view) {
          Intent gallery = new Intent(Intent.ACTION_PICK,
          MediaStore.Images.Media.INTERNAL_CONTENT_URI);
          gallery.setType("image/*");

        startActivityForResult(gallery, 200);
    }
    /**
     *comment.
     */
    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 200 && data != null) {
            Uri imagerUri = data.getData();
            imageView.setImageURI(imagerUri);
            uri = imagerUri;
            Toast.makeText(getApplicationContext(),
                    imagerUri.toString(), Toast.LENGTH_LONG).show();
        }

    }
    /**
     *comment.
     */
    public void setRegister(final View view, final String username,
                         final String email, final String password) {
        final ProgressDialog progressDialog =
                new ProgressDialog(RegisterActivity.this);
        progressDialog.setMessage("Uplode .....");
        progressDialog.show();

        if (uri == null) {
             image = "defult";
            auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener
                            ( new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete
                                ( final @NonNull Task<AuthResult> task )  {
                            if (task.isSuccessful()) {
                                FirebaseUser user = auth.getCurrentUser();
                                String userid = user.getUid();
                                reference = FirebaseDatabase.getInstance()
                                        .getReference("User").child(userid);
                                userc = new User(userid, username, image);

                                reference.setValue(userc)
                                        .addOnCompleteListener(
                                                new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete
                                            (final @NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            progressDialog.dismiss();
                                            Intent intent = new Intent(
                                                    RegisterActivity.this,
                                                    MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                            finish();
                                        }
                                    }
                                });
                            } else {
                                Toast.makeText(RegisterActivity.this, "ادخل الايميل و كلمة المرور", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                 imageView.setBackground(null);
        } else {
            Calendar calendar = Calendar.getInstance();


            final StorageReference filepath = storageRef.child("photo").child("image_" + calendar.getTimeInMillis());
            final UploadTask uploadTask = filepath.putFile(uri);

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(final UploadTask.TaskSnapshot taskSnapshot) {


                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(final Uri uri) {
                            Log.d("ss", "onSuccess: " + uri.toString());

                            image = uri.toString();
                            Toast.makeText(RegisterActivity.this, image, Toast.LENGTH_SHORT).show();

                            auth.createUserWithEmailAndPassword(email, password)
                                    .addOnCompleteListener(
                                            new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(final @NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()) {

                                                FirebaseUser user =
                                                        auth.getCurrentUser();
                                                String userid = user.getUid();
                                                reference = FirebaseDatabase.getInstance()
                                                        .getReference("User").child(userid);
                                                userc = new User(userid, username, image);

                                                reference.setValue(userc).addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(final @NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            progressDialog.dismiss();
                                                            Intent intent = new Intent(
                                                                    RegisterActivity.this, MainActivity.class);
                                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                                                                    | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(intent);
                                                            finish();
                                                        }
                                                    }
                                                });
                                            } else {
                                                Toast.makeText(RegisterActivity.this,
                                                         "ادخل الايميل و كلمة المرور",
                                                         Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                        }
                    });

                    imageView.setBackground(null);

                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(final @NonNull Exception e) {
                    Toast.makeText(RegisterActivity.this,
                            "onFailure", Toast.LENGTH_SHORT).show();

                }
            });
        }

    }


}
