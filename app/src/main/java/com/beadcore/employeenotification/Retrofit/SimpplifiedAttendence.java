package com.beadcore.employeenotification.Retrofit;

import java.io.Serializable;
import java.util.List;

public class SimpplifiedAttendence implements Serializable {
    /**
     * status_code : 200
     * status : true
     * data : [{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"09:48:33","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/txpfvykshqpnzbidovsjnzvdovxyeneznqcfqbokdtzbrnwhouefraduaayb.jpg","out_time":"14:18:05","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/wfelplxakudbkbldxpjankrxlvewqfavrqkphetfphewleubglmugstxxqqn.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"4 Hours 30 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"UNDER 4 Hours 30 Minutes","date":"2019-07-01","day":"MONDAY","attendance":"PRESENT","remark":"Person was Recognized!!"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"09:08:30","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/qyezisdoitkitbmjqzjdunvbackevaqaiznalnywembkdwshwcnfstbjzwal.jpg","out_time":"19:27:48","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/emaltjurrxwvuouqhhxhpmpfdzsexkccbegubqpekdzqkogwkbhfksktcrim.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"10 Hours 19 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"OVER 1 Hours 28 Minutes","date":"2019-06-28","day":"FRIDAY","attendance":"PRESENT","remark":"Person was Recognized!!"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"10:00:05","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/ojkvajwbgnyvhttsjojvoqekxihlowxbttotnjyrwwvoikfnsvsxrbwftdsg.jpg","out_time":"20:46:27","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/agmkeaexfjowntyhybgmtyjzpfijtvwihlrqttuobshgpbojbdxpgeuhlyyz.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"10 Hours 46 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"OVER 1 Hours 46 Minutes","date":"2019-06-27","day":"THURSDAY","attendance":"PRESENT","remark":"Person was Recognized!!"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"09:19:06","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/uarievvyoymnnyieqefxyzbfjvraenkofwomnfztnrnwdhsjykirwzumxhkq.jpg","out_time":"18:43:32","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/dvygjijjuakyskggdkriwrgopotwkduepsspvxzchbpjwtuurzmfrvgnfewg.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"9 Hours 24 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"OVER 0 Hours 24 Minutes","date":"2019-06-26","day":"WEDNESDAY","attendance":"PRESENT","remark":"Person was Recognized!!"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"09:15:09","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/cqkabubwzvubarolxrcitpmbxvbjhpbbjqqdxptueezyvndjuvzmqhedlaar.jpg","out_time":"17:45:08","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/rrztpqjrljcvnysvhrehlkmhqalfwyqliwlukfeksaoqtobljpyymbniqmsc.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"8 Hours 30 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"UNDER 0 Hours 15 Minutes","date":"2019-06-25","day":"TUESDAY","attendance":"PRESENT","remark":"Person was Recognized!!"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","in_device_name":"Abhinav Cabin Device","out_device_name":"Abhinav Cabin Device","employee_id":"emp001","employee_name":"Abhinav Mingwal","in_time":"08:45:09","in_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/stfqkftqavfgfejmpybpzpemblxcxehxqgsmmdfekzjxplfxezifbwryaasm.jpg","out_time":"17:43:07","out_time_image":"/home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/qomqpenwicmcspqvuddhbidfffotkemjnegejrbydemiagtskqngtiimzusu.jpg","shift_in_time":"09:00:00","shift_out_time":"18:00:00","actual_work_time":"8 Hours 58 Minutes","expected_work_time":"9 Hours 0 Minutes","over_time":"UNDER 0 Hours 2 Minutes","date":"2019-06-24","day":"MONDAY","attendance":"PRESENT","remark":"Person was Recognized!!"}]
     * message : Employee Attendance Have Been Fetched!!
     */

