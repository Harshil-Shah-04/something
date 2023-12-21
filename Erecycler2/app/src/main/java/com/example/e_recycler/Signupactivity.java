package com.example.e_recycler;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Signupactivity extends AppCompatActivity {

    EditText editTextUsername;
    EditText editTextPassword;
    EditText editTextMobileNumber;
    EditText editTextEmail;
    ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupactivity);

        editTextUsername = findViewById(R.id.editTextusername);
        editTextPassword = findViewById(R.id.editTextpassword);
        editTextMobileNumber = findViewById(R.id.editTextMobileNumber);
        editTextEmail = findViewById(R.id.editTextEmail);
        progressBar = findViewById(R.id.progressBar);

        mAuth = FirebaseAuth.getInstance();
    }

    public void onclicksignupbutton(View view) {

        String txtUsername = editTextUsername.getText().toString().trim();
        String txtpassword = editTextPassword.getText().toString().trim();

        String txtmobilenumber = editTextMobileNumber.getText().toString().trim();

        String txtemail = editTextEmail.getText().toString().trim();

        if (txtUsername.isEmpty() || txtpassword.isEmpty() || txtmobilenumber.isEmpty() || txtemail.isEmpty()) {
            // Show a generic error message or individual messages for each field
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_LONG).show();
            return;
        }


        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(txtemail, txtpassword)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("SignUpActivity", "Firebase authentication successful");

                            User user = new User(txtUsername, txtpassword, txtemail, txtmobilenumber);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Log.d("SignUpActivity", "User registration successful");
                                                Toast.makeText(Signupactivity.this, "User registration successful", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            } else {
                                                Log.e("SignUpActivity", "Error adding user to database: " + task.getException().getMessage());
                                                Toast.makeText(Signupactivity.this, "User registration failed", Toast.LENGTH_LONG).show();
                                                progressBar.setVisibility(View.GONE);
                                            }
                                        }
                                    });

                        } else {
                            Log.e("SignUpActivity", "Firebase authentication failed: " + task.getException().getMessage());
                            Toast.makeText(Signupactivity.this, "User registration failed", Toast.LENGTH_LONG).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }

                });


    }
}
