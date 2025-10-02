package edu.ucalgary.oop;
/**
 * This Testing class is resposible for testing the contructors and methods of the all the classes in the program
 * All of the tests together will allow of the major functionality to be thorughly tested to make sure they work and any/all expcetions are
 * caught properly.
 * @author SahibThethi
 * @author Brian Chu
 * @version 2
 * @since April 2023
 */
import static org.junit.Assert.*;
import org.junit.*;
import java.util.regex.*;
import java.util.ArrayList;


public class Testing {


    /**
    * Test case for testing the Animal constructors and the setName method.
    */
    @Test
    public void testAnimalConstructorsAndSetName() {
        // Constructs a copy of each animal
        Coyote coyote = new Coyote("coyote", 1);
        Beaver beaver = new Beaver("beaver", 1);
        Fox fox = new Fox("fox", 1);
        Porcupine porcupine = new Porcupine("porcupine", 1);
        Raccoon raccoon = new Raccoon("raccoon", 1);
        // Tests if the names created are correct, also uses the getName function from Animal
        assertEquals("Coyote was not created with the correct name", "coyote", coyote.getName());
        assertEquals("Beaver was not created with the correct name", "beaver", beaver.getName());
        assertEquals("Fox was not created with the correct name", "fox", fox.getName());
        assertEquals("Porcupine was not created with the correct name", "porcupine", porcupine.getName());
        assertEquals("Raccoon was not created with the correct name", "raccoon", raccoon.getName());
        // Tests if the IDs created are correct, also uses the getAnimalID function from Animal
        assertEquals("Coyote was not created with the correct ID", 1, coyote.getAnimalID());
        assertEquals("Beaver was not created with the correct ID", 1, beaver.getAnimalID());
        assertEquals("Fox was not created with the correct ID", 1, fox.getAnimalID());
        assertEquals("Porcupine was not created with the correct ID", 1, porcupine.getAnimalID());
        assertEquals("Raccoon was not created with the correct ID", 1, raccoon.getAnimalID());


        coyote.setName("name");


        assertEquals("The setName function did not set to the correct name", "name", coyote.getName());
    }
   
