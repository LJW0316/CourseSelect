package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.OpenCourse;
import com.course_select_back_end.entity.Teacher;
import com.course_select_back_end.entity.TeacherOpenCourse;
import com.course_select_back_end.mapper.OpenCourseMapper;
import com.course_select_back_end.mapper.TeacherMapper;
import com.course_select_back_end.mapper.TeacherOpenCourseMapper;
import com.course_select_back_end.service.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("opencourse")
public class OpenCourseController {
    @Autowired
    OpenCourseMapper openCourseMapper;
    @Autowired
    TeacherOpenCourseMapper teacherOpenCourseMapper;
    @Autowired
    TeacherMapper teacherMapper;

    //分页查询教师可以开设的课程
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String semester,
                              @RequestParam(defaultValue = "") String tnum) {
        //选出指定学期与教师同一学院的课程
        Teacher teacher = teacherMapper.selectById(tnum);
        LambdaQueryWrapper<TeacherOpenCourse> wrapper = Wrappers.<TeacherOpenCourse>lambdaQuery().eq(TeacherOpenCourse::getSemester, semester)
                .eq(TeacherOpenCourse::getCollege, teacher.getCollege());
        if (StrUtil.isNotBlank(search)) {
            wrapper.like(TeacherOpenCourse::getCname, search);
        }
        Page<TeacherOpenCourse> ClassPage = teacherOpenCourseMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
        return Result.success(ClassPage);
    }

    //教师开课
    @PostMapping
    Result<?> openCourse(@RequestBody OpenCourse openCourse) {
        if (teacherOpenCourseMapper.selectOne(Wrappers.<TeacherOpenCourse>lambdaQuery()
                .eq(TeacherOpenCourse::getCnum, openCourse.getCnum())) == null) {
            return Result.error("-1", "请输入正确的课程号");
        }
        TeacherOpenCourse teacherOpenCourse = teacherOpenCourseMapper.selectById(openCourse.getCnum());
        if (openCourseMapper.selectOne(Wrappers.<OpenCourse>lambdaQuery().eq(OpenCourse::getCnum, openCourse.getCnum())) != null){
            return Result.error("-1", "已开设该课程!");
        }
        List<OpenCourse> openCourseList = openCourseMapper.selectList(Wrappers.<OpenCourse>lambdaQuery().eq(OpenCourse::getTnum, openCourse.getTnum())
                .eq(OpenCourse::getSemester, openCourse.getSemester()));
        if (openCourseList != null) {
            for (OpenCourse item : openCourseList) {
                if (!Check.checkTimeConflict(openCourse.getOpenTime(), item.getOpenTime())) {
                    return Result.error("-1", "时间冲突，开课失败！");
                }
            }
        }
        openCourseMapper.insert(openCourse);
        return Result.success();
    }


}
