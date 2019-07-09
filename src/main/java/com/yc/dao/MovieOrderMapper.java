package com.yc.dao;

import com.yc.bean.MovieOrder;
import com.yc.bean.MovieOrderExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MovieOrderMapper {
    long countByExample(MovieOrderExample example);

    int deleteByExample(MovieOrderExample example);

    int deleteByPrimaryKey(Integer orderId);

    int insert(MovieOrder record);

    int insertSelective(MovieOrder record);

    List<MovieOrder> selectByExample(MovieOrderExample example);

    MovieOrder selectByPrimaryKey(Integer orderId);

    int updateByExampleSelective(@Param("record") MovieOrder record, @Param("example") MovieOrderExample example);

    int updateByExample(@Param("record") MovieOrder record, @Param("example") MovieOrderExample example);

    int updateByPrimaryKeySelective(MovieOrder record);

    int updateByPrimaryKey(MovieOrder record);
}