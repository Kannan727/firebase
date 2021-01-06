package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddtrackActivity extends AppCompatActivity {
    EditText trackname;
    SeekBar rating;
    TextView vehiclename;
    ListView listviewtracks;
    Button button;
    DatabaseReference databaseTracks;

    List<Track> tracks;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addtrack);

        vehiclename=findViewById(R.id.viewtrack1);
        trackname=findViewById(R.id.TrackName);
        rating=findViewById(R.id.Rating);
        listviewtracks=findViewById(R.id.ListViewTracks);
        button =findViewById(R.id.button);

        Intent intent=getIntent();

        tracks=new ArrayList<>();

        String id=intent.getStringExtra(MainActivity.VEHICLE_ID);
        String name=intent.getStringExtra(MainActivity.VEHICLE_NAME);
        vehiclename.setText(name);

        databaseTracks= FirebaseDatabase.getInstance("https://my-application-24aea-default-rtdb.firebaseio.com/").getReference("reference");
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTrack();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databaseTracks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                tracks.clear();
                for(DataSnapshot trackSnapshot : dataSnapshot.getChildren()){
                    Track track=trackSnapshot.getValue(Track.class);
                    tracks.add(track);
                }
                TrackList trackListAdapter = new TrackList(AddtrackActivity.this,tracks);
                listviewtracks.setAdapter(trackListAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void saveTrack()
    {
        String trackName = trackname.getText().toString().trim();
        int ratings = rating.getProgress();

        if(!TextUtils.isEmpty(trackName)){
            String id=databaseTracks.push().getKey();

            Track track=new Track(id,trackName,ratings);
            databaseTracks.child(id).setValue(track);
            Toast.makeText(this,"Track saved successfully",Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(this,"Track name should not be empty",Toast.LENGTH_LONG).show();
        }
    }
}