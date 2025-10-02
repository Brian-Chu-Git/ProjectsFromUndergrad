package edu.ucalgary.oop;
/**
* The Animal class represents an abstract entity of an animal with a feeding time schedule.
* @author Brian Chu
* @author Youssef Hamed
* @since April 2023
*/

abstract class Animal {
	/*
	*  The name of the animal
	*/
	protected String name;
	/**
	* The time in which the feeding of the animal starts.
	*/
	private final String TIMEACTIVE;
	/**
	*  Creating a FeedingTime object inside the Animal class.
	*/
	private FeedingTime feedingTime;
	/**
	* The specific species o the animal.
	*/
	private final String SPECIES;
	/**
	* The unique ID of the animal.
	*/
	private final int ANIMALID;
		/**
 * Constructs an animal object with a name, animal ID, species, time active,
 * feeding duration, preparation time, start hour, and maximum window.
 * @param name, The name of the animal.
 * @param animalID, The unique identifier of the animal.
 * @param species, The species of the animal.
 	* @param timeActive, The time of day the animal is active.
 	* @param feedingDuration, The duration of the animal's feeding.
 * @param prepTime, The time it takes to prepare the animal's food.
 * @param startHour, The start hour of the animal's feeding time.
 * @param maxWindow, The maximum window for the animal's feeding time.
 */
	public Animal(String name, int animalID, String species, String timeActive,
			int feedingDuration, int prepTime, int startHour, int maxWindow) {
		this.name = name;
		this.TIMEACTIVE = timeActive;
		
		this.feedingTime = new FeedingTime(feedingDuration, prepTime, startHour, maxWindow);
		this.SPECIES = species;
		this.ANIMALID = animalID;
	}
/**
 * Removes the feeding time for the animal.
 	*/
	public void removeFeedingTime () {
		this.feedingTime = null;
	}
/**
* Returns the species of the animal.
* @return the species of the animal
*/
	public String getSpecies() {
		return this.SPECIES;
	}
/**
* Sets the name of the animal.
 	* @param newName the new name of the animal
*/
	public void setName(String newName) {
		this.name = newName;
	}
		/**
 	* Returns the feeding time object for the animal.
 * @return the feeding time for the animal
 */
	public FeedingTime getFeedingTime() {
		return this.feedingTime;
	}
	/**
 * Returns the name of the animal.
 * @return the name of the animal
 	*/
	public String getName() {
		return this.name;
	}
/**
 * Returns the unique identifier of the animal.
 * @return the unique identifier of the animal
 	*/
	public int getAnimalID() {
		return this.ANIMALID;
	}
}
