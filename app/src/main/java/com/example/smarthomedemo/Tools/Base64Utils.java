package com.example.smarthomedemo.Tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import java.io.*;
import java.util.*;

public class Base64Utils {
 
    /**
     * java
     * 图片转化成base64字符串
     */
    public static String ImageToBase64(Context context, Uri uri) {
        String base64 = null;
        InputStream in = null;
        byte[] data = null;
        try {

            //in = new FileInputStream(new File(path));
            in = context.getContentResolver().openInputStream(uri);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            System.out.println("image to base64 failed");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //返回Base64编码过的字节数组字符串
        //Encoder encoder = Base64.getEncoder();
        String encode = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);
        return encode;
    }

    public static String ImageToBase64(Context context, Bitmap bitmap) {
        String base64 = null;
        InputStream in = null;
        byte[] data = null;
        try {

            in = bitmapToInputStream(bitmap);
            //in = context.getContentResolver().openInputStream(uri);

            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (Exception e) {
            System.out.println("image to base64 failed");
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        //返回Base64编码过的字节数组字符串
        //Encoder encoder = Base64.getEncoder();
        String encode = android.util.Base64.encodeToString(data, android.util.Base64.DEFAULT);
        return encode;
    }

    public static InputStream bitmapToInputStream(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        InputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }

}