package edu.ucalgary.oop;
/**
* The Fox class extends the Nocturnal class and represents a fox animal object in the       *database.
* It contains a constant value for the duration it takes to clean the cage.
* @author Brian Chu
* @since April 2023
*/
public class Fox extends Nocturnal{
	/**
	* The duration in minutes it takes to clean a fox’s cage
	*/ 
	private final int CAGECLEANINGDURATION = 5;
	/**
	* Constructs a Fox object with the specified name and animal ID.  
	* @param name,  The name of this fox.
	* @param animalID, The animalID of this unique fox.
	*/
	public Fox(String name, int animalID) {
		super(name, animalID, "fox", 5, 5);
	}
	/**
	* Returns the duration in minutes it takes to clean the cage
	* @return the value of the duration to clean the cage
	*/
	public int getCageClean() {
		return this.CAGECLEANINGDURATION;
	}
}
