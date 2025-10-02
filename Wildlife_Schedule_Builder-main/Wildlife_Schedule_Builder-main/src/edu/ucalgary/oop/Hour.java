package edu.ucalgary.oop;
/**
* The Hour class represents an hour in a schedule for animal treatments. It contains lots of *information about the specific hour, the treatments assigned to the hour and whether a backup *volunteer is available for the hour
* @author Youssef Hamed
* @author Meet Bhatt
* @April 2023
*/

import java.util.*;

public class Hour {
     /**
    * String representation of the hour
    */
    private String hourStr;
     /**
    * Specific hour of the day represented by this object.
    */
    private int hour;
    /**
    *  The number of minutes left in the hour. It is initialized to 60 minutes when the object is *created.
    */
    private int minsLeft = 60;
     /**
    * The array list of treatments that are assigned to this hour
    */
    private ArrayList<Treatment> hourtreatments = new ArrayList<Treatment>();
    /**
    * To check whether a backup volunteer is needed for this hour. It is initialized to false when *created. 
    */
    private boolean backupVolunteer = false;
    /**
    * Constructs a new Hour object with the given hour of the day.
    * @param givenHour, The hour of the day represented by this object
    */
    public Hour(int givenHour){
        if(0 <= givenHour && givenHour < 24){
            this.hour = givenHour;
            this.hourStr = Integer.toString(givenHour) + ":00";
        }
    }
    /**
    * Adds a treatment to the list of treatments assigned to this hour
    * @param givenTreatment, The treatment to be assigned to this hour
    */
    public void addTreatment(Treatment giveTreatment){
        this.hourtreatments.add(giveTreatment);
        this.minsLeft -= giveTreatment.getAnimalTask().getTaskDuration();
    }
    /**
     * Adds a Feeding Task to the schedule to be printed out
     * @param feedingTreat
     * @param aniNumb
     * @param nameString
     */
    public void addFeedingTask(Treatment feedingTreat, int aniNumb, String nameString)
    {
        //basically, use FeedingTime obj in Treatment Obj to calculate the correct duration for the Task obj 'AnimalTask' and add that to feedTreat, which will go to it's approriate treatment obj ArrayList in hour.
        int dur = feedingTreat.getAnimal().getFeedingTime().getFeedingDuration();
        int prep = feedingTreat.getAnimal().getFeedingTime().getPrepTime();
        int maxWind = feedingTreat.getAnimal().getFeedingTime().getMaxWindow();
        int calcDuration = prep + (aniNumb * dur);
        //descrption of task needs to have names of coyote
        
        String descriptionTask = "Feeding - " + feedingTreat.getAnimal().getSpecies() + " (" + Integer.toString(aniNumb) + ": " + nameString + ")";

        Task taskToAdd = new Task(0, descriptionTask, calcDuration, maxWind);
        feedingTreat.setAnimalTask(taskToAdd);
        this.hourtreatments.add(feedingTreat);
        this.minsLeft -= calcDuration;
    }
     /**
    * Prints the description of each treatment assigned to this hour
    */
    public void printTreatment()
    {
        for(int i = 0; i < hourtreatments.size(); i++)
        {
            if(hourtreatments.get(i).getAnimalTask().getDescription() != null)
            {
                System.out.println(hourtreatments.get(i).getAnimalTask().getDescription());
            }
        }
    }
    /**
    * Returns all the information to be displayed in the schedule.
    * @return The final result of the info
    */
    public String getInfo()
    {
        String finalResult = "";
        //logic for adding onto the finalResult.

        if(this.hourtreatments.size() > 0)//only execute this if there is something in this hour in the first place.
        {
            finalResult = finalResult + this.hourStr;

            if(this.backupVolunteer)
            {
                finalResult = finalResult + " [+ backup volunteer]\n";
            }
            else
            {
                finalResult = finalResult + "\n";
            }

            for(int i = 0; i < hourtreatments.size(); i++)
            {
                //goes through how treatment ArrayList. if the treatments don't have 
                if(hourtreatments.get(i).getAnimalTask().getDescription() != null)
                {
                    if(hourtreatments.get(i).getAnimalTask().getTaskID() != 0)//not cageClean nor feedingTask
                    {
                        finalResult = finalResult + "* " + hourtreatments.get(i).getAnimalTask().getDescription() + " ("+hourtreatments.get(i).getAnimal().getName() + ")\n";
                    }
                    else
                    {
                        finalResult = finalResult + "* " + hourtreatments.get(i).getAnimalTask().getDescription() + "\n";
                    }
                }
                else if(i == hourtreatments.size() - 1)
                {
                    finalResult = finalResult + "* " + hourtreatments.get(i).getAnimalTask().getDescription();
                }
            }

            finalResult = finalResult + "\n";

        }
        System.out.print(finalResult);
        return finalResult;
    }

