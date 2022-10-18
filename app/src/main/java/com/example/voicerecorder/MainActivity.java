package com.example.voicerecorder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.ContextWrapper;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    private static int MICROPHONE_PERMISSION_CODE=200;
    MediaRecorder mediaRecorder;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(isMicrophonePresent()){
            getMicrophonePermission();
        }

        }
    public void btnRecordPressed(View v){
        try{

            mediaRecorder =new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setOutputFile(getFilePath());
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.prepare();
            mediaRecorder.start();
            Toast.makeText(this, "Recording is started...", Toast.LENGTH_SHORT).show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }public void btnPlayPressed(View v){
        try {
            mediaPlayer =new MediaPlayer();
            mediaPlayer.setDataSource(getFilePath());
            mediaPlayer.prepare();
            mediaPlayer.start();
            Toast.makeText(this, "Recording is Playing...", Toast.LENGTH_SHORT).show();

        }catch (Exception e){
            e.printStackTrace();
        }

    }public void btnStopPressed(View v){
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder=null;
        Toast.makeText(this, "Recording is stop...", Toast.LENGTH_SHORT).show();
    }
    private boolean isMicrophonePresent(){
        if(this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_MICROPHONE))
            return true;
        else
            return false;
    }
    private void getMicrophonePermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)==PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.RECORD_AUDIO},MICROPHONE_PERMISSION_CODE);
        }
    }
    private String getFilePath(){
        ContextWrapper contextWrapper=new ContextWrapper(getApplicationContext());
        File musicDirectory=contextWrapper.getExternalFilesDir(Environment.DIRECTORY_MUSIC);
        File file=new File(musicDirectory,"testRecordingFile"+".mp3");
        return file.getPath();
    }
}