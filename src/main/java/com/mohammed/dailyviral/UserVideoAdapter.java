package com.mohammed.dailyviral;


import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;


import android.view.View;

import java.util.List;
import android.view.LayoutInflater;
import android.view.MotionEvent;

import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import android.widget.ImageButton;

import android.widget.VideoView;

import androidx.annotation.NonNull;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.mohammed.dailyviral.SavedVideoAdapter.mSavedVideos;


public class UserVideoAdapter extends ArrayAdapter<Video> {

    private Context mContext;
    private List<Video> mVideos;
    DatabaseReference databaseURLs;
    URLCallback urlCB;

    public UserVideoAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context, R.layout.uservideo_row, objects);
        mContext = context;
        mVideos = objects;
        databaseURLs = FirebaseDatabase.getInstance().getReference("Videos");
        if(context instanceof URLCallback)
            urlCB = (URLCallback) context;
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.uservideo_row, null);
            holder = new ViewHolder();

            holder.videoView = convertView.findViewById(R.id.videoView);

            holder.addButton = convertView.findViewById(R.id.SaveVideo);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        /***get clicked view and play video url at this position**/
        try {

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Video video = mVideos.get(position);
                    urlCB.getURL(video.getURL());
                    SavedVideosFragment.mVideoAdapter.notifyDataSetChanged();
                    mSavedVideos.add(new SavedVideo(video.getURL()));
                }
            });


            Video video = mVideos.get(position);

            String url = video.getURL();
            Uri videoUri = Uri.parse(url);


            if (url.equals(" ")) {
                videoUri = null;


            }
            holder.videoView.setVideoURI(videoUri);


            if (videoUri != null) {
                holder.videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mp) {


                        mp.setLooping(true);
                        holder.videoView.start();
                        holder.videoView.pause();
                    }
                });
            }
            holder.videoView.setOnTouchListener(new View.OnTouchListener() {
                @Override

                public boolean onTouch(View view, MotionEvent motionEvent) {
                    int action = motionEvent.getAction();


                    if (action == motionEvent.ACTION_DOWN && holder.videoView.isPlaying()) {
                        holder.videoView.pause();
                    } else if (action == motionEvent.ACTION_DOWN) {
                        holder.videoView.start();
                    }
                    return true;
                }


            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        return convertView;
    }



    public static class ViewHolder {
        VideoView videoView;
        ImageButton addButton;

    }

    interface URLCallback{
        void getURL(String url);
    }
}
