package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("selectcourse")
@AllArgsConstructor
@NoArgsConstructor
//选课表
public class CourseSelect {
    @TableId
    private String cnum;
    private String semester;
    private String snum;
    private String tnum;
    private float usualGrade;
    private float finalGrade;
    private float gpa;
}
