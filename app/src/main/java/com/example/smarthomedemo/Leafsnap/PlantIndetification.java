package com.example.smarthomedemo.Leafsnap;

import android.content.Context;
import android.net.Uri;
import android.util.Base64;
import android.util.Log;

import java.util.*;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.smarthomedemo.Datas.PlantInfo;
import com.example.smarthomedemo.Tools.Base64Utils;



public class PlantIndetification
{

    public interface MyHttpListener{
        void handlePlantData(final String st);
        void handleError();
    }

    /*
    public void setMyHttpListener(Context context, Uri uri, MyHttpListener myHttpListener){
        String data = Base64Utils.ImageToBase64(context, uri);
        identify(myHttpListener, context, data);
    }*/

    public void setMyHttpListener(Context context, String st, MyHttpListener myHttpListener){
        identify(myHttpListener, context, st);
    }


	
	public void identify(final MyHttpListener myHttpListener, Context context, String data) {

		//图片地址
		//String data = Base64Utils.ImageToBase64(context, uri);
		//Log.d("test", "the data is : "+data);

		final String host = "http://biology.market.alicloudapi.com";
	    final String path = "/ai_biology/add_new_job_step";
	    final String method = "POST";
	    final String appcode = "fe375f0753944227ad5a05dbca55129b";
	    final Map<String, String> headers = new HashMap<String, String>();
	    //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
	    headers.put("Authorization", "APPCODE " + appcode);
	    //根据API的要求，定义相对应的Content-Type
	    headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
	    final Map<String, String> querys = new HashMap<String, String>();
	    final Map<String, String> bodys = new HashMap<String, String>();
	    bodys.put("IMAGE", data);
	    bodys.put("IMAGE_TYPE", "0");
	    bodys.put("LOCATION", "湖南师范大学");
	    bodys.put("LOCATION_TYPE", "3");
	    bodys.put("STEP_DESCRIPTION", "步骤描述");
	    bodys.put("STEP_TITLE", "步骤名称");
	    bodys.put("iCREDIT_JOB_ID", "123");
	    bodys.put("iCREDIT_USER_ID", "123");

        /**
        * 重要提示如下:
        * HttpUtils请从
        * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
        * 下载
        *
        * 相应的依赖请参照
        * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
        */


		//获取response的body
		//System.out.println(EntityUtils.toString(response.getEntity()));

		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
					String str = EntityUtils.toString(response.getEntity());

					myHttpListener.handlePlantData(str);
					/*
					//解析json字符串
					JSONObject json = JSONObject.parseObject(str);
					//System.out.println(JSONObject.toJSONString(json, true));

					JSONObject json1 = json.getJSONObject("IMAGE_INFO_ENTITY");
					JSONArray array = json1.getJSONArray("PLANT_INFO_ENTITY");
					List<PlantInfo> list = JSONArray.parseArray(array.toJSONString(), PlantInfo.class);

					for(int i = 0; i < list.size(); ++i){
						Log.d("test", list.get(i).getName());
					}
					*/
				}catch (Exception e){
					Log.d("test", "request had a problem");
					myHttpListener.handleError();
				}
			}
		}).start();

	}

}
