package com.course_select_back_end.controller;

import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.User;
import com.course_select_back_end.mapper.AdminMapper;
import com.course_select_back_end.mapper.StudentMapper;
import com.course_select_back_end.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    StudentMapper studentMapper;
    @Autowired
    TeacherMapper teacherMapper;
    @Autowired
    AdminMapper adminMapper;

    @PostMapping("login")
    public Result<?> login(@RequestBody User user) {
        System.out.println(user);
        if (studentMapper.selectById(user.getUsername()) != null) {
            user.setRole("student");
            user.setName(studentMapper.selectById(user.getUsername()).getName());
        } else if (teacherMapper.selectById(user.getUsername()) != null) {
            user.setRole("teacher");
            user.setName(teacherMapper.selectById(user.getUsername()).getName());
        } else if (adminMapper.selectById(user.getUsername()) != null) {
            user.setRole("admin");
            user.setName(adminMapper.selectById(user.getUsername()).getName());
        } else {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(user);
    }
}
