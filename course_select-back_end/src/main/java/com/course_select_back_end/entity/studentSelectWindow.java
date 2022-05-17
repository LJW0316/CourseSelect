package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("student_select_window")
//学生选课视图
public class studentSelectWindow {
    @TableId
    private String cnum;
    private String cname;
    private Integer credit;
    private String tnum;
    private String openTime;
    private String place;
    private Integer curNum;
    private Integer maxNum;
    private String semester;
}
