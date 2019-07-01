package com.sweet.controller;

import com.alibaba.fastjson.JSONArray;
import com.sweet.model.Files;
import com.sweet.model.User;
import com.sweet.model.Userfiles;
import com.sweet.service.UserServiceI;
import com.sweet.utils.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("/userController")
public class UserController {
    @Autowired
    private UserServiceI service;

    @Autowired
    private JedisPool pool;

    //文件总大小
    private Long AllSize=0L;
    //已经上传的大小,用来计算上传百分比
    private Long uploadSize=0L;
    //如果为false,则提示计算文件中,否则为正在上传
    private Boolean isStart=false;
    //用来计算网速
    private Long thisSize=0L;
    //用来判定超时,假设用户刷新页面或者关闭浏览器就中断上传操作
    //超时时间设定为10S
    private Long time=0L;

    private String path="H:\\upload\\";

    @RequestMapping("/toUploadPage")
    public String toUploadPage( HttpSession session){
        //先设置该文件的UUID,该ID作为该文件在Redis中的唯一标识
        String fid=UUID.randomUUID().toString();
        //将该ID存入session域中,可以使其他方法在访问时可以同步数据
        session.setAttribute("FID",fid);
        return "redirect:/upload.html";
    }

    /**
     *
     * @param pic
     * @param session
     * @return
     *      -1:文件不存在<br>
     *      1:成功<br>
     *      2:由于网络等因素导致文件上传中断<br>
     *      3.服务器异常
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String upload(MultipartFile pic, HttpSession session) throws InterruptedException {
        //先设置该文件的UUID,该ID作为该文件在Redis中的唯一标识
        String fid=UUID.randomUUID().toString();
        //将该ID存入session域中,可以使其他方法在访问时可以同步数据
//        session.setAttribute("FID",fid);
//        String fid= (String) session.getAttribute("FID");
//        System.out.println("fid:"+fid);
//        Jedis jedis = pool.getResource();
//        Jedis jedis = new Jedis("127.0.0.1", 6379);
//        User user = (User) session.getAttribute("user");
        User user= new User();
        user.setUid("0");
        user.setUsername("root");
        user.setPassword("root");
        user.setNickname("超级管理员");

        //先创建Map集合
//        Map<String, Object> map = new HashMap<>();
//        map.put("AllSize",AllSize);//文件总大小
//        map.put("uploadSize",uploadSize);//文件上传大小
//        map.put("isStart",isStart);//文件是否在验证MD5中
//        //用来计算网速,因为异步一秒一次,所以每次获取时,先获取最新上传大小,
//        // 减去上次的thisSize,得到的就是实时网速
//        map.put("thisSize",thisSize);
//        //文件是否上传成功
//        map.put("isReady",false);
//        //将Map集合转成Json格式,方便存入Redis
//        String json = FileUtils.MapToJson(map);
//        //先将该JSON存入Redis,key就是fid,value就是json数据
//        jedis.set(fid,json);


        //先获取文件的总大小
        AllSize = pic.getSize();
        String postFileName = pic.getOriginalFilename();

//        json = jedis.get(fid);
//        map = FileUtils.JsonToMap(json);
//        map.put("AllSize",AllSize);
//        map.put("uploadSize",AllSize);
//        map.put("isStart",true);
//        map.put("thisSize",thisSize);
//        map.put("isReady",false);
//        json = FileUtils.MapToJson(map);
//        jedis.set(fid,json);

        //获得一个UUID作为文件名称,防止不同用户上传同名文件导致文件被覆盖
        String name = UUID.randomUUID().toString();
        // 获取上传文件的扩展名(jpg/png/...)
        String extension = FilenameUtils.getExtension(postFileName);
        String objectName=name+"."+extension;
        String MD5= FileUtils.getMd5(pic);
        //计算完成MD5后将状态改为true,同步更新Redis中的数据
        isStart=true;
//        json = jedis.get(fid);
//        map = FileUtils.JsonToMap(json);
//        map.put("isStart",isStart);
//        json = FileUtils.MapToJson(map);
//        jedis.set(fid,json);
        if(MD5 == null){
//            jedis.close();
            return "-1";
        }
        //判断该MD5在数据库中是否存在对应文件
        Files file=service.findFileByMD5(MD5);
        if(file != null){
            //说明文件存在,那么有以下两种情况
            if(file.getIsfinish() == 1){
                //1.文件完整
                //      执行文件秒传操作
//                json = jedis.get(fid);
//                map = FileUtils.JsonToMap(json);
//                map.put("AllSize",AllSize);
//                map.put("uploadSize",AllSize);
//                map.put("isStart",true);
//                map.put("thisSize","-1");
//                map.put("isReady",true);
//                json = FileUtils.MapToJson(map);
//                jedis.set(fid,json);
//                System.out.println("哈哈哈哈哈");
                //获取文件id以及用户id,然后保存到userfiles表中
                Userfiles userfiles=new Userfiles();
                userfiles.setFid(file.getFid());
                userfiles.setIssecondpass(1);//1代表属于文件秒传
                userfiles.setUid(user.getUid());
                userfiles.setUploadtime(new Date());
                userfiles.setUfid(UUID.randomUUID().toString());
                int c = service.save(userfiles);
                if(c == 1){
//                    jedis.close();
                    return "1";
                }else{
//                    jedis.close();
                    return "3";
                }
            }else if(file.getIsfinish() == 0){
                //2.文件不完整
                //      执行文件续传
                //先获取旧文件的大小,该大小就是续传时需要跳过的大小
                Long oldSize=new File(file.getUrl()).length();
//                json = jedis.get(fid);
//                map = FileUtils.JsonToMap(json);
//                map.put("uploadSize",oldSize);
//                json = FileUtils.MapToJson(map);
//                jedis.set(fid,json);

                File f=new File(file.getUrl());
                try {
                    InputStream inputStream = pic.getInputStream();
                    OutputStream outputStream=new FileOutputStream(f,true);
                    inputStream.skip(oldSize);
                    byte[] by=new byte[2048];
                    //每次传输之后都要将偏移量添加到thisSize和uploadSize中
                    uploadSize=oldSize;//初始化上传大小为续传大小
                    int len=0;
                    while((len=inputStream.read(by)) > 0){
                        outputStream.write(by,0,len);
                        thisSize+=len;
                        uploadSize+=len;
                        Long thisTime=System.currentTimeMillis();
                        //判断是否超时60S
                        if((thisTime - time)/1000L >= 10L){
                            System.out.println("续传已超时");
                            break;
                        }
                        Thread.sleep(10);
                        //先获取里面的值
//                        json = jedis.get(fid);
//                        map = FileUtils.JsonToMap(json);
//                        map.put("AllSize",AllSize);
//                        map.put("uploadSize",uploadSize);
//                        map.put("isStart",isStart);
//                        map.put("thisSize",thisSize);
//                        map.put("isReady",false);
//                        json = FileUtils.MapToJson(map);
//                        jedis.set(fid,json);
                    }
                    outputStream.close();
                    inputStream.close();
                    //上传成功,更新上传状态为true
//                    json = jedis.get(fid);
//                    map = FileUtils.JsonToMap(json);
//                    map.put("isReady",true);
//                    json = FileUtils.MapToJson(map);
//                    jedis.set(fid,json);
                    //如果文件上传成功,则重新更新上传时间以及上传状态
                    file.setUploadtime(new Date());
                    file.setIsfinish(1);
                    int c=service.update(file);
                    if (c == 1){
                        //如果保存成功,则将文件应用于该用户
                        Userfiles userfiles=new Userfiles();
                        userfiles.setFid(file.getFid());
                        userfiles.setIssecondpass(1);//1代表属于文件秒传
                        userfiles.setUid(user.getUid());
                        userfiles.setUploadtime(new Date());
                        userfiles.setUfid(UUID.randomUUID().toString());
                        int c1 = service.save(userfiles);
                        if(c1 == 1){
//                            jedis.close();
                            return "1";
                        }else{
//                            jedis.close();
                            return "3";
                        }
                    }else{
//                        jedis.close();
                        return "3";
                    }
                } catch (IOException e) {
//                    jedis.close();
                    e.printStackTrace();
                }
            }
            return "1";
        }
        //否则将文件复制到本地
        //复制前先将记录保存到数据库中
        Files files=new Files();
        files.setAllsize(AllSize);
        files.setAlreadysize(0L);
        files.setFid(fid);
        files.setIsfinish(0);//0代表未完成  1代表已完成
        files.setMd5(MD5);
        files.setUid(user.getUid());
        files.setUrl(path+objectName);
        files.setUploadtime(new Date());
        int count=service.save(files);
        //保存好之后开始准备上传
        try {
            InputStream inputStream = pic.getInputStream();
            OutputStream outputStream=new FileOutputStream(new File(path+objectName));

            byte[] by=new byte[2048];
            //每次传输之后都要将偏移量添加到thisSize和uploadSize中
            int len=0;
            while((len=inputStream.read(by)) > 0){
                outputStream.write(by,0,len);
                thisSize+=len;
                uploadSize+=len;

                Long thisTime=System.currentTimeMillis();
                //判断是否超时60S
                if((thisTime - time)/1000L >= 10L){
                    System.out.println("上传已超时");
                    break;
                }

                //先获取里面的值
//                json = jedis.get(fid);
//                map = FileUtils.JsonToMap(json);
//                map.put("AllSize",AllSize);
//                map.put("uploadSize",uploadSize);
//                map.put("isStart",isStart);
//                map.put("thisSize",thisSize);
//                map.put("isReady",false);
//                json = FileUtils.MapToJson(map);
//                jedis.set(fid,json);
                Thread.sleep(10);
            }
            outputStream.close();
            inputStream.close();

            //上传成功,更新上传状态为true
//            json = jedis.get(fid);
//            map = FileUtils.JsonToMap(json);
//            map.put("isReady",true);
//            json = FileUtils.MapToJson(map);
//            jedis.set(fid,json);
            //如果文件上传成功,则重新更新上传时间以及上传状态
            Files f2=service.findFileById(fid);
            f2.setUploadtime(new Date());
            f2.setIsfinish(1);
            int c=service.update(f2);
            if (c == 1){
                //如果保存成功,则将文件应用于该用户
                Userfiles userfiles=new Userfiles();
                userfiles.setFid(fid);
                userfiles.setIssecondpass(1);//1代表属于文件秒传
                userfiles.setUid(user.getUid());
                userfiles.setUploadtime(new Date());
                userfiles.setUfid(UUID.randomUUID().toString());
                int c1 = service.save(userfiles);
                if(c1 == 1){
//                    jedis.close();
                    return "1";
                }else{
//                    jedis.close();
                    return "3";
                }
            }else{
//                jedis.close();
                return "3";
            }
        } catch (IOException e) {
//            jedis.close();
            return "2";
        }
    }
    @RequestMapping("/JDT")
    @ResponseBody
    public String JDT(HttpSession session){
//        Jedis jedis = pool.getResource();
//        String fid = (String) session.getAttribute("FID");
//        System.out.println(fid);
//        if(fid != null){
//            Jedis jedis = new Jedis("127.0.0.1", 6379);
//            String json = jedis.get(fid);
//            Map<String, Object> map = FileUtils.JsonToMap(json);
//            if ((Boolean) map.get("isReady")){
//                jedis.del(fid);
//            }
//            jedis.close();
//            return json;
//        }else{
            Map<String, Object> map = new HashMap<>();
            map.put("AllSize",AllSize);//文件总大小
            map.put("uploadSize",uploadSize);//文件上传大小
            map.put("isStart","false");//文件是否在验证MD5中
            //用来计算网速,因为异步一秒一次,所以每次获取时,先获取最新上传大小,
            // 减去上次的thisSize,得到的就是实时网速
            map.put("thisSize",thisSize);
            //文件是否上传成功
            map.put("isReady",false);
            //设定时间,用户每次交互时都会更新时间
            time=System.currentTimeMillis();
            return FileUtils.MapToJson(map);
//        }
    }
}
