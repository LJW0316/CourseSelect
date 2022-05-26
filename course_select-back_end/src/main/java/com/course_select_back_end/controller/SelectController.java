package com.course_select_back_end.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.course_select_back_end.common.Result;
import com.course_select_back_end.entity.*;
import com.course_select_back_end.mapper.*;
import com.course_select_back_end.service.Check;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@ResponseBody
@RequestMapping("/student_course")
public class SelectController {
    @Autowired
    com.course_select_back_end.mapper.studentSelectWindowMapper studentSelectWindowMapper;
    @Autowired
    newOpencourseMapper newOpencourseMapper;
    @Autowired
    CourseSelectMapper courseSelectMapper;
    @Autowired
    SemesterMapper semesterMapper;
    @Autowired
    studentCourseWindowMapper studentCourseWindowMapper;
    @Autowired
    GradeMapper gradeMapper;
    @Autowired
    studentGradeWindowMapper studentGradeWindowMapper;


    @GetMapping("/search_course")
    public Result<?> search_course(@RequestParam(defaultValue = "1") Integer pageNum, @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String cname, @RequestParam(defaultValue = "2022春") String semester){
        Page<studentSelectWindow> studentSelectWindow=studentSelectWindowMapper.selectPage(new Page<>(pageNum,pageSize), Wrappers.<studentSelectWindow>lambdaQuery().like(com.course_select_back_end.entity.studentSelectWindow::getCname,cname).eq(com.course_select_back_end.entity.studentSelectWindow::getSemester,semester));
        return Result.success(studentSelectWindow);
    }

    @PostMapping("/view_course")
    public Result<?> view_course(@RequestBody dealWithStudentCourseWindow para){
        QueryWrapper<studentCourseWindow> q=new QueryWrapper<>();
        q.eq("snum",para.getSnum()).eq("semester",para.getSemester());
        List<studentCourseWindow> lst=studentCourseWindowMapper.selectList(q);

        return Result.success(lst);
    }

    @PostMapping("/total_credit")
    public Result<?> total_credit(@RequestBody dealWithStudentCourseWindow para){
        QueryWrapper<studentCourseWindow> q=new QueryWrapper<>();
        q.eq("snum",para.getSnum()).eq("semester",para.getSemester()).select("credit");
        List<studentCourseWindow> lst=studentCourseWindowMapper.selectList(q);
        int sum=0;
        for(int i=0;i<lst.size();i++){
            sum+=lst.get(i).getCredit();
        }
        return Result.success(sum);
    }

    @GetMapping("/getSemester")
    public List<Semester> getSemester(){
        QueryWrapper<Semester> q=new QueryWrapper<>();
        q.select("semester").groupBy("semester");
        List<Semester> lst=semesterMapper.selectList(q);
        return lst;
    }

    @PostMapping("/select_course")
    public Result<?> save(@RequestBody CourseSelect course){
        course.setFinalGrade(0);
        course.setUsualGrade(0);
        course.setGpa(0);
        QueryWrapper<newOpencourse> opencourse=new QueryWrapper<>();
        opencourse.eq("cnum",course.getCnum()).eq("tnum",course.getTnum()).eq("semester",course.getSemester());
        QueryWrapper<CourseSelect> selectcourse=new QueryWrapper<>();
        selectcourse.eq("cnum",course.getCnum()).eq("semester",course.getSemester()).eq("snum",course.getSnum());
        QueryWrapper<studentCourseWindow> selectcourse2=new QueryWrapper<>();
        selectcourse2.eq("semester",course.getSemester()).eq("snum",course.getSnum());
        if(newOpencourseMapper.selectCount(opencourse)==0){
            return Result.error("404","该课程本学期未开！");
        }
        else if(newOpencourseMapper.selectOne(opencourse).getCurNum()==newOpencourseMapper.selectOne(opencourse).getMaxNum()){
            return Result.error("404","人数已满！");
        }
        else if(courseSelectMapper.selectCount(selectcourse)!=0){
            return Result.error("404","不能重复选课！");
        }
        else{

            List<studentCourseWindow> selectResult=studentCourseWindowMapper.selectList(selectcourse2);

            if (selectResult != null) {
                    for(studentCourseWindow item : selectResult)
                    {
                        if (!Check.checkTimeConflict(item.getOpenTime(), newOpencourseMapper.selectOne(opencourse).getOpenTime())) {
                            return Result.error("-1", "时间冲突，选课失败！");
                        }
                    }
            }

            courseSelectMapper.insert(course);
            return Result.success("666","选课成功！");
        }
    }

    @PostMapping("/select_grade")
    public Result<?> select_grade(@RequestBody dealWithStudentCourseWindow para){
        QueryWrapper<studentGradeWindow> grade=new QueryWrapper<>();
        grade.eq("semester",para.getSemester()).eq("snum",para.getSnum());
        List<studentGradeWindow> lst=studentGradeWindowMapper.selectList(grade);

        return Result.success(lst);
    }

    @PostMapping("/select_rank")
    public Result<?> select_rank(@RequestBody dealWithStudentCourseWindow para){
        QueryWrapper<Grade> grade=new QueryWrapper<>();
        grade.eq("semester",para.getSemester()).eq("snum",para.getSnum());
        QueryWrapper<Grade> rank=new QueryWrapper<>();
        rank.eq("semester",para.getSemester()).orderByDesc("gpa");
        ArrayList<Float> lst = new ArrayList<Float>();
        float Grade=gradeMapper.selectOne(grade).getGpa()/gradeMapper.selectOne(grade).getCredit();
        float Credit=gradeMapper.selectOne(grade).getCredit();
        List<Grade> Ranks=gradeMapper.selectList(rank);
        float Rank=0;
        for(int i=0;i<Ranks.size();i++){
            if(Ranks.get(i).getSnum()== para.getSnum()){
                Rank=i;
            }
        }
        lst.add(Grade);
        lst.add(Credit);
        lst.add(Rank+1);
        return Result.success(lst);
    }

    @PostMapping("/retreat_course")
    public Result<?> delete(@RequestBody CourseSelect course){
        QueryWrapper<newOpencourse> opencourse=new QueryWrapper<>();
        opencourse.eq("cnum",course.getCnum()).eq("semester",course.getSemester());
        QueryWrapper<CourseSelect> selectcourse=new QueryWrapper<>();
        selectcourse.eq("cnum",course.getCnum()).eq("semester",course.getSemester()).eq("snum",course.getSnum());

        if(!newOpencourseMapper.exists(opencourse)){
            return Result.error("404","您没有选过该课程!");
        }
        else if(courseSelectMapper.selectOne(selectcourse).getFinalGrade()!=0||courseSelectMapper.selectOne(selectcourse).getUsualGrade()!=0||courseSelectMapper.selectOne(selectcourse).getGpa()!=0){
            return Result.error("404","老师已经登入成绩！无法退课！");
        }
        else{
            courseSelectMapper.delete(selectcourse);
            return Result.success("666","退课成功！");
        }
    }

}
