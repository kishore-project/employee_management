package com.department.service;

import java.sql.SQLException;
import java.util.List;

import com.exceptions.DataBaseException;
import com.department.dao.DepartmentRepository;
import com.department.service.DepartmentServiceImpl;
import com.employee.service.EmployeeService;
import com.model.Department;
import com.model.Employee;

/**
 *<p>
 * Interface for employeeservice  to handle  department-related operation.
 *</p>
 * @author  Kishore 
 * @version 1.0 
 *
 */
public interface DepartmentService {

    /**
     * Adds a new department to the repository.
     * @param id - The unique identifier for the department.
     * @param name - The name of the department.
     */
    public void addDepartment(int id, String name) throws DataBaseException;

    /**
     * Remove a department from the repository by its Id.
     *
     * @param id - The unique identifier of the department to be removed.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    public void removeDepartment(int id) throws IllegalArgumentException, DataBaseException;

    /**
     * Retrieves all department from the repository.
     *
     * @return A List of all department.
     */ 
    public List<Department> getAllDepartments() throws DataBaseException;

    /**
     * Retrieves a department by its unique identifier.
     *
     *@param id - The unique identifier of the department.
     *@return The department with the given ID, or null if not found.
     */
    public Department getDepartmentById(int id) throws DataBaseException;

    /**
     * Updates the details of an existing department.
     *
     * @param id - the unique identifier of the departmnet to the updated.
     * @param name - The new name for the department.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DataBaseException;

    /**
     * Finds employees by department ID.
     *
     * @param Id - deparmnetID of the department.
     * @return list employee.
     */    
    public List<Employee> getEmployeesByDepartmentId(int id) throws DataBaseException;

}
