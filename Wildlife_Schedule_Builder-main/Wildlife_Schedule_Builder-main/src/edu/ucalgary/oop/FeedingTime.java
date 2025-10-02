package edu.ucalgary.oop;
/**
*The FeedingTime class represents a time period for feeding an animal. It contains 
*information about the feeding duration, preparation time, start and maximum time for *feeding. *The class includes a constructor for creating the object t be represented in the *database and *methods for getting and setting each object’s properties.
* @author Sahib Thethi
* @author Brian Chu
* @since April 2023
*/
public class FeedingTime {
	/**
	* The duration of the animal is fed for.
	*/
	private int feedingDuration;
	/**
	* The amount of time it takes to prepares the animal’s food.
	*/
	private int prepTime;
	/**
	* The hour in which the feeding time begins.
	*/
	private int startHour;
	/**
	* The maximum time window during which the animal can be fed private int maxWindow;
	*/
	private int maxWindow;
	/**
	* Creates a new FeedingTime object with the same parameters as the variables definied *in this class
	* @param duration, The duration of the animal is fed for. 
	* @param prep, The amount of time it takes to prepares the animal’s food.
	* @param start, The hour in which the feeding time begins.
	* @param maxWind, The maximum time window during which the animal can be fed.
	*/ 
	public FeedingTime(int duration, int prep, int start, int maxWind) {
		this.feedingDuration = duration;
		this.prepTime = prep;
		this.startHour = start;
		this.maxWindow = maxWind;
	}
	/**
	* Getter method the get the current value of feeding duration
	* @return the feeding duration
	*/
	public int getFeedingDuration() {
		return this.feedingDuration;
	}
	/**
	* Setter method the set a new value for feeding duration
	* @param duration, the new feeding duration value
	*/
	public void setFeedingDuration(int duration) {
		this.feedingDuration = duration;
	}
	/**
	* Getter method the get the current value of preparation time
	* @return the preparation time
	*/
	public int getPrepTime() {
		return this.prepTime;
	}
	/**
	* Setter method the set a new value for preparation time
	* @param prep, the new prep time value
	*/
	public void setPrepTime(int prep) {
		this.prepTime = prep;
	}
	/**
	* Getter method the get the current value of the start hour
	* @return the start hour
	*/
	public int getStartHour() {
		return this.startHour;
	}
	/**
	* Setter method the set a new value for the start hour
	* @param startHour, the new start hour value
	*/
	public void setStartHour(int startHour) {
		this.startHour = startHour;
	}
	/**
	* Getter method the get the current value of max window
	* @return the max window
	*/
	public int getMaxWindow() {
		return this.maxWindow;
	}
	/**
	* Setter method the set a new value for max window
	* @param maxWindow, the new max window value
	*/
	public void setMaxWindow(int maxWindow) {
		this.maxWindow = maxWindow;
	}
}
