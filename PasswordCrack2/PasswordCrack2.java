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


//ParallelJava2
import edu.rit.pj2.Loop;
import edu.rit.pj2.Task;




/**
 * Class PasswordCrack is the password cracking main program.
 */
public class PasswordCrack2 extends Task{
	
	//The number of  characters to check (a-z) (0-9)
	private static final int CHARACTERS = 36;
	
	//The maximum password length
	private static final int LENGTH = 4;
	
	//Dictionary for storing the hashes
	private Dictionary dictionary  = new Dictionary();

	/**
	 * Main program.
	 */
	public void main(final String[] args) throws Exception {
		//Insure proper amount of arguments
		if (args.length != 1) {
			System.err.println("Usage: pj2 java PasswordCrack2" 
				+ "<databaseFile>");
			//Were done here
			return;
		}
		
		
		File databaseFile = new File(args[0]);
		
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

		
		//The final size of the dictionary
		//cast it as an int no precision will be lost
		int size = (int)Math.pow(CHARACTERS, LENGTH);
		//Let the dictionary know how many hashes to expect
		dictionary.setCount(size);
		
		//Arraylist of User threads
		ArrayList<Thread> users = new ArrayList<Thread>();
		
		//Create the new counter
		Counter counter = new Counter();
		
		String line = "";
		//Parse input and start thread group 2 (users)
		try {
			while((line = reader.readLine()) !=null) {
				//Split into tokens at any whitespace
				String[] tokens = line.split("\\s+");
				//Create and run a new thread
				Thread t = new Thread(new User(tokens[0], 
					tokens[1], dictionary, counter));
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
		
		counter.setCount(users.size());

		//Start all the user threads
		for (Thread t: users) {
			t.start();
			
		}
		
		
	
		/*
			Passwords ranging from 0-z can easily be expressed as a
			base 36 number. Loops work in base 10 so the loop goes
			over each possible password by looping from 0 (0)
			to 36^4 (zzzz).
			
			In the current scenario the program will user a maxium
			of 36^4 cores.
		*/
		parallelFor(0,size).exec(new Loop() {
			public void run(int i) {
				//Express the index as a base 36 integer
				String password = base36(i);
				
				//Add the hash to the database
				dictionary.add(password, Hash.passwordHash(password));
			}
		});
		
		//Join all the threads
		for (Thread t: users) {
			t.join();
		}
	
		

	}//End of constructor
	
	
	/**
		Encode an intger in base 36
		@param i The integer to encode
		@return The String representation of the base 36 number
	**/
	private static String base36(int i) {
		return Long.toString(i, 36);
	}
	
	
	
	
	
	
}//End of PasswordCrack
