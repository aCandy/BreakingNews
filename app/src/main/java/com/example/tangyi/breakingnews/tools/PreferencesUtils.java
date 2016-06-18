package com.example.tangyi.breakingnews.tools;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * SharedPreferencesUtils工具类。
 * Created by 在阳光下唱歌 on 2016/5/9.
 */
public class PreferencesUtils {

    public static Boolean getBoolean(Context context,String key,Boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key,value);
    }
    public static void setBoolean(Context context,String key,Boolean value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putBoolean(key,value).commit();
    }
    public static String getString(Context context,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getString(key,value);
    }
    public static void setString(Context context,String key,String value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putString(key,value).commit();
    }
    public static int getInt(Context context,String key,int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key,value);
    }public static void setInt(Context context,String key,int value){
        SharedPreferences sharedPreferences=context.getSharedPreferences("config",Context.MODE_PRIVATE);
        sharedPreferences.edit().putInt(key,value).commit();
    }

}
