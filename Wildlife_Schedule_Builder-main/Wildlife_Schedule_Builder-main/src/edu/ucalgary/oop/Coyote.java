package edu.ucalgary.oop;
/**
* The Coyote class extends the Crepuscular class and represents a coyote animal object in the      * database.
* It contains a constant value for the duration it takes to clean the cage.
* @author Brian Chu
* @since April 2023
*/
public class Coyote extends Crepuscular {
	/**
	* The duration in minutes it takes to clean a coyote’s cage
	*/ 
	private final int CAGECLEANINGDURATION = 5;
	/**
	* Constructs a coyote object with the specified name and animal ID.  
	* @param name,  The name of this coyote.
	* @param animalID, The animalID of this unique coyote.
	*/
	public Coyote(String name, int animalID) { 
		super(name, animalID, "coyote", 5, 10);
	}
	/**
	* Returns the duration in minutes it takes to clean the cage
	* @return the value of the duration to clean the cage
	*/
	public int getCageClean() {
		return this.CAGECLEANINGDURATION;
	}
}