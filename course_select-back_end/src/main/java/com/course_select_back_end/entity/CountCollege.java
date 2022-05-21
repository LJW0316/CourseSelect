package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("count_college")
//院系统计视图
public class CountCollege {
    @TableId
    private String collegeName; //学院名
    private Integer countStudent; //学生人数
    private Integer countTeacher; //教师人数
}
