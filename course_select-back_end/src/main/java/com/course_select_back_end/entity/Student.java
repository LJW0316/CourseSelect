package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student")
//学生表
public class Student {
    @TableId
    private String snum; //学号
    private String password; //密码
    private String name; //姓名
    private Integer grade; //年级
    private Double gpa; //绩点
    private String college; //学院
}
