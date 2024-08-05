package com.employee.controller;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.department.controller.DepartmentController;
import com.exceptions.DataBaseException;
import com.employee.service.EmployeeService;
import com.employee.service.EmployeeServiceImpl;
import com.model.Address;
import com.model.Department;
import com.model.Employee;
import com.model.Sport;
import com.utilities.Validator;

/**
 * Controller to manage employee  related operations.
 * Handles user input and interacts with employeeService to perform operations.
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeController {
    private static final Logger logger = LogManager.getLogger(EmployeeController.class);
    private EmployeeService employeeService = new EmployeeServiceImpl();
    private Validator validator;
    private static int idCounter = 1;  //Set the Id and increment the counter for auto increment Ids
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the employee management menu and handles user choices.
     */
    public void displayEmployeeMenu() {
        try {
            while (true) {
                System.out.println("---| Employee Management |---");
                System.out.println("1) Add Employee");
                System.out.println("2) Delete Employee");
                System.out.println("3) Display All Employees");
                System.out.println("4) Display Employee by ID");
                System.out.println("5) Update Employee");
                System.out.println("6) Add Sport To Employee");
                System.out.println("7) Remove Sport From Employee");
                System.out.println("8) Back to Main Menu");
                System.out.println("-----------------------------"); 
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createEmployee();
                        break;
                    case 2:
                        deleteEmployee();
                        break;
                    case 3:
                        displayAllEmployees();
                        break;
                    case 4:
                        displayEmployeeById();
                        break;
                    case 5:
                        updateEmployee();
                        break;
                    case 6:
                        addSportToEmployee();
                        break;
                    case 7:
                        removeSportFromEmployee();
                        break;
                        case 8:
                           return;
                        default:
                           System.out.println("Invalid choice.");
                 }
            }
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.warn(e.getMessage());
        }
    }

    /**
     * Prompts the user to create a new employess.
     */
    public void createEmployee() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Employee credentials validation has been initiated");
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            boolean checkZipCode = false;
            boolean checkStreet = false;
            boolean checkState = false;
            boolean checkCity = false;
            String state = "";
            String city = "";
            String street = "";
            LocalDate dob = null;
            String name = "";
            String emailId = "";
            String zip ="";

            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!validator.isValidname(name)) {
                System.out.println("InValid Input");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.print("Enter employee DOB (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());
                if(!validator.isValidFutureDate(dob)){
                    System.out.println("Invalid: Date in Future");
                } else {
                    checkDob = true;
                }
            }
            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                emailId = scanner.nextLine();
                if(!validator.isValidEmail(emailId)) {
                    System.out.println("InValid Input");
                } else {
                    checkEmail = true;
                }
            }
 
            for(Department departments : employeeService.getAllDepartment()){
               System.out.println("ID= " + departments.getId() + " name = " 
                                   + departments.getName());
            }

            System.out.print("Enter department Id: ");
            int deptId = scanner.nextInt();
            scanner.nextLine();
            
            // Address details
            while(!checkStreet) {
                System.out.print("Enter House No. & Street: ");
                street = scanner.nextLine();
                if(!validator.isValidNotNull(street)) {
                    System.out.println("Cannot be Null");
                } else {
                    checkStreet = true;
                }
            }
             
            while(!checkCity) {
                System.out.print("Enter City: ");
                city = scanner.nextLine();
                if(!validator.isValidNotNull(city)) {
                    System.out.println("Cannot be Null");
                } else {
                    checkCity = true;
                }
            }

            while(!checkState) {
                System.out.print("Enter State: ");
                state = scanner.nextLine();
                if(!validator.isValidNotNull(state)) {
                    System.out.println("Cannot be Null");
                } else {
                    checkState = true;
                }
            }

            while(!checkZipCode) {
                System.out.print("Enter ZipCode: ");
                zip = scanner.nextLine();
                if(!validator.isValidPinCode(zip)) {
                    System.out.println("InValid input or Cannot be Null");
                } else {
                    checkZipCode = true;
                }
            }

            Address address = new Address(street, city, state, zip);
            employeeService.addEmployee(idCounter, name, dob, emailId, deptId,address);
            logger.info("Employee added successfully." +name);
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while adding employee", e);
        }
    }

    /**
     * Prompts the user to delete an employee.
     */
    public void deleteEmployee() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter Employee Id to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            logger.debug("Getting employee id for remove"+id);

            employeeService.removeEmployee(id);
            logger.info("Employee deleted successfully."+id);
            System.out.println("Employee deleted successfully."+id);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while deleting Employee..", e);
        }
    }
    
    /**
     * Displays all employees in the system.
     */
    public void displayAllEmployees() throws DataBaseException {
        try {
            logger.debug("Getting employee list");
            List<Employee> employees = employeeService.getAllEmployees();
            System.out.printf("|%-10s | %-20s | %-5s | %-20s | %-30s| %-25s | %-30s |\n ","ID",
                            "Name","AGE", "DEPARTMENTNAME", "EMAILID", "SPORTLIST", "ADDRESS");
            for (Employee employee : employees) {
                System.out.println(employee);
            }
        } catch (HibernateException e) {
            logger.error("Error while display employee list ", e);
        }
    }
    
    /**
     *Prompts the user to enter an employee ID and displays the corresponding employee.
     */
    public void displayEmployeeById() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter Employee Id to display: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            logger.debug("Getting employee by ID: ", +id);

            Employee employee = employeeService.getEmployeeById(id);
            if (employee != null) {
                System.out.println(employee);
            } else {
                System.out.println("Employee not found."+id);
                logger.info("Employee not found."+id);
            }
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while getting Employee by ID" + e.getMessage());
        }
    }

    /**
     * Prompts the user to update an existing employee's details.
     */
    public void updateEmployee() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Employee update initiated");
            boolean checkName = false;
            boolean checkDob = false;
            boolean checkEmail = false;
            LocalDate dob = null;
            boolean checkZipCode = false;
            boolean checkStreet = false;
            boolean checkState = false;
            boolean checkCity = false;
            String state = "";
            String zip = "";
            String city = "";
            String street = "";
            String name = "";
            String emailId = "";
            System.out.print("Enter employee Id: ");
            int id = scanner.nextInt();
            if(employeeService.getEmployeeById(id) == null) {
                logger.info("Employee not found."+id);
                return;
            }
                   
            scanner.nextLine();

            while(!checkName){
                System.out.print("Enter employee Name: ");
                name = scanner.nextLine();
                if(!validator.isValidname(name)) {
                System.out.println("InValid input");
                } else {
                    checkName = true;
                }
             }
            
            while(!checkDob){
                System.out.print("Enter employee DOB (YYYY-MM-DD): ");
                dob = LocalDate.parse(scanner.nextLine());
                if(!validator.isValidFutureDate(dob)){
                    System.out.println("Invalid: Date in Future");
                } else {
                    checkDob = true;
                }
            }

            while(!checkEmail) {
                System.out.print("Enter Employee EmailId: ");
                emailId = scanner.nextLine();
                if(!validator.isValidEmail(emailId)) {
                    System.out.println("InValid");
                } else {
                    checkEmail = true;
                }
            }
            
             for(Department departments : employeeService.getAllDepartment()){
               System.out.println("ID = " + departments.getId() + " name = " 
                                   + departments.getName());
            }

            System.out.print("Enter department Id: ");
            int deptId = scanner.nextInt();
            scanner.nextLine();

            // Address details
            while(!checkStreet) {
                System.out.print("Enter House No. & Street: ");
                street = scanner.nextLine();
                if(!validator.isValidNotNull(street)) {
                    System.out.println("InValid");
                } else {
                    checkStreet = true;
                }
            }
             
            while(!checkCity) {
                System.out.print("Enter City: ");
                city = scanner.nextLine();
                if(!validator.isValidNotNull(city)) {
                    System.out.println("InValid");
                } else {
                    checkCity = true;
                }
            }

            while(!checkState) {
                System.out.print("Enter State: ");
                state = scanner.nextLine();
                if(!validator.isValidNotNull(state)) {
                    System.out.println("InValid");
                } else {
                    checkState = true;
                }
            }

            while(!checkZipCode) {
                System.out.print("Enter ZipCode: ");
                zip = scanner.nextLine();
                if(!validator.isValidPinCode(zip)) {
                    System.out.println("InValid");
                } else {
                    checkZipCode = true;
                }
            }

            Address address = new Address(street, city, state, zip);

            employeeService.updateEmployee(id, name, dob, emailId, deptId,address);
            logger.info("Employee updated successfully." +id);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("unable to update the employee" +e.getMessage());
        }
    }
    /**
     * Prompts the user to add a sport to an employee.
     */
    public void addSportToEmployee() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Employee added to sport initiated");
            System.out.println("Enter Employee Id: ");
            int employeeId = scanner.nextInt();

            for (Sport sport : employeeService.getAllSports()) {
            System.out.println("ID = " +sport.getId()+ " Name = " +sport.getName());
        }
            System.out.print("Enter Sport Id: ");
            int sportId = scanner.nextInt();
            scanner.nextLine();

            employeeService.addSportToEmployee(employeeId, sportId);
            logger.info("Sport added to employee successfully.");
            System.out.println("Sport added to employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Unable to add sport to project"+ e.getMessage());
        }
    }
    
    /**
     * Prompts the user to remove a sport form an employee.
     */
    public void removeSportFromEmployee() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Employee remove to sport initiated");
            System.out.print("Enter Employee Id: ");
            int employeeId = scanner.nextInt();
            System.out.print("Enter Sport Id: ");
            int sportId = scanner.nextInt();
            scanner.nextLine();

            employeeService.removeSportFromEmployee(employeeId, sportId);
            logger.info("Sport removed to employee successfully.");
            System.out.println("Sport removed from employee successfully.");
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Unable to remove sport to project"+ e.getMessage());
        }
    }
}
