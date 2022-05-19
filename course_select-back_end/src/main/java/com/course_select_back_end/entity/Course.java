package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course")
public class Course {
    @TableId
    private String cname; //课程名
    private Integer credit; //学分
    private String college; //学院
}
