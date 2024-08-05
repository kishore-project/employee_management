package com.sports.controller;

import java.util.Scanner;
import java.util.Set;
import org.hibernate.HibernateException;

import com.exceptions.DataBaseException;
import com.model.Employee;
import com.model.Sport;
import com.sports.service.SportService;
import com.sports.service.SportServiceImpl;

/**
 * Controller class for handling user input related to sports management.
 * This class provides a menu for adding, updating, deleting and displaying sports.
 * @author  Kishore 
 * @version 1.0 
 */
public class SportController {
    private SportService sportService = new SportServiceImpl();
    private static int idCounter = 1; //Set the Id and increment the counter for auto increment Ids
    private Scanner scanner = new Scanner(System.in);
    
    /**
     * Displays the menu for sport managemnt and processes user input.
     */
    public void displaySportMenu() {
        try{
            while (true) {
                System.out.println("-----| Sport Management |-----");
                System.out.println("1) Add Sport");
                System.out.println("2) Delete Sport");
                System.out.println("3) Display All Sports");
                System.out.println("4) Display Sport by ID");
                System.out.println("5) Update Sport");
                System.out.println("6) Display Employees In Sport");
                System.out.println("7) Back to Main Menu");
                System.out.println("------------------------------");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        createSport();
                        break;
                    case 2:
                        deleteSport();
                        break;
                    case 3:
                        displayAllSports();
                        break;
                    case 4:
                        displaySportById();
                        break;
                    case 5:
                        updateSport();
                        break;
                    case 6:
                        displayEmployeesInSport();
                        break;
                    case 7:
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
    
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prompts the user to enter details for a new sport and adds it to the system.
     */
    public void createSport() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter sport Name: ");
            String name = scanner.nextLine();

            sportService.addSport(idCounter, name);
            System.out.println("Sport added successfully.");
            idCounter++;
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Prompts the user to enter the ID of the sport to be deleted and removes it.
     */
    public void deleteSport() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter sport Id to delete: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            sportService.removeSport(id);
            System.out.println("Sport deleted successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Displays all sports currently in.
     */ 
    public void displayAllSports() throws DataBaseException {
        try{
            System.out.println("All Sports:");
            System.out.printf("%-5s | %-10s %n"," ID ", " Name ");
            for (Sport sport : sportService.getAllSports()) {
                System.out.printf(" %-5s | %-10s %n",sport.getId(), sport.getName());
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }
 
    /**
     * Prompts the user to enter the ID of a sport and displays its details.
     */
    public void displaySportById() throws IllegalArgumentException, DataBaseException {
        try {
            System.out.print("Enter sport Id to display: ");
            int id = scanner.nextInt();
            scanner.nextLine();

            Sport sport = sportService.getSportById(id);
            if (sport != null) {
                System.out.println("ID = " +sport.getId()+ " Name = " +sport.getName());
            } else {
                System.out.println("Sport not found." +id);
            }        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

   /**
    * prompts the user to enter details for an existing sport ID and updates its information.
    */
    public void updateSport() throws IllegalArgumentException, DataBaseException  {
        try {
            System.out.print("Enter sport Id: ");
            int id = scanner.nextInt();
            if(sportService.getSportById(id) == null) {
                System.out.println("Sport ID Not Found" +id);
                return;
            }
            scanner.nextLine();
            System.out.print("Enter sport Name: ");
            String name = scanner.nextLine();

            sportService.updateSport(id, name);
            System.out.println("Sport updated successfully.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * prompts the user to enter the ID of a sport and displays the employees participating in it.
     */
    public void displayEmployeesInSport() throws IllegalArgumentException, DataBaseException  {
        try {
             displayAllSports();
             System.out.println("enter Sport ID: ");
             int id=scanner.nextInt();
             scanner.nextLine();
             Set<Employee> employees = sportService.getSportById(id).getEmployees();
             System.out.printf("Employees in Sports %d:\n",id);
             for(Employee employee : employees) {
                 System.out.println(employee);
              }
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (HibernateException e) {
            System.out.println(e.getMessage());
        }
    }
}