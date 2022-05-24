package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("class")
//课程管理视图
public class Class {
    @TableId
    private String tnum;//教师号
    private String cnum;//课程号
    private String cname;//课程名
    private String semester;//学期
    private String openTime;//开课时间
    private String place;//开课地点
    private Integer curNum;//当前人数
    private Integer maxNum;//最大人数
    private String college;//学院
}
