package org.zerock.springex.mapper;

import org.apache.ibatis.annotations.Select;

public interface TimeMapper {
    @Select("select now()") //MyBatis 쿼리 작성할 수 있게 함
    String getTime();
}
