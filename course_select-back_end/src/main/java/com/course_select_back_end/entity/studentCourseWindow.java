package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student_course_window")
//学生选课视图
public class studentCourseWindow {
    @TableId
    private String snum;
    private String cnum;
    private String cname;
    private Integer credit;
    private String name;
    private String college;
    private String openTime;
    private String place;
    private String semester;
}

