package com.example.tangyi.breakingnews.tools;

import android.content.Context;

/**
 * 缓存工具类
 * 将json数据缓存在本地中
 */
public class CacheUtils {
    /**
     * 写入缓存的方法
     * 以url为key，以json数据为value，保存在本地中
     */
    public static void setCache(Context context,String url,String json){
        PreferencesUtils.setString(context,url,json);
    }
    /**
     * 获取缓存的方法
     *要获取缓存就要传入一个存入json数据的key，也就是上面的url
     */
    public static String getCache(Context context,String url){
        return PreferencesUtils.getString(context,url,null);
    }
}
