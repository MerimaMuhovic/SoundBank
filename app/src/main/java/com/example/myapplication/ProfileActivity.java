package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class ProfileActivity extends AppCompatActivity {
    // Define variables:
    Button saveButton;
    EditText profileUsername, profileEmail, profilePassword;
    int currentId;

    SoundbankDatabase soundbankDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        // Initialize the variables:
        saveButton = findViewById(R.id.saveButton);
        profileUsername = findViewById(R.id.profileUsernameText);
        profileEmail = findViewById(R.id.profileEmailText);
        profilePassword = findViewById(R.id.profilePasswordText);

        // Initialize the database
        soundbankDatabase = SoundbankDatabase.getInstance(this);

        // Fetch and display the current account id sent from previous activity:
        currentId = getCurrentId();

        // Happens when we click Save Changes button:
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Updating the information when Save Changes button is clicked:
                soundbankDatabase.accountDao().updateById(profileUsername.getText().toString(), profileEmail.getText().toString(), profilePassword.getText().toString(), currentId);
                Toast.makeText(getApplicationContext(), "Changes saved!", Toast.LENGTH_SHORT).show();
            }
        });

        // Fetching the account from database to be able to get it's credentials:
        Account accountFromDb = soundbankDatabase.accountDao().getAccountById(currentId);

        // Displaying the initial credentials of the user:
        profileUsername.setText(accountFromDb.getUsername());
        profileEmail.setText(accountFromDb.getEmail());
        profilePassword.setText(accountFromDb.getPassword());
    }

    // Function that fetches the current id of account sent from the previous activity using intent:
    private int getCurrentId() {
        Intent intent = getIntent();
        return Integer.parseInt(intent.getStringExtra("currentId"));
    }
}