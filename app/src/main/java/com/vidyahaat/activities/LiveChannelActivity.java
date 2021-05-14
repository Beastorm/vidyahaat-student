package com.vidyahaat.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.vidyahaat.R;

import io.agora.rtc.Constants;
import io.agora.rtc.IRtcEngineEventHandler;
import io.agora.rtc.RtcEngine;
import io.agora.rtc.models.ClientRoleOptions;
import io.agora.rtc.video.VideoCanvas;
import io.agora.rtc.video.VideoEncoderConfiguration;

public class LiveChannelActivity extends AppCompatActivity {


    private static final int PERMISSION_REQ_ID = 22;
    private RtcEngine mRtcEngine;
    // Ask for Android device permissions at runtime.
    private static final String[] REQUESTED_PERMISSIONS = {
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.CAMERA,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };
    IRtcEngineEventHandler mRtcEventHandler;

    ImageView endCall;
    RelativeLayout initialContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_channel);

        endCall = findViewById(R.id.btn_call);
        initialContainer = findViewById(R.id.initial_container);
        mRtcEventHandler = new IRtcEngineEventHandler() {

            @Override
            // Listen for the onJoinChannelSuccess callback.
            // This callback occurs when the local user successfully joins the channel.
            public void onJoinChannelSuccess(String channel, final int uid, int elapsed) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("agora", "Join channel success, uid: " + (uid & 0xFFFFFFFFL));
                    }
                });
            }

            @Override
            // Listen for the onFirstRemoteVideoDecoded callback.
            // This callback occurs when the first video frame of the host is received and decoded after the host successfully joins the channel.
            // You can call the setupRemoteVideo method in this callback to set up the remote video view.
            public void onFirstRemoteVideoDecoded(final int uid, int width, int height, int elapsed) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        endCall.setVisibility(View.VISIBLE);
                        initialContainer.setVisibility(View.GONE);
                        Log.i("agora", "First remote video decoded, uid: " + (uid & 0xFFFFFFFFL));
                        setupRemoteVideo(uid);
                    }
                });
            }

            @Override
            // Listen for the onUserOffline callback.
            // This callback occurs when the host leaves the channel or drops offline.
            public void onUserOffline(final int uid, int reason) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("agora", "User offline, uid: " + (uid & 0xFFFFFFFFL));
                        //  onRemoteUserLeft();
                    }
                });
            }
        };

        // If all the permissions are granted, initialize the RtcEngine object and join a channel.
        if (checkSelfPermission(REQUESTED_PERMISSIONS[0], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[1], PERMISSION_REQ_ID) &&
                checkSelfPermission(REQUESTED_PERMISSIONS[2], PERMISSION_REQ_ID)) {
            initEngineAndJoinChannel();
        }


        endCall.setOnClickListener(view -> {
            leaveChannel();
            finish();
        });

    }

    private void initEngineAndJoinChannel() {
        initializeEngine();
        setChannelProfile();
        setUserRole();
        setupLocalVideo();
        joinChannel();
    }

    private void setUserRole() {
        ClientRoleOptions clientRoleOptions = new ClientRoleOptions();
        clientRoleOptions.audienceLatencyLevel = Constants.AUDIENCE_LATENCY_LEVEL_LOW_LATENCY;
        mRtcEngine.setClientRole(IRtcEngineEventHandler.ClientRole.CLIENT_ROLE_AUDIENCE, clientRoleOptions);
    }

    private boolean checkSelfPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(this, permission) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, REQUESTED_PERMISSIONS, requestCode);
            return false;
        }

        return true;
    }

    // Initialize the RtcEngine object.
    private void initializeEngine() {
        try {
            mRtcEngine = RtcEngine.create(getBaseContext(), getString(R.string.agora_app_id), mRtcEventHandler);
        } catch (Exception e) {
            Log.e("TAG", Log.getStackTraceString(e));
            throw new RuntimeException("NEED TO check rtc sdk init fatal error\n" + Log.getStackTraceString(e));
        }



    }

    private void setChannelProfile() {
        mRtcEngine.setChannelProfile(Constants.CHANNEL_PROFILE_COMMUNICATION);
        mRtcEngine.enableVideo();
        mRtcEngine.setVideoEncoderConfiguration(new VideoEncoderConfiguration(VideoEncoderConfiguration.VD_640x480, VideoEncoderConfiguration.FRAME_RATE.FRAME_RATE_FPS_30, VideoEncoderConfiguration.STANDARD_BITRATE, VideoEncoderConfiguration.ORIENTATION_MODE.ORIENTATION_MODE_FIXED_PORTRAIT));
    }

    private void setupLocalVideo() {

        // Enable the video module.
        mRtcEngine.enableVideo();

        // Create a SurfaceView object.
        FrameLayout mLocalContainer = findViewById(R.id.local_video_view_container);
        SurfaceView mLocalView;
        mLocalView = RtcEngine.CreateRendererView(getBaseContext());
        mLocalView.setZOrderMediaOverlay(true);
        mLocalContainer.addView(mLocalView);
        // Set the local video view.
        VideoCanvas localVideoCanvas = new VideoCanvas(mLocalView, VideoCanvas.RENDER_MODE_HIDDEN, 0);
        mRtcEngine.setupLocalVideo(localVideoCanvas);
    }

    private void joinChannel() {

        // For SDKs earlier than v3.0.0, call this method to enable interoperability between the Native SDK and the Web SDK if the Web SDK is in the channel. As of v3.0.0, the Native SDK enables the interoperability with the Web SDK by default.
        mRtcEngine.enableWebSdkInteroperability(true);
        // Join a channel with a token.
        String mRoomName;
        mRoomName = getIntent().getStringExtra("channel");
        mRtcEngine.joinChannel(getIntent().getStringExtra("token"), mRoomName, "Extra Optional Data", 0);
    }

    private void setupRemoteVideo(int uid) {

        // Create a SurfaceView object.
        RelativeLayout mRemoteContainer = findViewById(R.id.remote_video_view_container);
        SurfaceView mRemoteView;

        mRemoteView = RtcEngine.CreateRendererView(getBaseContext());
        mRemoteContainer.addView(mRemoteView);
        // Set the remote video view.
        mRtcEngine.setupRemoteVideo(new VideoCanvas(mRemoteView, VideoCanvas.RENDER_MODE_HIDDEN, uid));

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        leaveChannel();
        RtcEngine.destroy();
    }

    private void leaveChannel() {
        // Leave the current channel.
        mRtcEngine.leaveChannel();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_REQ_ID) {
            if (grantResults[0] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[1] != PackageManager.PERMISSION_GRANTED ||
                    grantResults[2] != PackageManager.PERMISSION_GRANTED) {
                showLongToast();
                finish();
                return;
            }

            // Here we continue only if all permissions are granted.
            // The permissions can also be granted in the system settings manually.
            initEngineAndJoinChannel();
        }
    }

    private void showLongToast() {
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), "Need permissions android.permission.RECORD_AUDIO/android.permission.CAMERA/android.permission.WRITE_EXTERNAL_STORAGE", Toast.LENGTH_LONG).show();
            }
        });
    }

}