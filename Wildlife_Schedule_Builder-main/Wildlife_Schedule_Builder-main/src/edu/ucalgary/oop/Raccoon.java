package edu.ucalgary.oop;
/**
* The Raccoon class extends the Nocturnal class and represents a raccoon animal object in the * database.
* It contains a constant value for the duration it takes to clean the cage.
* @author Brian Chu
* @since April 2023
*/
public class Raccoon extends Nocturnal{
	/**
	* The duration in minutes it takes to clean a raccoon’s cage.
	*/ 
	private final int CAGECLEANINGDURATION = 5;
	/**
	* Constructs a Raccoon object with the specified name and animal ID.  
	* @param name,  The name of this raccoon.
	* @param animalID, The animalID of this unique raccoon.
	*/
	public Raccoon(String name, int animalID) {
		super(name, animalID, "raccoon", 5, 0);
	}
	/**
	* Returns the duration in minutes it takes to clean the cage
	* @return the value of the duration to clean the cage
	*/
	public int getCageClean() {
		return this.CAGECLEANINGDURATION;
	}
}
