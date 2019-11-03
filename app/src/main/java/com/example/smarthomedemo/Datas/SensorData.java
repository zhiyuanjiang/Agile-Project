package com.example.smarthomedemo.Datas;

/*
    每个设备采集的传感器数据
*/

public class SensorData {

    private String id;        //设备id
    private String ip;        //设备联网ip
    private double humi;      //湿度
    private double temp;      //温度

    public SensorData(String id, String ip, double humi, double temp){
        this.id = id;
        this.ip = ip;
        this.humi = humi;
        this.temp = temp;
    }

    public String getId(){
        return id;
    }

    public String getIp(){
        return ip;
    }

    public double getHumi(){
        return humi;
    }

    public double getTemp(){
        return temp;
    }
}
