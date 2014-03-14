import java.security.MessageDigest;

/**
 * Class Hash provides a static method for computing the digest of a password.
 * Provided for Concepts of Parallel and Distributed Systems project 1
 * Edit by Colin L Murphy <clm3888@rit.edu> for COPADS Project 2
 * @version 3/13/14
 */
public class Hash {
	/**
	 * Compute the digest of the given password.
	 *
	 * @param  password  Password.
	 *
	 * @return  Digest.
	 */
	public static byte[] passwordHash(String password) {
		try {
			MessageDigest md = MessageDigest.getInstance ("SHA-256");
			byte[] data = password.getBytes ("UTF-8");
			md.update (data);
			data = md.digest();
			return data;
		}
		catch (Throwable exc) {
			throw new IllegalStateException ("Shouldn't happen", exc);
		}
	}
}
