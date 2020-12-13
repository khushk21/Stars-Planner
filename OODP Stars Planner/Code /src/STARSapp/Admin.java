package STARSapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

/**
 * Admin information
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Admin extends User {
	/**
	 * Username of admin
	 */
	private String username;
	
	/**
	 * Path of stored admin file
	 */
	private static String Path = "data/AdminFile/";
	
	/**
	 * Type of file is admin
	 */
	private static String Type = "Admin";
	
	/**
	 * File name content
	 */
	private static String FileNameContent = "Username";
	
	/**
	 * Create new admin
	 * @param u username of this admin
	 * @param p password of this admin
	 */
	public Admin(String u, String p) {
		this.username = u;
		this.setHashedPwd(Admin.Hashing(p));
	}
	
	/**
	 * Get username of this admin
	 * @return username of this admin
	 */
	public String getUsername() {return username;}
	
	/**
	 * Load admin information
	 * @param name name of admin
	 * @return admin object
	 */
	public static Admin loadData(String name) {
		return (Admin) Ser_File.loadData(name, Path, Type, FileNameContent);
	}
	
	/**
	 * Save admin information
	 * @param o admin object
	 */
	public static void saveData(Admin o) {
		Ser_File.saveData(o, Path, Type, o.getUsername());
	}
	

}
