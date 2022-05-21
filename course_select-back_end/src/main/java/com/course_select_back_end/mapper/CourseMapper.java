package com.course_select_back_end.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.course_select_back_end.entity.Course;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CourseMapper extends BaseMapper<Course> {
}
