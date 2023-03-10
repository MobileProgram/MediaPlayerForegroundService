package com.mblhcmute.mediaplayerforegroundservice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private EditText edtDataIntent;
    private Button btnStartService;
    private Button btnStopService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtDataIntent = findViewById(R.id.edtDataIntent);
        btnStartService = findViewById(R.id.btnStartService);
        btnStopService = findViewById(R.id.btnStopService);

        btnStartService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickStartService();
            }
        });

        btnStopService.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                clickStopService();
            }
        });
    }

    private void clickStartService() {
        Song song = new Song("Let it go!","Idina Menzel", R.drawable.img_music, R.raw.letitgo);

        Intent intent = new Intent(this, MyService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("objectSong", song);
        intent.putExtras(bundle);

        startService(intent);
    }

    private void clickStopService() {
        Intent intent = new Intent(this, MyService.class);
        stopService(intent);
    }
}