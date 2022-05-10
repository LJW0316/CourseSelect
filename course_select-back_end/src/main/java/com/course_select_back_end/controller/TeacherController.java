package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.Teacher;
import com.course_select_back_end.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("teacher")
public class TeacherController {
    @Autowired
    TeacherMapper teacherMapper;

    //插入教师
    @PostMapping
    public Result<?> insert(@RequestBody  Teacher teacher) {
         Teacher res = teacherMapper.selectOne(Wrappers.<Teacher>lambdaQuery().eq(Teacher::getTnum, teacher.getTnum()));
        if (res == null) {
            teacherMapper.insert(teacher);
            return Result.success();
        } else {
            return Result.error("-1", "学号已存在！");
        }
    }

    //分页查询教师
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<Teacher> wapper = Wrappers.<Teacher>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(Teacher::getName, search);
        }
        Page<Teacher> studentPage = teacherMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
        return Result.success(studentPage);
    }

    //更新教师表
    @PutMapping
    public Result<?> update(@RequestBody Teacher teacher) {
        if (teacherMapper.updateById(teacher) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败");
        }
    }

    //删除教师表
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        if (teacherMapper.deleteById(id) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }
}
