package com.example.smarthomedemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarthomedemo.Datas.SensorData;
import com.example.smarthomedemo.Tools.BaseActivity;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class SensorDataActivity extends BaseActivity {

    public static String SERVER_IP_ADDR = "";

    private TextView tvTemp;
    private TextView tvHumi;
    private ImageView ivSwitch, ivBack;
    private boolean state;
    private String id;   //设备id;
    private boolean swift;    //开关状态


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensordata_activity);

        tvTemp = (TextView) findViewById(R.id.tvTemp);
        tvHumi = (TextView) findViewById(R.id.tvHumi);
        ivSwitch = (ImageView) findViewById(R.id.iv_switch);
        ivBack = (ImageView) findViewById(R.id.iv_back);

        id = getIntent().getExtras().get("id").toString();
        Log.d("test", "id is : "+id);
        state = true;
        swift = false;

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SensorDataActivity.this, SmartPlantActivity.class);
                startActivity(intent);
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient client = new OkHttpClient();
                while(state){
                    if(SERVER_IP_ADDR == "") continue;
                    try{
                        RequestBody requestBody = new FormBody.Builder()
                                .add("id", id)
                                .build();
                        Request request = new Request.Builder()
                                .url("http://"+SERVER_IP_ADDR+":8080/SmartHomeDemo/AppRequestData_servlet")
                                .post(requestBody)
                                .build();
                        Response response = client.newCall(request).execute();
                        String st = response.body().string();
                        if(!st.isEmpty() && st != "")
                            getData(st);
                        Thread.sleep(2000);
                    }catch (IOException e) {
                        Log.d("test", "获取数据失败");
                    }catch (InterruptedException e){

                    }
                }
            }
        }).start();

        ivSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            if (SERVER_IP_ADDR != "") {
                                OkHttpClient client = new OkHttpClient();
                                RequestBody requestBody = new FormBody.Builder()
                                        .add("id", id)
                                        .add("flag", "1")
                                        .build();
                                Request request = new Request.Builder()
                                        .url("http://"+SERVER_IP_ADDR+":8080/SmartHomeDemo/AppControl_servlet")
                                        .post(requestBody)
                                        .build();
                                Response response = client.newCall(request).execute();
                                String st = response.body().string();
                                if(st.charAt(0) == 's')
                                    changeSwift();
                                Log.d("test", "back data:"+st);
                            }
                        }catch(IOException e){
                            Log.d("test", "控制设备失败");
                        }
                    }
                }).start();
            }
        });
    }

    private void getData(final String st){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Gson gson = new Gson();
                SensorData sensorData = gson.fromJson(st, SensorData.class);
                tvHumi.setText(String.valueOf(sensorData.getHumi()));
                tvTemp.setText(String.valueOf(sensorData.getTemp()));
            }
        });
    }

    public void changeSwift(){
        Log.d("test", "aaa");
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(swift){
                    ivSwitch.setImageResource(R.drawable.second_switch);
                    swift = false;
                }else{
                    ivSwitch.setImageResource(R.drawable.first_switch);
                    swift = true;
                }
            }
        });

    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        state = false;
    }
}



