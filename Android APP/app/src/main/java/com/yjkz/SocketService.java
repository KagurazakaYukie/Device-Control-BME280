package com.yjkz;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

public class SocketService extends Service {
    public static String serid = "SocketService", ip = "";
    public static int state = 0;
    private UpdateBroadcastReceiver broadcastReceiver;
    private LocalBroadcastManager localBroadcastManager;
    private DataOutputStream dataOutputStream;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        localBroadcastManager = LocalBroadcastManager.getInstance(getApplicationContext());
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(serid);
        broadcastReceiver = new UpdateBroadcastReceiver();
        localBroadcastManager.registerReceiver(broadcastReceiver, intentFilter);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                if (state == 0) {
                    new Thread(ad).start();
                }
            }
        }, 0, 1000);
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        Intent intent = new Intent(ScrollingActivity.acid);
        intent.putExtra("ServiceDestroy", "yes");
        localBroadcastManager.sendBroadcast(intent);
        state = 0;
        localBroadcastManager.unregisterReceiver(broadcastReceiver);
        super.onDestroy();
    }

    private Runnable ad = new Runnable() {
        @Override
        public void run() {
            try {
                Socket socket = new Socket(ip, 10666);
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                dataOutputStream = new DataOutputStream(socket.getOutputStream());
                Intent intent2 = new Intent(ScrollingActivity.acid);
                intent2.putExtra("ServiceDestroy", "no");
                localBroadcastManager.sendBroadcast(intent2);
                String con;
                while (socket.isConnected() && !socket.isClosed()) {
                    state = 1;
                    con = dis.readUTF();
                    if (con.contains("Data:")) {
                        Intent intent = new Intent(ScrollingActivity.acid);
                        intent.putExtra("Data", con.split(":")[1]);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                    if (con.contains("Device:")) {
                        Intent intent = new Intent(ScrollingActivity.acid);
                        intent.putExtra("Device", con.split(":")[1]);
                        localBroadcastManager.sendBroadcast(intent);
                    }
                    if (con.contains("ServerFile:")) {
                        Intent intent = new Intent(ScrollingActivity.acid);
                        intent.putExtra("Init", con.split(":")[1].split(","));
                        localBroadcastManager.sendBroadcast(intent);
                    }
                }
                socket.close();
                state = 0;
                Intent intent = new Intent(ScrollingActivity.acid);
                intent.putExtra("ServiceDestroy", "yes");
                localBroadcastManager.sendBroadcast(intent);
            } catch (Exception e) {
                state = 0;
                Intent intent = new Intent(ScrollingActivity.acid);
                intent.putExtra("ServiceDestroy", "yes");
                localBroadcastManager.sendBroadcast(intent);
            }
        }
    };

    private class UpdateBroadcastReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getStringExtra("SetGPIO") != null) {
                Message m = new Message();
                m.what = 0;
                Bundle bundle = new Bundle();
                bundle.putString("SetGPIO", intent.getStringExtra("SetGPIO"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SetEL") != null) {
                Message m = new Message();
                m.what = 1;
                Bundle bundle = new Bundle();
                bundle.putString("SetEL", intent.getStringExtra("SetEL"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SetWH") != null) {
                Message m = new Message();
                m.what = 2;
                Bundle bundle = new Bundle();
                bundle.putString("SetWH", intent.getStringExtra("SetWH"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SetWL") != null) {
                Message m = new Message();
                m.what = 3;
                Bundle bundle = new Bundle();
                bundle.putString("SetWL", intent.getStringExtra("SetWL"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SetSH") != null) {
                Message m = new Message();
                m.what = 4;
                Bundle bundle = new Bundle();
                bundle.putString("SetSH", intent.getStringExtra("SetSH"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SetSL") != null) {
                Message m = new Message();
                m.what = 5;
                Bundle bundle = new Bundle();
                bundle.putString("SetSL", intent.getStringExtra("SetSL"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
            if (intent.getStringExtra("SaveFile") != null) {
                Message m = new Message();
                m.what = 6;
                Bundle bundle = new Bundle();
                bundle.putString("SaveFile", intent.getStringExtra("SaveFile"));
                m.setData(bundle);
                handler.sendMessage(m);
            }
        }
    }

    private Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            try {
                String da;
                if (msg.what == 0) {
                    da = msg.getData().getString("SetGPIO");
                    final String finalDa6 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetGPIOTMP:" + finalDa6);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 1) {
                    da = msg.getData().getString("SetEL");
                    final String finalDa5 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetELTMP:" + finalDa5);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 2) {
                    da = msg.getData().getString("SetWH");
                    final String finalDa4 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetWHTMP:" + finalDa4);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 3) {
                    da = msg.getData().getString("SetWL");
                    final String finalDa3 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetWLTMP:" + finalDa3);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 4) {
                    da = msg.getData().getString("SetSH");
                    final String finalDa2 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetSHTMP:" + finalDa2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 5) {
                    da = msg.getData().getString("SetSL");
                    final String finalDa1 = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SetSLTMP:" + finalDa1);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
                if (msg.what == 6) {
                    da = msg.getData().getString("SaveFile");
                    final String finalDa = da;
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                dataOutputStream.writeUTF("SaveFile:" + finalDa);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }).start();
                }
            } catch (Exception ignored) {
                state = 0;
                Intent intent = new Intent(ScrollingActivity.acid);
                intent.putExtra("ServiceDestroy", "yes");
                localBroadcastManager.sendBroadcast(intent);
            }
            return false;
        }
    });
}
