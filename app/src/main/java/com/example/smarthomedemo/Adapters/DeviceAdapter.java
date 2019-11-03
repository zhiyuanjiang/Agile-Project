package com.example.smarthomedemo.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.smarthomedemo.Datas.DeviceData;
import com.example.smarthomedemo.R;

import java.util.List;

public class DeviceAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView deviceicon;
        TextView devicename;
        LinearLayout deviceitem;
        public ViewHolder(View view){
            super(view);
            deviceicon = (ImageView) view.findViewById(R.id.civ_deviceicon);
            devicename = (TextView)  view.findViewById(R.id.tv_devicename);
            deviceitem = (LinearLayout) view.findViewById(R.id.device_item);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view , int position);
    }

    private List<DeviceData> mdata;
    private Context context;
    private OnItemClickListener myClickListener;

    public void setOnItemClickListener(OnItemClickListener myClickListener){
        this.myClickListener = myClickListener;
    }

    public DeviceAdapter(List<DeviceData> mdata, Context context){
        this.mdata = mdata;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.device_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, final int position) {
        DeviceData deviceData = mdata.get(position);
        Glide.with(context).load(deviceData.getIcon()).asBitmap().into(((ViewHolder)holder).deviceicon);
        ((ViewHolder)holder).devicename.setText(deviceData.getName());

        if(myClickListener != null){
            ((ViewHolder)holder).deviceitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myClickListener.onItemClick(((ViewHolder)holder).deviceitem, position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mdata.size();
    }
}
