package com.department.service;

import java.sql.SQLException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.exceptions.DataBaseException;
import com.department.dao.DepartmentRepository;
import com.department.dao.DepartmentRepositoryImpl;
import com.department.service.DepartmentService;
import com.employee.service.EmployeeService;
import com.model.Department;
import com.model.Employee;

/**
 * <p>
 * Implement if DepartmentService interface to handle department-related operations.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class DepartmentServiceImpl implements DepartmentService {
    private static final Logger logger = LogManager.getLogger(DepartmentServiceImpl.class);
    private DepartmentRepository departmentRepository;

    public DepartmentServiceImpl() {
        departmentRepository = new DepartmentRepositoryImpl();
    }

    /**
     * Adds a new department to the repository.
     *
     * @param id - The unique identifier for the department.
     * @param name - The name of the department.
     */
    @Override
    public void addDepartment(int id, String name) throws DataBaseException {
        Department department = new Department(id, name);
        departmentRepository.addDepartment(department);
    }

    /**
     * Remove a department from the repository by its Id.
     *
     * @param id - The unique identifier of the department to be removed.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    @Override
    public void removeDepartment(int id) throws IllegalArgumentException, DataBaseException {
        Department department = departmentRepository.findDepartmentById(id);
        if (department != null) {
            departmentRepository.deleteDepartment(id);
        } else {
            logger.error("Department not found" +id);
            throw new IllegalArgumentException("Department not found" +id);
        }
    }

    /**
     * Retrieves all department from the repository.
     *
     * @return A List of all department.
     */
    @Override 
    public List<Department> getAllDepartments() throws DataBaseException {
        return departmentRepository.getAllDepartments();
    }

    /**
     * Retrieves a department by its unique identifier.
     *
     *@param id - The unique identifier of the department.
     *@return The department with the given ID, or null if not found.
     */
    @Override
    public Department getDepartmentById(int id) throws DataBaseException {
        return departmentRepository.findDepartmentById(id);
    }

    /**
     * Updates the details of an existing department.
     *
     * @param id - the unique identifier of the departmnet to the updated.
     * @param name - The new name for the department.
     * @thows IllegalArgumentException if the department with the given ID is not found.
     */
    @Override
    public void updateDepartment(int id, String name) throws IllegalArgumentException, DataBaseException {
        Department department = departmentRepository.findDepartmentById(id);
        if (department != null) {
            department.setName(name);
            departmentRepository.updateDepartment(department);
        } else {
            logger.error("Department not found" +id);
            throw new IllegalArgumentException("Department not found"+id);
        }
    }

    /**
     * Finds employees by department ID.
     *
     * @param Id - deparmnetID of the department.
     * @return list employee.
     */
    @Override
    public List<Employee> getEmployeesByDepartmentId(int id) throws DataBaseException {
        return departmentRepository.getEmployeesByDepartmentId(id);
    }

}
