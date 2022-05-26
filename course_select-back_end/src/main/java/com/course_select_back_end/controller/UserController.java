package com.course_select_back_end.controller;

import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.Admin;
import com.course_select_back_end.entity.Student;
import com.course_select_back_end.entity.Teacher;
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
        Student student = studentMapper.selectById(user.getUsername());
        Teacher teacher = teacherMapper.selectById(user.getUsername());
        //Admin admin = adminMapper.selectById(user.getUsername());
        if (student != null && student.getPassword().equals(user.getPassword())) {
            user.setRole("student");
            user.setName(studentMapper.selectById(user.getUsername()).getName());
        } else if (teacher != null && teacher.getPassword().equals(user.getPassword())) {
            user.setRole("teacher");
            user.setName(teacherMapper.selectById(user.getUsername()).getName());
        }
/*
        else if (admin != null && admin.getPassword().equals(user.getPassword())) {
            user.setRole("admin");
            user.setName(adminMapper.selectById(user.getUsername()).getName());
        }*/
        else {
            return Result.error("-1", "用户名或密码错误");
        }
        return Result.success(user);
    }
}
