package com.beadcore.employeenotification.Retrofit;

public class LoginResponse {
    /**
     * status : true
     * message : Login verified Successfully
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2VtYWlsIjoiYWJoaW5hdi5pbnRlbGxpdmlzaW9uQGdtYWlsLmNvbSIsInVzZXJfaWQiOjEsInR5cGVfb2ZfdXNlciI6IkVNUExPWUVFIiwiaWF0IjoxNTYxOTY5NjExfQ.zC2hoWUEZsDj_GTF1mbSiMQ01pW1Iz9n2IZClRvphiA
     * data : {"employee_image":"/images/bucihgqcsfexdcigbujzlzxbkwnvlklimhqfkaflwymzbqptkookhstmbwkc.jpg","employee_name":"Abhinav Mingwal","company_name":"Intellivision Engineering Pvt. Ltd","office_name":"Head Office","department_name":"Development Department","shift_name":"Day Shift"}
     */

    private boolean status;
    private String message;
    private String token;
    private DataBean data;

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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * employee_image : /images/bucihgqcsfexdcigbujzlzxbkwnvlklimhqfkaflwymzbqptkookhstmbwkc.jpg
         * employee_name : Abhinav Mingwal
         * company_name : Intellivision Engineering Pvt. Ltd
         * office_name : Head Office
         * department_name : Development Department
         * shift_name : Day Shift
         */

        private String employee_image;
        private String employee_name;
        private String company_name;
        private String office_name;
        private String department_name;
        private String shift_name;

        public String getEmployee_image() {
            return employee_image;
        }

        public void setEmployee_image(String employee_image) {
            this.employee_image = employee_image;
        }

        public String getEmployee_name() {
            return employee_name;
        }

        public void setEmployee_name(String employee_name) {
            this.employee_name = employee_name;
        }

        public String getCompany_name() {
            return company_name;
        }

        public void setCompany_name(String company_name) {
            this.company_name = company_name;
        }

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
    }
}
