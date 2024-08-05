package com.sports.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.hibernate.HibernateException;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.exceptions.DataBaseException;
import com.model.Department;
import com.model.Employee;
import com.model.Sport;
import com.sports.dao.SportRepository;
import com.utilities.HibernateConnection;

/**
 * Repository for managing sport entities. This class provides methods to add, delete, update,
 * and retrieve sports.
 * @author  Kishore 
 * @version 1.0 
 */
public class SportRepositoryImpl implements SportRepository {

    /**
     * Adds a new sport to the database
     *
     * @param Sport - The sport to be added.
     */
    public void addSport(Sport sport) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.save(sport);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while adding Sport: " + sport.getName(), e);
        }
    }

   /**
    *Deletes an sport from the database by ID.
    *
    *@param id - sport to delete.
    */
    @Override
    public void deleteSport(int id) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            Sport sport = session.get(Sport.class, id);
            if (sport != null) {
                sport.setIsActive(false);
                session.update(sport);
            }
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while deleting sport: " + id, e);
        }
    }

    /**
     * Retrieves all sports from the database.
     *
     * @return alist of all sports.
     */
    @Override
    public Set<Sport> getAllSports() throws DataBaseException {
        try (Session session = HibernateConnection.getSession()) {
            Query<Sport> query = session.createQuery("FROM Sport WHERE isActive = true", Sport.class);
            return new HashSet<>(query.list());
        } catch (HibernateException e) {
            throw new DataBaseException("Error while getting all sports", e);
        }
    }

    /**
     * Finds an sport by ID.
     *
     * @param id - sport to find.
     * @return The sport if found, null otherwise.
     */
    @Override
    public Sport findSportById(int id) throws DataBaseException {
        try (Session session = HibernateConnection.getSession()) {
            return session.get(Sport.class, id);
        } catch (HibernateException e) {
            throw new DataBaseException("Error while getting sport by ID: " + id, e);
        }
    }

    /**
     * Updates an existing sport in the database.
     *
     * @param sport - Sport with update details.
     */
    @Override
    public void updateSport(Sport sport) throws DataBaseException {
        Transaction transaction = null;
        try (Session session = HibernateConnection.getSession()) {
            transaction = session.beginTransaction();
            session.update(sport);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null) {
                transaction.rollback();
            }
            throw new DataBaseException("Error while updating Sport: " + sport.getName(), e);
        }
    }

    /**
     * Retrives the employee association with an sports by their id.
     * 
     * @param sportId - Id of sport.
     * @return A list of employee association with sports.
     */
    @Override
    public Set<Employee> getEmployeesBySportId(int sportId) throws DataBaseException {
        try (Session session = HibernateConnection.getSession()) {
            Sport sport = session.get(Sport.class, sportId);
            return sport != null ? sport.getEmployees() : null;
        } catch (HibernateException e) {
            throw new DataBaseException("Error while getting employees by sport ID: " + sportId, e);
        }
    }
}
