package com.course_select_back_end;

import com.course_select_back_end.entity.College;
import com.course_select_back_end.entity.Semester;
import com.course_select_back_end.mapper.CollegeMapper;
import com.course_select_back_end.mapper.CourseSelectMapper;
import com.course_select_back_end.mapper.SemesterMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@SpringBootTest
class CourseSelectBackEndApplicationTests {

    @Autowired
    CollegeMapper collegeMapper;
    @Autowired
    SemesterMapper semesterMapper;
    @Autowired
    CourseSelectMapper courseSelectMapper;

    @Test
    void college() {
        List<College> collegeList;
        collegeList = collegeMapper.selectList(null);
        System.out.println(collegeList);
    }

    @Test
    void semester() {
        List<Semester> semesterList;
        semesterList = semesterMapper.selectList(null);
        System.out.println(semesterList);
    }
}
