package STARSapp;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Student information
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Student extends User {
	
	/**
	 * Name of school student is enrolled in
	 */
	String schoolId;
	
	/**
	 * Name of student
	 */
	String name;
	
	/**
	 * Matriculation number of student
	 */
	String matric;
	
    /**
     * Date of birth of student
     */
    LocalDate dob;
    
	/**
	 * Gender of student
	 */
	char gender;
	
	/**
	 * Nationality of student
	 */
	String nationality;
	
	/**
	 * Email address of student
	 */
	String email;
	
	/**
	 * Total academic unit (AU) of student
	 */
	int AU;
	
	/**
	 * The maximum number of AU a student could register without overloading
	 */
	private static int MAX_AU = 23;
	
	/**
	 * Location of student file
	 */
	private static String Path = "data/StudentFile/";
	
	/**
	 * Type of user
	 */
	private static String Type = "Student";
	
	/**
	 * File name content
	 */
	private static String FileNameContent = "Matric Number";
	
	/**
	 * Creates a new student
	 * @param schoolId Name of school this student is enrolled in
	 * @param name Name of this student
	 * @param matricno Matriculation number of this student
	 * @param localDate Date of birth of this student
	 * @param gender Gender of this student
	 * @param nationality Nationality of this student
	 * @param p Password of this student
	 * @param email email of this student
	 */
	public Student(String schoolId,String name,String matricno,LocalDate localDate,
			char gender,String nationality,String p,String email) {
		this.schoolId=schoolId;
		this.dob=localDate;
		this.name=name;
		this.gender=gender;
		this.matric=matricno;
		this.nationality=nationality;
		this.setHashedPwd(Student.Hashing(p));
		this.email = email;
		this.AU = 0;
	}
	
	/**
	 * Retrieve the maximum number of AU
	 * @return the maximum number of AU
	 */
	public static int getMAX_AU() {return MAX_AU;}
	
	/**
	 * Get the email address of this student 
	 * @return email address
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * Get name of school this student is enrolled in
	 * @return name of school this student is enrolled in
	 */
	public String getSchId() {
		return schoolId;
	}
	/**
	 * Get name of this student
	 * @return name of this student
	 */
	public String getName() {
		return name;
	}
	/**
	 * Get matriculation number of this student
	 * @return Matriculation number of this student
	 */
	public String getMatric() {
		return matric;
	}
	/**
	 * Get date of birth of this student
	 * @return Date of birth of this student
	 */
	public LocalDate getDob() {
		return dob;
	}
	/**
	 * Get gender of this student
	 * @return gender of this student
	 */
	public char getGender() {
		return gender;
	}
	/**
	 * Get nationality of this student
	 * @return nationality of this student
	 */
	public String getNational() {
		return nationality;
	}
	/**
	 * Change the name of school this student is enrolled in
	 * @param schoolId school name to change to
	 */
	public void setSchId(String schoolId) {
		this.schoolId=schoolId;
	}
	/**
	 * Change the name of this student
	 * @param name student name to change to
	 */
	public void setName(String name) {
		this.name=name;
	}
	/**
	 * Change the matriculation number of this student
	 * @param matricno matriculation number to change to
	 */
	public void setMatricno(String matricno) {
		this.matric=matricno;
	}
	/**
	 * Change the date of birth of this student
	 * @param dob date of birth to change to
	 */
	public void setDob(LocalDate dob) {
		this.dob=dob;
	}
	/**
	 * Change the gender of this student
	 * @param gender gender to change to
	 */
	public void setGender(char gender) {
		this.gender=gender;
	}
	/**
	 * Change the nationality of this student
	 * @param nationality nationality to change to
	 */
	public void setNational(String nationality) {
		this.nationality=nationality;
	}
	/**
	 * Increment the AU of this student
	 * @param au number of Academic unit to increase by
	 */
	public void addAU(int au)
	{
		this.AU += au;
	}
	/**
	 * Decrement the AU of this student
	 * @param au number of Academic unit to decrease by
	 */
	public void removeAU(int au)
	{
		this.AU -= au;
	}
	/**
	 * Get the AU of this student
	 * @return AU of this student
	 */
	public int getAU()
	{
		return AU;
	}
	
	/**
	 * Load student information from serializable file
	 * @param matric matriculation number of student 
	 * @return student object
	 */
	public static Student loadData(String matric) {
		return (Student) Ser_File.loadData(matric, Path, Type, FileNameContent);
	}
	
	/**
	 * Load all student objects from local files
	 * @return an ArrayList of student objects
	 */
	public static ArrayList<Student> loadAllData() {
		
		ArrayList<Ser_File> List = Ser_File.loadAllData(Path);
		ArrayList<Student> stuList = new ArrayList<Student>();
		for (Ser_File o : List) {
			stuList.add((Student)o);
		}
		return stuList;
	}
	
	/**
	 * Save student information to serializable file
	 * @param o student object
	 */
	public static void saveData(Student o) {
		Ser_File.saveData(o, Path, Type, o.getMatric());
	}
	
	/**
	 * Check if a student exist in database
	 * @param matric matriculation number of student
	 * @return true if student exist or false if student does not exist
	 */
	public static boolean isExist(String matric) {
		return Ser_File.isExist(matric, Path);
	}

}
