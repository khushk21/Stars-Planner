package STARSapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Base class for the serializable classes that require saving and loading
 * @author Huang Runtao
 * @version 1.0
 * @since 2020-11-22
 */
public class Ser_File implements Serializable {
	
	/**
	 * Check if a file exists
	 * @param fName The name of the file (without the filename extension)
	 * @param store_path The location of the file
	 * @return true if the file exists; false if the file does not exist
	 */
	public static boolean isExist(String fName, String store_path) {
		  try {
			  FileInputStream fileIn = new FileInputStream(store_path + fName + ".ser");
			  fileIn.close();
			  return true;
		  } catch (IOException i) {
			  //i.printStackTrace();
			  return false;
		  } 
	}
	
	/**
	 * Load an serializable object from a file
	 * @param fName The name of the file (without the filename extension)
	 * @param store_path The location of the file
	 * @param info_type The type of object stored in the file
	 * @param what_to_use_for_filename The type of information to use as the filename
	 * @return the object after the file is deserialized
	 */
	public static Ser_File loadData(String fName, String store_path, String info_type, String what_to_use_for_filename) {
		  Scanner sc = new Scanner(System.in);
		  Ser_File o = null;
		  try {
			  FileInputStream fileIn = new FileInputStream(store_path + fName + ".ser");
			  ObjectInputStream in = new ObjectInputStream(fileIn);
			  o = (Ser_File)in.readObject();
			  in.close();
			  fileIn.close();
		  } catch (IOException i) {
			  //i.printStackTrace();
			  System.out.println(info_type + " " + fName + " does not exist!");
			  System.out.println("Please enter the " + what_to_use_for_filename + " again: \n"
						+ "(or enter 0 to cancel)");
			  fName = sc.next();
	          if (fName.equals("0")) {
	        	  return null;
	          }
	          o = loadData(fName, store_path, info_type, what_to_use_for_filename);
		  } catch (ClassNotFoundException c) {
			  //c.printStackTrace();
			  System.out.println(info_type + " " + fName + "does not exist!");
			  System.out.println("Please enter the " + what_to_use_for_filename + " again: \n"
						+ "(or enter 0 to cancel)");
			  fName = sc.next();
	          if (fName.equals("0")) {
	        	  return null;
	          }
	          o = loadData(fName, store_path, info_type, what_to_use_for_filename);
		  }
		  return o;
	}
	
	/**
	 * Load all objects of a certain type from local files
	 * (Students and Courses)
	 * @param Path The location of that certain type of files
	 * @return an ArrayList of objects
	 */
	public static ArrayList<Ser_File> loadAllData(String Path) {
		  ArrayList<Ser_File> List = new ArrayList<Ser_File>();
		  File folder = new File(Path);
		  File[] listOfFiles = folder.listFiles();

		  for (File file : listOfFiles) {
		      if (file.isFile()) {
		    	  try {
		    	  FileInputStream fileIn = new FileInputStream(Path + file.getName());
		    	  ObjectInputStream in = new ObjectInputStream(fileIn);
		    	  List.add((Ser_File)in.readObject());
		    	  in.close();
		    	  fileIn.close();
		    	  } catch (IOException i) {
		    		  i.printStackTrace();
		    		  return null;
		    	  } catch (ClassNotFoundException c) {
		    		  c.printStackTrace();
		    		  return null;
		    	  }
		      }
		  }
		  return List;
	}
	
	/**
	 * Save a serializable object as a file
	 * @param s The serializable object
	 * @param store_path The location of the file
	 * @param info_type The type of object stored in the file
	 * @param filename The name of the file (without the filename extension)
	 */
	public static void saveData(Ser_File s, String store_path, String info_type, String filename) {
		try {
			FileOutputStream fileOut = new FileOutputStream(store_path + filename + ".ser");
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(s);
			out.close();
			fileOut.close();
			System.out.println(info_type + " " + filename + " has been successfully saved");
		} catch (IOException i) {
			System.out.println("Fail to Save " + info_type + " " + filename + "!");
			i.printStackTrace();
		}
	}
	
	
}
