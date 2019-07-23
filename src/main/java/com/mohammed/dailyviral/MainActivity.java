package com.mohammed.dailyviral;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView mVideosListView;
    private List<Video> mVideosList;
    private VideoAdapter mVideoAdapter;

    DatabaseReference databaseURLs;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //assign video
        databaseURLs = FirebaseDatabase.getInstance().getReference("Videos");

        mVideosListView = (ListView) findViewById(R.id.videoListView);
        mVideosList = new ArrayList<>();


    }
    @Override
    protected void onStart() {
        super.onStart();

        databaseURLs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                mVideosList.clear();

                for (DataSnapshot videoSnapShot : dataSnapshot.getChildren()) {
                    Video videoSnap = videoSnapShot.getValue(Video.class);

                    Video video = new Video(videoSnap.getURL(),videoSnap.getDescription());

                    mVideosList.add(video);


                }

                mVideoAdapter = new VideoAdapter(MainActivity.this, mVideosList);
                mVideosListView.setAdapter(mVideoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
