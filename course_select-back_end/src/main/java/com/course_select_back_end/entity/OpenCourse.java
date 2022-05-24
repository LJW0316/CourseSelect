package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("opencourse")
public class OpenCourse {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private String cnum; //课程号
    private String openTime; //开课时间
    private String tnum;   //教师号
    private String semester; //学期
    private Integer curNum; //已选课人数
    private Integer maxNum; //课程容量
    private String place;//开课地点
}
