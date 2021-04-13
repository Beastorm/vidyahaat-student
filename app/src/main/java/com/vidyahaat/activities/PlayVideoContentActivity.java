package com.vidyahaat.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.vidyahaat.R;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.vidyahaat.adapters.PlayListAdapter;
import com.vidyahaat.api.ApiClient;
import com.vidyahaat.api.ApiInterface;
import com.vidyahaat.model.video.VideoPlayListModel;
import com.vidyahaat.utilities.MyPreference;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlayVideoContentActivity extends AppCompatActivity implements PlayListAdapter.OnPlayListItemClickListener {

    PlayListAdapter playListAdapter;
    RecyclerView playListRv;
    ProgressBar progressBar;
    PlayerView playerView;
    SimpleExoPlayer simpleExoPlayer;
    TextView currentPlayListTv, infoTv;
    MyPreference myPreference;
    boolean isPlaying = false;
    String currentPlayListName, sln, videoUrl, videoId, courseId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_play_video_content);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        playListRv = findViewById(R.id.playlist_rv);
        progressBar = findViewById(R.id.progressBar);

        currentPlayListTv = findViewById(R.id.current_play_list_name);
        infoTv = findViewById(R.id.info_tv);
        myPreference = new MyPreference(this);

        courseId = getIntent().getStringExtra("course_id");

        LinearLayoutManager examLinearLayoutManager = new LinearLayoutManager(this);
        examLinearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        playListRv.setLayoutManager(examLinearLayoutManager);
        playListRv.setHasFixedSize(true);
        getALLVideoPlaylist();
        initExoPlayer(videoUrl);


        if (getIntent().getStringExtra("video_url") != null)
            onItemClicked(getIntent().getStringExtra("video_url"), getIntent().getStringExtra("currentPlayListName"), getIntent().getStringExtra("sln"), getIntent().getStringExtra("id"));

//        simpleExoPlayer.seekTo();

        if (isPlaying) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    //Do your work
                    Log.i("TAG", "onPlayerStateChanged: " + simpleExoPlayer.getDuration() / 1000 + "  " + simpleExoPlayer.getCurrentPosition() / 1000);
//                            videoWatchedTime= simpleExoPlayer.getCurrentPosition()/1000;
                }
            }, 1000);
        }
    }

    private void initExoPlayer(String videoUrl) {
        playerView = findViewById(R.id.player);
        simpleExoPlayer = ExoPlayerFactory.newSimpleInstance(this);


        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "Vidyahaat"));


        simpleExoPlayer.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, @Nullable Object manifest, int reason) {

            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playbackState == Player.STATE_BUFFERING) {

                    progressBar.setVisibility(View.VISIBLE);
                } else if (playbackState == Player.STATE_READY) {
                    progressBar.setVisibility(View.GONE);

                }


            }

            @Override
            public void onPlaybackSuppressionReasonChanged(int playbackSuppressionReason) {

            }

            @Override
            public void onIsPlayingChanged(boolean isPlaying) {

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });

    }

    private void getALLVideoPlaylist() {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        progressBar.setVisibility(View.VISIBLE);
        Call<VideoPlayListModel> call = apiInterface.getAllVideoPlayList(courseId);
        call.enqueue(new Callback<VideoPlayListModel>() {
            @Override
            public void onResponse(@NonNull Call<VideoPlayListModel> call, @NonNull Response<VideoPlayListModel> response) {
                progressBar.setVisibility(View.GONE);
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    if (response.body().getResponse().equals("Successful")) {

                        playListAdapter = new PlayListAdapter(response.body().getData(), PlayVideoContentActivity.this);
                        playListRv.setAdapter(playListAdapter);
                        playListAdapter.notifyDataSetChanged();

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<VideoPlayListModel> call, @NonNull Throwable t) {
                progressBar.setVisibility(View.GONE);
                Toast.makeText(PlayVideoContentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onItemClicked(String videoUrl, String currentPlayListName, String sln, String videoId) {

        isPlaying = true;
        this.videoUrl = videoUrl;
        this.currentPlayListName = currentPlayListName;
        this.sln = sln;
        this.videoId = videoId;

        infoTv.setVisibility(View.GONE);
        currentPlayListTv.setText(String.format("%s.  %s is playing...", sln, currentPlayListName));
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(this,
                Util.getUserAgent(this, "Vidyahaat"));

        MediaSource videoSource = new ExtractorMediaSource.Factory(dataSourceFactory)
                .createMediaSource(Uri.parse(videoUrl));

        playerView.setPlayer(simpleExoPlayer);
        playerView.setKeepScreenOn(true);

        simpleExoPlayer.prepare(videoSource);
        simpleExoPlayer.setPlayWhenReady(true);


        addVideoToWatchList(videoId);
    }

    private void addVideoToWatchList(String videoId) {
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<ResponseBody> call = apiInterface.addVideoToWatchList(videoId, myPreference.readStudentId());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {

                if (response.isSuccessful()) {
                    Log.i("TAG", "onResponse: added to watch list");
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Toast.makeText(PlayVideoContentActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        pausePlayer();
    }

    @Override
    protected void onResume() {
        super.onResume();
        startPlayer();
    }


    private void pausePlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(false);
            simpleExoPlayer.getPlaybackState();
        }

    }

    private void startPlayer() {
        if (simpleExoPlayer != null) {
            simpleExoPlayer.setPlayWhenReady(true);
            simpleExoPlayer.getPlaybackState();
        }


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {

        if (isPlaying) {
            outState.putString("video_url", videoUrl);
            outState.putString("currentPlayListName", currentPlayListName);
            outState.putString("sln", sln);
            outState.putString("video_id", videoId);
            outState.putString("course_id", courseId);
            outState.putLong("seek", simpleExoPlayer.getCurrentPosition());
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        onItemClicked(savedInstanceState.getString("video_url"), savedInstanceState.getString("currentPlayListName"), savedInstanceState.getString("sln"), savedInstanceState.getString("video_id"));
        simpleExoPlayer.seekTo(savedInstanceState.getLong("seek"));
    }
}