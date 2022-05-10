package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.mapper.OpenCourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("opencourse")
public class OpenCourseController {
    @Autowired
    OpenCourseMapper openCourseMapper;

    //分页查询开设课程
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<OpenCourse> wapper = Wrappers.<OpenCourse>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(OpenCourse::getCnum, search);
        }
        Page<OpenCourse> openCoursePage = openCourseMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
        return Result.success(openCoursePage);
    }

    //删除开课表表
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        if (openCourseMapper.deleteById(id) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }

}
