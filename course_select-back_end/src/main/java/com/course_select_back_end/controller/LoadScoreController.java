package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.CourseSelect;
import com.course_select_back_end.entity.LoadScore;
import com.course_select_back_end.mapper.CourseSelectMapper;
import com.course_select_back_end.mapper.LoadScoreMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("loadscore")
public class LoadScoreController {
    @Autowired
    LoadScoreMapper loadScoreMapper;
    @Autowired
    CourseSelectMapper courseSelectMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String semester,
                              @RequestParam(defaultValue = "") String cnum,
                              @RequestParam(defaultValue = "") String tnum) {
        //选出指定学期与教师同一学院的课程
        LambdaQueryWrapper<LoadScore> wrapper = Wrappers.<LoadScore>lambdaQuery().eq(LoadScore::getSemester, semester)
                .eq(LoadScore::getCnum, cnum).eq(LoadScore::getTnum, tnum);
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(LoadScore::getName, search);
        }
        Page<LoadScore> ClassPage = loadScoreMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ClassPage);
    }

    @PutMapping
    public Result<?> update(@RequestBody LoadScore loadScore) {
        LambdaQueryWrapper<CourseSelect> wrapper = Wrappers.<CourseSelect>lambdaQuery()
                .eq(CourseSelect::getCnum, loadScore.getCnum())
                .eq(CourseSelect::getSemester, loadScore.getSemester())
                .eq(CourseSelect::getTnum, loadScore.getTnum())
                .eq(CourseSelect::getSnum, loadScore.getSnum());
        CourseSelect courseSelect = courseSelectMapper.selectOne(wrapper);
        System.out.println(courseSelect);
        courseSelect.setUsualGrade(loadScore.getUsualGrade());
        courseSelect.setFinalGrade(loadScore.getFinalGrade());
        if (courseSelectMapper.updateById(courseSelect) == 1) {
            courseSelectMapper.setGrade(courseSelect.getCnum(), courseSelect.getSemester(), courseSelect.getSnum(),
                    courseSelect.getTnum(), courseSelect.getUsualGrade(), courseSelect.getFinalGrade());
            return Result.success();
        } else {
            return Result.error("-1", "更新失败");
        }
    }

}
