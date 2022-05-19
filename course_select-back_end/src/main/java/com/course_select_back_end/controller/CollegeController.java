package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.College;
import com.course_select_back_end.entity.CountCollege;
import com.course_select_back_end.mapper.CollegeMapper;
import com.course_select_back_end.mapper.CountCollegeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("college")
public class CollegeController {

    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    CountCollegeMapper countCollegeMapper;

    //插入学院
    @PostMapping
    public Result<?> insert(@RequestBody College college) {
        College res = collegeMapper.selectOne(Wrappers.<College>lambdaQuery().eq(College::getCollegeName, college.getCollegeName()));
        if (res == null) {
            collegeMapper.insert(college);
            return Result.success();
        } else {
            return Result.error("-1", "学院已存在！");
        }
    }

    //分页查询学院
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<CountCollege> wapper = Wrappers.<CountCollege>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(CountCollege::getCollegeName, search);
        }
        Page<CountCollege> collegePage = countCollegeMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
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
    @DeleteMapping("/{collegeName}")
    public Result<?> delete(@PathVariable String collegeName) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("college_name", collegeName);
        List<CountCollege> countCollegeList = countCollegeMapper.selectByMap(map);
        if (countCollegeList.get(0).getCountStudent() != 0 || countCollegeList.get(0).getCountTeacher() != 0) {
            return Result.error("-1","学院人数不为0，禁止删除！");
        }
        if (collegeMapper.deleteByMap(map) == 1) {
            return Result.success();
        } else {
            return Result.error("-1", "删除失败");
        }
    }
}
