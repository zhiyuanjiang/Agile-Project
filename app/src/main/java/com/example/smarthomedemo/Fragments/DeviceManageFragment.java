package com.example.smarthomedemo.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.smarthomedemo.Adapters.DeviceAdapter;
import com.example.smarthomedemo.Datas.DeviceData;
import com.example.smarthomedemo.R;
import com.example.smarthomedemo.SensorDataActivity;

import java.util.ArrayList;
import java.util.List;

public class DeviceManageFragment extends Fragment {

    private RecyclerView recyclerView;
    List<DeviceData> list;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.devicemanage_fragment, container, false);

        initData();
        recyclerView = (RecyclerView) view.findViewById(R.id.review_device);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DeviceAdapter adapter = new DeviceAdapter(list, getContext());
        adapter.setOnItemClickListener(myClickListener);
        recyclerView.setAdapter(adapter);

        return view;
    }

    DeviceAdapter.OnItemClickListener myClickListener = new DeviceAdapter.OnItemClickListener() {
        @Override
        public void onItemClick(View view, int position) {
            Intent intent = new Intent(getActivity(), SensorDataActivity.class);
            intent.putExtra("id", list.get(position).getId());
            startActivity(intent);
        }

        @Override
        public void onItemLongClick(View view, int position) {

        }
    };

    void initData(){
        list = new ArrayList<>();
        list.add(new DeviceData(R.drawable.device1, "12345678", "小草"));
        list.add(new DeviceData(R.drawable.device2, "12345679", "小花"));
        list.add(new DeviceData(R.drawable.device3, "12345687", "小蒋"));
        list.add(new DeviceData(R.drawable.device4, "12345689", "小远"));
    }
}
