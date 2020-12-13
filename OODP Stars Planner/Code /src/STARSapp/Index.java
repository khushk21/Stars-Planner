package STARSapp;

import java.util.*;
import java.io.File;
import java.io.Serializable;

/**
 * Index information
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Index implements Serializable {
	/**
	 * unique index id
	 */
	String indexid;
	/**
	 * course id that index is from
	 */
	String courseId;
	/**
	 * vacancy of index
	 */
	int vacancy;
	/**
	 * student matriculation number that are enrolled in this index
	 */
	ArrayList<String> student;
	/**
	 * student matriculation number that are in the waiting list of this index
	 */
	ArrayList<String> waitinglist;
	/**
	 * list of lecture information
	 */
	ArrayList<Lesson> lectureList;
	/**
	 * lab information
	 */
	Lesson lab;
	/**
	 * tutorial information
	 */
	Lesson tutorial;
	
	/**
	 * Create a new index
	 * @param indexid index id of this index
	 * @param courseId course id of this index
	 * @param vacancy vacancy of this index
	 */
	public Index(String indexid, String courseId, int vacancy) {
		this.indexid=indexid;
		this.courseId=courseId;
		this.vacancy=vacancy;
		student=new ArrayList<String>();
		waitinglist=new ArrayList<String>();
		lectureList=new ArrayList<Lesson>();
		lab=null;
		tutorial=null;
	}
	
	/**
	 * Get the index id of this index
	 * @return index id of this index
	 */
	public String getIndexid() {
		return indexid;
	}
	/**
	 * Get the course id of this index
	 * @return course id of this index
	 */
	public String getCourseId() {
		return courseId;
	}
	/**
	 * Get the vacancy of this index
	 * @return vacancy of this index
	 */
	public int getVacancy() {
		return vacancy;
	}
	/**
	 * Get the student list of this index
	 * @return student list of this index
	 */
	public ArrayList<String> getStu(){
		return student;
	}
	/**
	 * Get the waiting list of this index
	 * @return waiting list of this index
	 */
	public ArrayList<String> getWait(){
		return waitinglist;
	}
	/**
	 * Get the list of lecture information of this index
	 * @return list of lecture information of this index
	 */
	public ArrayList<Lesson> getLectures(){
		return lectureList;
	}
	/**
	 * Get the lab information of this index
	 * @return lab information of this index
	 */
	public Lesson getLab(){
		return lab;
	}
	/**
	 * Get the tutorial information of this index
	 * @return tutorial information of this index
	 */
	public Lesson getTut(){
		return tutorial;
	}
	
	/**
	 * Change the index id of this index
	 * @param indexid index id to change to
	 */
	public void setIndexid(String indexid) {
		this.indexid=indexid;
	}
	/**
	 * Change the course id of this index
	 * @param courseId course id to change to
	 */
	public void setCourseId(String courseId) {
		this.courseId=courseId;
	}
	/**
	 * Change the vacancy of this index
	 * @param vacancy vacancy to change to
	 */
	public void setVacancy(int vacancy) {
		this.vacancy=vacancy;
	}
	/**
	 * Change the list of students of this index
	 * @param student list of students to change to
	 */
	public void setStu(ArrayList<String>student) {
		this.student=student;
	}
	/**
	 * Register student to this index
	 * @param matric student matriculation number to add to this index
	 */
	public void regStu(String matric) {
		this.student.add(matric);
	}
	/**
	 * Remove student from this index
	 * @param matric student matriculation number to remove from this index
	 */
	public void removeStu(String matric) {
		this.student.remove(matric);
	}
	/**
	 * Add student to waiting list of this index
	 * @param matric student matriculation number to add to waiting list of this index
	 */
	public void addStu_waitlist(String matric) {
		this.waitinglist.add(matric);
	}
	/**
	 * Remove and return head of waiting list of this index
	 * @return student matriculation number from head of waiting list of this index
	 */
	public String popStu_waitlist() {
		String temp = this.waitinglist.get(0);
		this.waitinglist.remove(0);
		return temp;
	}
	/**
	 * Change the waiting list of this index
	 * @param waitinglist waiting list to change to
	 */
	public void setWait(ArrayList<String>waitinglist) {
		this.waitinglist=waitinglist;
	}
	/**
	 * Add lecture to this index
	 * @param lec lecture information to add to this index
	 */
	public void addLecture(Lesson lec){
		this.lectureList.add(lec);
	}
	/**
	 * Change lecture information of this index
	 * @param index index of lecture to change
	 * @param lec lecture information to change to
	 */
	public void setLectures(int index, Lesson lec){
		this.lectureList.set(index, lec);
	}
	/**
	 * Change the lab information of this index
	 * @param lab lab information to change to
	 */
	public void setLab(Lesson lab){
		this.lab=lab;
	}
	/**
	 * Change the tutorial information of this index
	 * @param tut tutorial information to change to
	 */
	public void setTut(Lesson tut){
		this.tutorial=tut;
	}
	
	/**
	 * Check if a index id exists
	 * @param indexId index id to check
	 * @return true if index id exist or false if index id does not exist
	 */
	public static boolean isExist(String indexId) {
		File folder = new File(Course.getPath());
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
		      if (file.isFile()) {
		    	  Course c = Course.loadData(file.getName().split(".ser")[0]);
	    		  for (Index i : c.getIndex()) {
	    			  if (indexId.equals(i.getIndexid())) {
	    				 return true; 
	    			  }
	    		  }
		      }
		 }
		 return false;
	}
	
	/**
	 * Load index information
	 * @param indexId index id to load information
	 * @return index information
	 */
	public static Index loadData(String indexId) {
		Scanner sc = new Scanner(System.in);
		File folder = new File(Course.getPath());
		File[] listOfFiles = folder.listFiles();
		for (File file : listOfFiles) {
		      if (file.isFile()) {
		    	  Course c = Course.loadData(file.getName().split(".ser")[0]);
	    		  for (Index i : c.getIndex()) {
	    			  if (indexId.equals(i.getIndexid())) {
	    				 return i; 
	    			  }
	    		  }
		      }
		 }
		 System.out.println("Index " + indexId + " does not exist!");
		 System.out.println("Please enter the Index again: \n"
				 		+ "(or enter 0 to cancel)");
		 indexId = sc.next();
         if (indexId.charAt(0) == '0') {
        	 return null;
         }
         return loadData(indexId);
	}
	
	
}