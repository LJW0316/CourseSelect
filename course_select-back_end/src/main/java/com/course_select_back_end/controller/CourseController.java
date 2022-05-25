package com.course_select_back_end.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.Course;
import com.course_select_back_end.entity.CourseShow;
import com.course_select_back_end.entity.Semester;
import com.course_select_back_end.entity.studentSelectWindow;
import com.course_select_back_end.mapper.CourseMapper;
import com.course_select_back_end.mapper.CourseShowMapper;
import com.course_select_back_end.mapper.SemesterMapper;
import com.course_select_back_end.mapper.studentSelectWindowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;


@RestController
@CrossOrigin
@RequestMapping("course")
public class CourseController {
    @Autowired
    CourseMapper courseMapper;
    @Autowired
    SemesterMapper semesterMapper;
    @Autowired
    CourseShowMapper courseShowMapper;
    @Autowired
    studentSelectWindowMapper studentSelectWindowMapper;

    //分页查询学院
    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        LambdaQueryWrapper<CourseShow> wapper = Wrappers.<CourseShow>lambdaQuery();
        if (StrUtil.isNotBlank(search)) {
            wapper.like(CourseShow::getCname, search);
        }
        Page<CourseShow> collegePage = courseShowMapper.selectPage(new Page<>(pageNum, pageSize), wapper);
        return Result.success(collegePage);
    }

    @PostMapping
    public Result<?> insert(@RequestBody CourseShow courseShow) {
        Semester res = semesterMapper.selectOne(Wrappers.<Semester>lambdaQuery().eq(Semester::getCnum, courseShow.getCnum())
                .eq(Semester::getSemester, courseShow.getSemester()));
        if (res == null) {
            Course course = new Course();
            course.setCname(courseShow.getCname());
            course.setCredit(courseShow.getCredit());
            course.setCollege(courseShow.getCollege());
            if (courseMapper.selectById(course.getCname()) == null) {
                courseMapper.insert(course);
            }
            Semester semester = new Semester();
            semester.setSemester(courseShow.getSemester());
            semester.setCname(courseShow.getCname());
            semester.setCnum(courseShow.getCnum());
            semesterMapper.insert(semester);
            return Result.success();
        } else {
            return Result.error("-1", "课程已存在！");
        }
    }

    @DeleteMapping
    public Result<?> delete(@RequestBody CourseShow courseShow) {
        List<studentSelectWindow> res = studentSelectWindowMapper.selectList(Wrappers.<studentSelectWindow>lambdaQuery().
                eq(studentSelectWindow::getCnum, courseShow.getCnum()).eq(studentSelectWindow::getSemester, courseShow.getSemester()));
        boolean flag = true;
        for (studentSelectWindow selectWindow : res) {
            if (selectWindow.getCurNum() > 0) {
                flag = false;
                break;
            }
        }
        if (flag) {
            if (semesterMapper.delete(Wrappers.<Semester>lambdaQuery()
                    .eq(Semester::getCnum, courseShow.getCnum()).eq(Semester::getSemester, courseShow.getSemester())) == 1) {
                return Result.success();
            } else {
                return Result.error("-1", "删除失败");
            }
        } else {
            return Result.error("-1", "选课人数不为0，禁止删除！");
        }
    }

    //返回学期列表
    @GetMapping("/semester")
    public Result<?> semester() {
        List<Semester> semester = semesterMapper.selectList(null);
        List<Object> semesterResList = new ArrayList<>();
        Set<String> semesterSet = new HashSet<>();
        for (Semester value : semester) {
            semesterSet.add(value.getSemester());
        }
        for (String value : semesterSet) {
            HashMap<String, String> item = new HashMap<>();
            item.put("label", value);
            item.put("value", value);
            semesterResList.add(item);
        }
        return Result.success(semesterResList);
    }
}
