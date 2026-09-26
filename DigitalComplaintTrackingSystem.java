package miniproject;


import javax.swing.*;
import java.util.*;

public class DigitalComplaintTrackingSystem {

    // ---------- Classes ----------
    static class User {
        String id, name;

        User(String id, String name) {
            this.id = id;
            this.name = name;
        }

        public String toString() {
            return id + " - " + name;
        }
    }

    static class Complaint {
        String id, description, category, priority, status, department;
        User user;
        LinkedList<String> resolutionHistory = new LinkedList<>();

        Complaint(String id, String description, String category,
                  String priority, User user) {
            this.id = id;
            this.description = description;
            this.category = category;
            this.priority = priority;
            this.status = "Pending";
            this.department = "Not Assigned";
            this.user = user;
        }

        public String toString() {
            return id + " | " + description + " | " +
                    category + " | " + priority + " | " +
                    status + " | " + department;
        }
    }

    // ---------- Collections ----------
    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Complaint> complaints = new ArrayList<>();

    static LinkedList<String> statusHistory = new LinkedList<>();

    static HashMap<String, Complaint> complaintMap = new HashMap<>();

    static TreeMap<String, Complaint> sortedComplaints = new TreeMap<>();

    // ---------- Arrays ----------
    static String[] categories = {
            "Maintenance", "Facilities", "IT",
            "Administration", "Library"
    };

    static String[] priorities = {
            "Low", "Medium", "High", "Critical"
    };

    // ---------- Main ----------
    public static void main(String[] args) {

        JFrame frame = new JFrame("Digital Complaint Tracking System");
        frame.setSize(500, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new java.awt.GridLayout(8, 1, 10, 10));

        JButton registerUser = new JButton("Register User");
        JButton registerComplaint = new JButton("Register Complaint");
        JButton searchComplaint = new JButton("Search Complaint");
        JButton updateStatus = new JButton("Update Status");
        JButton assignDepartment = new JButton("Assign Department");
        JButton addResolution = new JButton("Add Resolution");
        JButton deleteComplaint = new JButton("Delete Complaint");
        JButton report = new JButton("Sort & Generate Report");

        frame.add(registerUser);
        frame.add(registerComplaint);
        frame.add(searchComplaint);
        frame.add(updateStatus);
        frame.add(assignDepartment);
        frame.add(addResolution);
        frame.add(deleteComplaint);
        frame.add(report);

        // ---------- Register User ----------
        registerUser.addActionListener(e -> {

            String name = JOptionPane.showInputDialog(
                    frame, "Enter User Name:");

            if (name == null || name.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame, "Invalid name!");
                return;
            }

            String id = "U" + (users.size() + 1);

            users.add(new User(id, name));

            JOptionPane.showMessageDialog(
                    frame,
                    "User Registered!\nUser ID: " + id);
        });

        // ---------- Register Complaint ----------
        registerComplaint.addActionListener(e -> {

            if (users.isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Please register a user first!");
                return;
            }

            String userId = JOptionPane.showInputDialog(
                    frame,
                    "Enter User ID:");

            User user = findUser(userId);

            if (user == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "User not found!");
                return;
            }

            String description = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint Description:");

            if (description == null ||
                    description.trim().isEmpty()) {

                JOptionPane.showMessageDialog(
                        frame,
                        "Description cannot be empty!");
                return;
            }

            String category = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select Category:",
                    "Category",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    categories,
                    categories[0]);

            String priority = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select Priority:",
                    "Priority",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    priorities,
                    priorities[0]);

            String id = "C" + (complaints.size() + 1);

            Complaint c = new Complaint(
                    id,
                    description,
                    category,
                    priority,
                    user
            );

            complaints.add(c);
            complaintMap.put(id, c);
            sortedComplaints.put(id, c);

            JOptionPane.showMessageDialog(
                    frame,
                    "Complaint Registered!\nComplaint ID: " + id);
        });

        // ---------- Search Complaint ----------
        searchComplaint.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint ID:");

            Complaint c = complaintMap.get(id);

            if (c == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint not found!");
            } else {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint ID: " + c.id +
                        "\nDescription: " + c.description +
                        "\nCategory: " + c.category +
                        "\nPriority: " + c.priority +
                        "\nStatus: " + c.status +
                        "\nDepartment: " + c.department +
                        "\nUser: " + c.user.name);
            }
        });

        // ---------- Update Status ----------
        updateStatus.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint ID:");

            Complaint c = complaintMap.get(id);

            if (c == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint not found!");
                return;
            }

            String[] statuses = {
                    "Pending",
                    "In Progress",
                    "Resolved",
                    "Rejected"
            };

            String status = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select Status:",
                    "Status",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    statuses,
                    statuses[0]);

            c.status = status;
            statusHistory.add(id + " -> " + status);

            JOptionPane.showMessageDialog(
                    frame,
                    "Status Updated!");
        });

        // ---------- Assign Department ----------
        assignDepartment.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint ID:");

            Complaint c = complaintMap.get(id);

            if (c == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint not found!");
                return;
            }

            String[] departments = {
                    "Maintenance",
                    "IT Department",
                    "Administration",
                    "Facilities",
                    "Library"
            };

            String dept = (String) JOptionPane.showInputDialog(
                    frame,
                    "Select Department:",
                    "Department",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    departments,
                    departments[0]);

            c.department = dept;

            JOptionPane.showMessageDialog(
                    frame,
                    "Department Assigned!");
        });

        // ---------- Add Resolution ----------
        addResolution.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint ID:");

            Complaint c = complaintMap.get(id);

            if (c == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint not found!");
                return;
            }

            String resolution = JOptionPane.showInputDialog(
                    frame,
                    "Enter Resolution:");

            if (resolution == null ||
                    resolution.trim().isEmpty()) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Resolution cannot be empty!");
                return;
            }

            c.resolutionHistory.add(resolution);
            c.status = "Resolved";

            JOptionPane.showMessageDialog(
                    frame,
                    "Resolution Added!\nComplaint Resolved.");
        });

        // ---------- Delete Complaint ----------
        deleteComplaint.addActionListener(e -> {

            String id = JOptionPane.showInputDialog(
                    frame,
                    "Enter Complaint ID:");

            Complaint c = complaintMap.remove(id);

            if (c == null) {
                JOptionPane.showMessageDialog(
                        frame,
                        "Complaint not found!");
                return;
            }

            complaints.remove(c);
            sortedComplaints.remove(id);

            JOptionPane.showMessageDialog(
                    frame,
                    "Complaint Deleted Successfully!");
        });

        // ---------- Sort & Report ----------
        report.addActionListener(e -> {

            complaints.sort(
                    Comparator.comparing(c -> c.priority)
            );

            StringBuilder output =
                    new StringBuilder();

            output.append(
                    "===== COMPLAINT REPORT =====\n\n");

            output.append(
                    "Total Users: ")
                    .append(users.size())
                    .append("\n");

            output.append(
                    "Total Complaints: ")
                    .append(complaints.size())
                    .append("\n\n");

            output.append(
                    "Complaints Sorted by Priority:\n\n");

            for (Complaint c : complaints) {
                output.append(c).append("\n");
            }

            output.append(
                    "\n===== STATUS HISTORY =====\n");

            for (String s : statusHistory) {
                output.append(s).append("\n");
            }

            JOptionPane.showMessageDialog(
                    frame,
                    output.toString());
        });

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    // ---------- Search User ----------
    static User findUser(String id) {

        for (User u : users) {

            if (u.id.equalsIgnoreCase(id)) {
                return u;
            }
        }

        return null;
    }
}