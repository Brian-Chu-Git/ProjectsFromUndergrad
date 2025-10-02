package edu.ucalgary.oop;
/**
* The Beaver class extends the Dinurnal class and represents a beaver animal object in the database.
* It contains a constant value for the duration it takes to clean the cage.
* @author Brian Chu
* @since April 2023
*/

public class Beaver extends Diurnal{
	/**
	* The duration in minutes it takes to clean a beaver’s cage.
	*/  
	private final int CAGECLEANINGDURATION = 5;
	/**
	* Constructs a Beaver object with the specified name and animal ID.  
	* @param name,  The name of this beaver.
	* @param animalID, The animalID of this unique beaver.
	*/
	public Beaver(String name, int animalID) {
		super(name, animalID, "beaver", 5, 0);
	}
	/**
	* Returns the duration in minutes it takes to clean the cage
	* @return the value of the duration to clean the cage
	*/
	public int getCageClean() {
		return this.CAGECLEANINGDURATION;
	}
}
