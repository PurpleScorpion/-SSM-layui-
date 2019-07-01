package com.sweet.test;

import com.alibaba.fastjson.JSONArray;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

public class Demo {


    @Test
    public void run1(){
        List<Province> list=new ArrayList<>();
        list.add(new Province("1","江苏省"));
        list.add(new Province("2","安徽省"));
        list.add(new Province("3","湖南省"));
        list.add(new Province("4","广东省"));
        list.add(new Province("5","四川省"));

        String s = JSONArray.toJSONString(list);
        System.out.println(s);
    }

    @Test
    public void run2(){
        List<City> list=new ArrayList<>();
        list.add(new City("1","长沙市","3"));
        list.add(new City("2","株洲市","3"));
        list.add(new City("3","湘潭市","3"));
        list.add(new City("4","衡阳市","3"));
        list.add(new City("5","邵阳市","3"));

        String s = JSONArray.toJSONString(list);
        System.out.println(s);
    }
}
