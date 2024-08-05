package com.model;

import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.model.Department;
import com.model.Sport;

/**
 * Represents an Employee that can  associated with department & sports.
 * Contains details like id, name, date of birth (dob), department, email, active status and associated sports.
 * @author  Kishore 
 * @version 1.0 
 */
@Entity
@Table(name = "employee")
public class Employee {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "dob")
    private LocalDate dob;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "department_id", nullable = false)
    private Department department;

    @Column(name = "emailId", unique = true)
    private String emailId;

    @Column(name = "isActive")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_sport",
        joinColumns = @JoinColumn(name = "Employee_ID"),
        inverseJoinColumns = @JoinColumn(name = "SPORT_ID"))
    private Set<Sport> sports = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address; 

    public Employee() {
    }

    public Employee(int id, String name, LocalDate dob, Department department, 
                    String emailId, Address address) {
        this.id = id;
        this.name = name;
        this.dob = dob;
        this.department = department;
        this.emailId = emailId;
        this.isActive = true;
        this.address = address;
        
    }
    
    public boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }  

    private int calculateAge() {
        return Period.between(dob, LocalDate.now()).getYears();
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

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }
    
    public Set<Sport> getSports() {
        return sports;
    }

    public void setSports(Set<Sport> sports) {
        this.sports = sports;
    }
    
    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
       this.address = address;
    }

@Override
    public String toString() {
        // Format sports
        String sportsList = sports == null || sports.isEmpty() ? "No sports" : 
            sports.stream()
                  .map(Sport::getName)
                  .reduce((s1, s2) -> s1 + ", " + s2)
                  .orElse("No sports");
        
        // Format address
        String addressStr = address == null ? "No address" : address.toString();

        // Return formatted string
        return String.format("|%-10d | %-20s | %-5d | %-20s | %-30s | %-25s | %-30s |\n",
                             id, name, calculateAge(), department.getName(), emailId, sportsList, addressStr);
    }
}
