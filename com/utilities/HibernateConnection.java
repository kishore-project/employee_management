package com.utilities;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class to manage Hibernate sesssion.
 */
public class HibernateConnection {

    private static final Logger logger = LogManager.getLogger(HibernateConnection.class);
    private static HibernateConnection instance = new HibernateConnection();
    private SessionFactory sessionFactory;

    /**
     * Retrieves the single instance of HibernateConnection.
     * 
     * @return the singleton instance of HibernateConnection
     */
    public static HibernateConnection getInstance() {
        return instance;
    }

    /**
     * Private constructor to prevent instantiation.
     * Configures Hibernate and builds the session factory.
     */
    private HibernateConnection() {
        try {
            Configuration configuration = new Configuration();
            configuration.configure("hibernate.cfg.xml");
            sessionFactory = configuration.buildSessionFactory();
            logger.info("SessionFactory created succesfully.");
        } catch (HibernateException e) {
            // Log the exception or handle it accordingly
            logger.error("Error creating SessionFactory: ", e);
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Opens and returns a new Hibernate session.
     * 
     * @return a new Session instance
     */
    public static Session getSession() {
        try {
            logger.info("opening new Hibernate session.");
            return getInstance().sessionFactory.openSession();
        } catch (HibernateException e) {
            // Log the exception or handle it accordingly
            logger.error("Error opening session: ", e);
            return null; // Or consider throwing a custom exception
        }
    }
}