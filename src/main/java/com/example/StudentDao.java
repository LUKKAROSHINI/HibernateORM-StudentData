package com.example;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Scanner;

public class StudentDao {
    private static SessionFactory factory;

    static {
        try {
            // Create the table if it does not exist
            createTable();

            // Initialize Hibernate SessionFactory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static void createTable() {
        String url = "jdbc:mysql://localhost:3306/studentdb";
        String user = "root";
        String password = "rosh";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS students (" +
                                "id BIGINT AUTO_INCREMENT PRIMARY KEY, " +
                                "name VARCHAR(100) NOT NULL, " +
                                "gender VARCHAR(10), " +
                                "department VARCHAR(50), " +
                                "program VARCHAR(50), " +
                                "dateOfBirth DATE, " +
                                "contactNumber VARCHAR(20), " +
                                "graduationStatus BOOLEAN, " +
                                "cgpa DOUBLE, " +
                                "noOfBacklogs INT" +
                                ")";

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            statement.execute(createTableSQL);
            System.out.println("Table 'students' created successfully!");

        } catch (Exception e) {
            System.err.println("Error creating table: " + e.getMessage());
        }
    }

    public static void insertStudent(Student student) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(student);
            tx.commit();
            System.out.println("Student inserted successfully!");
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void updateStudent(Long id, Student updatedStudent) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                student.setName(updatedStudent.getName());
                student.setGender(updatedStudent.getGender());
                student.setDepartment(updatedStudent.getDepartment());
                student.setProgram(updatedStudent.getProgram());
                student.setDateOfBirth(updatedStudent.getDateOfBirth());
                student.setContactNumber(updatedStudent.getContactNumber());
                student.setGraduationStatus(updatedStudent.isGraduationStatus());
                student.setCgpa(updatedStudent.getCgpa());
                student.setNoOfBacklogs(updatedStudent.getNoOfBacklogs());
                session.update(student);
                tx.commit();
                System.out.println("Student updated successfully!");
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void deleteStudent(Long id) {
        Session session = factory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student = session.get(Student.class, id);
            if (student != null) {
                session.delete(student);
                tx.commit();
                System.out.println("Student deleted successfully!");
            } else {
                System.out.println("Student with ID " + id + " not found.");
            }
        } catch (RuntimeException e) {
            if (tx != null) tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String choice;

        do {
            System.out.println("Choose an option: ");
            System.out.println("1. Insert a student");
            System.out.println("2. Update a student");
            System.out.println("3. Delete a student");
            System.out.println("4. Exit");
            choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    // Insert student
                    Student student = new Student();
                    System.out.print("Enter Name: ");
                    student.setName(scanner.nextLine());

                    System.out.print("Enter Gender: ");
                    student.setGender(scanner.nextLine());

                    System.out.print("Enter Department: ");
                    student.setDepartment(scanner.nextLine());

                    System.out.print("Enter Program: ");
                    student.setProgram(scanner.nextLine());

                    System.out.print("Enter Date of Birth (yyyy-mm-dd): ");
                    try {
                        student.setDateOfBirth(java.sql.Date.valueOf(scanner.nextLine()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                        continue;
                    }

                    System.out.print("Enter Contact Number: ");
                    student.setContactNumber(scanner.nextLine());

                    System.out.print("Enter Graduation Status (true/false): ");
                    student.setGraduationStatus(Boolean.parseBoolean(scanner.nextLine()));

                    System.out.print("Enter CGPA: ");
                    try {
                        student.setCgpa(Double.parseDouble(scanner.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid CGPA format. Please enter a valid number.");
                        continue;
                    }

                    System.out.print("Enter Number of Backlogs: ");
                    try {
                        student.setNoOfBacklogs(Integer.parseInt(scanner.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format for backlogs. Please enter a valid integer.");
                        continue;
                    }

                    insertStudent(student);
                    break;

                case "2":
                    // Update student
                    System.out.print("Enter student ID to update: ");
                    Long updateId = Long.parseLong(scanner.nextLine());

                    Student updatedStudent = new Student();
                    updatedStudent.setId(updateId);
                    System.out.print("Enter new Name: ");
                    updatedStudent.setName(scanner.nextLine());

                    System.out.print("Enter new Gender: ");
                    updatedStudent.setGender(scanner.nextLine());

                    System.out.print("Enter new Department: ");
                    updatedStudent.setDepartment(scanner.nextLine());

                    System.out.print("Enter new Program: ");
                    updatedStudent.setProgram(scanner.nextLine());

                    System.out.print("Enter new Date of Birth (yyyy-mm-dd): ");
                    try {
                        updatedStudent.setDateOfBirth(java.sql.Date.valueOf(scanner.nextLine()));
                    } catch (IllegalArgumentException e) {
                        System.out.println("Invalid date format. Please use yyyy-mm-dd.");
                        continue;
                    }

                    System.out.print("Enter new Contact Number: ");
                    updatedStudent.setContactNumber(scanner.nextLine());

                    System.out.print("Enter new Graduation Status (true/false): ");
                    updatedStudent.setGraduationStatus(Boolean.parseBoolean(scanner.nextLine()));

                    System.out.print("Enter new CGPA: ");
                    try {
                        updatedStudent.setCgpa(Double.parseDouble(scanner.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid CGPA format. Please enter a valid number.");
                        continue;
                    }

                    System.out.print("Enter new Number of Backlogs: ");
                    try {
                        updatedStudent.setNoOfBacklogs(Integer.parseInt(scanner.nextLine()));
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid number format for backlogs. Please enter a valid integer.");
                        continue;
                    }

                    updateStudent(updateId, updatedStudent);
                    break;

                case "3":
                    // Delete student
                    System.out.print("Enter student ID to delete: ");
                    Long deleteId = Long.parseLong(scanner.nextLine());
                    deleteStudent(deleteId);
                    break;

                case "4":
                    // Exit
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice. Please select a valid option.");
            }

        } while (!choice.equals("4"));

        scanner.close();
    }
}
