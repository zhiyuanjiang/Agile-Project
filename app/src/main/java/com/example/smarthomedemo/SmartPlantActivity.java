package com.example.smarthomedemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.smarthomedemo.Fragments.DeviceManageFragment;
import com.example.smarthomedemo.Fragments.PlantDataFragment;
import com.example.smarthomedemo.Fragments.UserInfoFragment;

public class SmartPlantActivity extends FragmentActivity {

    private ImageView iv_device, iv_plant, iv_people;
    private TextView tv_device, tv_plant, tv_people;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.smartplant_activity);

        iv_device = (ImageView) findViewById(R.id.iv_device);
        iv_plant  = (ImageView) findViewById(R.id.iv_plant);
        iv_people = (ImageView) findViewById(R.id.iv_people);
        tv_device = (TextView) findViewById(R.id.tv_device);
        tv_people = (TextView) findViewById(R.id.tv_people);
        tv_plant  = (TextView) findViewById(R.id.tv_plant);

        iv_device.setOnClickListener(myClickListener);
        iv_plant.setOnClickListener(myClickListener);
        iv_people.setOnClickListener(myClickListener);

        iv_device.setImageResource(R.drawable.second_device);
        tv_device.setTextColor(Color.GREEN);
        replaceFragment(new DeviceManageFragment());
    }

    View.OnClickListener myClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.iv_device:{
                    ChangeIcon();
                    iv_device.setImageResource(R.drawable.second_device);
                    tv_device.setTextColor(Color.GREEN);
                    replaceFragment(new DeviceManageFragment());
                }break;
                case R.id.iv_plant:{
                    ChangeIcon();
                    iv_plant.setImageResource(R.drawable.second_plant);
                    tv_plant.setTextColor(Color.GREEN);
                    replaceFragment(new PlantDataFragment());
                }break;
                case R.id.iv_people:{
                    ChangeIcon();
                    iv_people.setImageResource(R.drawable.second_people);
                    tv_people.setTextColor(Color.GREEN);
                    replaceFragment(new UserInfoFragment());
                }break;
            }
        }
    };

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.frame_fragment, fragment);
        transaction.commit();
    }

    //将图片全部换成灰色状态
    void ChangeIcon(){
        iv_device.setImageResource(R.drawable.first_device);
        iv_people.setImageResource(R.drawable.first_people);
        iv_plant.setImageResource(R.drawable.first_plant);
        tv_device.setTextColor(Color.BLACK);
        tv_people.setTextColor(Color.BLACK);
        tv_plant.setTextColor(Color.BLACK);
    }
}
