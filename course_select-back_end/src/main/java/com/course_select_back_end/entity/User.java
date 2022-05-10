package com.course_select_back_end.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
//验证用户登录信息实体
public class User {
    private String name; //用户名
    private String password; //密码
}
