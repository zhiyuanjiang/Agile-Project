package com.example.smarthomedemo.Datas;

public class DeviceData {
    private int icon;
    private String id;
    private String name;

    public DeviceData(int icon, String id, String name){
        this.icon = icon;
        this.id = id;
        this.name = name;
    }

    public void setIcon(int icon){
        this.icon = icon;
    }

    public int getIcon(){
        return icon;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getId(){
        return id;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
