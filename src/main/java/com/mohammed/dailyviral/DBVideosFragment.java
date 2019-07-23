package com.mohammed.dailyviral;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;


import androidx.annotation.NonNull;

import androidx.fragment.app.Fragment;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;



import java.util.ArrayList;
import java.util.List;

public class DBVideosFragment extends Fragment {

    private static final String TAG = "Tab1Fragment";



    private ListView mVideosListView;
    private List<Video> mVideosList;
    private UserVideoAdapter mVideoAdapter;

    private DatabaseReference databaseURLs;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




        databaseURLs = FirebaseDatabase.getInstance().getReference("Videos");


        mVideosList = new ArrayList<>();


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main2, container, false);
        mVideosListView = root.findViewById(R.id.videoListView);
        return root;
    }

    @Override
    public void onStart() {
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

                mVideoAdapter = new UserVideoAdapter(getActivity(), mVideosList);
                mVideosListView.setAdapter(mVideoAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}