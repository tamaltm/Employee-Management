import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.LinkedList;
import java.util.List;
import javax.swing.*;
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
        JButton updateButton = new JButton("Update Employee");
        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        add(buttonPanel, BorderLayout.NORTH);

        // Table for displaying employee data
        String[] columnNames = {"ID", "Name", "Job Title", "Salary", "Performance", "Attendance"};
        tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Action Listeners for buttons
        addButton.addActionListener(e -> openAddEmployeeDialog());
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
        JComboBox<String> typeCombo = new JComboBox<>(new String[]{"Employee", "Manager", "Intern"});
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
            try {
                String name = nameField.getText();
                String s = salaryField.getText().replace(",", "");
                double salary = Double.parseDouble(s);
                String jobTitle = jobTitleField.getText();
                int id = Integer.parseInt(idField.getText());
                String type = (String) typeCombo.getSelectedItem();

                // Check for unique ID
                boolean idExists = employees.stream().anyMatch(emp -> emp.getId() == id) ||
                                   managers.stream().anyMatch(mgr -> mgr.getId() == id) ||
                                   interns.stream().anyMatch(intn -> intn.getId() == id);

                if (idExists) {
                    JOptionPane.showMessageDialog(dialog, "ID already exists! Please use a unique ID.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Add employee based on type
                if (type.equals("Employee")) {
                    employees.add(new Employee(name, salary, jobTitle, id));
                } else if (type.equals("Manager")) {
                    managers.add(new Manager(name, salary, jobTitle, id, additionalField.getText()));
                } else if (type.equals("Intern")) {
                    interns.add(new Intern(name, salary, jobTitle, id, additionalField.getText()));
                }

                // Update table and close dialog
                dialog.dispose();
                updateTable();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid input! Please check your entries.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        dialog.add(new JLabel()); // Empty cell for layout
        dialog.add(saveButton);
        dialog.setVisible(true);
    }

    private void openUpdateEmployeeDialog() {
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
            for (Manager e1 : managers) {
                if (e1.getName().equalsIgnoreCase(name)) {
                    e1.setPerformance(performance);
                    e1.setAttendance(attendance);
                    e1.updateBonus(bonus);
                }
            }
            for (Intern e1 : interns) {
                if (e1.getName().equalsIgnoreCase(name)) {
                    e1.setPerformance(performance);
                    e1.setAttendance(attendance);
                    e1.updateBonus(bonus);
                }
            }

            dialog.dispose();
            updateTable();
        });

        dialog.add(new JLabel()); // Empty cell for layout
        dialog.add(updateButton);
        dialog.setVisible(true);
    }

    // Helper method to refresh table
    private void updateTable() {
        tableModel.setRowCount(0); // Clear the table
        for (Employee em : employees) {
            tableModel.addRow(em.toObjectArray());
        }
        for (Manager m : managers) {
            tableModel.addRow(m.toObjectArray());
        }
        for (Intern i : interns) {
            tableModel.addRow(i.toObjectArray());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            EmployeeManagementGUI frame = new EmployeeManagementGUI();
            frame.setVisible(true);
        });
    }
}
