class Manager extends Employee {
    private String department;

    Manager(String name, double salary, String jobTitle, int employeeID, String department) {
        super(name, salary, jobTitle, employeeID);
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    @Override
    public Object[] toObjectArray() {
        return new Object[] { getEmployeeID(), getName(), getJobTitle(), getSalary(), getPerformance(), getAttendance(),
                department };
    }
}