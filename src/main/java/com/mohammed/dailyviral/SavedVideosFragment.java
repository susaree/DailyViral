package com.mohammed.dailyviral;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;




import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;



import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SavedVideosFragment extends Fragment {
    private static final String TAG = "Tab2Fragment";
    private ListView mVideosListView;
    private List<SavedVideo> mVideosList;
    static SavedVideoAdapter mVideoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mVideosList = new ArrayList<>();


    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main3, container, false);
        mVideosListView = root.findViewById(R.id.videoListView);


        return root;
    }


    @Override
    public void onStart() {
        super.onStart();

       // String path = Environment.getExternalStorageDirectory() + File.separator + "androiddeft/";
        File filepath = Environment.getExternalStorageDirectory();
        File directory = new File(filepath.getAbsolutePath() + File.separator + "DailyVideos" + File.separator);
       // Log.d("Files", "Path: " + path);
       // File directory = new File(path);
         @Nullable File[] files = directory.listFiles();
      //  Log.d("Files", "Size: " + files.length);
        mVideosList.clear();

        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                SavedVideo video = new SavedVideo(files[i].getPath());
                mVideosList.add(video);
                Log.d("Files", "FileName:" + files[i].getName());

            }
        }

        mVideoAdapter = new SavedVideoAdapter(getActivity(), mVideosList);
        mVideosListView.setAdapter(mVideoAdapter);
        }






    }



