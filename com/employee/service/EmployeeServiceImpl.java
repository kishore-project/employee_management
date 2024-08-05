package com.employee.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.time.LocalDate;

import com.exceptions.DataBaseException;
import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.employee.dao.EmployeeRepository;
import com.employee.dao.EmployeeRepositoryImpl;
import com.model.Address;
import com.model.Department;
import com.model.Employee;
import com.model.Sport;
import com.sports.service.SportService;
import com.sports.service.SportServiceImpl;

/**
 * <p>
 * Implement if EmployeeService interface to handle employee-related operations.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;
    private DepartmentService departmentService = new DepartmentServiceImpl();
    private SportService sportService = new SportServiceImpl();

    public EmployeeServiceImpl() {
        employeeRepository = new EmployeeRepositoryImpl();
    }

    /**
     * Adds a new employee to database.
     *
     * @param id - the unique identifier for the employee. Must not already exits.
     * @param name - The name of the employee.
     * @param dob - The date of birth of the employee .Should not be a future date.
     * @param emailId - The emailID of the employee. Must Be Valid.
     * @param DeptId - The ID of the department the employee belong to. must exist.
     * @param address - Address of the employee.
     * @throws IllegalArgumentException if the department is not found.
     */
    @Override
    public void addEmployee(int id, String name, LocalDate dob, String emailId,
                            int deptId, Address address) throws IllegalArgumentException, DataBaseException {
        Department department = departmentService.getDepartmentById(deptId);
        if (department == null) {
            throw new IllegalArgumentException("Department not found" +deptId);
        }

        Employee employee = new Employee(id, name, dob, department, emailId, address); //ID will be set in the repository
        employeeRepository.addEmployee(employee);
    }

    /**
     * Marks an employee as inactive (effectively removes them from active use.
     *
     * @param id - The unique identifier for the employee to removed. must exist.
     * @throws IllegalArgumentException if the employee is not found.
     */
    @Override
    public void removeEmployee(int id) throws IllegalArgumentException, DataBaseException {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee != null) {
            employeeRepository.deleteEmployee(id);
        } else {
            throw new IllegalArgumentException("Employee not found" +id);
        }
    }
    
    /**
     * Retrieves all employees in the database.
     *
     * @return A list of all employees.
     */
    @Override
    public List<Employee> getAllEmployees() throws DataBaseException {
        return employeeRepository.getAllEmployees();
    }

    /**
     * Retrieves an employee by their ID.
     *
     * @param id - The unique identifier for the employee. Must exist.
     * @return The employee if found, or null if not found.
     */
    @Override
    public Employee getEmployeeById(int id) throws DataBaseException {
        return employeeRepository.findEmployeeById(id);
    }        

    /**
     * Updates the details of an existing employee.
     *
     * @param name - The new name of the employee.
     * @param dob - The date of birth of the employee. Should not be a future date.
     * @param emailId - The new email Id of the employee.
     * @param deptId - The ID of the new department the employee belongs to. Must exist.
     * @param address - Address of the employee.
     * @throws IllegalArgumentException if the department or employee is not found.
     */
    @Override
    public void updateEmployee(int id, String name, LocalDate dob,
                                String emailId, int deptId, Address address) throws IllegalArgumentException, DataBaseException {
        Employee employee = employeeRepository.findEmployeeById(id);
        if (employee != null) {
            employee.setName(name);
            employee.setDob(dob);
            employee.setEmailId(emailId);
            employee.setAddress(address);

            Department department = departmentService.getDepartmentById(deptId);
            if (department != null) {
                employee.setDepartment(department);
            } else {
                throw new IllegalArgumentException("Department not found" +deptId);
            }
            employeeRepository.updateEmployee(employee);
        } else {
            throw new IllegalArgumentException("Employee not found"+id);
        }
    }

    /**
     * Retrieves a depatment by its ID.
     *
     * @param id - The unique identifier for the deparmtent. Must exist.
     * @return The department if founf, or null if not found.
     */
    @Override
    public Department getDepartmentById(int id) throws DataBaseException {
        return departmentService.getDepartmentById(id);
    }
    
    /**
     * Retrieves all departments in the database.
     * @return A list of all department.
     */
    @Override
    public List<Department>getAllDepartment() throws DataBaseException {
       return departmentService.getAllDepartments();
    }

    /**
     * Adds a sport to an employee's list of sports and updates the sports's list of employees.
     *
     * @param employeeID - The unique identifier of the employee.
     * @param sportId - The unique identifier of the sport.
     * @throws IllegalArgumentException if the employees or sport with the given Id is not found.
     */
    @Override
    public void addSportToEmployee(int employeeId, int sportId) throws DataBaseException {
        Employee employee = getEmployeeById(employeeId);
        Sport sport = sportService.getSportById(sportId);

        if (employee != null && sport != null) {
            employeeRepository.addSportToEmployee(employeeId, sportId);
        } else {
            throw new IllegalArgumentException("Employee or Sport not found");
        }
    }


    /*
     * Remove a sport to an employee's list of sports and updates the sports's list of employees.
     *
     * @param employeeID - The unique identifier of the employee.
     * @param sportId - The unique identifier of the sport.
     * @throws IllegalArgumentException if the employees or sport with the given Id is not found.
     */
    @Override
    public void removeSportFromEmployee(int employeeId, int sportId) throws DataBaseException {
        Employee employee = getEmployeeById(employeeId);
        Sport sport = sportService.getSportById(sportId);

        if (employee != null && sport != null) {
            employeeRepository.removeSportFromEmployee(employeeId, sportId);
        } else {
            throw new IllegalArgumentException("Employee or Sport not found");
        }
    }
    
    /**
     * Retrieves all sport in the database.
     *
     * @return A list of all department.
     */
    @Override
    public Set<Sport> getAllSports() throws DataBaseException {
        return sportService.getAllSports();
    }
}

