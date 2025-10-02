package edu.ucalgary.oop;
/**
* The printSchedule class is a simple Java class that contains the main method for starting the application.
* It creates a new instance of the GUIEWR class and sets it to be visible.
* @author Youssef Hamed
* @since April 2023
*/
public class printSchedule{
    /**
    * The main method creates a new instance of the GUIEWR class and sets it to be visible. 
    * @param args An array of command-line arguments for the application (not used).
    */
    public static void main(String []args){

        new GUIEWR().setVisible(true);

    }
}
