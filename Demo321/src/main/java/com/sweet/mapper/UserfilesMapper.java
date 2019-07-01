package com.sweet.mapper;

import com.sweet.model.Userfiles;
import com.sweet.model.UserfilesExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserfilesMapper {
    long countByExample(UserfilesExample example);

    int deleteByExample(UserfilesExample example);

    int deleteByPrimaryKey(String ufid);

    int insert(Userfiles record);

    int insertSelective(Userfiles record);

    List<Userfiles> selectByExample(UserfilesExample example);

    Userfiles selectByPrimaryKey(String ufid);

    int updateByExampleSelective(@Param("record") Userfiles record, @Param("example") UserfilesExample example);

    int updateByExample(@Param("record") Userfiles record, @Param("example") UserfilesExample example);

    int updateByPrimaryKeySelective(Userfiles record);

    int updateByPrimaryKey(Userfiles record);
}