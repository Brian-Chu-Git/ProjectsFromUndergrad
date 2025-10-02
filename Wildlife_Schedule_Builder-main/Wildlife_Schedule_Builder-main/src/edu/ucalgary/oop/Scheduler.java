package edu.ucalgary.oop;
/**
* The Schedule class represents a schedule of Treatments ordered by day. It contains a private * string variable for the scheduled day, an array of Treatment objects representing the ordered *treatments, and a Scheduler object representing the final scheduler.
* @author Sahib Thethi
* @since April 2023 
*/
import java.io.*;
import java.time.*;
import java.util.*;
// import java.sql.*;

public class Scheduler {

    private ArrayList<Treatment> treatments = new ArrayList<Treatment>();
    private ArrayList<Coyote>allCoy;
    private ArrayList<Fox>allFo;
    private ArrayList<Porcupine>allPor;
    private ArrayList<Beaver>allBea;
    private ArrayList<Raccoon>allRac;
    private ArrayList<Hour> finalHours;
    private Hour brokenHour;
/** 
* The Scheduler class represents a scheduler that takes in a list of treatments and organizes *them based on their start time and duration. The scheduler ensures that each treatment is *assigned to an available hour slot based on the maximum window of the task. In case of *unavoidable overlap, it throws an UnavoidableOverlapException.
*The class provides methods for adding treatments to the list, getting the list of treatments and *organizing them.
* @author Sahib Thethi
* @author Meet Bhatt
* @since April 2023
*/
    public Scheduler(ArrayList<Treatment>treatments, ArrayList<Coyote>coyAni, ArrayList<Fox>foAni, ArrayList<Porcupine>porAni, ArrayList<Beaver>beaAni, ArrayList<Raccoon>racAni)
    {
        this.treatments = treatments;
        this.allCoy = coyAni;
        this.allFo = foAni;
        this.allPor = porAni;
        this.allBea = beaAni;
        this.allRac = racAni;
    }

    public Hour getBrokenHour()
    {
        return this.brokenHour;
    }
     /**
    * Adds a treatment to the list of treatments.
    * @param givenTreatment, The treatment to be added to the list.
    */

    public void addTreatment(Treatment givenTreatment){

        this.treatments.add(givenTreatment);

    }
     /**
    * Returns the list of treatments.
    * @return The list of treatments.
    */
    public ArrayList<Treatment> getTreatments(){
        return this.treatments;
    }
    /**
    * Organizes the list of treatments based on their start time and duration and assigns each treatment to an available
    * hour slot based on the maximum window of the task. In case of unavoidable overlap, it throws an UnavoidableOverlapException.
    * @throws UnavoidableOverlapException If there is no available hour slot for a treatment.
    */

    public void organize() throws UnavoidableOverlapException
    {

        ArrayList<Treatment> treatmentarray = this.treatments;

        ArrayList<Coyote> Coy = new ArrayList<Coyote> ();
        ArrayList<Fox> Fo = new ArrayList<Fox> ();
        ArrayList<Porcupine> Por = new ArrayList<Porcupine> ();
        ArrayList<Beaver> Bea = new ArrayList<Beaver> ();
        ArrayList<Raccoon> Rac = new ArrayList<Raccoon> ();

        for(int i = 0; i < allCoy.size(); i++)
        {
            if(allCoy.get(i).getFeedingTime() != null)
            {
                Coy.add(allCoy.get(i));
            }
        }

        for(int i = 0; i < allFo.size(); i++)
        {
            if(allFo.get(i).getFeedingTime() != null)
            {
                Fo.add(allFo.get(i));
            }
        }

        for(int i = 0; i < allPor.size(); i++)
        {
            if(allPor.get(i).getFeedingTime() != null)
            {
                Por.add(allPor.get(i));
            }
        }

        for(int i = 0; i < allBea.size(); i++)
        {
            if(allBea.get(i).getFeedingTime() != null)
            {
                Bea.add(allBea.get(i));
            }
        }

        for(int i = 0; i < allRac.size(); i++)
        {
            if(allRac.get(i).getFeedingTime() != null)
            {
                Rac.add(allRac.get(i));
            }
        }
        ArrayList<Coyote> allCoy = Coy;
        ArrayList<Fox> allFo = Fo;
        ArrayList<Porcupine> allPor = Por;
        ArrayList<Beaver> allBea = Bea;
        ArrayList<Raccoon> allRac = Rac;

        // sorting treatmentarray by MaxWindow

        for (int i = 0; i < treatmentarray.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < treatmentarray.size(); j++) {
                if (treatmentarray.get(j).getAnimalTask().getMaxWindow() < treatmentarray.get(minIndex).getAnimalTask().getMaxWindow()) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Treatment temp = treatmentarray.get(i);
                treatmentarray.set(i, treatmentarray.get(minIndex));
                treatmentarray.set(minIndex, temp);
            }
        }