    private int status_code;
    private boolean status;
    private String message;
    private List<DataBean> data;

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * office_name : Head Office
         * department_name : Development Department
         * shift_name : Day Shift
         * in_device_name : Abhinav Cabin Device
         * out_device_name : Abhinav Cabin Device
         * employee_id : emp001
         * employee_name : Abhinav Mingwal
         * in_time : 09:48:33
         * in_time_image : /home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/txpfvykshqpnzbidovsjnzvdovxyeneznqcfqbokdtzbrnwhouefraduaayb.jpg
         * out_time : 14:18:05
         * out_time_image : /home/pluto/Web_Projects/Interns/Ashu/Employee-Attendance-Intern-Ashu/Backend/public/images/wfelplxakudbkbldxpjankrxlvewqfavrqkphetfphewleubglmugstxxqqn.jpg
         * shift_in_time : 09:00:00
         * shift_out_time : 18:00:00
         * actual_work_time : 4 Hours 30 Minutes
         * expected_work_time : 9 Hours 0 Minutes
         * over_time : UNDER 4 Hours 30 Minutes
         * date : 2019-07-01
         * day : MONDAY
         * attendance : PRESENT
         * remark : Person was Recognized!!
         */

        private String office_name;
        private String department_name;
        private String shift_name;
        private String in_device_name;
        private String out_device_name;
        private String employee_id;
        private String employee_name;
        private String in_time;
        private String in_time_image;
        private String out_time;
        private String out_time_image;
        private String shift_in_time;
        private String shift_out_time;
        private String actual_work_time;
        private String expected_work_time;
        private String over_time;
        private String date;
        private String day;
        private String attendance;
        private String remark;

        public String getOffice_name() {
            return office_name;
        }

        public void setOffice_name(String office_name) {
            this.office_name = office_name;
        }

        public String getDepartment_name() {
            return department_name;
        }

        public void setDepartment_name(String department_name) {
            this.department_name = department_name;
        }

        public String getShift_name() {
            return shift_name;
        }

        public void setShift_name(String shift_name) {
            this.shift_name = shift_name;
        }

        public String getIn_device_name() {
            return in_device_name;
        }

        public void setIn_device_name(String in_device_name) {
            this.in_device_name = in_device_name;
        }

        public String getOut_device_name() {
            return out_device_name;
        }

        public void setOut_device_name(String out_device_name) {
            this.out_device_name = out_device_name;
        }

        public String getEmployee_id() {
            return employee_id;
        }

        public void setEmployee_id(String employee_id) {
            this.employee_id = employee_id;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public String getIn_time() {
            return in_time;
        }

        public void setIn_time(String in_time) {
            this.in_time = in_time;
        }

        public String getIn_time_image() {
            return in_time_image;
        }

        public void setIn_time_image(String in_time_image) {
            this.in_time_image = in_time_image;
        }

        public String getOut_time() {
            return out_time;
        }

        public void setOut_time(String out_time) {
            this.out_time = out_time;
        }

        public String getOut_time_image() {
            return out_time_image;
        }

        public void setOut_time_image(String out_time_image) {
            this.out_time_image = out_time_image;
        }

        public String getShift_in_time() {
            return shift_in_time;
        }

        public void setShift_in_time(String shift_in_time) {
            this.shift_in_time = shift_in_time;
        }

        public String getShift_out_time() {
            return shift_out_time;
        }

        public void setShift_out_time(String shift_out_time) {
            this.shift_out_time = shift_out_time;
        }

        public String getActual_work_time() {
            return actual_work_time;
        }

        public void setActual_work_time(String actual_work_time) {
            this.actual_work_time = actual_work_time;
        }

        public String getExpected_work_time() {
            return expected_work_time;
        }

        public void setExpected_work_time(String expected_work_time) {
            this.expected_work_time = expected_work_time;
        }

        public String getOver_time() {
            return over_time;
        }

        public void setOver_time(String over_time) {
            this.over_time = over_time;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getAttendance() {
            return attendance;
        }

        public void setAttendance(String attendance) {
            this.attendance = attendance;
        }

        public String getRemark() {
            return remark;
        }

        public void setRemark(String remark) {
            this.remark = remark;
        }
    }
}
