package com.beadcore.employeenotification.Retrofit;

import java.util.List;

public class DetailedAttendenceResponse {
    /**
     * status_code : 200
     * status : true
     * data : [{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"14:18:05","image":"images/wfelplxakudbkbldxpjankrxlvewqfavrqkphetfphewleubglmugstxxqqn.jpg","latitude":"28.5718383","longitude":"77.3245738"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"14:18:02","image":"images/gcvsfotmgerjvvhtitrulaiowqjtbtclqxyvrfozjkxthqonenvccrhcayjx.jpg","latitude":"28.5718331","longitude":"77.3245696"},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"14:17:56","image":"images/umspnwndtjqqjccqtinrsyshhlracudnmdkhacgzejzwpuduveextzcstycz.jpg","latitude":null,"longitude":null},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"14:04:24","image":"images/buqmbdwnabjerooksasnnerljlxhalexjzmgoyrdkckdjpuwopvmntfpjaoz.jpg","latitude":null,"longitude":null},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"14:03:19","image":"images/hqictidnjbtvvalkdxjynuqqvhgyoreqndzjptuvzsxaylotdmwmieggkwxk.jpg","latitude":null,"longitude":null},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"13:59:44","image":"images/hjooppnyrqdtaveignbmgprjpfshdmprikeobwtgjffrkwfxuaqlwqwvokkc.jpg","latitude":null,"longitude":null},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"13:56:44","image":"images/lsvxpdufimiaeepkuzvybkfmglgvcardpxggbpvlrlkcccxkdleagkxhbjkx.jpg","latitude":null,"longitude":null},{"office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift","device_name":"Abhinav Cabin Device","mark_time":"13:56:39","image":"images/yyufzkqwmanjsuqmdyfhasjoowntliadvnqzilipsoljycpvovxuaxpnllgd.jpg","latitude":null,"longitude":null}]
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

    public static class DataBean {
        /**
         * office_name : Head Office
         * department_name : Development Department
         * shift_name : Day Shift
         * device_name : Abhinav Cabin Device
         * mark_time : 14:18:05
         * image : images/wfelplxakudbkbldxpjankrxlvewqfavrqkphetfphewleubglmugstxxqqn.jpg
         * latitude : 28.5718383
         * longitude : 77.3245738
         */

        private String office_name;
        private String department_name;
        private String shift_name;
        private String device_name;
        private String mark_time;
        private String image;
        private String latitude;
        private String longitude;

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

        public String getDevice_name() {
            return device_name;
        }

        public void setDevice_name(String device_name) {
            this.device_name = device_name;
        }

        public String getMark_time() {
            return mark_time;
        }

        public void setMark_time(String mark_time) {
            this.mark_time = mark_time;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }
    }
}
