package com.sweet.utils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

public class FileUtils {

    static ThreadLocal<Map<String,Object>> tl=new ThreadLocal<>();

    /**
     * 获取上传文件的md5
     *
     * @param file
     * @return
     */
    public static String getMd5(MultipartFile file) {

        try {
            byte[] uploadBytes = file.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte[] digest = md5.digest(uploadBytes);
            String hashString = new BigInteger(1, digest).toString(16);
            return hashString;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将JSON数据转成Map集合
     * @param json
     * @return
     */
    public static Map<String,Object> JsonToMap(String json){
        JSONObject jsonResult = JSONObject.parseObject(json);
        Long AllSize = jsonResult.getLong("AllSize");
        Long uploadSize = jsonResult.getLong("uploadSize");
        Long thisSize = jsonResult.getLong("thisSize");
        Boolean isStart = jsonResult.getBoolean("isStart");
        Boolean isReady = jsonResult.getBoolean("isReady");
        Map<String, Object> map=new HashMap<>();
        map.put("AllSize",AllSize);
        map.put("uploadSize",uploadSize);
        map.put("isStart",isStart);
        map.put("thisSize",thisSize);
        map.put("isReady",isReady);
        return map;
    }

    /**
     * 将Map集合转成JSON数据
     * @param map
     * @return
     */
    public static String MapToJson(Map<String, Object> map){
        String json= JSONArray.toJSONString(map);
        return json;
    }

    public static Map<String,Object> getMap(){
        if(tl.get() == null){
            return new HashMap<String,Object>();
        }else{
            return tl.get();
        }
    }
    public static void setMap(Map<String,Object> map){
        tl.set(map);
    }

    public static void removeMap(){
        if(tl.get() != null){
            tl.remove();
        }
    }
}
