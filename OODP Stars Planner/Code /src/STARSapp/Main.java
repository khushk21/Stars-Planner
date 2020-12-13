package STARSapp;

import java.io.Console;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * My Student Automated Registration System (MySTARS)
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class Main {
	/**
	 * Flag for password verification
	 */
	static boolean correctPassword = false;
	/**
	 * The main method of the application
	 * @param args array of string elements
	 * @throws ClassNotFoundException Thrown when an application tries to load in a class through its string name but no definition for the class with the specified name could be found 
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed
	 */
	public static void main(String[] args) throws ClassNotFoundException, FileNotFoundException {
		
		Scanner input = new Scanner(System.in);
		GetUserInput g = new GetUserInput();
		String name ="", pw = "";
		int choice = 1;
		Console console = System.console();
		
		Time t = Time.loadData();
		LocalTime st = t.getSTime();
		LocalTime et = t.getETime();
		LocalDate sd = t.getSDate();
		LocalDate ed = t.getEDate();

		
		//only accept student or admin
		while (choice != 0) 
		{
			System.out.println("Login as student or admin \n"
					+ "1: Student \n"
					+ "2: Admin \n"
					+ "0: Exit");
		    choice = g.inputInteger();
		    switch(choice) {
		    case 0: 
		    	System.out.println("The program is terminated.");
				System.exit(0);
		    case 1: //Student
		    	Student s = null;
				LocalTime currTime = LocalTime.now();
				LocalDate currDate = LocalDate.now();
				if(currTime.compareTo(st)>0 && currTime.compareTo(et)<0
						&& currDate.compareTo(sd)>=0 && currDate.compareTo(ed)<=0) //to allow access only in pre-defined time
				//if(true)
				{
					while(correctPassword == false)
					{
						if(console != null)
						{
							name = console.readLine("Please enter your Matric Number: ");
							s = Student.loadData(name);
							if (s == null) {
								break;
							}
							char[] pwd = console.readPassword("Please enter your password: ");
							pw = new String(pwd);
						}
						else
						{
							System.out.println("Please enter your Matric Number: ");
							name = input.nextLine();
							s = Student.loadData(name);
							if (s == null) {
								break;
							}
							System.out.println("Please enter your password: ");
							pw = input.nextLine();
						}	
						correctPassword = s.verifyPwd(pw);
						if (!correctPassword) {
							System.out.println("Wrong Password!");
						}
					}
					if (correctPassword) {
						studentChoice(input, s);
					} 
				}
				else
				{
					System.out.println("Please access it during " + st + " - " + et + "\n"
										+ "from " + sd + " to " + ed);
				}
		    	break;
		    case 2: //Admin
		    	Admin a = null;
				while(correctPassword == false)
				{
					if(console != null)
					{
						name = console.readLine("Please enter your userid: ");
						a = Admin.loadData(name);
						if (a == null) {
							break;
						}
						char[] pwd = console.readPassword("Please enter your password: ");
						pw = new String(pwd);
					}
					else
					{
						System.out.println("Please enter your userid: ");
						name = input.nextLine();
						a = Admin.loadData(name);
						if (a == null) {
							break;
						}
						System.out.println("Please enter your password: ");
						pw = input.nextLine();
					}
					correctPassword = a.verifyPwd(pw);
					if (!correctPassword) {
						System.out.println("Wrong Password!");
					}
				}
				if (correctPassword) {
					adminChoice(input, a);
				}
		    	break;
		    }//switch end
		}//last while end
	}

	/**
	 * Student main menu
	 * @param input get user input
	 * @param student The student (current user) information
	 * @throws ClassNotFoundException Thrown when an application tries to load in a class through its string name but no definition for the class with the specified name could be found
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed
	 */
	private static void studentChoice(Scanner input, Student student) throws ClassNotFoundException, FileNotFoundException
	{
		SendEmail mail = new SendEmail();
		StudentController s = new StudentController(mail);

		System.out.println("Your Matric Number is " + student.getMatric());
		int choice;
		do {
		System.out.println(
				"(1) Add Course\r\n" + 
				"(2) Drop Course\r\n" + 
				"(3) Check/Print Courses Registered\r\n" + 
				"(4) Check Vacancies Available\r\n" + 
				"(5) Change Index Number of Course\r\n" + 
				"(6) Swop Index Number with Another Student\r\n" + 
				"(7) Logout from account\r\n" +
				"(8) Exit");

		 System.out.println("Enter the number of your choice:");
		 choice = input.nextInt();
		 boolean loop = true;
			switch(choice)
			{
			case 1:
				loop = true;
				while(loop) {
				System.out.println("==========ADD COURSE==========");
				System.out.println("Enter Index Number to add: ");
				String courseIndex_add = input.next();
				int ret = 0;
				Index index = null;
				if(Index.isExist(courseIndex_add)){
					//SHOW INDEX INFORMATION
					index = Index.loadData(courseIndex_add);
					System.out.println("Course: " + index.getCourseId() + " Index: " + index.getIndexid());
					ArrayList<Lesson> lect = index.getLectures();
					System.out.println("Class Type\tDay of Week\tTime\t\tVenue");
					if(index.getLab() != null) {
						Lesson lab = index.getLab();
						System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
					}
					if(index.getTut() != null) {
						Lesson tut = index.getTut();
						System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
					}
					for(int i = 0; i<lect.size();i++) {
						System.out.println("LEC\t\t" + lect.get(i).getday() + "\t\t" + lect.get(i).getst() + "-" + lect.get(i).getet() + "\t" + lect.get(i).getvenue());
					}
					
					ret = s.addCourse(student, index.getIndexid());
				}
				else {
					ret = 5;
				}
				
				
				switch(ret) {
				case 0://Student is in waitlist
					System.out.println("You are already in the waitlist of " + index.getCourseId());
					break;
				case 1://Student is in course
					System.out.println("You are already in " + index.getCourseId());
					break;
				case 2: //add student to waiting list
					System.out.println("You have been added to waiting list");
					break;
				case 3: //add student to index's student list
					System.out.println("You have been added to " + index.getIndexid());
					break;
				case 4: // You cannot add any more course as there is a maximum of 21-23 AU
					System.out.println("You cannot add any more course as there is a maximum of 21-23 AU");
					break;
				case 5:
					System.out.println("Invalid Index Number");
					break;
				case 10:
					System.out.println("Clashed with NEW index Lab Time");
					break;
				case 11:
					System.out.println("Clashed with NEW index Tutorial Time");
					break;
				case 12:
					System.out.println("Clashed with NEW index Lecture Time");
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
				System.out.println(
									"(1) Enter another Index Number to add\r\n" +
									"(2) Return to main menu\r\n" +
									"(3) Exit");
				String select = input.next();
				if(select.contains("1")) {	
					System.out.println("You have selected enter another index number to add");
					inp = false;
				}
				else if(select.contains("2")) {
					System.out.println("You have selected return to main menu");
					studentChoice(input, student);
					loop = false;
					inp = false;
				}
				else if(select.contains("3")) {
					System.out.println("The program is terminated");
					loop = false;
					inp = false;
					System.exit(0);
				}
				}
				
				}
				break;
			case 2: //drop course
				loop = true;
				while(loop) {
				System.out.println("==========DROP COURSE==========");
				System.out.println("Enter Index Number to drop: ");
				String courseIndex_drop = input.next();
				int ret = 0;
				Index index = null;
				if(Index.isExist(courseIndex_drop)) {
					index = Index.loadData(courseIndex_drop);
					System.out.println("Course: " + index.getCourseId() + " Index: " + index.getIndexid());
					ret = s.dropCourse(student, index.getIndexid());
					
					//SHOW USER INDEX REGISTERED INFORMATION
					s.printCourse(student);
				}
				else {
					ret = 5;
				}
				
				switch(ret) {
				case 1: //Drop student from course
					System.out.println("You have been dropped from " + index.getCourseId());
					break;
				case 5: 
					System.out.println("Invalid Index Number");
					break;
				case -1:
					System.out.println("You are not found in " + index.getIndexid());
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
				System.out.println(
									"(1) Enter another Index Number to drop\r\n" +
									"(2) Return to main menu\r\n" +
									"(3) Exit");
				String select = input.next();
				if(select.contains("1")) {	
					System.out.println("You have selected enter another index number to drop");
					inp = false;
				}
				else if(select.contains("2")) {
					System.out.println("You have selected return to main menu");
					studentChoice(input, student);
					loop = false;
					inp = false;
				}
				else if(select.contains("3")) {
					System.out.println("The program is terminated");
					loop = false;
					inp = false;
					System.exit(0);
				}
				}
				
				}
				break;
			case 3: //check/print courses registered
				while(loop) {
				System.out.println("===========CHECK/PRINT COURSES REGISTERED===========");
				int ret;
				ret = s.printCourse(student);

				
				switch(ret) {
				case 1:
					System.out.println();
					break;
				case -1:
					System.out.println("You are not REGISTERED/WAITLIST in a course");
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
					System.out.println(
										"(1) Return to main menu\r\n" + 
										"(2) Exit");
					String select = input.next();
					if(select.contains("1")) {
						System.out.println("You have selected return to main menu");
						studentChoice(input, student);
						loop = false;
						inp = false;
					}
					else if(select.contains("2")) {
						System.out.println("The program is terminated");
						loop = false;
						inp = false;
						System.exit(0);
					}
				}
				
				}
				break;
			case 4: //check vacancies available
				while(loop) {
				System.out.println("===========CHECK VACANCIES AVAILABLE===========");
				System.out.println("Enter the Course Code to check: ");
				String courseId = input.next();
				Course newCourse = Course.loadData(courseId);
				if (newCourse == null) {
					return;
				}
				System.out.println("List of Indexes: ");
				for (Index i : newCourse.getIndex()) {
					System.out.println(i.getIndexid());
				}
				System.out.println("Enter the Index to check: ");
				String courseIndex_vac = input.next();
				int ret = 0;
				if(Index.isExist(courseIndex_vac)) {
					int index_in_course = 0;
					for (Index index : newCourse.getIndex()){
						if (index.getIndexid().equals(courseIndex_vac)) {
							index_in_course = 1;
							System.out.println("Course: " + index.getCourseId() + " Index: " + index.getIndexid());
							
							//SHOW INDEX INFORMATION
							ArrayList<Lesson> lect = index.getLectures();
							System.out.println("Class Type\tDay\t\tTime\t\tVenue");
							if(index.getLab() != null) {
								Lesson lab = index.getLab();
								System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
							}
							if(index.getTut() != null) {
								Lesson tut = index.getTut();
								System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
							}
							for(int i = 0; i<lect.size();i++) {
								System.out.println("LEC\t\t" + lect.get(i).getday() + "\t\t" + lect.get(i).getst() + "-" + lect.get(i).getet() + "\t" + lect.get(i).getvenue());
							}
							
							System.out.println("Places Available: " + index.getVacancy() + " Length of Waitlist: " + index.getWait().size() + "\n");
						}
					}
					if (index_in_course == 0) {
						System.out.println("The Index is not in Course " + courseId);
					}
					
				}
				else {
					ret = 5;
				}
				
				switch(ret) {
				case 5:
					System.out.println("Invalid Index Number");
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
				System.out.println(
									"(1) Enter another Index Number to check\r\n" +
									"(2) Return to main menu\r\n" +
									"(3) Exit");
				String select = input.next();
				if(select.contains("1")) {	
					System.out.println("You have selected enter another index number to check");
					inp = false;
				}
				else if(select.contains("2")) {
					System.out.println("You have selected return to main menu");
					studentChoice(input, student);
					loop = false;
					inp = false;
				}
				else if(select.contains("3")) {
					System.out.println("The program is terminated");
					loop = false;
					inp = false;
					System.exit(0);
				}
				}
				
				}
				break;
			case 5: //change index number of course
				while(loop) {
				System.out.println("============CHANGE COURSE INDEX============");
				System.out.println("Enter Current Index Number: ");
				String oldIndex_change = input.next();
				System.out.println("Enter New Index Number: ");
				String newIndex_change = input.next();
				Index oldIndex = null;
				Index newIndex = null;
				int ret = 0;
				if(Index.isExist(oldIndex_change)) {
					oldIndex = Index.loadData(oldIndex_change);
					if(Index.isExist(newIndex_change)) {
						newIndex = Index.loadData(newIndex_change);
						ret = s.changeIndex(student, oldIndex_change, newIndex_change);
					}
					else {
						ret = 6; //invalid newIndex
					}
				}
				else {
					ret = 5;//invalid oldIndex
				}
				
				
				
				if(ret == 4) {
					System.out.println("Course: " + oldIndex.getCourseId());
					System.out.println("Old Index Number: " + oldIndex.getIndexid());
					//SHOW INDEX INFORMATION
					ArrayList<Lesson> lect = oldIndex.getLectures();
					System.out.println("Class Type\tDay\t\tTime\t\tVenue");
					if(oldIndex.getLab() != null) {
						Lesson lab = oldIndex.getLab();
						System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
					}
					if(oldIndex.getTut() != null) {
						Lesson tut = oldIndex.getTut();
						System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
					}
					for(int i = 0; i<lect.size();i++) {
						System.out.println("LEC\t\t" + lect.get(i).getday() + "\t\t" + lect.get(i).getst() + "-" + lect.get(i).getet() + "\t" + lect.get(i).getvenue());
					}
					System.out.println("New Index Number: " + newIndex.getIndexid());
					//SHOW INDEX INFORMATION
					ArrayList<Lesson> lectt = newIndex.getLectures();
					System.out.println("Class Type\tDay\t\tTime\t\tVenue");
					if(newIndex.getLab() != null) {
						Lesson lab = newIndex.getLab();
						System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
					}
					if(newIndex.getTut() != null) {
						Lesson tut = newIndex.getTut();
						System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
					}
					for(int i = 0; i<lectt.size();i++) {
						System.out.println("LEC\t\t" + lectt.get(i).getday() + "\t\t" + lectt.get(i).getst() + "-" + lectt.get(i).getet() + "\t" + lectt.get(i).getvenue());
					}
				}
				
				switch(ret) {
				case 1:
					System.out.println("Both Index are of different Course ");
					break;
				case 2:
					System.out.println(newIndex.getIndexid() + "has no vacancy");
					break;
				case 3:
					System.out.println("You are not enrolled in index " + oldIndex.getIndexid());
					break;
				case 4:
					System.out.println("Successfully change from " + oldIndex.getIndexid() + " to " + newIndex.getIndexid());
					break;
				case 5:
					System.out.println("Invalid current index");
					break;
				case 6:
					System.out.println("Invalid new index");
					break;
				case 10:
					System.out.println("Clashed with NEW index Lab Time");
					break;
				case 11:
					System.out.println("Clashed with NEW index Tutorial Time");
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
				System.out.println(
									"(1) Enter another Index Number to change\r\n" +
									"(2) Return to main menu\r\n" +
									"(3) Exit");
				String select = input.next();
				if(select.contains("1")) {	
					System.out.println("You have selected enter another index number to change");
					inp = false;
				}
				else if(select.contains("2")) {
					System.out.println("You have selected return to main menu");
					studentChoice(input, student);
					loop = false;
					inp = false;
				}
				else if(select.contains("3")) {
					System.out.println("The program is terminated");
					loop = false;
					inp = false;
					System.exit(0);
				}
				}
				
				}
				break;
			case 6: //swap index number with another student
				while(loop) {
				Console console = System.console();
					
				System.out.println("============SWAP COURSE WITH ANOTHER STUDENT=============");
				System.out.println("Enter Your Index Number: ");
				String student1Index = input.next();
				
				input.nextLine();
				String student2user = "", student2pw = "", student2Index = "";
				
				Student student2 = null;
				Boolean correctPasswordStudent2 = false;
				while(correctPasswordStudent2 == false)
				{
					if(console != null)
					{
						student2user = console.readLine("Please enter your peer's Matric Number: ");
						 student2 = Student.loadData(student2user);
						if (student2 == null) {
							break;
						}
						char[] pwd = console.readPassword("Please enter your peer's password: ");
						student2pw = new String(pwd);
					}
					else
					{
						System.out.println("Please enter your peer's Matric Number: ");
						student2user = input.nextLine();
						student2 = Student.loadData(student2user);
						if (s == null) {
							break;
						}
						System.out.println("Please enter your peer's password: ");
						student2pw = input.nextLine();
					}	
					correctPasswordStudent2 = student2.verifyPwd(student2pw);
					if (!correctPasswordStudent2) {
						System.out.println("Wrong Password!");
					}
				}
			
			if (correctPasswordStudent2) {
				System.out.println("Enter your Peer's Index Number: ");
				 student2Index = input.next();
			} 
			

				
				Index student1in = null;
				Index student2in = null;
				int ret = -1;
				if(Index.isExist(student1Index)) {
					student1in = Index.loadData(student1Index);
					if(Index.isExist(student2Index)) {
						student2in = Index.loadData(student2Index);
						ret = s.swapIndex(student, student1Index, student2, student2Index, student2user, student2pw);
					}
					else {
						ret = 6;//invalid student2Index
					}
				}
				else {
					ret = 5; //invalid student1Index
				}
				
				if(ret == 4) {
					Index student1Indexx = Index.loadData(student1Index);
					Index student2Indexx = Index.loadData(student2Index);
					System.out.println("Course: " + student1Indexx.getCourseId());
					System.out.println("Old Index Number: " + student1Indexx.getIndexid());
					//SHOW INDEX INFORMATION
					ArrayList<Lesson> lect = student1Indexx.getLectures();
					System.out.println("Class Type\tDay\t\tTime\t\tVenue");
					if(student1Indexx.getLab() != null) {
						Lesson lab = student1Indexx.getLab();
						System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
					}
					if(student1Indexx.getTut() != null) {
						Lesson tut = student1Indexx.getTut();
						System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
					}
					for(int i = 0; i<lect.size();i++) {
						System.out.println("LEC\t\t" + lect.get(i).getday() + "\t\t" + lect.get(i).getst() + "-" + lect.get(i).getet() + "\t" + lect.get(i).getvenue());
					}
					System.out.println("New Index Number: " + student2Indexx.getIndexid());
					//SHOW INDEX INFORMATION
					ArrayList<Lesson> lectt = student2Indexx.getLectures();
					System.out.println("Class Type\tDay\t\tTime\t\tVenue");
					if(student2Indexx.getLab() != null) {
						Lesson lab = student2Indexx.getLab();
						System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
					}
					if(student2Indexx.getTut() != null) {
						Lesson tut = student2Indexx.getTut();
						System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
					}
					for(int i = 0; i<lectt.size();i++) {
						System.out.println("LEC\t\t" + lectt.get(i).getday() + "\t\t" + lectt.get(i).getst() + "-" + lectt.get(i).getet() + "\t" + lectt.get(i).getvenue());
					}
				}
				
				switch(ret) {
				case 0:
					System.out.println("Your course and Your peer's course are different ");
					break;
				case 1:
					System.out.println("You are not in index " + student1Index);
					break;
				case 2:
					System.out.println("Your peer is not in " + student2Index);
					break;
				case 3:
					System.out.println("You are currently in the same index as your peer");
					break;
				case 4:
					System.out.println("Successfully swap from " + student1Index + " to " + student2Index);
					break;
				case 5:
					System.out.println("Your Index Number is invalid ");
					break;
				case 6:
					System.out.println("Your Peer's Index Number is invalid ");
					break;
				case 7:
					System.out.println("Your REGISTERED clashed with NEW index Lab Time");
					break;
				case 8:
					System.out.println("Your REGISTERED clashed with NEW index Tutorial Time");
					break;
				case 9:
					System.out.println("Your Peer's REGISTERED clashed with NEW index Lab Time");
					break;
				case 10:
					System.out.println("Your Peer's REGISTERED clashed with NEW index Tutorial Time");
					break;
				default:
					break;
				}
				
				boolean inp = true;
				loop = true;
				while(inp) {
				System.out.println(
									"(1) Enter another Index Number to swap\r\n" +
									"(2) Return to main menu\r\n" +
									"(3) Exit");
				String select = input.next();
				if(select.contains("1")) {	
					System.out.println("You have selected enter another index number to swap");
					inp = false;
				}
				else if(select.contains("2")) {
					System.out.println("You have selected return to main menu");
					studentChoice(input, student);
					loop = false;
					inp = false;
				}
				else if(select.contains("3")) {
					System.out.println("The program is terminated");
					loop = false;
					inp = false;
					System.exit(0);
				}
				}
				
				}
				break;
			case 7:
				correctPassword = false;
				main(null);
				break;
			case 8:
				System.out.println("The program is terminated.");
				System.exit(0);
				break;
			}
		}while(choice != 8);
	}
	
	/**
	 * Admin main menu
	 * @param input get user input
	 * @param a The admin (current user) information
	 * @throws ClassNotFoundException Thrown when an application tries to load in a class through its string name but no definition for the class with the specified name could be found
	 * @throws FileNotFoundException Signals that an attempt to open the file denoted by a specified pathname has failed
	 */
	private static void adminChoice(Scanner input, Admin a) throws ClassNotFoundException, FileNotFoundException
	{
		AdminController ac = new AdminController();
		
		int choice;
		do {
			System.out.println(
					"(1) Edit student access period\r\n" + 
					"(2) Add student\r\n" + 
					"(3) Add a course\r\n" + 
					"(4) Update a course\r\n" +
					"(5) Check available slot for an index number\r\n" + 
					"(6) Print student list by index number\r\n" + 
					"(7) Print student list by course\r\n" + 
					"(8) Logout from account\r\n" +
					"(9) Exit");
		 System.out.println("Enter the number of your choice:");
		 choice = input.nextInt();
		
		switch(choice)
		{
		case 1:
			ac.editAccessPeriod();
			break;
		case 2:
			ac.addStudent();
			break;
		case 3:
			ac.addCourse();
			break;
		case 4:
			ac.updateCourse();
			break;
		case 5:
			ac.checkVacancy();
			break;
		case 6:
			ac.studentList_Index();
			break;
		case 7:
			ac.studentList_Course();
			break;
		case 8:
			correctPassword = false;
			main(null);
			break;
		case 9:
			System.out.println("The program is terminated.");
			System.exit(0);
			break;
		}
		}while(choice!=9);
		
		
	}
	
}
