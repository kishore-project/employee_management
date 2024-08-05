package com.employee.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exceptions.DataBaseException;
import com.model.Employee;
import com.model.Department;
import com.model.Sport;
import com.employee.service.EmployeeService;
import com.employee.dao.EmployeeRepository;
import com.utilities.HibernateConnection;

/**
 * Repository for managing employees, including adding, removing, updating and retrieving employees.
 * @author  Kishore 
 * @version 1.0 
 */
public class EmployeeRepositoryImpl implements EmployeeRepository{
    
    /**
     * Adds a new employee to the database.
     *
     * @param employee - The employee to be added.
     */
    @Override
    public void addEmployee(Employee employee) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.save(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while adding Employee: " + employee.getName(), e);
        }
    }

   /**
    *Deletes an employee from the database by ID.
    *
    *@param id - employee to delete.
    */
    @Override
    public void deleteEmployee(int id) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, id);
            if (employee != null) {
                employee.setIsActive(false);
                session.update(employee);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while deleting Employee: " + id, e);
        }
    }

    /**
     * Retrieves all employees from the database.
     *
     * @return alist of all employees.
     */
    @Override
    public List<Employee> getAllEmployees() throws DataBaseException {
        try (Session session = HibernateConnection.getSession()) {
            Query<Employee> query = session.createQuery("From Employee where isActive = true", Employee.class);
            return query.list();
        } catch (HibernateException e) {
            throw new DataBaseException("Error while getting all employees", e);
        }
    }

    /**
     * Finds an employee by ID.
     *
     * @param id - employee to find.
     * @return The employee if found, null otherwise.
     */
    @Override
    public Employee findEmployeeById(int id) throws DataBaseException {
        try (Session session = HibernateConnection.getSession()) {
            String hql = "FROM Employee e LEFT JOIN FETCH e.department WHERE e.id = :id";
            Query<Employee> query = session.createQuery(hql, Employee.class);
            query.setParameter("id", id);
            return query.uniqueResult();
        } catch (HibernateException e) {
            throw new DataBaseException("Error while getting Employee by ID: " + id, e);
        }
    }

    /**
     * Updates an existing employee in the database.
     *
     * @param employee - emplyee with update details.
     */
    @Override
    public void updateEmployee(Employee employee) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.update(employee);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while updating Employee: " + employee.getName(), e);
        }
    }

    /**
     * Adds a sport to an employee.
     *
     * @param employeeId - Id of employee.
     * @param sportId - Id of sport.
     */
    @Override    
    public void addSportToEmployee(int employeeId, int sportId) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Sport sport = session.get(Sport.class, sportId);
            if (employee != null && sport != null) {
                employee.getSports().add(sport);
                sport.getEmployees().add(employee);
                session.saveOrUpdate(employee);
                session.saveOrUpdate(sport);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while adding Sport to Employee: " + employeeId, e);
        }
    }

    /**
     * remove a sport to an employee.
     *
     * @param employeeId - Id of employee.
     * @param sportId - Id of sport.
     */
    @Override
    public void removeSportFromEmployee(int employeeId, int sportId) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Employee employee = session.get(Employee.class, employeeId);
            Sport sport = session.get(Sport.class, sportId);
            if (employee != null && sport != null) {
                employee.getSports().remove(sport);
                sport.getEmployees().remove(employee);
                session.saveOrUpdate(employee);
                session.saveOrUpdate(sport);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) { 
                transaction.rollback();
            }
            throw new DataBaseException("Error while removing Sport from Employee: " + employeeId, e);
        }
    }
              
}
       

    
