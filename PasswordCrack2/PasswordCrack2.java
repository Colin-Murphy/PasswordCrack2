/**
	File: PasswordCrack.java	
	Designed for RIT Concepts of Paralel and Distributed Systems Project 1
	
	@author Colin L Murphy <clm3888@rit.edu>
	@version 3/5/14
*/


//Read files
import java.io.File;
//Arraylist for storing users and hashed passwords
import java.util.ArrayList;
//Buffered reader
import java.io.BufferedReader;
//File reader
import java.io.FileReader;
//Io exception
import java.io.IOException;
//File not found
import java.io.FileNotFoundException;




/**
 * Class PasswordCrack is the password cracking main program.
 */
public class PasswordCrack2 extends Task{
	
	//The number of  characters to check (a-z) (0-9)
	private static final int CHARACTERS = 36
	
	//The maximum password length
	private static final int LENGTH = 4;

	/**
	 * Main program.
	 */
	public static void main(String[] args) throws Exception {
		//Insure proper amount of arguments
		if (args.length != 1) {
			System.err.println("Usage: java PasswordCrack2" 
				\+ "<databaseFile>");
			//Were done here
			return;
		}
		
		File dictionaryFile = new File(args[0]);
		
		//Reader for reading the input file
		BufferedReader reader;

		//Read the database
		try {
			reader = new BufferedReader
				(new FileReader(databaseFile));
		}
		
		//File not found, tell the user and give up
		catch(FileNotFoundException e) {
			System.err.println("File " + databaseFile.getName() + 
				" does not exist");
			System.exit (1);
			//End the program
			return;
		}

		//Dictionary for storing the hashes
		Dictionary dictionary  = new Dictionary();
		
		//The final size of the dictionary
		int size = Math.pow(CHARACTERS, LENGTH)
		//Let the dictionary know how many hashes to expect
		dictionary.setCount(size);
		
		//Arraylist of User threads
		ArrayList<Thread> users = new ArrayList<Thread>();
		//Parse input and start thread group 2 (users)
		try {
			while((line = reader.readLine()) !=null) {
				//Split into tokens at any whitespace
				String[] tokens = line.split("\\s+");
				//Create and run a new thread
				Thread t = new Thread(new User(tokens[0], dictionary));
				//Start the thread
				t.start();
				//Add the thread to the group
				users.add(t);

				
			}
		}
		
		//Something went wrong, give up
		catch(IOException e) {
			System.err.println("Error reading " + 
				databaseFile.getName());
			System.exit (1);
		}
		
		
		//Join all the threads
		for (Thread t: users) {
			t.join();
		}
	
	/*
		Passwords ranging from 0-z can easily be expressed as an integer
		ranging from 0 to 36 and then converted to the text as base 36 numbers.
		The loop goes over each possible password by looping from 0 (0)
		to 36^4 (zzzz)
	*/

		

	}//End of constructor
}//End of PasswordCrack