    /**
     * This test method tests the species identification of Animal objects, It checks whether the species identification of each animal is
     * set correctly or not by using the assertEquals method to compare the actual and expected species identification strings.
     */
    @Test
    public void testGetSpecies() {
        Coyote coyote = new Coyote("coyote", 1);
        Beaver beaver = new Beaver("beaver", 1);
        Fox fox = new Fox("fox", 1);
        Porcupine porcupine = new Porcupine("porcupine", 1);
        Raccoon raccoon = new Raccoon("raccoon", 1);


        assertEquals("Coyote was not created with the correct Species identification", "coyote", coyote.getSpecies());
        assertEquals("Beaver was not created with the correct Species identification", "beaver", beaver.getSpecies());
        assertEquals("Fox was not created with the correct Species identification", "fox", fox.getSpecies());
        assertEquals("Porcupine was not created with the correct Species identification", "porcupine", porcupine.getSpecies());
        assertEquals("Raccoon was not created with the correct Species identification", "raccoon", raccoon.getSpecies());
    }
    /**
     *  This test method tests the creating of FeedingTime objects for all the possible animals and ensures that the feeding times are correctly
     * inialized with the ecpected values.
     */
    @Test
    public void testFeedingTimeCreation() {
        Coyote coyote = new Coyote("coyote", 1);
        Beaver beaver = new Beaver("beaver", 1);
        Fox fox = new Fox("fox", 1);
        Porcupine porcupine = new Porcupine("porcupine", 1);
        Raccoon raccoon = new Raccoon("raccoon", 1);


        assertEquals("Coyote was not created with the correct FeedingDuration", 5, coyote.getFeedingTime().getFeedingDuration());
        assertEquals("Coyote was not created with the correct MaxWindow", 3, coyote.getFeedingTime().getMaxWindow());
        assertEquals("Coyote was not created with the correct PrepTime", 10, coyote.getFeedingTime().getPrepTime());
        assertEquals("Coyote was not created with the correct StartHour", 19, coyote.getFeedingTime().getStartHour());


        assertEquals("Beaver was not created with the correct FeedingDuration", 5, beaver.getFeedingTime().getFeedingDuration());
        assertEquals("Beaver was not created with the correct MaxWindow", 3, beaver.getFeedingTime().getMaxWindow());
        assertEquals("Beaver was not created with the correct PrepTime", 0, beaver.getFeedingTime().getPrepTime());
        assertEquals("Beaver was not created with the correct StartHour", 8, beaver.getFeedingTime().getStartHour());


        assertEquals("Fox was not created with the correct FeedingDuration", 5, fox.getFeedingTime().getFeedingDuration());
        assertEquals("Fox was not created with the correct MaxWindow", 3, fox.getFeedingTime().getMaxWindow());
        assertEquals("Fox was not created with the correct PrepTime", 5, fox.getFeedingTime().getPrepTime());
        assertEquals("Fox was not created with the correct StartHour", 0, fox.getFeedingTime().getStartHour());


        assertEquals("Porcupine was not created with the correct FeedingDuration", 5, porcupine.getFeedingTime().getFeedingDuration());
        assertEquals("Porcupine was not created with the correct MaxWindow", 3, porcupine.getFeedingTime().getMaxWindow());
        assertEquals("Porcupine was not created with the correct PrepTime", 0, porcupine.getFeedingTime().getPrepTime());
        assertEquals("Porcupine was not created with the correct StartHour", 19, porcupine.getFeedingTime().getStartHour());
       
        assertEquals("Raccoon was not created with the correct FeedingDuration", 5, raccoon.getFeedingTime().getFeedingDuration());
        assertEquals("Raccoon was not created with the correct MaxWindow", 3, raccoon.getFeedingTime().getMaxWindow());
        assertEquals("Raccoon was not created with the correct PrepTime", 0, raccoon.getFeedingTime().getPrepTime());
        assertEquals("Raccoon was not created with the correct StartHour", 0, raccoon.getFeedingTime().getStartHour());
    }


    /**
     * This test method tests the cage cleaning duration for all the possible animals in the database. It ensures that Coyote, Beaver, Fox,
     * and Raccoon should have a cage cleaning duration of 5, while Porcupine should have a duration of 10.
     */
    @Test
    public void testCageClean() {
        Coyote coyote = new Coyote("coyote", 1);
        Beaver beaver = new Beaver("beaver", 1);
        Fox fox = new Fox("fox", 1);
        Porcupine porcupine = new Porcupine("porcupine", 1);
        Raccoon raccoon = new Raccoon("raccoon", 1);


        assertEquals("Coyote was not created with the correct Cage cleaning duration", 5, coyote.getCageClean());
        assertEquals("Beaver was not created with the correct Cage cleaning duration", 5, beaver.getCageClean());
        assertEquals("Fox was not created with the correct Cage cleaning duration", 5, fox.getCageClean());
        assertEquals("Porcupine was not created with the correct Cage cleaning duration", 10, porcupine.getCageClean());
        assertEquals("Raccoon was not created with the correct Cage cleaning duration", 5, raccoon.getCageClean());
    }


