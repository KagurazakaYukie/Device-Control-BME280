package com.company;

import java.io.*;
import java.net.*;
import java.util.*;

public class SocketServer implements Runnable {
    private Socket s;
    private DataInputStream dis;
    private DataOutputStream dos;

    SocketServer(Socket s) throws IOException {
        this.s = s;
        dis = new DataInputStream(s.getInputStream());
        dos = new DataOutputStream(s.getOutputStream());
        dos.writeUTF(("ServerFile:"+Main.wkqid+","+Main.s+","+Main.wl+","+Main.wh+","+Main.sl+","+Main.sh));
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                try {
                    if(Main.data!=null) {
                        double data1 = Double.parseDouble(Main.data.split(",")[0]);
                        dos.writeUTF("Data:" + Main.data);
                        if (data1 < Main.wl) {
                            dos.writeUTF("Device:加温中");
                        }
                        if (data1 > Main.wl && data1 < Main.wh) {
                            dos.writeUTF("Device:温度正常");
                        } else {
                            if (data1 > Main.wh) {
                                dos.writeUTF("Device:当前温度过高");
                            }
                        }
                    }
                } catch (Exception e) {
                    try {
                        Main.arrayList.remove(s);
                        s.close();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }, 0, 3500);
    }

    @Override
    public void run() {
        try {
            String content1;
            while (s.isConnected() && !s.isClosed()) {
                content1 = dis.readUTF();
                if (content1.contains("SaveFile:")) {
                    String data = content1.split(":")[1];
                    FileOutputStream fos = new FileOutputStream("/root/BME280/jl.data", false);
                    fos.write(data.getBytes());
                    fos.close();
                }
                if (content1.contains("SetGPIOTMP:")) {
                    Main.wkqid = Integer.parseInt(content1.split(":")[1]);
                }
                if (content1.contains("SetELTMP:")) {
                    Main.s = Integer.parseInt(content1.split(":")[1]);
                    if (Main.s == 1) {
                        Main.j = 0;
                    }
                    if (Main.s == 0) {
                        Main.j = 1;
                    }
                }
                if (content1.contains("SetWHTMP:")) {
                    Main.wh = Double.parseDouble(content1.split(":")[1]);
                }
                if (content1.contains("SetWLTMP:")) {
                    Main.wl = Double.parseDouble(content1.split(":")[1]);
                }
                if (content1.contains("SetSHTMP:")) {
                    Main.sh = Double.parseDouble(content1.split(":")[1]);
                }
                if (content1.contains("SetSLTMP:")) {
                    Main.sl = Double.parseDouble(content1.split(":")[1]);
                }
            }
            s.close();
            Main.arrayList.remove(s);
        } catch (Exception e) {
            try {
                Main.arrayList.remove(s);
                this.s.close();
            } catch (IOException ex) {
                System.out.println(ex.toString());
            }
            System.out.println(e.toString());
        }
    }
}
