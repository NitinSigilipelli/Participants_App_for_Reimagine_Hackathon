package com.example.reimagine;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.File;

public class MainActivity extends AppCompatActivity
{
    Button loginBtn;
    private EditText emailET;
    private EditText passEt;




    //Firebase Authentication
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Firebase Connection
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        File dexOutputDir = getCodeCacheDir();
        dexOutputDir.setReadOnly();
        setContentView(R.layout.activity_main);
        loginBtn = findViewById(R.id.loginButton);
        emailET = findViewById(R.id.emailEditText);
        passEt = findViewById(R.id.passwordEditText);
        firebaseAuth = FirebaseAuth.getInstance();
        boolean isLoggedIn = checkLoginStatus();
        boolean isAdmin = checkAdminStatus();
        if (isLoggedIn && isAdmin) {
            // The user is logged in, proceed to the app's main content
            Intent i = new Intent(getApplicationContext(),AdminDashboardActivity.class);
            startActivity(i);
            finish();
        } else if(isLoggedIn) {
            // The user is not logged in, show the login screen or take appropriate action
            Intent i = new Intent(getApplicationContext(),DashboardActivity.class);
            startActivity(i);
            finish();
        }
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginEmailPasswordUser(
                        emailET.getText().toString().trim(),
                        passEt.getText().toString().trim()
                );
            }
        });

    }
    private void LoginEmailPasswordUser(String email, String pwd) {
        // Checking for empty texts
        if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {
            firebaseAuth.signInWithEmailAndPassword(email, pwd)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Login successful, retrieve user ID
                                String userId = firebaseAuth.getCurrentUser().getUid();
                                // Fetch participant document based on user ID
                                FirebaseFirestore.getInstance().collection("Participants")
                                        .document(userId)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    DocumentSnapshot document = task.getResult();
                                                    if (document.exists()) {
                                                        // Participant document found, check if isAdmin is true
                                                        boolean isAdmin = document.getBoolean("isAdmin");
                                                        if (isAdmin) {
                                                            // User is admin, navigate to DashboardActivity
                                                            Toast.makeText(MainActivity.this, "Admin login successful", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(MainActivity.this, AdminDashboardActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                            saveLoginStatus(true);
                                                            saveAdminStatus(true);
                                                        } else {
                                                            Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                                                            Intent i = new Intent(MainActivity.this, DashboardActivity.class);
                                                            startActivity(i);
                                                            finish();
                                                            saveLoginStatus(true);
                                                        }
                                                    } else {
                                                        // Participant document does not exist
                                                        Toast.makeText(MainActivity.this, "Participant document not found", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
                                                    // Error fetching participant document
                                                    Toast.makeText(MainActivity.this, "Error fetching participant document", Toast.LENGTH_SHORT).show();
                                                    Log.e(TAG, "Error getting participant document", task.getException());
                                                }
                                            }
                                        });
                            } else {
                                // Login failed
                                Toast.makeText(MainActivity.this, "Login failed. Please check your credentials", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            if (e instanceof FirebaseAuthInvalidCredentialsException) {
                                passEt.setError("Invalid Password");
                                passEt.requestFocus();
                            } else if (e instanceof FirebaseAuthInvalidUserException) {
                                emailET.setError("Email Not Registered");
                            } else {
                                Toast.makeText(MainActivity.this, "Something went Wrong", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }
    private void saveLoginStatus(boolean isLoggedIn) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isLoggedIn", isLoggedIn);
        editor.apply();
    }
    private void saveAdminStatus(boolean isAdmin) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("isAdmin", isAdmin);
        editor.apply();
    }

    private boolean checkLoginStatus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isLoggedIn", false);
    }
    private boolean checkAdminStatus() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        return sharedPreferences.getBoolean("isAdmin", false);
    }



}