package com.model;

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
import javax.persistence.OneToMany;

import com.model.Employee;

/**
 * Represents an Department that can  associated with employees.
 * Contains details like department id, department name.
 * @author  Kishore 
 * @version 1.0 
 */
@Entity
@Table(name = "department")
public class Department {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "isDeleted")
    private boolean isDeleted;

    @OneToMany(mappedBy = "department", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Employee> employees;

    public Department(int id, String name) {
        this.id = id;
        this.name = name;
        this.isDeleted = false;
        this.employees = new HashSet<>();
    }
    
    public Department() {
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
    
    public boolean getIsDeleted() {
        return isDeleted;
    }
    
    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    public void setName(String name) {
        this.name = name;
    }
    public Set<Employee> getEmployees(){
        return employees;
    } 
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }

    public String toString() {
       return String.format("|%-10s | %-20s |",id,name);
    }
}
