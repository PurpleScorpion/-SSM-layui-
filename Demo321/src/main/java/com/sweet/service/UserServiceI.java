package com.sweet.service;

import com.sweet.model.Files;
import com.sweet.model.Userfiles;

public interface UserServiceI {
    Files findFileByMD5(String md5);

    int save(Files files);

    int save(Userfiles userfiles);

    int update(Files f2);

    Files findFileById(String fid);
}
