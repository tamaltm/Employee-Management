class Intern extends Employee {
    private String schoolName;

    Intern(String name, double salary, String jobTitle, int employeeID, String schoolName) {
        super(name, salary, jobTitle, employeeID);
        this.schoolName = schoolName;
    }

    public String getSchoolName() {
        return schoolName;
    }

    @Override
    public Object[] toObjectArray() {
        return new Object[] { getEmployeeID(), getName(), getJobTitle(), getSalary(), getPerformance(), getAttendance(),
                schoolName };
    }
}
