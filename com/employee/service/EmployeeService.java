package com.employee.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.exceptions.DataBaseException;
import com.model.Address;
import com.model.Department;
import com.model.Employee;
import com.model.Sport;

/**
 *<p>
 * Interface for employeeservice  to handle  employee-related operation.
 *</p>
 * @author  Kishore 
 * @version 1.0 
 */
public interface EmployeeService {

    /**
     * Adds a new employee to the 
     *
     * @param id - the unique identifier for the employee. Must not already exits.
     * @param name - The name of the employee.
     * @param dob - The date of birth of the employee .Should not be a future date.
     * @param emailId - The emailID of the employee. Must Be Valid.
     * @param DeptId - The ID of the department the employee belong to. must exist.
     * @throws IllegalArgumentException if the department is not found.
     */
    void addEmployee(int id, String name, LocalDate dob, String emailId,
                            int deptId,Address address) throws IllegalArgumentException, DataBaseException;

    /**
     * Marks an employee as inactive (effectively removes them from active use).
     *
     * @param id - The unique identifier for the employee to removed. must exist.
     * @throws IllegalArgumentException if the employee is not found.
     */
    void removeEmployee(int id) throws IllegalArgumentException, DataBaseException;

    /**
     * Retrieves all employees in the system.
     *
     * @return A list of all employees.
     */
    List<Employee> getAllEmployees() throws DataBaseException;

    /**
     * Retrieves an employee by their ID.
     *
     * @param id - The unique identifier for the employee. Must exist.
     * @return The employee if found, or null if not found.
     */
    Employee getEmployeeById(int id) throws DataBaseException;

    /**
     * Updates the details of an existing employee.
     *
     * @param name - The new name of the employee.
     * @param dob - The date of birth of the employee. Should not be a future date.
     * @param emailId - The new email Id of the employee.
     * @param deptId - The ID of the new department the employee belongs to. Must exist.
     * @throws IllegalArgumentException if the department or employee is not found.
     */
    void updateEmployee(int id, String name, LocalDate dob,
                                String emailId, int deptId,Address address) throws DataBaseException;


    /**
     * Retrieves a depatment by its ID.
     *
     * @param id - The unique identifier for the deparmtent. Must exist.
     * @return The department if founf, or null if not found.
     */
    Department getDepartmentById(int id) throws DataBaseException;

    /**
     * Retrieves all departments in the database.
     *
     * @return A list of all department.
     */
    List<Department>getAllDepartment() throws DataBaseException;

    /**
     * Adds a sport to an employee's list of sports and updates the sports's list of employees.
     *
     * @param employeeID - The unique identifier of the employee.
     * @param sportId - The unique identifier of the sport.
     * @throws IllegalArgumentException if the employees or sport with the given Id is not found.
     */
    void addSportToEmployee(int employeeId, int sportId) throws DataBaseException;

    /**
     * Remove a sport to an employee's list of sports and updates the sports's list of employees.
     *
     * @param employeeID - The unique identifier of the employee.
     * @param sportId - The unique identifier of the sport.
     * @throws IllegalArgumentException if the employees or sport with the given Id is not found.
     */
    void removeSportFromEmployee(int employeeId, int sportId) throws DataBaseException;
    
    /**
     * Retrieves all sport in the database.
     */
    public Set<Sport> getAllSports() throws DataBaseException;
}
