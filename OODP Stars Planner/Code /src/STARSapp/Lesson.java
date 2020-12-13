package STARSapp;

import java.io.Serializable;
import java.util.Date;
import java.time.LocalTime;
import java.time.LocalDate;

/**
 * Lesson information
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Lesson implements Serializable{
	/**
	 * day of the lesson
	 */
	int day;
	/**
	 * start time of the lesson
	 */
	LocalTime starttime;
	/**
	 * end time of the lesson
	 */
	LocalTime endtime;
	/**
	 * venue of the lesson
	 */
	String venue;
	/**
	 * index id of the lesson
	 */
	String indexid;
	
	/**
	 * Create a new lesson
	 * @param day day of this lesson
	 * @param starttime start time of this lesson
	 * @param endtime end time of this lesson
	 * @param venue venue of this lesson
	 * @param indexid index id of this lesson
	 */
	public Lesson(int day, LocalTime starttime, LocalTime endtime, String venue, String indexid) {
		this.day=day;
		this.starttime=starttime;
		this.endtime=endtime;
		this.venue=venue;
		this.indexid=indexid;
	}
	/**
	 * Get the start time of this lesson
	 * @return start time of this lesson
	 */
	public LocalTime getst() {
		return starttime;
	}
	/**
	 * Get the end time of this lesson
	 * @return end time of this lesson
	 */
	public LocalTime getet() {
		return endtime;
	}
	/**
	 * Get the venue of this lesson
	 * @return venue of this lesson
	 */
	public String getvenue()
	{
		return venue;
	}
	/**
	 * Get the index id of this lesson
	 * @return index id of this lesson
	 */
	public String getindex()
	{
		return indexid;
	}
	/**
	 * Get the day of this lesson
	 * @return day of this lesson
	 */
	public int getday()
	{
		return day;
	}
	/**
	 * Change the start time of this lesson
	 * @param starttime start time to change to
	 */
	public void setst(LocalTime starttime)
	{
		this.starttime=starttime;
	}
	/**
	 * Change the end time of this lesson
	 * @param endtime end time to change to
	 */
	public void setet(LocalTime endtime)
	{
		this.endtime=endtime;
	}
	/**
	 * Change the venue of this lesson
	 * @param venue venue to change to
	 */
	public void setvenue(String venue) {
		this.venue=venue;
	}
	/**
	 * Change index id of this lesson
	 * @param indexid index id to change to
	 */
	public void setindex(String indexid) {
		this.indexid=indexid;
	}
	/**
	 * Change the day of this lesson
	 * @param day day to change to
	 */
	public void setday(int day) {
		this.day=day;
	}

}
