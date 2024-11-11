class Employee {
        private String name;
        private double salary;
        private String jobTitle;
        private int employeeID;
        private int attendance;
        private int performance;
    
        Employee(String name, double salary, String jobTitle, int employeeID) {
            this.name = name;
            this.salary = salary;
            this.jobTitle = jobTitle;
            this.employeeID = employeeID;
            this.attendance = 0;
            this.performance = 0;
        }
    
        public String getName() {
            return name;
        }
    
        public double getSalary() {
            return salary;
        }
    
        public void setSalary(double salary) {
            this.salary = salary;
        }
    
        public String getJobTitle() {
            return jobTitle;
        }
    
        public int getEmployeeID() {
            return employeeID;
        }
    
        public int getAttendance() {
            return attendance;
        }
    
        public void setAttendance(int attendance) {
            this.attendance = attendance;
        }
    
        public int getPerformance() {
            return performance;
        }
    
        public void setPerformance(int performance) {
            this.performance = performance;
        }
    
        public void updateBonus(int bonus) {
            this.salary = salary + ((salary * bonus) / 100);
        }
    
        public Object[] toObjectArray() {
            return new Object[] { employeeID, name, jobTitle, salary, performance, attendance };
        }
    }
