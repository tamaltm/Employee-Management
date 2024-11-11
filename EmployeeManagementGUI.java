import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class EmployeeManagementGUI extends JFrame {

    private List<Employee> employees;
    private List<Manager> managers;
    private List<Intern> interns;
    private DefaultTableModel tableModel;

    public EmployeeManagementGUI() {
        employees = new LinkedList<>();
        managers = new LinkedList<>();
        interns = new LinkedList<>();

        setTitle("Employee Management System");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Main Buttons
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Employee");
        JButton displayButton = new JButton("Display Employees");
        JButton updateButton = new JButton("Update Employee");
        buttonPanel.add(addButton);
        buttonPanel.add(displayButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Table for displaying employee data
        String[] columnNames = { "ID", "Name", "Job Title", "Salary", "Performance", "Attendance", "Additional Info" };
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listeners for buttons
        addButton.addActionListener(e -> openAddEmployeeDialog());
        displayButton.addActionListener(e -> displayEmployees());
        updateButton.addActionListener(e -> openUpdateEmployeeDialog());
    }

    private void openAddEmployeeDialog() {
        JDialog dialog = new JDialog(this, "Add Employee", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(8, 2));

        JTextField nameField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField jobTitleField = new JTextField();
        JTextField idField = new JTextField();
        JComboBox<String> typeCombo = new JComboBox<>(new String[] { "Employee", "Manager", "Intern" });
        JTextField additionalField = new JTextField();
        JLabel additionalLabel = new JLabel("Department/School");

        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Salary:"));
        dialog.add(salaryField);
        dialog.add(new JLabel("Job Title:"));
        dialog.add(jobTitleField);
        dialog.add(new JLabel("Employee ID:"));
        dialog.add(idField);
        dialog.add(new JLabel("Type:"));
        dialog.add(typeCombo);
        dialog.add(additionalLabel);
        dialog.add(additionalField);

        typeCombo.addActionListener(e -> {
            if (typeCombo.getSelectedItem().equals("Manager")) {
                additionalLabel.setText("Department:");
            } else if (typeCombo.getSelectedItem().equals("Intern")) {
                additionalLabel.setText("School Name:");
            } else {
                additionalLabel.setText("N/A");
                additionalField.setText("");
                additionalField.setEnabled(false);
            }
        });

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(e -> {
            String name = nameField.getText();
            double salary = Double.parseDouble(salaryField.getText());
            String jobTitle = jobTitleField.getText();
            int id = Integer.parseInt(idField.getText());
            String type = (String) typeCombo.getSelectedItem();

            if (type.equals("Employee")) {
                employees.add(new Employee(name, salary, jobTitle, id));
            } else if (type.equals("Manager")) {
                managers.add(new Manager(name, salary, jobTitle, id, additionalField.getText()));
            } else if (type.equals("Intern")) {
                interns.add(new Intern(name, salary, jobTitle, id, additionalField.getText()));
            }
            dialog.dispose();
        });

        dialog.add(new JLabel()); // empty cell for layout
        dialog.add(saveButton);
        dialog.setVisible(true);
    }

    private void displayEmployees() {
        tableModel.setRowCount(0); // Clear the table
        for (Employee e : employees) {
            tableModel.addRow(e.toObjectArray());
        }
        for (Manager m : managers) {
            tableModel.addRow(m.toObjectArray());
        }
        for (Intern i : interns) {
            tableModel.addRow(i.toObjectArray());
        }
    }

    private void openUpdateEmployeeDialog() {
        // Dialog to handle updating employee details
        JDialog dialog = new JDialog(this, "Update Employee", true);
        dialog.setSize(400, 300);
        dialog.setLayout(new GridLayout(5, 2));

        JTextField nameField = new JTextField();
        JTextField performanceField = new JTextField();
        JTextField attendanceField = new JTextField();
        JTextField bonusField = new JTextField();
        dialog.add(new JLabel("Name:"));
        dialog.add(nameField);
        dialog.add(new JLabel("Performance:"));
        dialog.add(performanceField);
        dialog.add(new JLabel("Attendance:"));
        dialog.add(attendanceField);
        dialog.add(new JLabel("Bonus (%):"));
        dialog.add(bonusField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(e -> {
            String name = nameField.getText();
            int performance = Integer.parseInt(performanceField.getText());
            int attendance = Integer.parseInt(attendanceField.getText());
            int bonus = Integer.parseInt(bonusField.getText());

            for (Employee e1 : employees) {
                if (e1.getName().equalsIgnoreCase(name)) {
                    e1.setPerformance(performance);
                    e1.setAttendance(attendance);
                    e1.updateBonus(bonus);
                }
            }
            dialog.dispose();
        });

        dialog.add(new JLabel()); // empty cell for layout
        dialog.add(updateButton);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI frame = new EmployeeManagementGUI();
            frame.setVisible(true);
        });
    }
}
