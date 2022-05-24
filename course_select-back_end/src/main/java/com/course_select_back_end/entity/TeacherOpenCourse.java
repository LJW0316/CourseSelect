package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher_open_course")
public class TeacherOpenCourse {
    @TableId
    private String cnum;
    private String cname;
    private String semester;
    private String college;
    private Integer credit;
}
