package com.course_select_back_end.service;

public class Check {
    //判断时间是否冲突
    public static boolean checkTimeConflict(String time1, String time2) {
        String date1 = time1.substring(0, 1);
        String date2 = time2.substring(0, 1);
        int start1 = Integer.parseInt(time1.substring(1, 2));
        int start2 = Integer.parseInt(time2.substring(1, 2));
        int end1 = Integer.parseInt(time1.substring(3,4));
        int end2 = Integer.parseInt(time2.substring(3,4));
        if (date1.equals(date2) && ((start2 >= start1 && start2 <= end1) || (start1 >= start2 && start1 <= end2))) {
            return false;
        }
        return true;
    }
}
