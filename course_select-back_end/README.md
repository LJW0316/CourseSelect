# NOTICE
The course number and the semester determine a course together.

For example, there is an entity contents of a course number(1001), a senester(2022春) and a course name(数据结构), which means that the course number(1001) marks the course named "数据结构" in semester "2022春".

Of particular note is the course number may mark different courses in different semester, but courses and course numbers are one-to-one for specific semesters. That's why when a student want to select one course, he/she should offer a course number, a semester and a teacher number.Only in this way can a course be uniquely identified.


#NEW of BACK END

entity&mapper of selectcourse\newOpencourse\studentSelectWindow\semester\student_course_window\dealWithStudentCourseWindow

update com/course_select_back_end/common/Result.java add method  public static Result success(String code, String msg)
#NEW of FRONT END
view of SelectCourse