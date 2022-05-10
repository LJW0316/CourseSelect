package com.course_select_back_end;

import com.course_select_back_end.entity.College;
import com.course_select_back_end.entity.Student;
import com.course_select_back_end.mapper.CollegeMapper;
import com.course_select_back_end.mapper.StudentMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class CourseSelectBackEndApplicationTests {

    @Autowired
    CollegeMapper collegeMapper;

    @Test
    void college() {
        List<College> collegeList;
        collegeList = collegeMapper.selectList(null);
        System.out.println(collegeList);
    }


}
