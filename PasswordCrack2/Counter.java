/**
	File: Counter.java	
	Designed for RIT Concepts of Paralel and Distributed Systems Project 2
	
	@author Colin L Murphy <clm3888@rit.edu>
	@version 3/13/14
*/



/**
 * Counter tracks the success of User threads and prints the final statistics
 * Counter also makes user of synchronized methods in its critical sections
 */
 
public class Counter {

	//The number of Users that found their password
	private int foundCount = 0;
	
	//The total number of users that will be waited on
	private int totalCount = 0;
	
	//The number of users that have finished
	private int finishedCount = 0;

	/**
	 * Construct a new Counter object.
	 */
	public Counter() {}

	/**
	 * Called when a User finds its password, increments the counter so that
	 * the final count will be accurate
	 */
	public synchronized void foundPassword() {
		foundCount++;
	}

	/**
	 * Lets the counter know a user is done, prints the stats if this is the
	 * last user.
	 */
	public synchronized void userDone() {
		finishedCount++;
		
		if (finishedCount == totalCount) {
			printStats();
		}

	}
	
	/**
		Print the stats for the password crack
		Only called when done, no syncronization needed
	**/
	private void printStats() {
		System.out.println(totalCount + " users");
		System.out.println(foundCount + " passwords found");
	
	
	}
	
	/**
		Set the total number of user threads running
		@param totalCount The total number of users
	**/
	public void setCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	
	
	
}
