package com.course_select_back_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course_select_back_end.entity.courseSelect;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface courseSelectMapper extends BaseMapper<courseSelect> {
    void setGrade(float usual_grade,float final_grade);
}

