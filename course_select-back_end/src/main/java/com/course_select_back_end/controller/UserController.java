package com.course_select_back_end.controller;

import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.User;
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

    @PostMapping
    public Result<?> login(@RequestBody User user) {
        return Result.success();
    }
}
