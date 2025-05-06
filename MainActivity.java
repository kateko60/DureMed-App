package com.example.healthapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

//Doctors or employees screen
public class MainActivity extends AppCompatActivity {
    ImageView menu, editLogOut;
    TextView greetText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //get user details and greet
        SharedPreferences sharedPref = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
        String firstname = sharedPref.getString("firstname", "User");

        //convert First letter to uppercase the greet
        String edited_firstname = firstname.substring(0,1).toUpperCase() + firstname.substring(1).toLowerCase();
        TextView greetText = findViewById(R.id.editTextGreet);
        greetText.setText("Hi, Dr " + edited_firstname); // Greet with firstname

        //implement logout function
        editLogOut = findViewById(R.id.logOutBtn);
        editLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //clear sharedpreferences
                SharedPreferences sharedPrefs = getSharedPreferences("shared_prefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPrefs.edit();
                editor.clear();
                editor.apply();

                Intent navIntent = new Intent(MainActivity.this, Login.class);
                navIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(navIntent);
                Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_SHORT).show();
            }
        });
    }
}