    /**
     * This test method tests the functionality of creating a kit feeding treatment for a Animal object. It will check if the kit feeding
     * removed a feeding time object through Animal and Treatment.
     */
    @Test
    public void testCreateKitFeeding() {
        Coyote coyote = new Coyote("coyote", 1);


        Treatment newTreat = new Treatment(coyote, new Task(1, "Kit feeding", 30, 2), 1, 1);


        assertEquals("Creating a treatment with Kit Feeding did not remove Feeding Time from the animal, Tested from Animal", null, coyote.getFeedingTime());
        assertEquals("Creating a treatment with Kit Feeding did not remove Feeding Time from the animal, Tested from Treatment", null, newTreat.getAnimal().getFeedingTime());
    }
    /**
     * Test the functionality of the the Task Class
     * Sets up a Task object with given properties, modifies some properties and asserts their values
     */
    @Test
    public void testingTask() {
        // create a Task object with given properties
        Task task = new Task(1, "Task 1", 5, 10);
    // test the setTaskDuration() method
        task.setTaskDuration(10);
        assertEquals("Task duration should be equal to 10 after calling setTaskDuration() method.", 10, task.getTaskDuration());
    // test the setDescription() method
        task.setDescription("Updated Task Description");
        assertEquals("Description should be equal to 'Updated Task Description' after calling setDescription() method.","Updated Task Description", task.getDescription());
    // test the setMaxWindow() method
        task.setMaxWindow(20);
        assertEquals("Max window should be equal to 20 after calling setMaxWindow() method.",20, task.getMaxWindow());
        // test the getTaskID() method
        assertEquals("Task ID should be equal to 1.", 1, task.getTaskID());
    }


/**
 * This test method tests the functionality of getAnimal() from the Treatment class.
 */
    @Test
    public void testingTreatmentGetAnimal() {
        Coyote coyote = new Coyote("Coyote", 1);
        Treatment treatment = new Treatment(coyote, new Task(0, "description", 123, 123), 0, 0);
        assertEquals("treatment.getAnimal() did not return the correct animal.", coyote, treatment.getAnimal());


    }


    /**
     * The method tests to ensure that the expected task for a given treatment is return using the getAnimalTask() method.
     */
    @Test
    public void testTreatmentGetAnimalTask() {
        Task task = new Task(0, "description", 123, 123);
        Treatment treatment = new Treatment(new Coyote("Coyote", 1), task, 0, 0);
        assertEquals("The getAnimalTask() method did not return the expected task for the given treatment.",task, treatment.getAnimalTask());
    }


    /**
     * Tests the functionality of setting and retrieving the animal task for a Treatment object.
     */
    @Test
    public void testTreatmentSetAnimalTask() {
        Task task1 = new Task(0, "description", 123, 123);
        Task task2 = new Task(1, "description", 123, 123);
        Treatment treatment = new Treatment(new Coyote("Coyote", 1), task1, 0, 0);
        assertEquals("The initial getAnimalTask() did not return the right value", task1, treatment.getAnimalTask());
        treatment.setAnimalTask(task2);
        assertEquals("The new getAnimalTask() did not return the right value",task2, treatment.getAnimalTask());
    }


    /**
     * Tests the getStartHour method  to ensure that it return the correct start hour for a given treatment.
     */
    @Test
    public void testTreatmentGetStartHour() {
        Treatment treatment = new Treatment(new Coyote("Coyote", 1), new Task(0, "description", 123, 123), 1, 0);
        assertEquals("The start hour of the treatment should be 1",1, treatment.getStartHour());
    }


    /**
     * This method tests the functionality of the setStartHour method in the Treatment class. It ensure that a new hour updates over the old hour.
     */
    @Test
    public void testTreatmentSetStartHour() {
        Treatment treatment = new Treatment(new Coyote("Coyote", 1), new Task(0, "description", 123, 123), 0, 0);
        assertEquals("Initial start hour should be 0",0, treatment.getStartHour());
        treatment.setStartHour(1);
        assertEquals("Start hour should be updated to 1",1, treatment.getStartHour());
    }

    /**
     * Test the addTreatment() in the hour class
     */
    @Test
    public void testAddTreatmentForHour() {
        Fox fox = new Fox("steve", 2);
        Hour hour = new Hour(12);
        Treatment t1 = new Treatment(fox, new Task(1, "Checkup", 30, 60), 10, 2);
        hour.addTreatment(t1);
        ArrayList<Treatment> treatments = hour.getTreatments();
        assertEquals(1, treatments.size());
        assertTrue(treatments.contains(t1));
    }

