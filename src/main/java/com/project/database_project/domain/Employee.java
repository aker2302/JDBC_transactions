package com.project.database_project.domain;

import java.time.LocalDate;

public class Employee {

    private String Fname;
    private String Minit;
    private String Lname;
    private int Ssn;
    private LocalDate Bdate;
    private String Address;
    private String Sex;
    private Double Salary;
    private int Super_ssn;
    private int Dno;

    public Employee(String fname, String minit, String lname, int ssn, LocalDate bdate, String address, String sex,Double salary, int super_ssn, int dno) {
        Fname = fname;
        Minit = minit;
        Lname = lname;
        Ssn = ssn;
        Bdate = bdate;
        Address = address;
        Sex = sex;
        Salary = salary;
        Super_ssn = super_ssn;
        Dno = dno;
    }

    public String getFname() {
        return Fname;
    }

    public void setFname(String fname) {
        Fname = fname;
    }

    public String getMinit() {
        return Minit;
    }

    public void setMinit(String minit) {
        Minit = minit;
    }

    public String getLname() {
        return Lname;
    }

    public void setLname(String lname) {
        Lname = lname;
    }

    public int getSsn() {
        return Ssn;
    }

    public void setSsn(int ssn) {
        Ssn = ssn;
    }

    public LocalDate getBdate() {
        return Bdate;
    }

    public void setBdate(LocalDate bdate) {
        Bdate = bdate;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public Double getSalary() {
        return Salary;
    }

    public void setSalary(Double salary) {
        Salary = salary;
    }

    public int getSuper_ssn() {
        return Super_ssn;
    }

    public void setSuper_ssn(int super_ssn) {
        Super_ssn = super_ssn;
    }

    public int getDno() {
        return Dno;
    }

    public void setDno(int dno) {
        Dno = dno;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "Fname='" + Fname + '\'' +
                ", Minit='" + Minit + '\'' +
                ", Lname='" + Lname + '\'' +
                ", Ssn=" + Ssn +
                ", Bdate=" + Bdate +
                ", Address='" + Address + '\'' +
                ", Sex='" + Sex + '\'' +
                ", Salary=" + Salary +
                ", Super_ssn=" + Super_ssn +
                ", Dno=" + Dno +
                '}'+ "\n";
    }
}
