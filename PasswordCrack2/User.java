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
	
	

	/**
	 * Construct a new User thread for the given user and password hash.
	 *
	 * @param  user        User name.
	 * @param  digestHex   Password digest as a hexadecimal string.
	 * @param  dictionary  Dictionary in which to search for passwords.
	 */
	public Group2Thread(int id, String user, String digestHex,
		 Dictionary dictionary, Turn turn) {
		 
		 //Store all parameters
		 this.user = user;
		 this.digestHex = digestHex;
		 this.dictionary = dictionary;

	}

	/**
	 * Run this User thread.
	 */
	public void run() {
		try {

			//Check the dictionary for the plain text password
			String plainText = 
				dictionary.get(Hex.toByteArray(digestHex));
			//Wait for this threads turn to print
			//It was found!, print it
			if (plainText != null) {
				System.out.println(user + " " +  plainText);
			}

		}
		
		//Not responsible for handling interrupts
		catch( InterruptedException e) {};

	}
}
