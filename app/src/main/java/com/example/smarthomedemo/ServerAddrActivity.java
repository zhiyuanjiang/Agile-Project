package com.example.smarthomedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.smarthomedemo.Tools.BaseActivity;

public class ServerAddrActivity extends BaseActivity {

    private EditText et_addr;
    private Button btn_ok;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.serveraddr_activity);

        et_addr = (EditText) findViewById(R.id.et_addr);
        btn_ok = (Button) findViewById(R.id.btn_ok);

        et_addr.setText(SensorDataActivity.SERVER_IP_ADDR);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SensorDataActivity.SERVER_IP_ADDR = et_addr.getText().toString();
            }
        });

    }
}
