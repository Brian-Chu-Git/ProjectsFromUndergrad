package edu.ucalgary.oop;
/** 
* The Task class represents a task that needs to be completed with a specific duration and maximum time window.
* It contains field for the task duration, task ID, task description and maximum time window.
* @author Sahib Thethi
* @since April 2023
*/
public class Task {
    /**
	* The duration the task would take to complete.
	*/
    private int taskDuration;
    /**
	* The specific ID of the task.
	*/
	private int taskID;
    /**
	* The task description to identify what the task is.
	*/
	private String description;
    /**
	* The window in which the task needs to get completed in.
	*/
	private int maxWindow;
    /**
   * Creates a new Task with the specified task ID, description, duration and maximum time  *window.
   * @param id, The task ID.
   * @param des, The task description.
   * @param duration, The duration of the task.
   * @param maxWind, The maximum time window for the task.
   */
    public Task(int id, String des, int duration, int maxWind)
    {
        this.taskID = id;
        this.taskDuration = duration;
        this.description = des;
        this.maxWindow = maxWind;
    }
    /**
    * Sets the duration of the task.
    * @param duration The new duration of the task.
    */
    public void setTaskDuration(int duration){
        this.taskDuration = duration;
    }
    /**
    * Sets the description of the task.
    * @param des The new description of the task.
    */
    public void setDescription(String des){
        this.description = des;
    }
    /**
    * Sets the maximum time window for the task.
    * @param maxWind The new maximum time window for the task.
    */
    public void setMaxWindow(int maxWind){
        this.maxWindow = maxWind;
    }
    /**
    * Gets the description of the task.
    * @return The description of the task.
    */
    public String getDescription(){
        return this.description;
    }
/**
 * Gets the duration of the task.
 * @return The duration of the task.
 */
    public int getTaskDuration(){
        return this.taskDuration;
    }
    /**
    * Gets the maximum time window for the task.
    * @return The maximum time window for the task.
    */
    public int getMaxWindow(){
        return this.maxWindow;
    }
    /**
    * Gets the ID of the task.
    * @return The ID of the task.
    */
    public int getTaskID(){
        return this.taskID;
    }
}
