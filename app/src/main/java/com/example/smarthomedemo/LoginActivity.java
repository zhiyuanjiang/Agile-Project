package com.example.smarthomedemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarthomedemo.Tools.BaseActivity;
import com.example.smarthomedemo.Tools.EditTextClearTools;

public class LoginActivity extends BaseActivity {

    EditText username, userpwd;
    ImageView deletename, deletepwd;
    Button btn;
    TextView register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        EditText username = (EditText) findViewById(R.id.et_username);
        EditText userpwd  = (EditText) findViewById(R.id.et_userpwd);
        ImageView deletename = (ImageView) findViewById(R.id.iv_deletename);
        ImageView deletepwd  = (ImageView) findViewById(R.id.iv_deletepwd);
        TextView register  = (TextView) findViewById(R.id.tv_register);
        Button btn = (Button) findViewById(R.id.login);

        EditTextClearTools.addClearListener(username, deletename);
        EditTextClearTools.addClearListener(userpwd, deletepwd);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SmartPlantActivity.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

}
