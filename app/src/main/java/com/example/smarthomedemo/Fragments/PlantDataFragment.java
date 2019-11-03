package com.example.smarthomedemo.Fragments;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.bumptech.glide.Glide;
import com.example.smarthomedemo.Datas.PlantInfo;
import com.example.smarthomedemo.Leafsnap.PlantIndetification;
import com.example.smarthomedemo.PlantInfoActivity;
import com.example.smarthomedemo.R;
import com.example.smarthomedemo.Tools.Base64Utils;
import com.example.smarthomedemo.Tools.RealPathFromUriUtils;

import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PlantDataFragment extends Fragment {

    public static final int REQUEST_PICK_IMAGE = 101;
    public static final int REQUEST_TAKE_PHOTO = 102;
    String[] mPermissionList = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE};


    private ImageView iv_album, iv_camera;
    private TextView tv_plant_name, tv_hint;
    private CircleImageView civ_plant_picture;

    private PlantInfo plantInfo = null;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.plantdata_fragment, container, false);

        iv_album = (ImageView) view.findViewById(R.id.iv_album);
        iv_camera = (ImageView) view.findViewById(R.id.iv_camera);
        tv_plant_name = (TextView) view.findViewById(R.id.tv_plant_name);
        tv_hint = (TextView) view.findViewById(R.id.tv_hint);
        civ_plant_picture = (CircleImageView) view.findViewById(R.id.civ_plant_picture);
        iv_album.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(ContextCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(getActivity(), mPermissionList, 1);
                }else {
                    getImage();
                }
            }
        });

        iv_camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, REQUEST_TAKE_PHOTO);
            }
        });

        civ_plant_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), PlantInfoActivity.class);
                intent.putExtra("name", plantInfo.getName());
                intent.putExtra("info", plantInfo.getBaike_info().getDescription());
                startActivity(intent);
            }
        });

        civ_plant_picture.setClickable(false);

        tv_plant_name.setText("请选择图片");

        return view;
    }

    private void getImage() {

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            startActivityForResult(new Intent(Intent.ACTION_GET_CONTENT).setType("image/*"),
                    REQUEST_PICK_IMAGE);
        } else {
            //Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            Intent intent = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            //intent.addCategory(Intent.CATEGORY_OPENABLE);
            //intent.setType("image/*");
            startActivityForResult(intent, REQUEST_PICK_IMAGE);
        }

        /*
        Intent intent = new Intent("android.intent.action.GET_CONTENT");
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_PICK_IMAGE);
        */
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 1:{
                if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    getImage();
                }else{
                    Toast.makeText(getActivity(), "You denied the permission", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQUEST_PICK_IMAGE:{
                    if (data != null) {
                        //String realPathFromUri = RealPathFromUriUtils.getRealPathFromUri(getActivity(), data.getData());
                        //Log.d("test", "the url is : " +realPathFromUri );
                        Context context = getActivity();
                        Uri uri = data.getData();
                        String str = Base64Utils.ImageToBase64(context, uri);
                        deal(context, str);
                    } else {
                        Toast.makeText(getActivity(), "图片损坏，请重新选择", Toast.LENGTH_SHORT).show();
                    }
                }break;
                case REQUEST_TAKE_PHOTO:{
                    if(data != null){
                        Log.d("test", "aaa");
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        //civ_plant_picture.setImageBitmap(bitmap);
                        Context context = getActivity();
                        String st = Base64Utils.ImageToBase64(context, bitmap);
                        deal(context, st);
                    }
                }break;
            }
        }
    }

    void deal(final Context context, String st){
        tv_plant_name.setText("请耐心等待.....");
        tv_hint.setText("");
        civ_plant_picture.setImageDrawable(null);
        PlantIndetification plantIndetification = new PlantIndetification();
        plantIndetification.setMyHttpListener(context, st, myHttpListener);
    }

    //监听器，监听返回的植物信息数据
    PlantIndetification.MyHttpListener myHttpListener = new PlantIndetification.MyHttpListener() {
        @Override
        public void handlePlantData(String st) {
            dealJsonData(st);
        }

        @Override
        public void handleError() {
            Log.d("test", "请求失败");
            present(2);
        }
    };

    //处理返回的植物信息数据
    void dealJsonData(String st){
        Log.d("test", st);
        JSONObject json = JSONObject.parseObject(st);
        //System.out.println(JSONObject.toJSONString(json, true));

        JSONObject json1 = json.getJSONObject("IMAGE_INFO_ENTITY");
        JSONArray array = json1.getJSONArray("PLANT_INFO_ENTITY");
        List<PlantInfo> list = JSONArray.parseArray(array.toJSONString(), PlantInfo.class);

        Log.d("test", "size:"+list.size());

        if(list.isEmpty()){
            Log.d("test", "无法识别");
            present(1);
        }else{
            for(int i = 0; i < list.size(); ++i){
                Log.d("test", "name:"+list.get(i).getName()
                        +" \nscore:"+list.get(i).getScore()
                        +" \nuri:"+list.get(i).getBaike_info().getBaike_url()
                        +" \nimage uri:"+list.get(i).getBaike_info().getBaike_image_url()
                        +" \ndes:"+list.get(i).getBaike_info().getDescription());
            }
            presentPlantInfo(list.get(0));
            plantInfo = list.get(0);
        }
    }

    void present(final int val){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(val == 1) {
                    tv_hint.setText("");
                    tv_plant_name.setText("无法识别");
                    civ_plant_picture.setImageDrawable(null);
                    Toast.makeText(getActivity().getApplicationContext(), "无法识别",
                            Toast.LENGTH_SHORT).show();
                }
                if(val == 2) {
                    tv_hint.setText("");
                    tv_plant_name.setText("请求失败");
                    civ_plant_picture.setImageDrawable(null);
                    Toast.makeText(getActivity().getApplicationContext(), "请求失败",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    void presentPlantInfo(final PlantInfo plantInfo){
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                tv_hint.setText("点击查看详情");
                tv_plant_name.setText(plantInfo.getName());

                civ_plant_picture.setClickable(true);

                Glide.with(getActivity())
                        .load(plantInfo.getBaike_info().getBaike_image_url())
                        .asBitmap()
                        .into(civ_plant_picture);

            }
        });
    }
}
