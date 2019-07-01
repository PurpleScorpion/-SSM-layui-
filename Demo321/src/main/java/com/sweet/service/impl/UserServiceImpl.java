package com.sweet.service.impl;

import com.sweet.mapper.FilesMapper;
import com.sweet.mapper.UserMapper;
import com.sweet.mapper.UserfilesMapper;
import com.sweet.model.Files;
import com.sweet.model.Userfiles;
import com.sweet.service.UserServiceI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserServiceI {

//    @Autowired
//    private UserMapper userMapper;
    @Autowired
    private UserfilesMapper userfilesMapper;
    @Autowired
    private FilesMapper filesMapper;

    @Override
    public int save(Files files) {
        return filesMapper.insert(files);
    }

    @Override
    public Files findFileByMD5(String md5) {
        return filesMapper.findFileByMD5(md5);
    }

    @Override
    public int update(Files f2) {
        return filesMapper.updateByPrimaryKey(f2);
    }

    @Override
    public int save(Userfiles userfiles) {
        return userfilesMapper.insert(userfiles);
    }

    @Override
    public Files findFileById(String fid) {
        return filesMapper.selectByPrimaryKey(fid);
    }
}
