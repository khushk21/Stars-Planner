package STARSapp;

import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Course information
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Course extends Ser_File{
	/**
	 * Name of course
	 */
	String name;
	/**
	 * Unique course id
	 */
	String courseId;
	/**
	 * School name that course is in
	 */
	String school;
	/**
	 * Academic unit of course
	 */
	int AU;
	
	/**
	 * List of index information in a course
	 */
	ArrayList<Index> index;	
	
	/**
	 * Path course info is stored in
	 */
	private static String Path = "data/CourseInfo/";
	
	/**
	 * Type of file is course
	 */
	private static String Type = "Course";
	
	/**
	 * File name content
	 */
	private static String FileNameContent = "Course Code";
	
	/**
	 * Create a new course
	 * @param name name of this course
	 * @param courseId course id of this course
	 * @param school school name this course is in
	 * @param au Number of academic units of this course
	 * @param index List of index information of this course
	 */
	public Course(String name, String courseId, String school, int au, ArrayList<Index> index) {
		this.name=name;
		this.courseId=courseId;
		this.school=school;
		this.index=index;
		this.AU = au;
	}
	/**
	 * Get name of this course
	 * @return name of this course
	 */
	public String getName() {
		return name;
	}
	/**
	 * Get course id of this course
	 * @return course id of this course
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * Get school name this course is from
	 * @return school name this course is from
	 */
	public String getSchool() {
		return school;
	}
	/**
	 * Get number of academic units of this course
	 * @return number of academic units of this course
	 */
	public int getAU()
	{
		return AU;
	}
	/**
	 * Get list of index information of this course
	 * @return list of index information of this course
	 */
	public ArrayList<Index> getIndex() {
		return index;
	}
	/**
	 * Change name of this course
	 * @param name name to change to
	 */
	public void setName(String name) {
		this.name=name;
	}
	/**
	 * Change course id of this course
	 * @param courseId course id to change to
	 */
	public void setCourseId(String courseId) {
		this.courseId=courseId;
	}
	
	/**
	 * Change list of index information of this course
	 * @param index list of index information to change to
	 */
	public void setIndex(ArrayList<Index>index) {
		this.index=index;
	}
	
	/**
	 * Add a new index to the course
	 * @param i The new Index object to be added
	 */
	public void addIndex(Index i) {
		this.index.add(i);
	}
	
	/**
	 * Get the path the course file is stored in
	 * @return path the course file is stored in
	 */
	public static String getPath() { 
		return Path; 
	}
	
	/**
	 * Delete course file
	 * @param courseId course id to delete course file
	 * @return 1 if deleted or 0 not deleted
	 */
	public static int deleteCourseFile(String courseId) {
		File courseFile = new File(getPath() + courseId + ".ser"); 
	    if (courseFile.delete()) { 
	      return 1;
	    } else {
	      return 0;
	    } 
	}
	
	/**
	 * Load course file
	 * @param courseId course id to load course file
	 * @return course object
	 */
	public static Course loadData(String courseId) {
		return (Course) Ser_File.loadData(courseId, Path, Type, FileNameContent);
	}
	
	/**
	 * Save course file
	 * @param o course object to save to course file
	 */
	public static void saveData(Course o) {
		Ser_File.saveData(o, Path, Type, o.getCourseId());
	}
	
	/**
	 * Check if a course id exists
	 * @param courseId course id to check if this course exists
	 * @return true if course id exist and false if course id does not exist
	 */
	public static boolean isExist(String courseId) {
		return Ser_File.isExist(courseId, Path);
	}
	
	/**
	 * Load all course objects from local files
	 * @return an ArrayList of course objects
	 */
	public static ArrayList<Course> loadAllData() {
		
		ArrayList<Ser_File> List = Ser_File.loadAllData(Path);
		ArrayList<Course> courseList = new ArrayList<Course>();
		for (Ser_File o : List) {
			courseList.add((Course)o);
		}
		return courseList;
	}
}
