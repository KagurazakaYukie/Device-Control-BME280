package com.company;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    static double wl = 25, wh = 28, sl = 40, sh = 60;
    static int s = 0, j = 1, wkqid = 12;
    static ArrayList<Socket> arrayList = new ArrayList<>();

    static {
        System.load("/root/BME280/a.so");
    }

    public static void main(String[] arg) {
        try {
            FileInputStream fis = new FileInputStream("/root/BME280/jl.data");
            byte[] ba = new byte[1024];
            String con = null;
            while (fis.read(ba, 0, 1024) != -1) {
                con = new String(ba);
            }
            if (con != null) {
                String[] ad = con.split(",");
                wl = Double.parseDouble(ad[0]);
                wh = Double.parseDouble(ad[1]);
                sl = Double.parseDouble(ad[2]);
                sh = Double.parseDouble(ad[3]);
                wkqid = Integer.parseInt(ad[4]);
                s = Integer.parseInt(ad[5]);
                if (s == 1) {
                    j = 0;
                }
                if (s == 0) {
                    j = 1;
                }
            }
            BME280Jni b = new BME280Jni();
            b.BME280Init();
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    String d = b.BME280GetData();
                    double data = Double.parseDouble(d.split(",")[0]);
                    if (data > Main.wh) {
                        b.GPIOSetPin(Main.wkqid, Main.s);
                    }
                    if (data < Main.wl) {
                        b.GPIOSetPin(Main.wkqid, Main.j);
                    }
                }
            }, 0, 1000);
            ServerSocket ss = new ServerSocket(10666);
            while (true) {
                Socket s = ss.accept();
                arrayList.add(s);
                new Thread(new SocketServer(s, b)).start();
                System.out.println(arrayList.size());
            }
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
