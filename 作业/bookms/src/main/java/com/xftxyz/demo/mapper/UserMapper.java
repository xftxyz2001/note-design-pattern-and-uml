package com.xftxyz.demo.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.xftxyz.demo.domain.User;
import com.xftxyz.demo.domain.UserExample;

public interface UserMapper {
    long countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer userId);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer userId);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    // 分页查询
    List<User> selectByPageNum(@Param("currIndex") int currIndex, @Param("pageSize") int pageSize);

    // 查询总数
    int selectUserCount();
}