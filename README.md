# device-control-BME280

附加开源协议：
[![LICENSE](https://img.shields.io/badge/license-Anti%20996-blue.svg)](https://github.com/KagurazakaYukie/996-1.5/blob/master/996%E8%AE%B8%E5%8F%AF%E8%AF%81)


一个蚂蚁挂机饲养的项目

我主要是懒着喂这个那个 目前是写了 温度控制这一块，其他功能暂时没搞，以后慢慢来。
懒是原罪

传感器是用某宝的BME280 然后魔改他们的官方DEMO 然后加了JNI
然后平台 是Raspberry 3b 官方最小化系统

BME280Server是服务端
AndroidAPP是客户端

没有写外网控制，是内网控制。
外网暂时还用不到所以就没有写，就是加个“中转站”就好了

so.sh 是编译文件

Android 客户端使用一个网络的一个公开的源码（已修改成app合适的功能）。已经在源文件内标识地址。

若有侵犯请联系我！
