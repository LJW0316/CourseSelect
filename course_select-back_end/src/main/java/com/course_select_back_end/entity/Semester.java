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
@TableName("semester")
public class Semester {
    @TableId(type = IdType.ASSIGN_ID)
    private Integer id;
    private String cnum; //课程号
    private String semester; //学期
    private String cname; //课程名
}
