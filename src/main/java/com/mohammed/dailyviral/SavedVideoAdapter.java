package com.mohammed.dailyviral;

import android.annotation.SuppressLint;
import android.content.Context;
import android.media.MediaPlayer;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.VideoView;
import androidx.annotation.NonNull;

import java.io.File;
import java.util.List;



public class SavedVideoAdapter extends ArrayAdapter<SavedVideo> {


    private Context mContext;
    static List<SavedVideo> mSavedVideos;
    RefreshCallBack refreshCB;

    public SavedVideoAdapter(@NonNull Context context, @NonNull List<SavedVideo> objects) {
        super(context, R.layout.video_row, objects);
        mContext = context;
        mSavedVideos = objects;
        if(context instanceof RefreshCallBack)
            refreshCB = (RefreshCallBack) context;

    }

    @SuppressLint("ClickableViewAccessibility")
    @Override

    public View getView(final int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.savedvideo_row, null);
            holder = new ViewHolder();

            holder.videoView = convertView.findViewById(R.id.videoView);

            holder.removeButton = convertView.findViewById(R.id.removeVideo);

            convertView.setTag(holder);

        } else {

            holder = (ViewHolder) convertView.getTag();

        }


        /***get clicked view and play video url at this position**/
        try {

            holder.removeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SavedVideo video = mSavedVideos.get(position);
                    File file = new File(video.getFilePath());
                    mSavedVideos.remove(position);
                    file.delete();
                    notifyDataSetChanged();
                    refreshCB.refresh();



                    Toast.makeText(mContext, "Removed Video", Toast.LENGTH_SHORT).show();

                }
            });


            SavedVideo video = mSavedVideos.get(position);


            String videoPath = video.getFilePath();
            holder.videoView.setVideoPath(video.getFilePath());


            if (videoPath != null) {
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
        ImageButton removeButton;

    }

    public interface RefreshCallBack{
        void refresh();
    }


}
