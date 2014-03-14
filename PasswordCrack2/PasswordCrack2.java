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
import edu.rit.pj2.LongLoop;
import edu.rit.pj2.Task;
import edu.rit.pj2.LongVbl;

//Hex Class
import edu.rit.util.Hex;

//Users are contained in a hashmap
import java.util.*;




/**
 * Class PasswordCrack is the password cracking main program.
 */
public class PasswordCrack2 extends Task{
	
	//The number of  characters to check (a-z) (0-9)
	private static final int CHARACTERS = 36;
	
	//The maximum password length
	private static final int LENGTH = 4;
	
	//Hashmap to contain the users and their hashed passwords
	private HashMap<String, String> users = new HashMap<String, String>();
	
	//Count of users that found their password
   	private LongVbl count;
   	
   	//total amount of passwords to crack
   	private long size;
	

	/**
	 * Main program.
	 */
	public void main(final String[] args) throws Exception {
		//Insure proper amount of arguments
		if (args.length != 1) {
			System.err.println("Usage: java pj2 PasswordCrack2" 
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
		//cast it as a long no precision will be lost
		size = (long)Math.pow(CHARACTERS, LENGTH);

		
		
		String line = "";
		//Parse input and start thread group 2 (users)
		try {
			while((line = reader.readLine()) !=null) {
				//Split into tokens at any whitespace
				String[] tokens = line.split("\\s+");
				
				//Add this password and use to the hashmap
				users.put(tokens[1], tokens[0]);

				
			}
		}
		
		
		//Something went wrong, give up
		catch(IOException e) {
			System.err.println("Error reading " + 
				databaseFile.getName());
			System.exit (1);
		}
		
		
		
	
		/*
			Passwords ranging from 0-z can easily be expressed as a
			base 36 number. Loops work in base 10 so the loop goes
			over each possible password by looping from 0 (0)
			to 36^4 (zzzz).
			
			In the current scenario the program will user a maxium
			of 36^4 cores.
		*/
		count = new LongVbl.Sum(0);
		parallelFor(0,size).exec(new LongLoop() {
			LongVbl localCount;
			
			public void start() {
				localCount = threadLocal(count);
			}
			
			public void run(long i) {
				//Plain text password
				String password = base36(i);
				//Get the password hash of the index in base 36 as a string
				String hex = Hex.toString(Hash.passwordHash(password));
				
				//If this hash exists then print it
				if (users.containsKey(hex)) {
					System.out.println(users.get(hex) + " " + password);
					localCount.item++;
				}
			}
		});
		
		//Print the statistics
		System.out.println(users.size() + " users");
		System.out.println(count.item + " passwords found");
		
		

	}//End of constructor
	
	
	/**
		Encode an intger in base 36
		@param i The long to encode
		@return The String representation of the base 36 number
	**/
	private static String base36(long i) {
		return Long.toString(i, 36);
	}
	
	
	
	
	
	
}//End of PasswordCrack
