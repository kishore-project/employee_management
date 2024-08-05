package com.sports.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Set;
import org.hibernate.HibernateException;

import com.exceptions.DataBaseException;
import com.model.Sport;
import com.model.Employee;
import com.model.Department;

/**
 * <p>
 * Interface for SportService to  handle sport related operation.
 * </p>
 *
 * @author  Kishore 
 * @version 1.0 
 */
public interface SportRepository {

    /**
     * Adds a new sport to the database
     * @param Sport - The sport to be added.
     */
    public void addSport(Sport sport) throws DataBaseException;

    /**
     *Deletes an sport from the database by ID.
     *
     *@param id - sport to delete.
     */
    public void deleteSport(int id) throws DataBaseException;

    /**
     * Retrieves all sports from the database.
     *
     * @return alist of all sports.
     */
    public Set<Sport> getAllSports() throws DataBaseException;

    /**
     * Finds an sport by ID.
     *
     * @param id - sport to find.
     * @return The sport if found, null otherwise.
     */

    public Sport findSportById(int id) throws DataBaseException;

    /**
     * Updates an existing sport in the database.
     *
     * @param sport - Sport with update details.
     */

    public void updateSport(Sport sport) throws DataBaseException;

    /**
     * Retrives the employee association with an sports by their id.
     *
     * @param sportId - Id of sport.
     * @return A list of employee association with sports.
     */
    public Set<Employee> getEmployeesBySportId(int sportId) throws DataBaseException;

}