    /**
     * Tests the addFeedingTask() method to ensure that all the feeding task have been added
     */
    @Test
    public void testAddFeedingTask() {
        Coyote coyote = new Coyote("Brian", 5);
        Hour hour = new Hour(12);
        Treatment t1 = new Treatment(coyote, new Task(9, "Eyedrops", 15, 2), 12, 5);
        hour.addFeedingTask(t1, 1, "Brian");
        ArrayList<Treatment> treatments = hour.getTreatments();
        assertEquals(1, treatments.size());
        Treatment added = treatments.get(0);
        assertEquals(15, added.getAnimalTask().getTaskDuration());
        assertEquals(3, added.getAnimalTask().getMaxWindow());

    }

    /**
     * Test the getInfor() method to ensure that the correect info is displayed when creating the schedule
     */
    @Test
    public void testGetInfo() {
        Hour hour = new Hour(12);
        Raccoon raccoon = new Raccoon("John", 5);
        Task task1 = new Task(1, "Checkup", 30, 2);
        Task task2 = new Task(2, "Cage clean", 60, 3);
        Treatment t1 = new Treatment(raccoon, task1, 12, 5);
        Treatment t2 = new Treatment(raccoon, task2, 12, 5);
        hour.addTreatment(t1);
        hour.addTreatment(t2);
        String info = hour.getInfo();
        assertTrue(info.contains("12:00"));
        assertTrue(info.contains("* Checkup (John)"));
        assertTrue(info.contains("* Cage clean"));
        assertFalse(info.contains("* Feeding"));
    }

    /**
     * Testing the addBackup() to ensure that backup volunteer have been added at the correct time
     */
    @Test
    public void testAddBackup() {
        Hour hour = new Hour(12);
        Beaver beaver = new Beaver("Mike", 5);
        Treatment t1 = new Treatment(beaver, new Task(1, "Checkup", 30, 2), 10, 5);
        Treatment t2 = new Treatment(beaver, new Task(2, "Cage clean", 60, 3), 11, 5);
        hour.addTreatment(t1);
        hour.addTreatment(t2);
        try {
            hour.addBackup();
            fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException e) {
            assertEquals("Not enough time present within the hour", e.getMessage());
        }
        assertEquals(false, hour.getBackupvolunteer());
        assertTrue(hour.getminsleft() < 60);
        hour.getTreatments().clear();
        hour.addTreatment(t1);
        hour.addBackup();
        assertEquals(0, hour.getminsleft());
        assertTrue(hour.getBackupvolunteer());
    }

    private Hour hour;
    private Treatment treatment3;
    private Treatment treatment4;
    private Treatment treatment5;
    
    /**
     * This method sets up the initial state for the test by creating instances of Porcupine, creating instances of Treatment for each
     * animal, and all the treatments into the hour class. This method is annotated with @Before, which means that it will be executed
     * before each test method.
     */
    @Before
    public void setUp2() {
        hour = new Hour(10);
        Porcupine porc = new Porcupine("Ellis", 4);
        Porcupine porc2 = new Porcupine("Ellis Jr", 5);
        Porcupine porc3 = new Porcupine("Ellis Sr", 6);
        treatment3 = new Treatment(porc, new Task(1, "Cage cleaning", 30, 2), 10, 4);
        treatment4 = new Treatment(porc2, new Task(2, "Feeding", 45, 3),11,5);
        treatment5 = new Treatment(porc3, new Task(3, "Health check", 15, 1),12,6);
        hour.addTreatment(treatment3);
        hour.addTreatment(treatment4);
        hour.addTreatment(treatment5);
    }
    
