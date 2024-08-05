package com.utilities;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 *<p>
 * Validating various type of input such as name,email addresses and dates.
 * </p>
 * @author  Kishore 
 * @version 1.0 
 */
public class Validator {
    private static final Pattern EmailPattern = Pattern.compile("\\b[A-za-z0-9._%-]"
                                            +"+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}\\b");
    private static final Pattern NamePattern = Pattern.compile("^^[\\p{L} .'-]+$");
    
    /**
     * Validates if the given name is valid.
     *
     * @param name - The name to validate
     * @return true if the name is valid, false otherwise
     */
    public static boolean isValidname(String Name) {
        if(Name == null) {
            return false;
        }

        Matcher matcher=NamePattern.matcher(Name);
        return matcher.matches();
    }

    /**
     * Validates if the given emailId is valid.
     *
     * @param emailId - The emailId to Validate
     * @return true if the emailid is valid, false otherwise
     */
    public static boolean isValidEmail(String emailId) {
        
        if(emailId.isEmpty()) {
           return false;
         }
         Matcher matcher = EmailPattern.matcher(emailId);
         return matcher.matches();
    }

    /**
     * Validates if the given date is not a future date.
     *
     * @param givenDate - The date to check
     * @return true if the date is not in future, false otherwise
     */
    public static boolean isValidFutureDate(LocalDate givenDate){
         if (givenDate == null) {
            return false;
         }
         return !givenDate.isAfter(LocalDate.now());
    }
   
    /*
     *It can be only six digits.
     *It should not start with zero.
     *First digit of the pin code must be from 1 to 9.
     *Next five digits of the pin code may range from 0 to 9.
     *It should allow only one white space, but after three digits, although this is optional.
     */
    public static boolean isValidPinCode(String pinCode) {
        String regex = "^[1-9]{1}[0-9]{2}\\s{0,1}[0-9]{3}$";
        Pattern p = Pattern.compile(regex);
        if (pinCode == null) {
            return false;
        }
        Matcher m = p.matcher(pinCode);
        return m.matches();
    }

    public static boolean isValidNotNull(String input) {
        if(!input.isEmpty()) { 
            return true;
        }
        return false;
    }
}