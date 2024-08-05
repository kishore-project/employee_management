package com.department.controller;

import java.util.List;
import java.util.Scanner;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;

import com.exceptions.DataBaseException;
import com.department.service.DepartmentService;
import com.department.service.DepartmentServiceImpl;
import com.model.Employee;
import com.utilities.Validator;

/**
 * Controller to manage department  related operations.
 * Handles user input and interacts with departmentService to preform operations.
 * @author  Kishore 
 * @version 1.0 
 */
public class DepartmentController {
    private static final Logger logger = LogManager.getLogger(DepartmentController.class);
    private DepartmentService departmentService  = new DepartmentServiceImpl();
    private Validator validator;
    private static int idCounter = 1; //Set the Id and increment the counter for auto increment Ids
    private Scanner scanner = new Scanner(System.in);

    /**
     * Displays the Department management menu and handles user choices.
     */
    public void displayDepartmentMenu() {
        try {
            while (true) {
                System.out.println("---| Department Management |---");
                System.out.println("1) Add Department");
                System.out.println("2) Delete Department");
                System.out.println("3) Display All Departments");
                System.out.println("4) Update Department");
                System.out.println("5) Department Based Employee"); 
                System.out.println("6) Back to Main Menu");
                System.out.println("-------------------------------");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        createDepartment();
                        break;
                    case 2:
                        deleteDepartment();
                        break;
                    case 3:
                        displayAllDepartments();
                        break;
                    case 4:
                        updateDepartment();
                        break;
                    case 5:
                        displayEmployeesInDepartment();
                        break;
                    case 6:
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
     * Prompts the user to create a new department.
     */
    public void createDepartment() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Department credentials validation has been initiated");
            String name = "";
            boolean checkName = false;
            scanner.nextLine();
            while(!checkName){
                System.out.print("Enter Department Name: ");
                name = scanner.nextLine();
                if(!validator.isValidname(name)) {
                    System.out.println("InValid");
                } else {
                    checkName = true;
                }
            }

            departmentService.addDepartment(idCounter, name);
            logger.info("Department added successfully." +name);
            idCounter++;
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while adding department", e);
        }
    }

    /**
     * Prompts the user to enter the ID of the department to be deleted and removes it.
     */
    public void deleteDepartment() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter Department ID: ");
            int id = scanner.nextInt();
            logger.debug("Getting department id for remove"+id);
            departmentService.removeDepartment(id);
            logger.info("Department deleted successfully.");
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while deleting department..", e);
        }
    }

    /**
     * Displays all department currently in.
     */
    public void displayAllDepartments() throws DataBaseException {
        try {
            logger.debug("Getting department list");
            System.out.printf("|%-10s | %-20s |\n","ID","Department Name");
            departmentService.getAllDepartments().forEach(System.out::println);
        } catch (HibernateException e) {
            logger.error("Error while display department list ", e);
        }
    }

    /**
     * prompts the user to enter details for an existing department ID and updates its information.
     */
    public void updateDepartment() throws IllegalArgumentException, DataBaseException {
        try {
            logger.debug("Department update initiated");
            System.out.print("Enter the ID to update: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter new name: ");
            String name = scanner.nextLine();

            departmentService.updateDepartment(id, name);
            logger.info("Department updated successfully." +id);
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("Error while update department", e.getMessage());
        }
    }

    /**
     * prompts the user to enter the ID of a department and displays the employees in it.
     */
    public void displayEmployeesInDepartment() throws IllegalArgumentException, DataBaseException {
        try {
             displayAllDepartments();
             System.out.println("enter Department ID: ");
             int id=scanner.nextInt();
             scanner.nextLine();
             logger.debug("display employee by department ID" +id);

             List<Employee> employees = departmentService.getEmployeesByDepartmentId(id);
             System.out.printf("Employees in Department %d:\n",id);
             for(Employee employee : employees) {
                 System.out.println(employee);
              }
        } catch (IllegalArgumentException e) {
            logger.warn(e.getMessage());
        } catch (HibernateException e) {
            logger.error("error while getting employee list", e);
        }
    }
}
