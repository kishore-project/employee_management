import java.util.Scanner;

import com.employee.controller.EmployeeController;
import com.department.controller.DepartmentController;
import com.sports.controller.SportController;


/**
 * <p>
 * The main class for the Employee Management System. This class contains the main method
 * which starts the application and provides a menu for navigating bewtween different management
 * functonalities.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeManagement {
    public static void main(String[] args) throws IllegalArgumentException {
        // Create instances of controllers
        EmployeeController employeeController = new EmployeeController();
        DepartmentController departmentController = new DepartmentController();
        SportController sportController = new  SportController();
         
        // Display the main menu
        try {
            while (true) {
                System.out.println("------| Main Menu |------");
                System.out.println("1) Employee Management");
                System.out.println("2) Department Management");
                System.out.println("3) Sports");
                System.out.println("4) Exit");
                System.out.print("Enter your choice: ");

            // Read user input
                int choice = new Scanner(System.in).nextInt();

            //Perform action based on user choice
                switch (choice) {
                    case 1:
                        employeeController.displayEmployeeMenu();
                        break;
                    case 2:
                        departmentController.displayDepartmentMenu();
                        break;
                     case 3:
                        sportController.displaySportMenu();
                        break;
                     case 4:
                        System.out.println("Exiting...!");
                        System.exit(0);
                        break;
                     default:
                        System.out.println("Invalid choice.");
                }
            }
         } catch (IllegalArgumentException e) {
             System.out.println(e.getMessage());
         }
    }
}
