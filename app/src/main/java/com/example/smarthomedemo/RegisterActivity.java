package com.example.smarthomedemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.smarthomedemo.Tools.BaseActivity;


public class RegisterActivity extends BaseActivity {

    EditText et_name, et_pwd;
    Button bt_register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);

        et_name = (EditText)findViewById(R.id.et_name);
        et_pwd = (EditText)findViewById(R.id.et_pwd);
        bt_register = (Button)findViewById(R.id.bt_register);

        bt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }
}
