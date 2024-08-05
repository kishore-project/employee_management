package com.utilities;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class to manage Hibernate sesssion.
 */
public class HibernateConnection {

    private static  HibernateConnection instance = new  HibernateConnection();
    private SessionFactory sessionFactory;
    
    /**
     * Retrieves the single instance of HibernateConnection.
     * 
     * @return the singleton instance of HibernateConnection
     */
    public static  HibernateConnection getInstance() {
        return instance;
    }
    
    /**
     * Private constructor to prevent instantiation.
     * Configures Hibernate and builds the session factory.
     */
    private  HibernateConnection() {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");         
        sessionFactory = configuration.buildSessionFactory();
    }
    
    /**
     * Opens and returns a new Hibernate session.
     * 
     * @return a new Session instance
     */
    public static Session getSession() {
        Session session =  getInstance().sessionFactory.openSession();  
        return session;
    }
}