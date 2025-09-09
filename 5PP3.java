import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class EmployeeBean implements Serializable {
    private String employeeId;
    private String firstName;
    private String lastName;
    private double salary;
    private String department;
    private Date hireDate;
    private boolean isActive;

    // Default no-argument constructor (JavaBean requirement)
    public EmployeeBean() {
    }

    // Parameterized constructor
    public EmployeeBean(String employeeId, String firstName, String lastName,
                        double salary, String department, Date hireDate, boolean isActive) {
        this.employeeId = employeeId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.setSalary(salary); // validation
        this.department = department;
        this.hireDate = hireDate;
        this.isActive = isActive;
    }

    // Standard getters
    public String getEmployeeId() { return employeeId; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public double getSalary() { return salary; }
    public String getDepartment() { return department; }
    public Date getHireDate() { return hireDate; }
    public boolean isActive() { return isActive; }

    // Standard setters
    public void setEmployeeId(String employeeId) { this.employeeId = employeeId; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public void setDepartment(String department) { this.department = department; }
    public void setHireDate(Date hireDate) { this.hireDate = hireDate; }
    public void setActive(boolean active) { this.isActive = active; }

    public void setSalary(double salary) {
        if (salary < 0) {
            throw new IllegalArgumentException("Salary must be non-negative.");
        }
        this.salary = salary;
    }

    // Computed properties
    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }

    public int getYearsOfService() {
        if (hireDate == null) return 0;
        LocalDate hireLocal = hireDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Period.between(hireLocal, LocalDate.now()).getYears();
    }

    public String getFormattedSalary() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(Locale.US);
        return formatter.format(salary);
    }

    // Derived property setter
    public void setFullName(String fullName) {
        if (fullName == null || fullName.trim().isEmpty()) return;
        String[] parts = fullName.trim().split(" ", 2);
        this.firstName = parts[0];
        this.lastName = (parts.length > 1) ? parts[1] : "";
    }

    // Override toString
    @Override
    public String toString() {
        return "EmployeeBean{" +
                "employeeId='" + employeeId + '\'' +
                ", fullName='" + getFullName() + '\'' +
                ", salary=" + getFormattedSalary() +
                ", department='" + department + '\'' +
                ", hireDate=" + hireDate +
                ", yearsOfService=" + getYearsOfService() +
                ", active=" + isActive +
                '}';
    }

    // Override equals & hashCode based on employeeId
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeeBean)) return false;
        EmployeeBean that = (EmployeeBean) o;
        return Objects.equals(employeeId, that.employeeId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employeeId);
    }

    // Main test
    public static void main(String[] args) {
        EmployeeBean emp1 = new EmployeeBean();
        emp1.setEmployeeId("E101");
        emp1.setFullName("Alice Johnson");
        emp1.setSalary(55000);
        emp1.setDepartment("IT");
        emp1.setHireDate(new Date(120, 0, 1)); // Jan 1, 2020
        emp1.setActive(true);

        EmployeeBean emp2 = new EmployeeBean("E102", "Bob", "Smith",
                60000, "Finance", new Date(118, 5, 15), true);

        System.out.println(emp1);
        System.out.println(emp2);

        System.out.println("Emp1 Full Name: " + emp1.getFullName());
        System.out.println("Emp2 Years of Service: " + emp2.getYearsOfService());

        // Test validation
        try {
            emp1.setSalary(-1000);
        } catch (IllegalArgumentException e) {
            System.out.println("Validation works: " + e.getMessage());
        }

        // Reflection demo
        System.out.println("\n--- Using JavaBeanProcessor ---");
        JavaBeanProcessor.printAllProperties(emp1);

        EmployeeBean emp3 = new EmployeeBean();
        JavaBeanProcessor.copyProperties(emp1, emp3);
        System.out.println("Copied Employee: " + emp3);
    }
}
