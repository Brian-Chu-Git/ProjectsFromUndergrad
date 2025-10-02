package edu.ucalgary.oop;
/**
* The Porcupine class extends the Crepuscular class and represents a porcupine animal object * in the database.
* It contains a constant value for the duration it takes to clean the cage.
* @author Brian Chu
* @since April 2023
*/

public class Porcupine extends Crepuscular {
	/**
	* The duration in minutes it takes to clean a porcupine’s cage.
	*/ 
	private final int CAGECLEANINGDURATION = 10;
	/**
	* Constructs a Porcupine object with the specified name and animal ID.  
	* @param name,  The name of this porcupine.
	* @param animalID, The animalID of this unique porcupine.
	*/
	public Porcupine(String name, int animalID) {
		super(name, animalID, "porcupine", 5, 0);
	}
	/**
	* Returns the duration in minutes it takes to clean the cage
	* @return the value of the duration to clean the cage
	*/
	public int getCageClean() {
		return this.CAGECLEANINGDURATION;
	}
}
