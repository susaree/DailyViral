package com.mohammed.dailyviral;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.annotation.NonNull;


import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class VideoAdapter extends ArrayAdapter<Video> {

    private Context mContext;
    private List<Video> mVideos;
    DatabaseReference databaseURLs;

    public VideoAdapter(@NonNull Context context, @NonNull List<Video> objects) {
        super(context, R.layout.video_row, objects);
        mContext = context;
        mVideos = objects;
        databaseURLs = FirebaseDatabase.getInstance().getReference("Videos");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.video_row, null);
            holder = new ViewHolder();

            holder.videoView = convertView.findViewById(R.id.videoView);

            holder.addButton = convertView.findViewById(R.id.SaveVideo);
            holder.newUrlField = convertView.findViewById(R.id.newUrlTextView);
            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        /***get clicked view and play video url at this position**/
        try {

            holder.addButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    holder.newUrlField.setVisibility(View.VISIBLE);

                    Toast.makeText(mContext, "Paste URL", Toast.LENGTH_SHORT).show();

                }
            });

            holder.newUrlField.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence s, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (s != null && s.length() > 0 && s.charAt(s.length() - 1) == ' ') {
                        Video video = mVideos.get(position);
                        video.setURL(String.valueOf(s));

                        int positionInt = position+1;

                        String videoFinder = "Video" + positionInt;

                      databaseURLs.child(videoFinder).child("URL").setValue(String.valueOf(s));


                        holder.newUrlField.setVisibility(View.GONE);

                    }
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
        EditText newUrlField;

    }
}
