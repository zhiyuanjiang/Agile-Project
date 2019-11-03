package com.example.smarthomedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.smarthomedemo.Tools.BaseActivity;

public class PlantInfoActivity extends BaseActivity {

    private TextView tv_name, tv_info;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.plantinfo_activity);

        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_info = (TextView) findViewById(R.id.tv_info);

        Intent intent = getIntent();
        String name = intent.getExtras().get("name").toString();
        String info = intent.getExtras().get("info").toString();

        tv_name.setText(name);
        tv_info.setText(info);

    }
}
