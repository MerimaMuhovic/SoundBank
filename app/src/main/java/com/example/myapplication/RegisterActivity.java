package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.security.spec.ECField;

public class RegisterActivity extends AppCompatActivity {
    // Defining variables:
    TextView emailText, passwordText, usernameText, confirmPasswordText;
    Button registerButton;

    SoundbankDatabase soundbankDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Intializing the variables:
        emailText = findViewById(R.id.registerEmailText);
        passwordText = findViewById(R.id.registerPasswordText);
        usernameText = findViewById(R.id.usernameText);
        confirmPasswordText = findViewById(R.id.confirmPasswordText);
        registerButton = findViewById(R.id.registrationButton);

        soundbankDatabase = SoundbankDatabase.getInstance(this);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPassword(passwordText.getText().toString(), confirmPasswordText.getText().toString());
                register(emailText.getText().toString(), passwordText.getText().toString(), usernameText.getText().toString());
            }
        });
    }

    // Checks to see if password and confirm password are the same.
    private void checkPassword(String password, String confirmPassword) {
        try {
            if (!password.equals(passwordText.getText().toString())) {
                throw new Exception("Fields do not match!");
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void register(String email, String password, String username) {
        // TO DO: Use Room Persistence Library to register a new user into database.
        try {
            Account newAccount = new Account(username, password, email);
            soundbankDatabase.accountDao().addAccount(newAccount);
            Toast.makeText(this, "You have successfully registered!", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } catch(Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }
}