package com.yjkz;

class Info {
    private int gpio = 12, el = 0;
    private double wl = 0, wh = 0, sl = 0, sh = 0;

    int GetEL() {
        return this.el;
    }

    void SetEL(int el) {
        this.el = el;
    }

    int GetGPIO() {
        return this.gpio;
    }

    void SetGPIO(int gpio) {
        this.gpio = gpio;
    }

    void SetSL(double sl) {
        this.sl = sl;
    }

    void SetSH(double sh) {
        this.sh = sh;
    }

    void SetWL(double wl) {
        this.wl = wl;
    }

    void SetWH(double wh) {
        this.wh = wh;
    }

    double GetWL() {
        return this.wl;
    }

    double GetWH() {
        return this.wh;
    }

    double GetSL() {
        return this.sl;
    }

    double GetSH() {
        return this.sh;
    }

}
