package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class Login extends AppCompatActivity {
    EditText edusername, edpassword;
    Button loginButton;
    TextView edRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Initialize UI elements
        edusername = findViewById(R.id.editTextLoginUsername); // Email input
        edpassword = findViewById(R.id.editTextPassoword); // Password input
        edRegister = findViewById(R.id.registerNewUserBtn); // Register link
        loginButton = findViewById(R.id.loginBtn); // Login button

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edusername.getText().toString().trim();
                String password = edpassword.getText().toString().trim();

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please enter your credentials", Toast.LENGTH_SHORT).show();
                    return;
                }

                dbConnector db = new dbConnector(getApplicationContext(), "Healthcare", null, 1);
                String firstname = db.login(email, password); // Get firstname if login is valid

                if (firstname != null) {
                    String occupation = db.getOccupation(email, password);

                    if (occupation != null) {
                        occupation = occupation.toLowerCase();

                        Toast.makeText(getApplicationContext(), "Login successfully", Toast.LENGTH_SHORT).show();

                        // Save user data in SharedPreferences
                        SharedPreferences sharedPref = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString("email", email);
                        editor.putString("firstname", firstname);
                        editor.apply();

                        if (occupation.equals("doctor")) {
                            startActivity(new Intent(Login.this, MainActivity.class));
                        } else {
                            startActivity(new Intent(Login.this, userHome.class));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Could not determine occupation", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Invalid email or password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        edRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, register_user.class));
            }
        });
    }
}