    /**
    * Adds a backup volunteer to this hour if required by the schedule
    * @throws IllegalArgumentException If there is not enough time left in the hour to add a *backup volunteer.
    */
    public void addBackup(){

        int netmins = 0;
            
        for(int i = 0; i < this.hourtreatments.size(); i++){
            netmins += hourtreatments.get(i).getAnimalTask().getTaskDuration();
        }

        if(netmins + 60 <= 120){
            new GUIBackupVolunteerConfirmation(this).setVisible(true);
            this.backupVolunteer = true;
            this.minsLeft += 60;
        }
        else{
            throw new IllegalArgumentException("Not enough time present within the hour");
        }


    }
    /** 
    * Returns the number of treatments assigned to this hour.
    * @return The number of treatments.
   */
    public int getHourTreatmentsSize()
    {
        return this.hourtreatments.size();
    }
     /**
    * Returns the hour of the day represented by this object.
   * @return The hour of the day.
    */
    public int getHour(){
        return this.hour;
    }
    /**
    * Returns The string representation of the hour of the day represented by this object.
    * @return The string representation of the hour of the day
    */
    public String gethourStr(){
        return this.hourStr;
    }
    /**
    * Returns The number of minutes left in this hour.
   * @return The number of minutes left.
    */
    public int getminsleft(){
        return this.minsLeft;
    }
    /**
    * Returns The status of the backup volunteer for this hour.
    * @return The status of the backup volunteer.
    */
    public boolean getBackupvolunteer(){
        return this.backupVolunteer;
    }
    /**
     * Returns the array list of all the treatments added to the list
     * @return the array list of all the treatments
     */
    public ArrayList<Treatment> getTreatments(){
        return this.hourtreatments;
    }

    public static void main(String[] args)
    {
        Hour newHour = new Hour(2);//hour for 2am

        Fox newFox = new Fox("One, Two, and Three", 6);
        Task newTask = new Task(1, "Kit Feeding", 30, 2);
        //add this treat to hour of 2am
        Treatment newTreat = new Treatment(newFox, newTask, 2, 6);//at 2 am
        newHour.addTreatment(newTreat);

        //add their feeding times to the hour of 2am
        Coyote newCoyote = new Coyote("Coyote1", 1);
        Beaver newBeaver = new Beaver("Beaver1", 2);
        Fox anotherFox = new Fox("Fox1", 3);
        Porcupine newPor = new Porcupine("Por1", 4);
        Raccoon newRac = new Raccoon("Rac1", 5);

        Task coyFeedTask = new Task(0, "something", 5, 3);
        Task beaFeedTask = new Task(0, "something", 5, 3);
        Task foxFeedTask = new Task(0, "something", 5, 3);
        Task porFeedTask = new Task(0, "something", 5, 3);
        Task racFeedTask = new Task(0, "something", 5, 3);
        //
        Treatment coyFeed = new Treatment(newCoyote, coyFeedTask, 2, 0);
        Treatment beaFeed = new Treatment(newBeaver, beaFeedTask, 2, 0);
        Treatment foxFeed = new Treatment(anotherFox, foxFeedTask, 2, 0);
        Treatment porFeed = new Treatment(newPor, porFeedTask, 2, 0);
        Treatment racFeed = new Treatment(newRac, racFeedTask, 2, 0);


        newHour.addFeedingTask(coyFeed, 1, "Feeding - coyote (1: Coyote1)");
        newHour.addFeedingTask(beaFeed, 1, "Feeding - beaver (1: Beaver1)");
        // newHour.addFeedingTask(foxFeed, 1, "Feeding - fox (1: Fox1)");
        newHour.addFeedingTask(porFeed, 1, "Feeding - porcupine (1: Por1)");
        newHour.addFeedingTask(racFeed, 1, "Feeding - raccoon (1: Rac1)");

        // assertEquals(0, newHour.getminsleft());

        newHour.getInfo();
    }
}