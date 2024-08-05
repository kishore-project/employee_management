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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToMany;

import com.model.Employee;

/**
 * Represnts a sport that can be associated with employees.
 * A sport has a unique Identifier, a name and a list of employees who participate in it.
 * @author  Kishore 
 * @version 1.0 
 */

@Entity
@Table(name = "SPORT")
public class Sport {
    @Id

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "NAME", unique = true)
    private String name;

    @Column(name = "IS_ACTIVE")
    private boolean isActive;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "employee_Sport",
        joinColumns = @JoinColumn(name = "Sport_ID"),
        inverseJoinColumns = @JoinColumn(name = "EMPLOYEE_ID")
    )
    private Set<Employee> employees = new HashSet<>();
    
    /**
     * Constructs a sport with the specified id and name.
     */
    public Sport(int id, String name) {
       this.id = id;
       this.name = name;
       this.isActive = true; 
    }

    public Sport() {
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

    public Set<Employee> getEmployees() {
        return employees;
    }

    public boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
     
    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }


}

     

  

