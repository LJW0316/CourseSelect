package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@TableName("college")
@AllArgsConstructor
@NoArgsConstructor
//学院表
public class College {
    @TableId(type = IdType.AUTO)
    private Integer collegeNum; //院系号
    private String collegeName; //院系名
}
