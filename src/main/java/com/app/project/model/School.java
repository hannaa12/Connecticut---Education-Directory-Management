package com.app.project.model;

public class School {
    private int id;
    private String name;
    private String phone;
    private String studentOpenDate;

    public School() {
    }

    public School(int id, String name, String phone, String studentOpenDate) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.studentOpenDate = studentOpenDate;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentOpenDate() {
        return studentOpenDate;
    }

    public void setStudentOpenDate(String studentOpenDate) {
        this.studentOpenDate = studentOpenDate;
    }
}
