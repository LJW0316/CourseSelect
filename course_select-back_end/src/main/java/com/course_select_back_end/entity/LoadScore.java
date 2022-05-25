package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("loadscore")
public class LoadScore {
    @TableId
    private String cnum;
    private String semester;
    private String tnum;
    private String snum;
    private String name;
    private float usualGrade;
    private float finalGrade;
}
