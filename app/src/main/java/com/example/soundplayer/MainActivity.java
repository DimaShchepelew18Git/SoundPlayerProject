package com.example.soundplayer;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {

    ListView lvDate;

    SeekBar sbValue;
    ImageView btnPlay,btnPause,btnStop;
    MediaPlayer mediaPlayer;
    Handler handler = new Handler();//
    //Runnable runnable;//
    boolean isPlay = false,isPause = false,isStop = false;// булева переменная для работоспособности кнопок

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvDate = findViewById(R.id.lvDate);
        
        sbValue = findViewById(R.id.sbValue); //SeekBar

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        mediaPlayer = MediaPlayer.create(this,R.raw.burito); //Для воспроизведения музыки
        sbValue.setMax(mediaPlayer.getDuration());//Макс. продолжительность песни

        //обработчик соприкосновения с SeekBar
        sbValue.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

                //проверяем, что это пользователь
                if(fromUser){
                    mediaPlayer.seekTo(progress); //можем перематывать песню с помощью SeekBar
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        btnPlay.setAlpha(0.5F); //кнопка играть, прозрачность 50%
        btnPause.setAlpha(0.5F); //кнопка пауза, прозрачность 50%
        btnStop.setAlpha(0.5F); //кнопка стоп, прозрачность 50%

        btnPlay.setOnClickListener(v -> {
            if(!isPlay){
                mediaPlayer.start();
                //isPause = true;
                isPlay = true;
                btnPause.setAlpha(1F);
                btnPlay.setAlpha(0.5F);
                btnStop.setAlpha(1F);

                UpdateSeekBar updateSeekBar = new UpdateSeekBar();
                handler.post(updateSeekBar);
            }
        });
        btnPause.setOnClickListener(v -> {
            if(isPlay){
                mediaPlayer.pause();
                //isPause = false;
                isPlay = false;
                btnPause.setAlpha(0.5F);
                btnPlay.setAlpha(1F);
               //btnStop.setAlpha(1F);
            }
        });
        btnStop.setOnClickListener(v -> { //new View.OnClickListener()
                //if(isStop){
                    mediaPlayer.stop();
                    isPlay = false;
                    btnPlay.setAlpha(0.5F);
                    btnPause.setAlpha(0.5F);
                    btnStop.setAlpha(0.5F);
                //}
        });
    }
    //класс Runnable для движения песни (создание после OnCreate)
    public class UpdateSeekBar implements Runnable{

        @Override
        public void run() {
            sbValue.setProgress(mediaPlayer.getCurrentPosition());
            handler.postDelayed(this,1000); //задержка песни
        }
    }
}