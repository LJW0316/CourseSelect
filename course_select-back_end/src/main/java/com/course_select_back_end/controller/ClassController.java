package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.Class;
import com.course_select_back_end.mapper.ClassMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("class")
public class ClassController {
    @Autowired
    ClassMapper classMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String semester,
                              @RequestParam(defaultValue = "") String tnum) {
        System.out.println(semester);
        System.out.println(tnum);
        LambdaQueryWrapper<Class> wrapper = Wrappers.<Class>lambdaQuery().eq(Class::getSemester, semester).eq(Class::getTnum, tnum);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(Class::getCname, search);
        }
        Page<Class> ClassPage = classMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ClassPage);
    }
}