    /**
    *This test method checks if the number of treatments at a particular hour is equal to the expected value.
    */
    @Test
    public void testGetHourTreatmentsSize() {
        assertEquals(3, hour.getHourTreatmentsSize());
    }
    /**
    *This test method checks if the hour value of a particular hour object is equal to the expected value.
    */
    @Test
    public void testGetHour() {
        assertEquals(10, hour.getHour());
    }
    /**
    *This test method checks if the hour string representation of a particular hour object is equal to the expected value.
    */
    @Test
    public void testGethourStr() {
        assertEquals("10:00", hour.gethourStr());
    }
    /**
    This test method checks if the minutes left for treatments at a particular hour object is equal to the expected value.
    */
    @Test
    public void testGetminsleft() {
        assertEquals(60 - 30 - 45 - 15, hour.getminsleft());
    }
    /**
    *This test method checks if the backup volunteer status of a particular hour object is false as expected.
    */
    @Test
    public void testGetBackupvolunteer() {
        assertFalse(hour.getBackupvolunteer());
    }
    /**
    *This test method checks if a particular hour object's treatments list contains expected treatment objects.
    */
    @Test
    public void testGetTreatments() {
        assertTrue(hour.getTreatments().contains(treatment3));
        assertTrue(hour.getTreatments().contains(treatment4));
        assertTrue(hour.getTreatments().contains(treatment5));
    }

    //Creating global objects to be used in the methods below
    private ArrayList<Treatment> treatments;
    private ArrayList<Coyote>allCoy;
    private ArrayList<Fox>allFo;
    private ArrayList<Porcupine>allPor;
    private ArrayList<Beaver>allBea;
    private ArrayList<Raccoon>allRac;
    private Treatment treatment1;
    private Treatment treatment2;
    private Scheduler scheduler;

    /**
     * This method sets up the initial state for the test by creating instances of Coyote and Fox, creating instances of Treatment for each
     * animal, and initializing the scheduler with treatment1. This method is annotated with @Before, which means that it will be executed
     * before each test method.
     */
     @Before
    public void setUp() {
         Coyote coyote = new Coyote("oscar", 1);
         Fox fox = new Fox("adam", 2);
         treatments = new ArrayList<Treatment>();
         treatment1 = new Treatment(coyote, new Task(2, "Rebandage leg wound", 20, 1), 10, 1);
         treatment2 = new Treatment(fox, new Task(9, "Eyedrops", 35 , 1), 11, 2);
         scheduler = new Scheduler(treatments, allCoy, allFo, allPor, allBea, allRac);
     }
    // /**
    //  * Tests the addition of a new treatment object to the scheduler's list of treatments.
    //  */
     @Test
     public void testAddTreatment() {
         scheduler.addTreatment(treatment2);
         ArrayList<Treatment> treatments = scheduler.getTreatments();
         assertEquals("The treatment.size() did not return the correct value",1, treatments.size());
         assertTrue("The list of treatments should contain the newly added treatment2",treatments.contains(treatment2));
    }
    /**
     * Tests the getTreatments() method and ensures that the right object gets returned  
     */
    @Test
    public void testGetTreatment(){
        scheduler.addTreatment(treatment1);
        assertEquals("The getTreatments() did not return the correct treatment object", treatment1, scheduler.getTreatments().get(0));
    }
<<<<<<< HEAD
=======


    @Test
    public void testHourObjectOfScheduler()
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

        assertEquals(0, newHour.getminsleft());

        newHour.getInfo();
        // newHour.addBackup();

        // assertEquals(50, newHour.getminsleft());

        String expStr = "2:00\n* Kit Feeding (One, Two, and Three)\n* Feeding - coyote (1: Feeding - coyote (1: Coyote1))\n* Feeding - beaver (1: Feeding - beaver (1: Beaver1))\n* Feeding - porcupine (1: Feeding - porcupine (1: Por1))\n* Feeding - raccoon (1: Feeding - raccoon (1: Rac1))\n\n";
        assertEquals(expStr, newHour.getInfo());
    }
    
>>>>>>> 3f66b7b819c51cf39f77363d8481c5ab6e363fd2
   
}













