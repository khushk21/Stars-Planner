package STARSapp;

import java.time.LocalTime;
import java.time.LocalDate;

/**
 * Serializable object containing the access period for student users.
 * There is only one such file called "Access Period" in the folder.
 * @author Huang Runtao
 * @version 1.0
 * @since 2020-11-22
 */
public class Time extends Ser_File {
	
	/**
	 * The first day when students can start to log in to the application.
	 */
	private LocalDate start_date;
	
	/**
	 * The last day when students can to log in to the application.
	 */
	private LocalDate end_date;
	
	/**
	 * The start time for students to access the system every day.
	 */
	private LocalTime start_time;
	
	/**
	 * The end time for students to access the system every day.
	 */
	private LocalTime end_time;
	
	/**
	 * Location of "Access Period" file.
	 */
	private static String Path = "data/Time/";
	
	/**
	 * The type of object stored in the file.
	 * It is null because the filename, "Access Period", is specific enough. 
	 */
	private static String Type = "";
	
	/**
	 * The type of information to use as the filename.
	 * It is null because the filename, "Access Period", is specific enough. 
	 */
	private static String FileNameContent = "";
	
	/**
	 * The only filename for this object.
	 */
	private static String FileName = "Access Period";
	
	/**
	 * Creates a new Time object.
	 * @param start_date The first day when students can start to log in to the application.
	 * @param end_date The last day when students can start to log in to the application.
	 * @param start_time The start time for students to access the system every day.
	 * @param end_time The end time for students to access the system every day.
	 */
	public Time (LocalDate start_date, LocalDate end_date, LocalTime start_time, LocalTime end_time) {
		this.start_date = start_date;
		this.end_date = end_date;
		this.start_time = start_time;
		this.end_time = end_time;
	}
	
	/**
	 * Gets the start date.
	 * @return The start date.
	 */
	public LocalDate getSDate() { return start_date; }
	
	/**
	 * Gets the end date.
	 * @return The end date.
	 */
	public LocalDate getEDate() { return end_date; }
	
	/**
	 * Gets the start time.
	 * @return The start time.
	 */
	public LocalTime getSTime() { return start_time; }
	
	/**
	 * Gets the end time.
	 * @return The end time.
	 */
	public LocalTime getETime() { return end_time; }
	
	/**
	 * Sets the start date.
	 *@param sd The start date.
	 */
	public void setSDate(LocalDate sd) { start_date = sd; }
	
	/**
	 * Sets the end date.
	 *@param ed The end date.
	 */
	public void setEDate(LocalDate ed) { end_date = ed; }
	
	/**
	 * Sets the start time.
	 *@param st The start time.
	 */
	public void setSTime(LocalTime st) { start_time = st; }
	
	/**
	 * Sets the end time.
	 *@param et The end time.
	 */
	public void setETime(LocalTime et) { end_time = et; }
	
	/**
	 * Loads the "Access Period" file.
	 * @return A Time object. 
	 */
	public static Time loadData() {
		return (Time) Ser_File.loadData(FileName, Path, Type, FileNameContent);
	}
	
	/**
	 * Saves the time object into the "Access Period" file.
	 * @param o The Time object.
	 */
	public static void saveData(Time o) {
		Ser_File.saveData(o, Path, Type, FileName);
	}
	

}
