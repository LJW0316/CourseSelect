package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("course_show")
//课程管理视图
public class CourseShow {
    @TableId
    private String cnum; //课程号
    private String cname; //课程名
    private String college; //学院
    private String semester;//学期
    private Integer credit; //学分
}
