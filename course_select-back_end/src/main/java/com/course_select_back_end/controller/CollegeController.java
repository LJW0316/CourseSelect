package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.College;
import com.course_select_back_end.mapper.CollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("college")
public class CollegeController {

    @Autowired
    CollegeMapper collegeMapper;

    //插入学院
    @PostMapping
    public Result<?> insert(@RequestBody College college) {
        College res = collegeMapper.selectOne(Wrappers.<College>lambdaQuery().eq(College::getCollegeNum, college.getCollegeNum()));
        if (res == null) {
            collegeMapper.insert(college);
            return Result.success();
        } else {
            return Result.error("-1", "学院号已存在！");
        }
    }

    //分页查询学院
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<College> wapper = Wrappers.<College>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(College::getCollegeName, search);
        }
        Page<College> collegePage = collegeMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
        return Result.success(collegePage);
    }

    //更新院系表
    @PutMapping
    public Result<?> update(@RequestBody College college) {
        if (collegeMapper.updateById(college) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "更新失败");
        }
    }

    //删除院系表
    @DeleteMapping("/{id}")
    public Result<?> delete(@PathVariable Integer id) {
        if (collegeMapper.deleteById(id) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }
}
