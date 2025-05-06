package com.example.healthapp;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class register_user extends AppCompatActivity {
    EditText EdName, EdPassword, EdSurname, EdOccupation, EdEmail;
    Button registerButton;
    TextView edLogin;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register_user);

        EdName = findViewById(R.id.editTextName);
        EdSurname = findViewById(R.id.editTextSurname);
        EdEmail = findViewById(R.id.editTextEmail);
        EdOccupation = findViewById(R.id.editTextOccupation);
        EdPassword = findViewById(R.id.editTextPassoword);
        registerButton = findViewById(R.id.registerBtn);
        edLogin = findViewById(R.id.loginText);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = EdName.getText().toString().trim();
                String lastname = EdSurname.getText().toString().trim();
                String email = EdEmail.getText().toString().trim();
                String occupation = EdOccupation.getText().toString().trim();
                String userPassword = EdPassword.getText().toString().trim();

                dbConnector db = new dbConnector(getApplicationContext(), "Healthcare", null, 1);

                if (firstname.isEmpty() || lastname.isEmpty() || email.isEmpty() || occupation.isEmpty() || userPassword.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!isValidPassword(userPassword)) {
                    Toast.makeText(getApplicationContext(),
                            "Password must be at least 8 characters with a letter, digit, and symbol",
                            Toast.LENGTH_LONG).show();
                    return;
                }

                db.register(firstname, lastname, email, occupation, userPassword);
                Toast.makeText(getApplicationContext(), "Successfully registered", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(register_user.this, Login.class));
            }
        });

        edLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register_user.this, Login.class));
            }
        });
    }

    public static boolean isValidPassword(String pass) {
        int hasLetter = 0, hasDigit = 0, hasSymbol = 0;

        if (pass.length() < 8) return false;

        for (char c : pass.toCharArray()) {
            if (Character.isLetter(c)) hasLetter = 1;
            else if (Character.isDigit(c)) hasDigit = 1;
            else if ((c >= 33 && c <= 46) || c == 64) hasSymbol = 1;
        }

        return hasLetter == 1 && hasDigit == 1 && hasSymbol == 1;
    }
}
