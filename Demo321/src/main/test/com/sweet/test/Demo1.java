package com.sweet.test;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Demo1 {



    public static void main(String[] args) {
        //文件总大小
        Long AllSize=0L;
        //已经上传的大小,用来计算上传百分比
        Long uploadSize=0L;
        //如果为false,则提示计算文件中,否则为正在上传
        Boolean isStart=false;
        //用来计算网速
        Long thisSize=0L;

        Map<String, Object> map = new HashMap<>();
        map.put("AllSize",AllSize);
        map.put("uploadSize",uploadSize);
        map.put("isStart",isStart);
        map.put("thisSize",thisSize);
        map.put("isReady",false);
        String json= JSONArray.toJSONString(map);
        System.out.println(json);
        run1(json);
    }

    public static void run1(String json){
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
        System.out.println(map);

    }
}
