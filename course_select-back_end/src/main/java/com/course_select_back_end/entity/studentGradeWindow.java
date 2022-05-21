package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student_grade_window")
//学生成绩视图
public class studentGradeWindow {
    @TableId
    private String semester;
    private String snum;
    private String cname;
    private float usualGrade;
    private float finalGrade;
    private float gpa;
}
