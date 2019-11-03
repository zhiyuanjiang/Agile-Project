package com.example.smarthomedemo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.smarthomedemo.R;
import com.example.smarthomedemo.ServerAddrActivity;

public class UserInfoFragment extends Fragment {

    private RelativeLayout rt_ip;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.userinfo_fragment, container, false);

        rt_ip = (RelativeLayout) view.findViewById(R.id.rt_ip);
        rt_ip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ServerAddrActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
