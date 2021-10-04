package com.example.mymvvm.view.fragment.view;

import static com.example.mymvvm.Constants.ACTION_SET_TIMER;
import static com.example.mymvvm.Constants.MY_ACTION_KEY;
import static com.example.mymvvm.Constants.MY_SONG_KEY;
import static com.example.mymvvm.Constants.NULL_STRING;
import static com.example.mymvvm.Constants.SEEK_BAR;
import static com.example.mymvvm.Constants.TIME_KEY;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymvvm.R;
import com.example.mymvvm.model.Song;
import com.example.mymvvm.repo.Helper;
import com.example.mymvvm.service.MusicService;
import com.example.mymvvm.ultis.MAction;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class DetailActivity extends AppCompatActivity {
    private ImageView img, pre, next, play_or_pause, looping, timer;
    private TextView tvName, tvSinger, tvTimePast, tvDuration, showTime;
    private Song song;
    private Boolean isPLaying = true;
    private SeekBar seekBar;
    boolean isLooping = false;
    private Button button;
    private boolean isTimerSet;
    long getTime;
    private EditText editText;
    private View view;
    public boolean SETT_TIME = true;
    public boolean CANCEL_TIME = false;

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getExtras() != null) {
                Bundle bundle = intent.getExtras();
                song = (Song) bundle.get(MY_SONG_KEY);
                MAction mAction = (MAction) bundle.get(MY_ACTION_KEY);
                isPLaying = (Boolean) bundle.get("status");
                handleAction(mAction);
                setInfo();

            }

        }
    };
    private BroadcastReceiver timeBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Long time = intent.getLongExtra(TIME_KEY, 0);

            if (time > 1000) {
                //   showTime.setVisibility(View.VISIBLE);
                showTime.setText(Helper.timeFormat(time));
                button.setText(getString(R.string.button_setTimer_off));
                isTimerSet = true;
            } else {
                showTime.setText(NULL_STRING);
                button.setText(R.string.button_setTimer_on);
                isTimerSet = false;
            }


        }
    };
    private BroadcastReceiver seekBarBroadCastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int duration = intent.getIntExtra(SEEK_BAR, 0);
            seekBar.setProgress(duration);
            tvTimePast.setText(Helper.timeFormat((long) duration));
        }
    };

    private void handleAction(MAction mAction) {
        switch (mAction) {
            case PLAY:
                play_or_pause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
                break;
            case PAUSE:
                play_or_pause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
        }
    }

    private void actionDetail() {
        pre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActionService(MAction.PREV);

            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActionService(MAction.NEXT);
            }
        });
        play_or_pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isPLaying) {
                    startActionService(MAction.PAUSE);
                } else {
                    startActionService(MAction.PLAY);
                }
            }
        });
    }

    public void startActionService(MAction mAction) {
        Intent intent = new Intent(this, MusicService.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable(MY_ACTION_KEY, mAction);
        intent.putExtra(MY_ACTION_KEY, bundle);
        startService(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        init();

    }

    void init() {
        img = findViewById(R.id.detail_img);
        pre = findViewById(R.id.detail_pre);
        next = findViewById(R.id.detail_next);
        play_or_pause = findViewById(R.id.detail_play_or_pause);
        tvName = findViewById(R.id.detai_title);
        tvSinger = findViewById(R.id.detail_singer);
        seekBar = findViewById(R.id.seekBar);
        tvDuration = findViewById(R.id.duration);
        tvTimePast = findViewById(R.id.timePast);
        looping = findViewById(R.id.looping);
        timer = findViewById(R.id.timmer);

        actionDetail();
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, new IntentFilter("send_action"));
        LocalBroadcastManager.getInstance(this).registerReceiver(seekBarBroadCastReceiver, new IntentFilter(SEEK_BAR));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            song = (Song) bundle.get(MY_SONG_KEY);
            isPLaying = (Boolean) bundle.get("status");
            setInfo();
        }

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            long d = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                d = i;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                sendSeekBarDuration((long) d);
            }
        });

        setLooping();

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetTimer();
            }
        });

    }

    private void setInfo() {
        if (song != null) {
            tvName.setText(song.getTitle() + "");
            tvSinger.setText(song.getSinger() + "");
            //  img.setImageBitmap(Helper.imageFromPath(song.getThumbnail()));
            // img.setImageBitmap(song.getThumbnail());
            if (isPLaying) {
                play_or_pause.setImageResource(R.drawable.ic_baseline_pause_circle_filled_24);
            } else {
                play_or_pause.setImageResource(R.drawable.ic_baseline_play_circle_filled_24);
            }
            seekBar.setMax((int) song.getDuration());
            tvDuration.setText(Helper.timeFormat(song.getDuration()));
            Bitmap bt = Helper.getAlbumart(song.getThumbnail(), this);
            if(bt!=null){
                img.setImageBitmap(bt);
            }
            else{
                img.setImageResource(R.drawable.ic_baseline_music_note_24);
            }
        }


    }

    public void sendSeekBarDuration(Long duration) {
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(SEEK_BAR, duration);
        startService(intent);
    }

    public void setLooping() {
        looping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isLooping) {
                    looping.setImageResource(R.drawable.ic_baseline_repeat_one_24);
                    isLooping = true;
                } else {
                    isLooping = false;
                    looping.setImageResource(R.drawable.ic_baseline_repeat_24);
                }
                Intent intent = new Intent(DetailActivity.this, MusicService.class);
                intent.putExtra("loop", isLooping);
                startService(intent);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(DetailActivity.this).unregisterReceiver(broadcastReceiver);
        LocalBroadcastManager.getInstance(DetailActivity.this).unregisterReceiver(seekBarBroadCastReceiver);
        LocalBroadcastManager.getInstance(DetailActivity.this).unregisterReceiver(timeBroadcastReceiver);
    }

    private void initBottomView() {
        view = getLayoutInflater().inflate(R.layout.layout_bottom_sheet_timer, null);
        button = view.findViewById(R.id.turn_onOrOff);
        editText = view.findViewById(R.id.input_time);
        showTime = view.findViewById(R.id.showTime);
        // showTime.setVisibility(View.INVISIBLE);
        setButtonClick();
    }

    private void showBottomSheetTimer() {
        initBottomView();

        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();
        LocalBroadcastManager.getInstance(this).registerReceiver(timeBroadcastReceiver, new IntentFilter(TIME_KEY));


    }

    public void setButtonClick() {
        button.setOnClickListener(new View.OnClickListener() {
            long t = -1;

            @Override
            public void onClick(View view) {


                if (isTimerSet == false) {


                    if(!editText.getText().toString().equals(NULL_STRING)){
                        t = Long.valueOf(editText.getText().toString());
                        if (t > 0 ) {
                            setTime(t, SETT_TIME);
                            isTimerSet = true;
                            button.setText(getString(R.string.button_setTimer_off));
                            editText.setText(NULL_STRING);

                        }
                        else{
                            Toast.makeText(DetailActivity.this, getString(R.string.warning_invalid_timer_input), Toast.LENGTH_SHORT).show();

                        }
                    }
                    else {
                        Toast.makeText(DetailActivity.this, getString(R.string.warning_invalid_timer_input), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    t = 0;
                    setTime(t, CANCEL_TIME);
                    isTimerSet = false;
                    button.setText(getString(R.string.button_setTimer_on));
                }


            }
        });
    }

    private void setTime(long t, boolean action) {
        Intent intent = new Intent(DetailActivity.this, MusicService.class);
        //t = Long.valueOf(editText.getText().toString());
        intent.putExtra(TIME_KEY, t * 1000 * 60);
        intent.putExtra(ACTION_SET_TIMER, action);
        startService(intent);

    }


}