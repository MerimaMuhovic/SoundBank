package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    // Defining variables:
    Button searchButton, profileButton;
    TextView searchText;
    ListView songsList;
    SoundbankDatabase soundbankDatabase;
    ArrayList<String> songNames;
    ArrayList<String> resourceNames;
    int currentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Initializing the variables:
        searchButton = findViewById(R.id.searchButton);
        profileButton = findViewById(R.id.profileButton);
        searchText = findViewById(R.id.searchText);
        songsList = findViewById(R.id.songsList);

        // Initializing the List View:
        listSongs();

        // Initializing the database:
        soundbankDatabase = SoundbankDatabase.getInstance(this);

        // Fetching the id of current account being used:
        currentId = getCurrentId();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search(searchText.getText().toString());
            }
        });

        profileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendToProfile();
            }
        });

        songsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
                intent.putExtra("resource_name", resourceNames.get(position));
                intent.putExtra("full_song_name", songNames.get(position));
                startActivity(intent);
            }
        });
    }

    // Initially lists songs in the List View:
    private void listSongs() {
        // Used to display the song name in List View:
        songNames = new ArrayList<String>();
        // Since the names in resources can't contain spaces or weird characters, the names are concatenated.
        // This serves as a way to read from the raw folder the names of songs. (Example Sickick - Last Time is lasttime):
        resourceNames = new ArrayList<String>();

        // Procedure for addings songs through code:
        songNames.add("Sickick - Last Time");
        resourceNames.add("lasttime");

        songNames.add("Demo");
        resourceNames.add("demo");

        songNames.add("EpicSaxGuy");
        resourceNames.add("epicsaxguy");

        songNames.add("The Passenger");
        resourceNames.add("thepassenger");

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, songNames);
        songsList.setAdapter(adapter);
    }

    // Function that fetches the id of the current account logged in using intent sent from previous activity:
    private int getCurrentId() {
        Intent intent = getIntent();
        return Integer.parseInt(intent.getStringExtra("currentId"));
    }

    // Sends the user to the Profile Activity:
    private void sendToProfile() {
        Intent intent = new Intent(this, ProfileActivity.class);
        intent.putExtra("currentId", String.valueOf(currentId));
        startActivity(intent);
    }

    private void search(String searchString) {
        // Checking to see if the search string is empty:
        if (searchString.equals("")) {
            for(int i = 0; i < songNames.size(); i++) {
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, songNames);
                songsList.setAdapter(adapter);
            }
        }

        // To ensure correct listing, we created a new array in which we store the names and that the adapter shows in the ListView:
        ArrayList<String> searchedNames = new ArrayList<String>();
        for(int i = 0; i < songNames.size(); i++) {
            // toLowerCase() is used for case sensitivity of the search string:
            if(songNames.get(i).toLowerCase().contains(searchString.toLowerCase())) {
                searchedNames.add(songNames.get(i));
            }
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, searchedNames);
            songsList.setAdapter(adapter);
        }
    }
}