        ArrayList<Hour> completeHours = new ArrayList<Hour>(24);
        for(int i = 0; i < 24; i++)
        {
            completeHours.add(new Hour(i));
        }

        for(int i = 0; i < treatmentarray.size(); i++){
            

            Hour currtime = completeHours.get(treatmentarray.get(i).getStartHour());
            
            if(currtime.getminsleft() >= treatmentarray.get(i).getAnimalTask().getTaskDuration())
            {   // if hour has enough time in it
                currtime.addTreatment(treatmentarray.get(i));           // add treatment to the hour and update the completeHours list
                // completeHours.add(currtime.getHour(), currtime);
                // currtime = new Hour(currtime.getHour() + 1);
            }
            else
            {
                boolean placement = false;
                
                int maxWind = treatmentarray.get(i).getAnimalTask().getMaxWindow();

                for(int j = 0; j < maxWind; j++){
                    Hour tempHour = completeHours.get(treatmentarray.get(i).getStartHour() + j);
                    if(tempHour.getminsleft() >= treatmentarray.get(i).getAnimalTask().getTaskDuration()){
                        tempHour.addTreatment(treatmentarray.get(i));
                        placement = true;
                        break;
                    }
                    
                    // TODO: add an else here
                }

                if(placement == false){
                    
                    for(int k = 0; k < maxWind; k++){
                        Hour tempHour = completeHours.get(treatmentarray.get(i).getStartHour() + k); 
                        if(!tempHour.getBackupvolunteer()){
                            tempHour.addBackup();//should add backup volunteer to the current hour on
                            tempHour.addTreatment(treatmentarray.get(i));
                            placement = true;
                            break;
                        }
                    }

                    if(placement == false){
                        // make a custom exception here
                        System.out.println("Schedule not possible");
                        this.brokenHour = currtime;
                        System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                        throw new UnavoidableOverlapException();
                        
                    }


                }


            }


        }
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add the feeding task algo for coy
        int coyNum = allCoy.size();
        if(coyNum > 0)
        {
            int coyDur = allCoy.get(0).getFeedingTime().getFeedingDuration();
            int coyPre = allCoy.get(0).getFeedingTime().getPrepTime();
            int coyStr = allCoy.get(0).getFeedingTime().getStartHour();
            int coyMax = allCoy.get(0).getFeedingTime().getMaxWindow();

        

            boolean feedPlacement = false;//keep track if the job has been assigned.
            ArrayList<Integer> aniNumbHour = new ArrayList<Integer> (coyMax);
            int feedNum = coyNum;//how many animals to feed.
            
            for(int k = 0; k < coyMax; k++)
            {
                Hour HourtoAdd = completeHours.get(coyStr + k); //get the right hour

                int timeLeft = HourtoAdd.getminsleft();
                
                if(timeLeft >= (coyPre + coyDur) && feedNum > 0)
                {
                    //timeLeft needs to be atleast preptime + one duration
                    //if coyNum is already 0, then break out of the for loop
                    //else assign as many animals for feeding as possible in the timeLeft.
                        //remember the hour you have the numb of 
                    
                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(k, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(k, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                    

                }
                else
                {
                    //if there is not enough time for prep + one duration than the number of animal feed in this hour is zero.
                    aniNumbHour.add(k, 0);
                }
            }

            if(feedPlacement)
            {
                int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                //add the feedingTask in their respective hours.
                for(int j = 0; j < aniNumbHour.size(); j++)
                {
                    if(aniNumbHour.get(j) != 0)
                    {
                        String taskDes = "";
                        for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                        {
                            String nameAni = allCoy.get(aniIndex).getName();
                            aniIndex = aniIndex + 1;//update the aniIndex
                            if(i != aniNumbHour.get(j) - 1)
                            {
                                taskDes = taskDes + nameAni + ", ";
                            } 
                            else
                            {
                                taskDes = taskDes + nameAni;
                            }
                            
                        }
                        Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                        Task newTask = new Task(0, "something", 5, 3);
                        Treatment treat = new Treatment(allCoy.get(0), newTask, coyStr + j, 0);
                        HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                    }
                }
            }
            else//feedPlacement is False, not all animals were feed.
            {
                //try with backup volunteers.

                feedNum = coyNum;//reset how many need to be feed.
                for(int i = 0; i < coyMax; i++)
                {
                    Hour HourtoAdd = completeHours.get(coyStr + i); //get the right hour
                    
                    if(!HourtoAdd.getBackupvolunteer())//add backup volunteer
                    {
                        HourtoAdd.addBackup();//should add +60 to timeLeft
                    }
                    //now check how many can be feed
                    int timeLeft = HourtoAdd.getminsleft();//time left in the HourtoAdd
                    

                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(i, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(i, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                }

                if(feedPlacement)
                {
                    int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                    //add the feedingTask in their respective hours.
                    for(int j = 0; j < aniNumbHour.size(); j++)
                    {
                        if(aniNumbHour.get(j) != 0)
                        {
                            String taskDes = "";
                            for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                            {
                                String nameAni = allCoy.get(aniIndex).getName();
                                aniIndex = aniIndex + 1;//update the aniIndex
                                if(i != aniNumbHour.get(j) - 1)
                                {
                                    taskDes = taskDes + nameAni + ", ";
                                } 
                                else
                                {
                                    taskDes = taskDes + nameAni;
                                }
                                
                            }
                            Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                            Task newTask = new Task(0, "something", 5, 3);
                            Treatment treat = new Treatment(allCoy.get(0), newTask, coyStr + j, 0);
                            HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                        }
                    }
                }
                else
                {
                    System.out.println("Schedule not possible");

                    this.brokenHour = completeHours.get(coyStr);
                    //throw exception
                    System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                    throw new UnavoidableOverlapException();
                }

            }
        }

        
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add the feeding task algo for rac
        coyNum = allRac.size();
        
        
        if(coyNum > 0)
        {
            int coyDur = allRac.get(0).getFeedingTime().getFeedingDuration();
            int coyPre = allRac.get(0).getFeedingTime().getPrepTime();
            int coyStr = allRac.get(0).getFeedingTime().getStartHour();
            int coyMax = allRac.get(0).getFeedingTime().getMaxWindow();

        

            boolean feedPlacement = false;//keep track if the job has been assigned.
            ArrayList<Integer> aniNumbHour = new ArrayList<Integer> (coyMax);
            //{6,6,6} != {5,0,0}
            int feedNum = coyNum;//how many animals to feed.
            
            for(int k = 0; k < coyMax; k++)
            {
                Hour HourtoAdd = completeHours.get(coyStr + k); //get the right hour

                int timeLeft = HourtoAdd.getminsleft();
                
                if(timeLeft >= (coyPre + coyDur) && feedNum > 0)
                {
                    //timeLeft needs to be atleast preptime + one duration
                    //if coyNum is already 0, then break out of the for loop
                    //else assign as many animals for feeding as possible in the timeLeft.
                        //remember the hour you have the numb of 
                    
                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(k, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(k, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                    

                }
                else
                {
                    //if there is not enough time for prep + one duration than the number of animal feed in this hour is zero.
                    aniNumbHour.add(k, 0);
                }
            }

            if(feedPlacement)
            {
                int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                //add the feedingTask in their respective hours.
                for(int j = 0; j < aniNumbHour.size(); j++)
                {
                    if(aniNumbHour.get(j) != 0)
                    {
                        String taskDes = "";
                        for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                        {
                            String nameAni = allRac.get(aniIndex).getName();
                            aniIndex = aniIndex + 1;//update the aniIndex
                            if(i != aniNumbHour.get(j) - 1)
                            {
                                taskDes = taskDes + nameAni + ", ";
                            } 
                            else
                            {
                                taskDes = taskDes + nameAni;
                            }
                            
                        }
                        Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                        Task newTask = new Task(0, "something", 5, 3);
                        Treatment treat = new Treatment(allCoy.get(0), newTask, coyStr + j, 0);
                        HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                    }
                }
            }
            else//feedPlacement is False, not all animals were feed.
            {
                //try with backup volunteers.

                feedNum = coyNum;//reset how many need to be feed.
                for(int i = 0; i < coyMax; i++)
                {
                    Hour HourtoAdd = completeHours.get(coyStr + i); //get the right hour
                    
                    if(!HourtoAdd.getBackupvolunteer())//add backup volunteer
                    {
                        HourtoAdd.addBackup();//should add +60 to timeLeft
                    }
                    //now check how many can be feed
                    int timeLeft = HourtoAdd.getminsleft();//time left in the HourtoAdd
                    

                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(i, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(i, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                }

                if(feedPlacement)
                {
                    int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                    //add the feedingTask in their respective hours.
                    for(int j = 0; j < aniNumbHour.size(); j++)
                    {
                        if(aniNumbHour.get(j) != 0)
                        {
                            String taskDes = "";
                            for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                            {
                                String nameAni = allRac.get(aniIndex).getName();
                                aniIndex = aniIndex + 1;//update the aniIndex
                                if(i != aniNumbHour.get(j) - 1)
                                {
                                    taskDes = taskDes + nameAni + ", ";
                                } 
                                else
                                {
                                    taskDes = taskDes + nameAni;
                                }
                                
                            }
                            Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                            Task newTask = new Task(0, "something", 5, 3);
                            Treatment treat = new Treatment(allRac.get(0), newTask, coyStr + j, 0);
                            HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                        }
                    }
                }
                else
                {
                    System.out.println("Schedule not possible");

                    this.brokenHour = completeHours.get(coyStr);
                    //throw exception
                    System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                    throw new UnavoidableOverlapException();
                }

            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add the feeding task algo for fox
        coyNum = allFo.size();


        if(coyNum > 0)
        {
            int coyDur = allFo.get(0).getFeedingTime().getFeedingDuration();
            int coyPre = allFo.get(0).getFeedingTime().getPrepTime();
            int coyStr = allFo.get(0).getFeedingTime().getStartHour();
            int coyMax = allFo.get(0).getFeedingTime().getMaxWindow();

        

            boolean feedPlacement = false;//keep track if the job has been assigned.
            ArrayList<Integer> aniNumbHour = new ArrayList<Integer> (coyMax);
            //{6,6,6} != {5,0,0}
            int feedNum = coyNum;//how many animals to feed.
            
            for(int k = 0; k < coyMax; k++)
            {
                Hour HourtoAdd = completeHours.get(coyStr + k); //get the right hour

                int timeLeft = HourtoAdd.getminsleft();
                
                if(timeLeft >= (coyPre + coyDur) && feedNum > 0)
                {
                    //timeLeft needs to be atleast preptime + one duration
                    //if coyNum is already 0, then break out of the for loop
                    //else assign as many animals for feeding as possible in the timeLeft.
                        //remember the hour you have the numb of 
                    
                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(k, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(k, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                    

                }
                else
                {
                    //if there is not enough time for prep + one duration than the number of animal feed in this hour is zero.
                    aniNumbHour.add(k, 0);
                }
            }

            if(feedPlacement)
            {
                int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                //add the feedingTask in their respective hours.
                for(int j = 0; j < aniNumbHour.size(); j++)
                {
                    if(aniNumbHour.get(j) != 0)
                    {
                        String taskDes = "";
                        for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                        {
                            String nameAni = allFo.get(aniIndex).getName();
                            aniIndex = aniIndex + 1;//update the aniIndex
                            if(i != aniNumbHour.get(j) - 1)
                            {
                                taskDes = taskDes + nameAni + ", ";
                            } 
                            else
                            {
                                taskDes = taskDes + nameAni;
                            }
                            
                        }
                        Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                        Task newTask = new Task(0, "something", 5, 3);
                        Treatment treat = new Treatment(allFo.get(0), newTask, coyStr + j, 0);
                        HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                    }
                }
            }
            else//feedPlacement is False, not all animals were feed.
            {
                //try with backup volunteers.

                feedNum = coyNum;//reset how many need to be feed.
                for(int i = 0; i < coyMax; i++)
                {
                    Hour HourtoAdd = completeHours.get(coyStr + i); //get the right hour
                    
                    if(!HourtoAdd.getBackupvolunteer())//add backup volunteer
                    {
                        HourtoAdd.addBackup();//should add +60 to timeLeft
                    }
                    //now check how many can be feed
                    int timeLeft = HourtoAdd.getminsleft();//time left in the HourtoAdd
                    

                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(i, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(i, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                }

                if(feedPlacement)
                {
                    int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                    //add the feedingTask in their respective hours.
                    for(int j = 0; j < aniNumbHour.size(); j++)
                    {
                        if(aniNumbHour.get(j) != 0)
                        {
                            String taskDes = "";
                            for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                            {
                                String nameAni = allFo.get(aniIndex).getName();
                                aniIndex = aniIndex + 1;//update the aniIndex
                                if(i != aniNumbHour.get(j) - 1)
                                {
                                    taskDes = taskDes + nameAni + ", ";
                                } 
                                else
                                {
                                    taskDes = taskDes + nameAni;
                                }
                                
                            }
                            Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                            Task newTask = new Task(0, "something", 5, 3);
                            Treatment treat = new Treatment(allFo.get(0), newTask, coyStr + j, 0);
                            HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                        }
                    }
                }
                else
                {
                    System.out.println("Schedule not possible");

                    this.brokenHour = completeHours.get(coyStr);
                    //throw exception
                    System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                    throw new UnavoidableOverlapException();
                }

            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add the feeding task algo for por
        coyNum = allPor.size();


        if(coyNum > 0)
        {
            int coyDur = allPor.get(0).getFeedingTime().getFeedingDuration();
            int coyPre = allPor.get(0).getFeedingTime().getPrepTime();
            int coyStr = allPor.get(0).getFeedingTime().getStartHour();
            int coyMax = allPor.get(0).getFeedingTime().getMaxWindow();

        

            boolean feedPlacement = false;//keep track if the job has been assigned.
            ArrayList<Integer> aniNumbHour = new ArrayList<Integer> (coyMax);
            //{6,6,6} != {5,0,0}
            int feedNum = coyNum;//how many animals to feed.
            
            for(int k = 0; k < coyMax; k++)
            {
                Hour HourtoAdd = completeHours.get(coyStr + k); //get the right hour

                int timeLeft = HourtoAdd.getminsleft();
                
                if(timeLeft >= (coyPre + coyDur) && feedNum > 0)
                {
                    //timeLeft needs to be atleast preptime + one duration
                    //if coyNum is already 0, then break out of the for loop
                    //else assign as many animals for feeding as possible in the timeLeft.
                        //remember the hour you have the numb of 
                    
                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(k, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(k, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                    

                }
                else
                {
                    //if there is not enough time for prep + one duration than the number of animal feed in this hour is zero.
                    aniNumbHour.add(k, 0);
                }
            }

            if(feedPlacement)
            {
                int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                //add the feedingTask in their respective hours.
                for(int j = 0; j < aniNumbHour.size(); j++)
                {
                    if(aniNumbHour.get(j) != 0)
                    {
                        String taskDes = "";
                        for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                        {
                            String nameAni = allPor.get(aniIndex).getName();
                            aniIndex = aniIndex + 1;//update the aniIndex
                            if(i != aniNumbHour.get(j) - 1)
                            {
                                taskDes = taskDes + nameAni + ", ";
                            } 
                            else
                            {
                                taskDes = taskDes + nameAni;
                            }
                            
                        }
                        Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                        Task newTask = new Task(0, "something", 5, 3);
                        Treatment treat = new Treatment(allPor.get(0), newTask, coyStr + j, 0);
                        HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                    }
                }
            }
            else//feedPlacement is False, not all animals were feed.
            {
                //try with backup volunteers.

                feedNum = coyNum;//reset how many need to be feed.
                for(int i = 0; i < coyMax; i++)
                {
                    Hour HourtoAdd = completeHours.get(coyStr + i); //get the right hour
                    
                    if(!HourtoAdd.getBackupvolunteer())//add backup volunteer
                    {
                        HourtoAdd.addBackup();//should add +60 to timeLeft
                    }
                    //now check how many can be feed
                    int timeLeft = HourtoAdd.getminsleft();//time left in the HourtoAdd
                    

                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(i, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(i, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                }

                if(feedPlacement)
                {
                    int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                    //add the feedingTask in their respective hours.
                    for(int j = 0; j < aniNumbHour.size(); j++)
                    {
                        if(aniNumbHour.get(j) != 0)
                        {
                            String taskDes = "";
                            for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                            {
                                String nameAni = allPor.get(aniIndex).getName();
                                aniIndex = aniIndex + 1;//update the aniIndex
                                if(i != aniNumbHour.get(j) - 1)
                                {
                                    taskDes = taskDes + nameAni + ", ";
                                } 
                                else
                                {
                                    taskDes = taskDes + nameAni;
                                }
                                
                            }
                            Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                            Task newTask = new Task(0, "something", 5, 3);
                            Treatment treat = new Treatment(allPor.get(0), newTask, coyStr + j, 0);
                            HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                        }
                    }
                }
                else
                {
                    System.out.println("Schedule not possible");

                    this.brokenHour = completeHours.get(coyStr);
                    //throw exception
                    System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                    throw new UnavoidableOverlapException();
                }

            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //add the feeding task algo for bea
        coyNum = allBea.size();


        if(coyNum > 0)
        {
            int coyDur = allBea.get(0).getFeedingTime().getFeedingDuration();
            int coyPre = allBea.get(0).getFeedingTime().getPrepTime();
            int coyStr = allBea.get(0).getFeedingTime().getStartHour();
            int coyMax = allBea.get(0).getFeedingTime().getMaxWindow();

        

            boolean feedPlacement = false;//keep track if the job has been assigned.
            ArrayList<Integer> aniNumbHour = new ArrayList<Integer> (coyMax);
            //{6,6,6} != {5,0,0}
            int feedNum = coyNum;//how many animals to feed.
            
            for(int k = 0; k < coyMax; k++)
            {
                Hour HourtoAdd = completeHours.get(coyStr + k); //get the right hour

                int timeLeft = HourtoAdd.getminsleft();
                
                if(timeLeft >= (coyPre + coyDur) && feedNum > 0)
                {
                    //timeLeft needs to be atleast preptime + one duration
                    //if coyNum is already 0, then break out of the for loop
                    //else assign as many animals for feeding as possible in the timeLeft.
                        //remember the hour you have the numb of 
                    
                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(k, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(k, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                    

                }
                else
                {
                    //if there is not enough time for prep + one duration than the number of animal feed in this hour is zero.
                    aniNumbHour.add(k, 0);
                }
            }

            if(feedPlacement)
            {
                int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                //add the feedingTask in their respective hours.
                for(int j = 0; j < aniNumbHour.size(); j++)
                {
                    if(aniNumbHour.get(j) != 0)
                    {
                        String taskDes = "";
                        for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                        {
                            String nameAni = allBea.get(aniIndex).getName();
                            aniIndex = aniIndex + 1;//update the aniIndex
                            if(i != aniNumbHour.get(j) - 1)
                            {
                                taskDes = taskDes + nameAni + ", ";
                            } 
                            else
                            {
                                taskDes = taskDes + nameAni;
                            }
                            
                        }
                        Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                        Task newTask = new Task(0, "something", 5, 3);
                        Treatment treat = new Treatment(allBea.get(0), newTask, coyStr + j, 0);
                        HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                    }
                }
            }
            else//feedPlacement is False, not all animals were feed.
            {
                //try with backup volunteers.

                feedNum = coyNum;//reset how many need to be feed.
                for(int i = 0; i < coyMax; i++)
                {
                    Hour HourtoAdd = completeHours.get(coyStr + i); //get the right hour
                    
                    if(!HourtoAdd.getBackupvolunteer())//add backup volunteer
                    {
                        HourtoAdd.addBackup();//should add +60 to timeLeft
                    }
                    //now check how many can be feed
                    int timeLeft = HourtoAdd.getminsleft();//time left in the HourtoAdd
                    

                    timeLeft = timeLeft - coyPre;
                    int aniFeed = Math.floorDiv(timeLeft, coyDur);//should give me how many animals can be feed
                    if(feedNum >= aniFeed)
                    {
                        feedNum = feedNum - aniFeed;//update how many animal need to be feed
                        aniNumbHour.add(i, aniFeed);//store how many animal need to be feed in each hour respectively.
                    }
                    else
                    { 
                        aniNumbHour.add(i, feedNum);//store how many animal need to be feed in their respective hour.
                        feedNum = 0;//no more feeding after this hour.
                        feedPlacement = true;
                    }
                }

                if(feedPlacement)
                {
                    int aniIndex = 0;//tells me which animal I am on in the animal ArrayList.
                    //add the feedingTask in their respective hours.
                    for(int j = 0; j < aniNumbHour.size(); j++)
                    {
                        if(aniNumbHour.get(j) != 0)
                        {
                            String taskDes = "";
                            for(int i = 0; i < aniNumbHour.get(j); i++)//iterate and add the animal names to taskDes
                            {
                                String nameAni = allBea.get(aniIndex).getName();
                                aniIndex = aniIndex + 1;//update the aniIndex
                                if(i != aniNumbHour.get(j) - 1)
                                {
                                    taskDes = taskDes + nameAni + ", ";
                                } 
                                else
                                {
                                    taskDes = taskDes + nameAni;
                                }
                                
                            }
                            Hour HourtoAdd = completeHours.get(coyStr + j); //get the right hour
                            Task newTask = new Task(0, "something", 5, 3);
                            Treatment treat = new Treatment(allBea.get(0), newTask, coyStr + j, 0);
                            HourtoAdd.addFeedingTask(treat, aniNumbHour.get(j), taskDes);//takes the names of animals that need to feed.
                        }
                    }
                }
                else
                {
                    System.out.println("Schedule not possible");

                    this.brokenHour = completeHours.get(coyStr);
                    //throw exception
                    System.out.println("The brokenHour hour: " + this.brokenHour.gethourStr());
                    throw new UnavoidableOverlapException();
                }

            }
        }


        //add cageCleaning for each animal. including orphanes.
        for(int i = 0; i < this.allCoy.size(); i++)
        {
            for(int j = 0; j < completeHours.size(); j++)
            {
                Hour TempHourtoAdd = completeHours.get(j);
                int cageCleanTime = this.allCoy.get(i).getCageClean();
                if(TempHourtoAdd.getminsleft() >= cageCleanTime)
                {
                    String taskStr = "Coyote Cage Clean for " + this.allCoy.get(i).getName(); 
                    Task newTask = new Task(0, taskStr, cageCleanTime, 24);
                    Treatment newTreat = new Treatment(this.allCoy.get(i), newTask, 0, 0);
                    TempHourtoAdd.addTreatment(newTreat);
                    break;
                }
            }   
        }

        for(int i = 0; i < this.allBea.size(); i++)
        {
            for(int j = 0; j < completeHours.size(); j++)
            {
                Hour TempHourtoAdd = completeHours.get(j);
                int cageCleanTime = this.allBea.get(i).getCageClean();
                if(TempHourtoAdd.getminsleft() >= cageCleanTime)
                {
                    String taskStr = "Beaver Cage Clean for " + this.allBea.get(i).getName(); 
                    Task newTask = new Task(0, taskStr, cageCleanTime, 24);
                    Treatment newTreat = new Treatment(this.allBea.get(i), newTask, 0, 0);
                    TempHourtoAdd.addTreatment(newTreat);
                    break;
                }
            }
        }

        for(int i = 0; i < this.allFo.size(); i++)
        {
            for(int j = 0; j < completeHours.size(); j++)
            {
                Hour TempHourtoAdd = completeHours.get(j);
                int cageCleanTime = this.allFo.get(i).getCageClean();
                if(TempHourtoAdd.getminsleft() >= cageCleanTime)
                {
                    String taskStr = "Fox Cage Clean for " + this.allFo.get(i).getName(); 
                    Task newTask = new Task(0, taskStr, cageCleanTime, 24);
                    Treatment newTreat = new Treatment(this.allFo.get(i), newTask, 0, 0);
                    TempHourtoAdd.addTreatment(newTreat);
                    break;
                }
            }
        }

        for(int i = 0; i < this.allRac.size(); i++)
        {
            for(int j = 0; j < completeHours.size(); j++)
            {
                Hour TempHourtoAdd = completeHours.get(j);
                int cageCleanTime = this.allRac.get(i).getCageClean();
                if(TempHourtoAdd.getminsleft() >= cageCleanTime)
                {
                    String taskStr = "Raccoon Cage Clean for " + this.allRac.get(i).getName(); 
                    Task newTask = new Task(0, taskStr, cageCleanTime, 24);
                    Treatment newTreat = new Treatment(this.allRac.get(i), newTask, 0, 0);
                    TempHourtoAdd.addTreatment(newTreat);
                    break;
                }
            }
        }

        for(int i = 0; i < this.allPor.size(); i++)
        {
            for(int j = 0; j < completeHours.size(); j++)
            {
                Hour TempHourtoAdd = completeHours.get(j);
                int cageCleanTime = this.allPor.get(i).getCageClean();
                if(TempHourtoAdd.getminsleft() >= cageCleanTime)
                {
                    String taskStr = "Porcupine Cage Clean for " + this.allPor.get(i).getName(); 
                    Task newTask = new Task(0, taskStr, cageCleanTime, 24);
                    Treatment newTreat = new Treatment(this.allPor.get(i), newTask, 0, 0);
                    TempHourtoAdd.addTreatment(newTreat);
                    break;
                }
            }
        }

        this.finalHours = completeHours;
        System.out.println("Start:");
        for(int l = 0; l < this.finalHours.size(); l++)
        {

            completeHours.get(l).getInfo();

            String data = completeHours.get(l).getInfo();
            LocalDate newDate = LocalDate.now().plusDays(1);
            System.out.println(newDate);
            if (l == 0) {
                try {
                    FileWriter writer = new FileWriter("schedule.txt");
                    data = "Schedule for " + newDate + "\n\n" + data;
                    writer.write(data);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
            else {
                try {
                    FileWriter writer = new FileWriter("schedule.txt", true);
                    writer.write(data);
                    writer.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }



    }


}
