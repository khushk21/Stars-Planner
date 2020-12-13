package STARSapp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The base class for user accounts,
 * including Student and Admin.
 * @author Huang Runtao
 * @version 1.0
 * @since 2020-11-22
 */
public class User extends Ser_File{
	
	/**
	 * The user's hashed password.
	 */
	private String password;
	
	/**
	 * Returns the user's hashed password.
	 * @return Hashed password.
	 */
	public String getHashedPwd() {return password;}
	
	/**
	 * Sets the user's hashed password.
	 * @param p The hashed password.
	 */
	public void setHashedPwd(String p) {password = p;}
	
	/**
	 * Hashes the input string.
	 * @param p A string that has not been hashed.
	 * @return return the generated hash password
	 */
	public static String Hashing(String p) {
		String passwordToHash = p;
        String generatedPassword = null;
        try {
            // Create MessageDigest instance for MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            //Add password bytes to digest
            md.update(passwordToHash.getBytes());
            //Get the hash's bytes 
            byte[] bytes = md.digest();
            //This bytes[] has bytes in decimal format;
            //Convert it to hexadecimal format
            StringBuilder sb = new StringBuilder();
            for(int i=0; i< bytes.length ;i++)
            {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            //Get complete hashed password in hex format
            generatedPassword = sb.toString();
        } 
        catch (NoSuchAlgorithmException e) 
        {
            e.printStackTrace();
        }
		return generatedPassword;
	}
	
	/**
	 * Checks if the input string is the same as the user's password.
	 * @param p The input string.
	 * @return true they are the same; false if they are not the same.
	 */
	public boolean verifyPwd(String p) {
		String generatedPassword = Hashing(p);
		if (generatedPassword.equals(getHashedPwd())) {
			return true;
		}
		else
			return false;
	}
}
