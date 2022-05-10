package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.Student;
import com.course_select_back_end.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("student")
public class StudentController {
    @Autowired
    StudentMapper studentMapper;

    //插入学生
    @PostMapping
    public Result<?> insert(@RequestBody Student student) {
        Student res = studentMapper.selectOne(Wrappers.<Student>lambdaQuery().eq(Student::getSnum, student.getSnum()));
        if (res == null) {
            studentMapper.insert(student);
            return Result.success();
        } else {
            return Result.error("-1", "学号已存在！");
        }
    }

    //分页查询学生
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Student> wapper = Wrappers.<Student>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(Student::getName, search);
        }
        Page<Student> studentPage = studentMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
        return Result.success(studentPage);
    }

    //更新学生表
    @PutMapping
    public Result<?> update(@RequestBody Student student) {
        if (studentMapper.updateById(student) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败");
        }
    }

    //删除学生表
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        if (studentMapper.deleteById(id) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }
}
