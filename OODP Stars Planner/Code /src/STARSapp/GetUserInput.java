package STARSapp;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

/**
 * Class containing functions to get error-free user input,
 * including integer, weekday, LocalDate and LocalTime.
 * @author Huang Runtao
 * @version 1.0
 * @since 2020-11-22
 */
public class GetUserInput {
	
	/**
	 * Scanner for getting user input.
	 */
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Get an error-free integer.
	 * @return an error-free integer.
	 */
	public int inputInteger() {
		Scanner sc = new Scanner(System.in);
		int valid = 0;
		int output = 0;
		while (valid == 0) {
			try{
				output = sc.nextInt();
		        valid = 1;
		    }
			catch(Exception e){
		        System.out.println("Please Enter an Integer!");
		        sc.nextLine();
		    }
		}
		return output;
	}
	
	/**
	 * Get an error-free weekday number.
	 * @return an error-free weekday number.
	 */
	public int inputWeekday() {
		int output = inputInteger();
		while (output < 1 || output > 5) {
			System.out.println("Please Enter a Valid Weekday Integer!");
			output = inputInteger();
		}
		return output;
	}
	
	/**
	 * Get an error-free LocalTime.
	 * @return an error-free LocalTime.
	 */
	public LocalTime inputTime(){
		LocalTime t = null;
		int valid = 0;
		while (valid == 0) {
			try {
				t = LocalTime.parse(sc.next());
				valid = 1;
			} catch (DateTimeParseException e) {
				System.out.println("Error!! Please Use the Correct Time Format (HH:mm)");
				sc.nextLine();
			}
		}
		return t;
	}
	
	/**
	 * Get an error-free LocalDate.
	 * @return an error-free LocalDate.
	 */
	public LocalDate inputDate(){
		LocalDate d = null;
		int valid = 0;
		while (valid == 0) {
			try {
				d = LocalDate.parse(sc.next());
				valid = 1;
			} catch (DateTimeParseException e) {
				System.out.println("Error!! Please Use the Correct Date Format (yyyy-mm-dd)");
				sc.nextLine();
			}
		}
		return d;
	}
	

}
