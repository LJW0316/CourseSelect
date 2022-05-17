package com.course_select_back_end.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("opencourse")

//开课表
public class newOpencourse {
    @TableId
    private String cnum;
    private String semester;
    private String tnum;
    private String openTime;
    private String place;
    private Integer curNum;
    private Integer maxNum;
}
