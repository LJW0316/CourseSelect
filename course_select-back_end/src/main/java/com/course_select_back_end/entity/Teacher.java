package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("teacher")
//教师表
public class Teacher {
    @TableId
    private String tnum; //工号
    private String name; //姓名
    private String sex; //性别
    private String password; //密码
    private String college; //院系
    private String title; //职称
}
