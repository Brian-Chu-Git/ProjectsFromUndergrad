package edu.ucalgary.oop;
/**
* The Treatment class represents a treatment for an animal. It contains information such as the *feeding time, animal ID, start hour, animal being treated, and the task associated with the *treatment.
* @author Sahib Thethi
* @author Youssed Hamed
* @since April 2023
*/
public class Treatment {
    /**
 * The ID of the animal being treated.
 	*/
    private final int ANIMALID;
    /**
* The start hour of the treatment.
 	*/
    private int startHour;
    /**
 	* The animal being treated.
 	*/

    private Animal animalTreatment;
    /**
 * The task associated with the treatment.
 	*/
    private Task animalTask;
/**
 * Constructs a new Treatment object for a Coyote.
 * @param giveAnimal The Coyote being treated.
 * @param givenAnimalTask The task associated with the treatment.
 * @param givenStartHour The start hour of the treatment.
 * @param animalID The ID of the Coyote being treated.
 */    

    public Treatment(Coyote giveAnimal, Task givenAnimalTask, int givenStartHour, int animalID)
    {
        this.startHour = givenStartHour;
        this.animalTask = givenAnimalTask;
        this.animalTreatment = giveAnimal;
		this.ANIMALID = animalID;
        if (givenAnimalTask.getTaskID() == 1) {
            giveAnimal.removeFeedingTime();
        }
    }
/**
 * Constructs a new Treatment object for a Fox.
 * @param giveAnimal, The Fox being treated.
 * @param givenAnimalTask, The task associated with the treatment.
 * @param givenStartHour, The start hour of the treatment.
 * @param animalID, The ID of the Fox being treated.
 */

    public Treatment(Fox giveAnimal, Task givenAnimalTask, int givenStartHour, int animalID)
    {
        this.startHour = givenStartHour;
        this.animalTask = givenAnimalTask;
        this.animalTreatment = giveAnimal;
		this.ANIMALID = animalID;
        if (givenAnimalTask.getTaskID() == 1) {
            giveAnimal.removeFeedingTime();
        }
    }
/**
 * Constructs a new Treatment object for a Porcupine.
 * @param giveAnimal, The Porcupine being treated.
 * @param givenAnimalTask, The task associated with the treatment.
 * @param givenStartHour, The start hour of the treatment.
 * @param animalID, The ID of the Porcupine being treated.
 */

    public Treatment(Porcupine giveAnimal, Task givenAnimalTask, int givenStartHour, int animalID)
    {
        this.startHour = givenStartHour;
        this.animalTask = givenAnimalTask;
        this.animalTreatment = giveAnimal;
		this.ANIMALID = animalID;
        if (givenAnimalTask.getTaskID() == 1) {
            giveAnimal.removeFeedingTime();
        }
    }

/**
 * Constructs a new Treatment object for a Raccoon.
 * @param giveAnimal, The Raccoon being treated.
 * @param givenAnimalTask, The task associated with the treatment.
 * @param givenStartHour, The start hour of the treatment.
 * @param animalID, The ID of the Raccoon being treated.
 */

    public Treatment(Raccoon giveAnimal, Task givenAnimalTask, int givenStartHour, int animalID)
    {
        this.startHour = givenStartHour;
        this.animalTask = givenAnimalTask;
        this.animalTreatment = giveAnimal;
		this.ANIMALID = animalID;
        if (givenAnimalTask.getTaskID() == 1) {
            giveAnimal.removeFeedingTime();
        }
    }
/**
 * Constructs a new Treatment object for a Beaver.
 * @param giveAnimal, The Beaver being treated.
 * @param givenAnimalTask, The task associated with the treatment.
 * @param givenStartHour, The start hour of the treatment.
 * @param animalID, The ID of the Beaver being treated.
 */

    public Treatment(Beaver giveAnimal, Task givenAnimalTask, int givenStartHour, int animalID)
    {
        this.startHour = givenStartHour;
        this.animalTask = givenAnimalTask;
        this.animalTreatment = giveAnimal;
		this.ANIMALID = animalID;
        if (givenAnimalTask.getTaskID() == 1) {
            giveAnimal.removeFeedingTime();
        }
    }
  /**
 * Returns the animal object in the treatment.
 * @return the animal object in the treatment
 */


    public Animal getAnimal(){
        return this.animalTreatment;
    }

/**
 * Returns the task object in the treatment.
 * @return the task object in the treatment
 */

    public Task getAnimalTask(){
        return this.animalTask;
    }
/**
 * Sets the task object in the treatment.
 * @param addedTasks the new task object to be set in the treatment
 */

    public void setAnimalTask(Task addedTasks){
        this.animalTask = addedTasks;
    }
/**
 * Returns the starting hour of the treatment.
 * @return the starting hour of the treatment
 */

    public int getStartHour() {
        return this.startHour;
    }
/**
 * Sets the starting hour of the treatment.
 * @param givenStartHour the new starting hour to be set in the treatment
 */

    public void setStartHour(int givenStartHour) {
        this.startHour = givenStartHour;
    }
}