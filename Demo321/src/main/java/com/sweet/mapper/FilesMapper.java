package com.sweet.mapper;

import com.sweet.model.Files;
import com.sweet.model.FilesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FilesMapper {
    long countByExample(FilesExample example);

    int deleteByExample(FilesExample example);

    int deleteByPrimaryKey(String fid);

    int insert(Files record);

    int insertSelective(Files record);

    List<Files> selectByExample(FilesExample example);

    Files selectByPrimaryKey(String fid);

    int updateByExampleSelective(@Param("record") Files record, @Param("example") FilesExample example);

    int updateByExample(@Param("record") Files record, @Param("example") FilesExample example);

    int updateByPrimaryKeySelective(Files record);

    int updateByPrimaryKey(Files record);

    Files findFileByMD5(String md5);
}