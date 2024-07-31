package com.example;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "students")
public class Student {
    public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getProgram() {
		return program;
	}
	public void setProgram(String program) {
		this.program = program;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public String getContactNumber() {
		return contactNumber;
	}
	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}
	public boolean isGraduationStatus() {
		return graduationStatus;
	}
	public void setGraduationStatus(boolean graduationStatus) {
		this.graduationStatus = graduationStatus;
	}
	public double getCgpa() {
		return cgpa;
	}
	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}
	public int getNoOfBacklogs() {
		return noOfBacklogs;
	}
	public void setNoOfBacklogs(int noOfBacklogs) {
		this.noOfBacklogs = noOfBacklogs;
	}
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String gender;
    private String department;
    private String program;

    @Temporal(TemporalType.DATE)
    private Date dateOfBirth;

    private String contactNumber;
    private boolean graduationStatus;
    private double cgpa;
    private int noOfBacklogs;

    // Getters and Setters
}
