package com.course_select_back_end.service;

public class Check {
    //判断时间是否冲突
    public static boolean checkTimeConflict(String time1, String time2) {
        String date1 = time1.substring(0, 1);
        String date2 = time2.substring(0, 1);
        int start1 = time1.charAt(1);
        int start2 = time2.charAt(1);
        int end1 = time1.charAt(3);
        int end2 = time1.charAt(3);
        if (date1.equals(date2) && ((start2 >= start1 && start2 <= end1) || (start1 >= start2 && start1 <= end2))) {
            return false;
        }
        return true;
    }
}
