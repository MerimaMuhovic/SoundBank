package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    // Defining variables:
    TextView sendToRegister, emailText, passwordText;
    Button loginButton;
    SoundbankDatabase soundbankDatabase;
    Account accountFromDb;
    int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initializing values:
        emailText = findViewById(R.id.emailText);
        passwordText = findViewById(R.id.passwordText);
        sendToRegister = findViewById(R.id.sendToRegisterTextView);
        loginButton = findViewById(R.id.loginButton);

        // Initializing the database:
        soundbankDatabase = SoundbankDatabase.getInstance(this);

        // Happens when we click register:
        sendToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToRegister();
            }
        });

        // Happens when we click login:
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login(emailText.getText().toString(), passwordText.getText().toString());
            }
        });
    }

    private boolean checkLogin(String email, String password) {
        // TO DO: In future development, encrypt the password in the database.
        // TO DO: Using Room Persistence Library validate the login using the database:

        // Checking if credentials are empty:
        if (TextUtils.isEmpty(email)) return false;
        if (TextUtils.isEmpty(password)) return false;

        // Fetching the account by email:
        accountFromDb = soundbankDatabase.accountDao().getAccountByEmail(email);

        // Checking if the account is valid:
        if (accountFromDb == null) return false;
        if (accountFromDb.getPassword().equals(password)) {
            return true;
        }
        return false;
    }

    private void login(String email, String password) {
        if(checkLogin(email, password)) {
            sendToSearch();
        } else {
            Toast.makeText(this, "Invalid credentials!", Toast.LENGTH_SHORT).show();
        }
    }

    // Send the user to SearchActivity:
    private void sendToSearch() {
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra("currentId", String.valueOf(accountFromDb.getId()));
        startActivity(intent);
    }

    // Sends the user to RegisterActivity:
    private void sendToRegister() {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}