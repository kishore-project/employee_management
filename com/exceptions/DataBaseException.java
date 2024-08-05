package com.exceptions;

import org.hibernate.HibernateException;

/**
 * This class handles custom exceptions related to database operations.
 * Extends HibernateExceptions to provide more specific error handling. 
 * @author kishore
 */
public class DataBaseException extends HibernateException {
   
    /**
     * Contructs a DatabaseException with the specified details message and cause.
     *
     * @param message the details message.
     * @param error the cause (which is saved for later retrival by the method).
     */
    public DataBaseException(String message, Throwable error) {  
        super(message, error);  
    }  

}