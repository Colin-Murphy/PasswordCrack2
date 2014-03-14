/**
	File: User.java	
	Designed for RIT Concepts of Paralel and Distributed Systems Project 1
	Updated for project 2
	
	@author Colin L Murphy <clm3888@rit.edu>
	@version 3/13/14
*/


/**
 * Class Group2Thread provides a thread that searches for a password hash in the
 * dictionary.
 */
import edu.rit.util.Hex;

public class User extends Thread {
	
	//The dictionary to perform lookups on
	private Dictionary dictionary;
	
	//The username
	private String user;
	
	//The password hex
	private String digestHex;
	
	//The counter object to alert when done
	private Counter counter;
	
	

	/**
	 * Construct a new User thread for the given user and password hash.
	 *
	 * @param  user        User name.
	 * @param  digestHex   Password digest as a hexadecimal string.
	 * @param  dictionary  Dictionary in which to search for passwords.
	 * @param counter      The counter object to track when to print the stats
	 */
	public User(String user, String digestHex,
		 Dictionary dictionary, Counter counter) {
		 
		 //Store all parameters
		 this.user = user;
		 this.digestHex = digestHex;
		 this.dictionary = dictionary;
		 this.counter = counter;

	}

	/**
	 * Run this User thread.
	 */
	public void run() {
		try {

			//Check the dictionary for the plain text password
			String plainText = 
				dictionary.get(Hex.toByteArray(digestHex));
			//It was found!, print it
			if (plainText != null) {
				System.out.println(user + " " +  plainText);
				//Let the counter know a password was found
				counter.foundPassword();
			}
			
			//Tell the counter that this user is done
			counter.userDone();

		}
		
		//Not responsible for handling interrupts
		catch( InterruptedException e) {};

	}

}
