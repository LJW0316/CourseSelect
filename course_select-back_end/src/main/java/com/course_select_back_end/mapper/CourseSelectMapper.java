package com.course_select_back_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course_select_back_end.entity.CourseSelect;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CourseSelectMapper extends BaseMapper<CourseSelect> {
    void setGrade(@Param("Cnum") String Cnum,
                  @Param("Semester") String Cemester,
                  @Param("Snum") String Snum,
                  @Param("Tnum") String Tnum,
                  @Param("usualgrade") float usualgrade,
                  @Param("finalgrade") float finalgrade);
}

