package com.yjkz;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Locale;
import java.util.Objects;

public class ScrollingActivity extends AppCompatActivity {
    public static String acid = "MainActivity";
    public static Info info = new Info();
    private UpdateBroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private TextView wd, sd, sb;
    private TextView wltext, whtext, sltext, shtext, gpiotext, eltext, cc;
    private EditTextS wled, whed, sled, shed, gpioed, eled;
    private TextView[] tl = null;
    private EditTextS[] el = null;
    private LinearLayout ddlj, ass, kzlb;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(acid);
        broadcastReceiver = new UpdateBroadcastReceiver();
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);


        wd = findViewById(R.id.wd);
        sd = findViewById(R.id.sd);
        sb = findViewById(R.id.sb);
        wltext = findViewById(R.id.wltext);
        wled = findViewById(R.id.wled);
        whtext = findViewById(R.id.whtext);
        whed = findViewById(R.id.whed);
        sltext = findViewById(R.id.sltext);
        sled = findViewById(R.id.sled);
        shtext = findViewById(R.id.shtext);
        shed = findViewById(R.id.shed);
        gpiotext = findViewById(R.id.gpiotext);
        gpioed = findViewById(R.id.gpioed);
        eltext = findViewById(R.id.eltext);
        eled = findViewById(R.id.eled);
        ddlj = findViewById(R.id.ddlj);
        ass = findViewById(R.id.ass);
        kzlb = findViewById(R.id.kzlb);
        final EditTextS ided = findViewById(R.id.iped);
        Button ipq = findViewById(R.id.idqa);
        cc = findViewById(R.id.cc);
        final LinearLayout iplay = findViewById(R.id.iplay);

        ipq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cc.setText("连接中");
                iplay.setVisibility(View.GONE);
                SocketService.ip = Objects.requireNonNull(ided.getText()).toString();
                ScrollingActivity.this.startService(new Intent(ScrollingActivity.this, SocketService.class));
            }
        });

        tl = new TextView[]{wltext, whtext, sltext, shtext, gpiotext, eltext};
        el = new EditTextS[]{wled, whed, sled, shed, gpioed, eled};

        wltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                wltext.setVisibility(View.GONE);
                wled.setVisibility(View.VISIBLE);
                wled.setText(String.format(Locale.getDefault(), "%.2f", info.GetWL()));
                ssl(wled);
            }
        });

        wled.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                wled.setVisibility(View.GONE);
                wltext.setVisibility(View.VISIBLE);
                info.SetWL(Double.parseDouble(Objects.requireNonNull(wled.getText()).toString()));
                wltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWL()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(wled.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetWL", wled.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        whtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                whtext.setVisibility(View.GONE);
                whed.setVisibility(View.VISIBLE);
                whed.setText(String.format(Locale.getDefault(), "%.2f", info.GetWH()));
                ssl(whed);
            }
        });

        whed.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                whed.setVisibility(View.GONE);
                whtext.setVisibility(View.VISIBLE);
                info.SetWH(Double.parseDouble(Objects.requireNonNull(whed.getText()).toString()));
                whtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWH()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(whed.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetWH", whed.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        sltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                sltext.setVisibility(View.GONE);
                sled.setVisibility(View.VISIBLE);
                sled.setText(String.format(Locale.getDefault(), "%.2f", info.GetSL()));
                ssl(sled);
            }
        });

        sled.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                sled.setVisibility(View.GONE);
                sltext.setVisibility(View.VISIBLE);
                info.SetSL(Double.parseDouble(Objects.requireNonNull(sled.getText()).toString()));
                sltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSL()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(sled.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetSL", sled.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        shtext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                shtext.setVisibility(View.GONE);
                shed.setVisibility(View.VISIBLE);
                shed.setText(String.format(Locale.getDefault(), "%.2f", info.GetSH()));
                ssl(shed);
            }
        });

        shed.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                shed.setVisibility(View.GONE);
                shtext.setVisibility(View.VISIBLE);
                info.SetSH(Double.parseDouble(Objects.requireNonNull(shed.getText()).toString()));
                shtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSH()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(shed.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetSH", shed.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        eltext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                eltext.setVisibility(View.GONE);
                eled.setVisibility(View.VISIBLE);
                eled.setText(String.format(Locale.getDefault(), "%d", info.GetEL()));
                ssl(eled);
            }
        });

        eled.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                eled.setVisibility(View.GONE);
                eltext.setVisibility(View.VISIBLE);
                info.SetEL(Integer.parseInt(Objects.requireNonNull(eled.getText()).toString()));
                eltext.setText(String.format(Locale.getDefault(), "%d", info.GetEL()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(eled.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetEL", eled.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        gpiotext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qbsz();
                gpiotext.setVisibility(View.GONE);
                gpioed.setVisibility(View.VISIBLE);
                gpioed.setText(String.format(Locale.getDefault(), "%d", info.GetGPIO()));
                ssl(gpioed);
            }
        });

        gpioed.setOnKeyBoardHideListener(new EditTextS.OnKeyBoardHideListener() {
            @Override
            public void onKeyHide(int keyCode, KeyEvent event) {
                gpioed.setVisibility(View.GONE);
                gpiotext.setVisibility(View.VISIBLE);
                info.SetGPIO(Integer.parseInt(Objects.requireNonNull(gpioed.getText()).toString()));
                gpiotext.setText(String.format(Locale.getDefault(), "%d", info.GetGPIO()));
                InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                if (inputMethodManager != null) {
                    inputMethodManager.hideSoftInputFromWindow(gpioed.getWindowToken(), 0);
                }
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SetGPIO", gpioed.getText().toString());
                localBroadcastManager.sendBroadcast(intent);
            }
        });

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SocketService.serid);
                intent.putExtra("SaveFile", (Objects.requireNonNull(info.GetWL()) + "," + Objects.requireNonNull(info.GetWH()) + "," + Objects.requireNonNull(info.GetSL()) + "," + Objects.requireNonNull(info.GetSH()) + "," + Objects.requireNonNull(info.GetGPIO()) + "," + Objects.requireNonNull(info.GetEL()) + ","));
                localBroadcastManager.sendBroadcast(intent);
            }
        });

    }

    private void qbsz() {
        for (int i1 = 0; i1 < el.length; i1++) {
            el[i1].setVisibility(View.GONE);
            if (i1 == 0) {
                if (!Objects.requireNonNull(wled.getText()).toString().equals("")) {
                    info.SetWL(Double.parseDouble(wled.getText().toString()));
                    wltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWL()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetWL", wled.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            if (i1 == 1) {
                if (!Objects.requireNonNull(whed.getText()).toString().equals("")) {
                    info.SetWH(Double.parseDouble(whed.getText().toString()));
                    whtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWH()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetWH", whed.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            if (i1 == 2) {
                if (!Objects.requireNonNull(sled.getText()).toString().equals("")) {
                    info.SetSL(Double.parseDouble(sled.getText().toString()));
                    sltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSL()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetSL", sled.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            if (i1 == 3) {
                if (!Objects.requireNonNull(shed.getText()).toString().equals("")) {
                    info.SetSH(Double.parseDouble(shed.getText().toString()));
                    shtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSH()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetSH", shed.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            if (i1 == 4) {
                if (!Objects.requireNonNull(gpioed.getText()).toString().equals("")) {
                    info.SetGPIO(Integer.parseInt(gpioed.getText().toString()));
                    gpiotext.setText(String.format(Locale.getDefault(), "%d", info.GetGPIO()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetGPIO", gpioed.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            if (i1 == 5) {
                if (!Objects.requireNonNull(eled.getText()).toString().equals("")) {
                    info.SetEL(Integer.parseInt(eled.getText().toString()));
                    eltext.setText(String.format(Locale.getDefault(), "%d", info.GetEL()));
                    Intent intent = new Intent(SocketService.serid);
                    intent.putExtra("SetEL", eled.getText().toString());
                    localBroadcastManager.sendBroadcast(intent);
                }
            }
            tl[i1].setVisibility(View.VISIBLE);
        }
    }

    private void ssl(EditTextS editText) {
        editText.setFocusable(true);
        editText.setFocusableInTouchMode(true);
        editText.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(editText, 0);
        }
    }

    private class UpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("Data") != null) {
                Message m = new Message();
                m.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("Data", intent.getStringExtra("Data"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("Device") != null) {
                Message m = new Message();
                m.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("Device", intent.getStringExtra("Device"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("ServiceDestroy") != null) {
                Message m = new Message();
                m.what = 2;
                Bundle bundle = new Bundle();
                bundle.putString("ServiceDestroy", intent.getStringExtra("ServiceDestroy"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringArrayExtra("Init") != null) {
                Message m = new Message();
                m.what = 3;
                Bundle bundle = new Bundle();
                bundle.putStringArray("Init", intent.getStringArrayExtra("Init"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @SuppressLint("RestrictedApi")
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == 0) {
                String[] data = Objects.requireNonNull(msg.getData().getString("Data")).split(",");
                wd.setText((data[0] + "℃"));
                sd.setText((data[1] + "％"));
            }
            if (msg.what == 1) {
                String Device = msg.getData().getString("Device");
                sb.setText(Device);
            }
            if (msg.what == 2) {
                if (Objects.requireNonNull(msg.getData().getString("ServiceDestroy")).equals("yes")) {
                    kzlb.setVisibility(View.GONE);
                    ass.setVisibility(View.GONE);
                    ddlj.setVisibility(View.VISIBLE);
                    cc.setText("与服务端断开连接");
                    fab.setVisibility(View.GONE);
                } else {
                    kzlb.setVisibility(View.VISIBLE);
                    ass.setVisibility(View.VISIBLE);
                    ddlj.setVisibility(View.GONE);
                    fab.setVisibility(View.VISIBLE);
                }
            }
            if (msg.what == 3) {
                String[] da = Objects.requireNonNull(msg.getData().getStringArray("Init"));
                ScrollingActivity.info.SetGPIO(Integer.parseInt(da[0]));
                ScrollingActivity.info.SetEL(Integer.parseInt(da[1]));
                ScrollingActivity.info.SetWL(Double.parseDouble(da[2]));
                ScrollingActivity.info.SetWH(Double.parseDouble(da[3]));
                ScrollingActivity.info.SetSL(Double.parseDouble(da[4]));
                ScrollingActivity.info.SetSH(Double.parseDouble(da[5]));
                wltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWL()));
                whtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetWH()));
                sltext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSL()));
                shtext.setText(String.format(Locale.getDefault(), "%.2f", info.GetSH()));
                eltext.setText(String.format(Locale.getDefault(), "%d", info.GetEL()));
                gpiotext.setText(String.format(Locale.getDefault(), "%d", info.GetGPIO()));
            }
            return false;
        }
    });

    @Override
    protected void onDestroy() {
        this.stopService(new Intent(ScrollingActivity.this, SocketService.class));
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }
}
