package STARSapp;

import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalTime;
import java.io.FileNotFoundException;
import java.time.LocalDate;

/**
 * Functions that an admin can do in the application
 * @author Huang Runtao
 * @version 1.0
 * @since 2020-11-22
 */
public class AdminController {
	
	/**
	 * Scanner for getting user input
	 */
	Scanner sc = new Scanner(System.in);
	
	/**
	 * Object for getting error-free user input
	 */
	GetUserInput g = new GetUserInput();
	
	/**
	 * Edit the access period for student users
	 */
	public void editAccessPeriod() {
		System.out.println("===========EDIT ACCESS PERIOD===========");
		Time t = Time.loadData();
		System.out.println("The Access Period\n"
						+ "starts on " + t.getSDate() + "\n"
						+ "ends after " + t.getEDate() + "\n"
						+ "Students can login after " + t.getSTime()
						+ " and before " + t.getETime() + " every day\n");
		int c = 1;
		while (c != 0) {
			System.out.print("Enter your choice: \n"
					+ "1. Update Start Date\n"
					+ "2. Update End Date\n"
					+ "3. Update Start Time (everyday)\n"
					+ "4. Update End Time (everyday)\n"
					+ "0. Cancel\n"
					+ "> ");
			c = g.inputInteger();
			switch(c) {
			case 0:
				Time.saveData(t);
				return; 
			case 1:
				System.out.println("Enter new Start Date (yyyy-mm-dd): ");
				LocalDate sd = g.inputDate();		
				t.setSDate(sd);
				System.out.println("Start Date updated successfully");
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
			case 2:
				System.out.println("Enter new End Date (yyyy-mm-dd): ");
				LocalDate ed = g.inputDate();
				t.setEDate(ed);
				System.out.println("End Date updated successfully");
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
			case 3:
				System.out.println("Enter new Start Time (HH:mm): ");
				LocalTime st = g.inputTime();
				t.setSTime(st);
				System.out.println("Start Time updated successfully");
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
			case 4:
				System.out.println("Enter new End Time (HH:mm): ");
				LocalTime et = g.inputTime();
				t.setETime(et);
				System.out.println("End Time updated successfully");
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
			default: 
				System.out.println("Please Enter a Valid Choice!");
			}
		}
	}

	/**
	 * Add a student account and save it as a serializable file
	 */
	public void addStudent() {
		System.out.println("===========CREATE NEW STUDENT ACCOUNT===========");
		System.out.println("Enter the student's Matric Number: ");
		String matric = sc.next();
		while (Student.isExist(matric)) {
        	System.out.println("This student cannot be added "
        			+ "because he/she has already existed in the database!");
        	System.out.println("Please enter the Matric Number again: \n"
        				+ "(or enter 0 to cancel)");
    		matric = sc.next();
    		if (matric.equals("0")) {
	        	return;
	        }  
		}
		System.out.println("Enter the student's Name: ");
		sc.nextLine();
		String name = sc.nextLine();
		System.out.println("Enter the student's School's Abbreviation: ");
		String school = sc.nextLine();
		System.out.println("Enter the student's Gender (M/F): ");
		char gender = sc.next().charAt(0);
		while (gender != 'M' && gender != 'F') {
			System.out.println("Wrong Format! \nEnter the student's Gender (M/F): ");
			gender = sc.next().charAt(0);
		}
		System.out.println("Enter the student's Nationality: ");
		String nation = sc.next();
		System.out.println("Enter the student's Date of Birth (yyyy-mm-dd): ");
		LocalDate dob = g.inputDate();
		System.out.println("Enter the Password for the student's account: ");
		String password = sc.next();
		System.out.println("Enter the Email Address for the student's account: ");
		String email = sc.next();
		Student.saveData(new Student(school, name, matric, dob, gender, nation, password, email));
		System.out.println("\nThe Student's info is listed below:\n"
				+ "Name: " + name + ";  Matric Number: " + matric + "\n" 
				+ "Gender: " + gender + "\n"
				+ "School: " + school + "\n"
				+ "Date of Birth: " + dob + "\n"
				+ "Nationality: " + nation + "\n"
				+ "Password: " + password + "\n"
				+ "Email: " + email + "\n");
		System.out.println("Press <Enter> key to continue:");
		sc.nextLine();
		sc.nextLine();
		System.out.println("The list of all the students: \n"
				+ "[Matric Number: Student Name]");
		ArrayList<Student> slist = Student.loadAllData();
		for (Student s : slist) {
			System.out.println(s.getMatric() + ":\t" + s.getName());
		}
		System.out.println("Press <Enter> key to continue:");
		sc.nextLine();
	}

	/**
	 * Add a course with indexes and lessons and save it as a serializable file
	 * Different courses cannot have the same course code or indexes
	 */
	public void addCourse() {
		System.out.println("===========CREATE NEW COURSE===========");
		ArrayList<Index> indexList = new ArrayList<Index>();
		ArrayList<Lesson> lessonList = new ArrayList<Lesson>();
		ArrayList<Lesson> lecList = new ArrayList<Lesson>();
		int noTimeClash = 0;
		System.out.println("Enter Course Code: ");
		String courseCode = sc.next();
        while (Course.isExist(courseCode)) {
        	System.out.println("This course cannot be added "
        			+ "because it has already existed in the database!");
        	System.out.println("Please enter the Course Code again: \n"
    				+ "(or enter 0 to cancel)");
        	courseCode = sc.next();
        	if (courseCode.equals("0")) {
        		return;
        	}  
		}
        System.out.println("Enter Course Name: ");
        sc.nextLine();
		String courseName = sc.nextLine();
		System.out.println("Enter the Abreviation for School Name: ");
		String courseSchool = sc.next();
		System.out.println("Enter the amount of AU: ");
		int amtOfAU = g.inputInteger();
		while (amtOfAU <= 0) {
			System.out.println("The Number of AU Must be Positive!");
			System.out.println("Enter the amount of AU: ");
			amtOfAU = g.inputInteger();
		}
		
		System.out.println("The Lecture Schedule for this course:");
		System.out.println("Enter the number of weekly lectures for this index (int): ");
		int numLec = g.inputInteger();
		while (numLec < 1) {
			System.out.println("Every course must conduct at least 1 lecture per week!");
			System.out.println("Enter the number of weekly lectures for this index (int): ");
			numLec = g.inputInteger();
		}
		System.out.println("Enter the venue for the lectures: ");
		sc.nextLine();
		String lecVenue = sc.nextLine();
		for (int j=0; j<numLec; j++) {
			while (noTimeClash==0) {
				System.out.printf("For Lecture %d: \n", j+1);
				System.out.println("Enter the day this lecture is on (int): ");
				int d_lecture = g.inputWeekday();
				System.out.println("Enter the start time for this lecture (eg. 14:55): ");
				LocalTime ST_lecture = g.inputTime();
				System.out.println("Enter the end time for this lecture (eg. 14:55): ");
				LocalTime ET_lecture = g.inputTime();
				Lesson newLec = new Lesson(d_lecture, ST_lecture, ET_lecture, lecVenue, "Undefined");
				noTimeClash = checkTimeClash(lessonList, newLec);
				if (noTimeClash==1) {
					lecList.add(newLec);
					lessonList.add(newLec);
					System.out.printf("Lecture %d is added\n", j+1);
				} 
			}
			noTimeClash = 0;
		}
		
		System.out.println("Enter the Number of Course Indexes to add: ");
		int numIndex = g.inputInteger();
		while (numIndex <= 0) {
			System.out.println("The Number of Indexes Must be Positive!");
			System.out.println("Enter the Number of Course Indexes to add: ");
			numIndex = g.inputInteger();
		}
		for(int i=0;i<numIndex;i++) {
			System.out.println("Enter a New Index for this course: ");
			String indexId = sc.next();
			for (Index ii : indexList) {
				if (indexId.equals(ii.getIndexid())) {
					System.out.println("This index cannot be added "
		        			+ "because it has been used by this course!");
		        	System.out.println("Please enter the New Index again: \n"
		        				+ "(or enter 0 to cancel)");
		        	indexId = sc.next();
		    		if (indexId.equals("0")) {
			        	return;
			        } 
				}
			}
			while (Index.isExist(indexId)) {
				System.out.println("This index cannot be added "
	        			+ "because it has been used by another course!");
	        	System.out.println("Please enter the New Index again: \n"
	        				+ "(or enter 0 to cancel)");
	        	indexId = sc.next();
	    		if (indexId.equals("0")) {
		        	return;
		        }  
			}
			System.out.println("Enter Vacancy for Index " + indexId + ": ");
			int vac = g.inputInteger();
			while (vac < 0) {
				System.out.println("The Vacancy Must be Non-negative!");
				System.out.println("Enter Vacancy for " + indexId + ": ");
				vac = g.inputInteger();
			}
			Index newIndex = new Index(indexId, courseCode, vac);
			for (Lesson lec : lecList) {
				lec.setindex(indexId);
				newIndex.addLecture(lec);
			}
			
			System.out.println("The Tutorial and Lab Schedule for Index " + indexId + ":");
			System.out.println("Enter the day of tutorial for this index (int; Enter 0 to cancel): ");
			int d_tut = g.inputInteger();
			while (d_tut < 1 || d_tut > 5) {
				if (d_tut == 0) {
					break;
				}
				System.out.println("Please Enter a Valid Weekday Integer! (Enter 0 to cancel)");
				d_tut = g.inputInteger();
			}
			if (d_tut != 0) {
				System.out.println("Enter the venue of the tutorial: ");
				sc.nextLine();
				String tutVenue = sc.nextLine();
				noTimeClash = 0;
				while (noTimeClash==0) {
					System.out.println("Enter the start time for this tutorial (eg. 14:55): ");
					LocalTime ST_tut = g.inputTime();
					System.out.println("Enter the end time for this tutorial (eg. 14:55): ");
					LocalTime ET_tut = g.inputTime();
					Lesson newTut = new Lesson(d_tut, ST_tut, ET_tut, tutVenue, indexId);
					noTimeClash = checkTimeClash(lessonList, newTut);
					if (noTimeClash==1) {
						newIndex.setTut(newTut);
						lessonList.add(newTut);
					} 
				}
				
				System.out.println("Enter the day of lab for this index (int; Enter 0 to cancel): ");
				int d_lab = g.inputInteger();
				while (d_lab < 1 || d_lab > 5) {
					if (d_lab == 0) {
						break;
					}
					System.out.println("Please Enter a Valid Weekday Integer! (Enter 0 to cancel)");
					d_lab = g.inputInteger();
				}
				if (d_lab != 0) {
					System.out.println("Enter the venue of the lab: ");
					String labVenue = sc.nextLine();
					noTimeClash = 0;
					while (noTimeClash==0) {
						System.out.println("Enter the start time for this lab (eg. 14:55): ");
						LocalTime ST_lab = g.inputTime();
						System.out.println("Enter the end time for this lab (eg. 14:55): ");
						LocalTime ET_lab = g.inputTime();
						Lesson newLab = new Lesson(d_lab, ST_lab, ET_lab, labVenue, indexId);
						noTimeClash = checkTimeClash(lessonList, newLab);
						if (noTimeClash==1) {
							newIndex.setLab(newLab);
						}
					}
				}
			}
			
			indexList.add(newIndex);
		}
		Course c = new Course(courseName, courseCode, courseSchool, amtOfAU, indexList);
		Course.saveData(c);	
		System.out.println("\nThe New Course's info is listed below:\n"
				+ "Course Name: " + courseName + ";  Course Code: " + courseCode + "\n"
				+ "School: " + courseSchool + ";  AU: " + amtOfAU);
		for (Index i : c.getIndex()) {
			System.out.println("For Index " + i.getIndexid() + ":");
			ArrayList<Lesson> lect = i.getLectures();
			System.out.println("Class Type\tDay of Week\tTime\t\tVenue");
			if(i.getLab() != null) {
				Lesson lab = i.getLab();
				System.out.println("LAB\t\t" + lab.getday() + "\t\t" + lab.getst()+"-" + lab.getet() + "\t" + lab.getvenue());
			}
			if(i.getTut() != null) {
				Lesson tut = i.getTut();
				System.out.println("TUT\t\t" + tut.getday() + "\t\t" + tut.getst()+"-" + tut.getet() + "\t" + tut.getvenue());
			}
			for(int j = 0; j<lect.size(); j++) {
				System.out.println("LEC\t\t" + lect.get(j).getday() + "\t\t" + lect.get(j).getst() + "-" + lect.get(j).getet() + "\t" + lect.get(j).getvenue());
			}
		}
		System.out.println("Press <Enter> key to continue:");
		sc.nextLine();
		System.out.println("The list of all the courses: \n"
				+ "[Course Code: Name]");
		ArrayList<Course> clist = Course.loadAllData();
		for (Course co : clist) {
			System.out.println(co.getCourseId() + ":\t" + co.getName());
		}
		System.out.println("Press <Enter> key to continue:");
		sc.nextLine();
	}
	
	/**
	 * Check if the newly created index has internal time clash
	 * @param lessonList An ArrayList containing all the lessons created for the index so far
	 * @param newLesson The newly created lesson object
	 * @return 0 if there is a time clash; 1 if there is no time clash
	 */
	public int checkTimeClash(ArrayList<Lesson> lessonList, Lesson newLesson) {
		if (newLesson.getet().compareTo(newLesson.getst())<=0) {
			System.out.println("Please input a valid time period");
			return 0;
		}
		for (Lesson l : lessonList) {
			if (l.getday() == newLesson.getday()) {
				if (l.getst().compareTo(newLesson.getst())*l.getet().compareTo(newLesson.getst())<0
						|| l.getst().compareTo(newLesson.getst())==0 
						|| l.getst().compareTo(newLesson.getet())*l.getet().compareTo(newLesson.getet())<0
						|| l.getet().compareTo(newLesson.getet())==0) {
					System.out.println("Time Clash!! Another lesson of this index starts at " + l.getst() +
									" and ends at " + l.getet());
					System.out.println("Please input the schedule again");
					return 0;
				}
			}
		}
		return 1;
	}

	/**
	 * Change a course's code, name, index number or vacancy
	 */
	public void updateCourse() {
		System.out.println("===========UPDATE COURSE INFO===========");
		System.out.println("Enter the Course Code of the course you want to update: ");
		String courseId = sc.next();
		Course newCourse = Course.loadData(courseId);
		if (newCourse == null) {
			return;
		}
		
		int c = 1;
		while (c != 0) {
			System.out.print("Enter your choice: \n"
					+ "1. Update Course Code\n"
					+ "2. Update Couse Name\n"
					+ "3. Update Index Number\n"
					+ "4. Delete Index Number\n"
					+ "5. Add New Index\n"
					+ "6. Update Index Vacancy\n"
					+ "0. Cancel\n"
					+ "> ");
			c = g.inputInteger();
			switch(c) {
			case 0:
				return;
			case 1:
				int is_newId_created = 0;
				while (is_newId_created == 0) {
					System.out.println("Enter the new Course Code: \n"
							+ "(or enter 0 to cancel)");
					String new_courseId = sc.next();
					if (new_courseId.equals("0")) {
		        		return;
		        	} 
					
					if (Course.isExist(new_courseId)) {
						if (new_courseId.equals(courseId)) {
							is_newId_created = 1;
						}
						else {
							System.out.println("This Course Code cannot be used "
				        			+ "because it has been taken by another course!");
						}
					}
					else {
						is_newId_created = 1;
					}
					
					if (is_newId_created == 1) {
						newCourse.setCourseId(new_courseId);
						Course.deleteCourseFile(courseId);
						Course.saveData(newCourse);
						System.out.println("Press <Enter> key to continue:");
						sc.nextLine();
						sc.nextLine();
					}
				}
				break;
				
			case 2:
				System.out.println("Enter the new Course Name: ");
				String name = sc.nextLine();
				newCourse.setName(name);
				Course.saveData(newCourse);
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
			case 3:
				System.out.println("List of Indexes: ");
				for (Index i : newCourse.getIndex()) {
					System.out.println(i.getIndexid());
				}
				System.out.println("Enter the Index to be changed: ");
				String old_index = sc.next();
				int valid3 = 0;
				for (Index i : newCourse.getIndex()) {
					if (i.getIndexid().equals(old_index)) {
						int v = 0;
						while (v == 0) {
							System.out.println("Enter the new Index: ");
							String new_index = sc.next();
							
							if (new_index.equals(old_index)) {
								v = 1;
							}
							else if (Index.isExist(new_index)) {
								System.out.println("This index cannot be added "
					        			+ "because it has been used by a course!");
								System.out.println("Press <Enter> key to continue:");
								sc.nextLine();
								sc.nextLine();
							}
							else { v = 1; }
							if (v == 1) {
								i.setIndexid(new_index);
								System.out.println("Index " + old_index + " is changed to " + "Index " + new_index);
								Course.saveData(newCourse);
								System.out.println("Press <Enter> key to continue:");
								sc.nextLine();
								sc.nextLine();
							}
						}
						valid3 = 1;
						break;
					}
				}
				if (valid3 == 0) {
					System.out.println("The Index does not exist!");
					System.out.println("Press <Enter> key to continue:");
					sc.nextLine();
					sc.nextLine();
				}
				break;
			case 4:
				System.out.println("List of Indexes: ");
				for (Index i : newCourse.getIndex()) {
					System.out.println(i.getIndexid());
				}
				System.out.println("Enter the Index to delete: ");
				String index_input2 = sc.next();
				int valid4 = 0;
				for (Index i : newCourse.getIndex()) {
					if (i.getIndexid().equals(index_input2)) {
						if (!i.getStu().isEmpty()) {
							System.out.println("Fail to delete! Some Students have registered this index!");
						}
						else if (!i.getWait().isEmpty()) {
							System.out.println("Fail to delete! Some Students is on the waitlist of this index!");
						}
						else {
							newCourse.getIndex().remove(i);
							System.out.println("Index " + i.getIndexid() + " has been deleted");
							Course.saveData(newCourse);
						}
						valid4 = 1;
						break;
					}
				}
				if (valid4 == 0) {
					System.out.println("The Index does not exist!");
				}
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				sc.nextLine();
				break;
			case 5:
				System.out.println("Enter a New Index for this course: ");
				String indexId = sc.next();
				for (Index ii : newCourse.getIndex()) {
					if (indexId.equals(ii.getIndexid())) {
						System.out.println("This index cannot be added "
			        			+ "because it has been used by this course!");
			        	System.out.println("Please enter the New Index again: \n"
			        				+ "(or enter 0 to cancel)");
			        	indexId = sc.next();
			    		if (indexId.equals("0")) {
				        	return;
				        } 
					}
				}
				while (Index.isExist(indexId)) {
					System.out.println("This index cannot be added "
		        			+ "because it has been used by another course!");
		        	System.out.println("Please enter the New Index again: \n"
		        				+ "(or enter 0 to cancel)");
		        	indexId = sc.next();
		    		if (indexId.equals("0")) {
			        	return;
			        }  
				}
				System.out.println("Enter Vacancy for Index " + indexId + ": ");
				int vac = g.inputInteger();
				while (vac < 0) {
					System.out.println("The Vacancy Must be Non-negative!");
					System.out.println("Enter Vacancy for " + indexId + ": ");
					vac = g.inputInteger();
				}
				Index newIndex = new Index(indexId, newCourse.getCourseId(), vac);
				ArrayList<Lesson> lessonList = newCourse.getIndex().get(0).getLectures();
				for (Lesson l : lessonList) {
					newIndex.addLecture(l);
				}
				
				int noTimeClash;
				System.out.println("The Tutorial and Lab Schedule for Index " + indexId + ":");
				System.out.println("Enter the day of tutorial for this index (int; Enter 0 to cancel): ");
				int d_tut = g.inputInteger();
				while (d_tut < 1 || d_tut > 5) {
					if (d_tut == 0) {
						break;
					}
					System.out.println("Please Enter a Valid Weekday Integer! (Enter 0 to cancel)");
					d_tut = g.inputInteger();
				}
				if (d_tut != 0) {
					System.out.println("Enter the venue of the tutorial: ");
					sc.nextLine();
					String tutVenue = sc.nextLine();
					noTimeClash = 0;
					while (noTimeClash==0) {
						System.out.println("Enter the start time for this tutorial (eg. 14:55): ");
						LocalTime ST_tut = g.inputTime();
						System.out.println("Enter the end time for this tutorial (eg. 14:55): ");
						LocalTime ET_tut = g.inputTime();
						Lesson newTut = new Lesson(d_tut, ST_tut, ET_tut, tutVenue, indexId);
						noTimeClash = checkTimeClash(lessonList, newTut);
						if (noTimeClash==1) {
							newIndex.setTut(newTut);
							lessonList.add(newTut);
						} 
					}
					
					System.out.println("Enter the day of lab for this index (int; Enter 0 to cancel): ");
					int d_lab = g.inputInteger();
					while (d_lab < 1 || d_lab > 5) {
						if (d_lab == 0) {
							break;
						}
						System.out.println("Please Enter a Valid Weekday Integer! (Enter 0 to cancel)");
						d_lab = g.inputInteger();
					}
					if (d_lab != 0) {
						System.out.println("Enter the venue of the lab: ");
						String labVenue = sc.nextLine();
						noTimeClash = 0;
						while (noTimeClash==0) {
							System.out.println("Enter the start time for this lab (eg. 14:55): ");
							LocalTime ST_lab = g.inputTime();
							System.out.println("Enter the end time for this lab (eg. 14:55): ");
							LocalTime ET_lab = g.inputTime();
							Lesson newLab = new Lesson(d_lab, ST_lab, ET_lab, labVenue, indexId);
							noTimeClash = checkTimeClash(lessonList, newLab);
							if (noTimeClash==1) {
								newIndex.setLab(newLab);
							}
						}
					}
				}
				newCourse.addIndex(newIndex);
				System.out.println("New Index " + newIndex.getIndexid() + " is added");
				Course.saveData(newCourse);
				System.out.println("Press <Enter> key to continue:");
				sc.nextLine();
				break;
				
			case 6:
				System.out.println("List of Indexes: ");
				for (Index i : newCourse.getIndex()) {
					System.out.println(i.getIndexid());
				}
				System.out.println("Enter the Index: ");
				String index_input5 = sc.next();
				int valid5 = 0;
				for (Index i : newCourse.getIndex()) {
					if (i.getIndexid().equals(index_input5)) {
						System.out.println("Enter the new Vacancy (int): ");
						int v = g.inputInteger();
						while (v < 0) {
							System.out.println("The Vacancy Must be Non-negative!");
							System.out.println("Enter Vacancy for " + i.getIndexid() + ": ");
							v = g.inputInteger();
						}
						i.setVacancy(v);
						ArrayList<String> wait_temp = new ArrayList<String>();
						SendEmail Email = new SendEmail();
						while (i.getVacancy() > 0 && !i.getWait().isEmpty()) {
							String matric = i.popStu_waitlist();
							Student stu = Student.loadData(matric); 
							if(stu.getAU()+newCourse.getAU()<=Student.getMAX_AU()) {
								i.regStu(matric);
								i.setVacancy(i.getVacancy()-1);
								System.out.println("Sending Email..");
								Email.sendMessage(stu.getEmail(), "Success in adding " + newCourse.getCourseId(), 
										stu.getName() + " has successfully added course " + newCourse.getCourseId() 
										+ " of index " + i.getIndexid());
							}
							else {
								wait_temp.add(matric);
							}
						}
						wait_temp.addAll(i.getWait());
						i.setWait(wait_temp);
						Course.saveData(newCourse);
						//updateWaitlist(i.getIndexid());
						System.out.println("Press <Enter> key to continue:");
						sc.nextLine();
						sc.nextLine();
						valid4 = 1;
						break;
					}
				}
				if (valid5 == 0) {
					System.out.println("The Index does not exist!");
					System.out.println("Press <Enter> key to continue:");
					sc.nextLine();
					sc.nextLine();
				}
				break;
			default: 
				System.out.println("Please Enter a Valid Choice!");
			}
		}
			
	}
	
	/**
	 * Check the vacancy of an index
	 */
	public void checkVacancy() {
		System.out.println("===========CHECK INDEX VACANCY===========");
		System.out.println("Enter the Course Code to check: ");
		String courseId = sc.next();
		Course newCourse = Course.loadData(courseId);
		if (newCourse == null) {
			return;
		}
		System.out.println("List of Indexes: ");
		for (Index i : newCourse.getIndex()) {
			System.out.println(i.getIndexid());
		}
		System.out.println("Enter the Index to check: ");
		String index_input = sc.next();
		int index_in_course = 0;
		for (Index i : newCourse.getIndex()){
			if (i.getIndexid().equals(index_input)) {
				index_in_course = 1;
				System.out.println("Vacancy: " + i.getVacancy() + "\n"
							+ "Waitlist Length: " + i.getWait().size());
			}
		}
		if (index_in_course == 0) {
			System.out.println("The Index is not in Course " + courseId);
		}
		
		System.out.println("Press <Enter> key to continue:");
		sc.nextLine();
		sc.nextLine();
	}
	
	/**
	 * Print a list of students who have registered the same course
	 * Only name, gender and nationality will be printed
	 */
	public void studentList_Course() {
		System.out.println("===========PRINT STUDENT LIST IN COURSE===========");
		System.out.println("Enter the Course Code: ");
		String courseId = sc.next();
		Course newCourse = Course.loadData(courseId);
		if (newCourse == null) {
			return;
		}
		System.out.println("Students who have registered this Course: \n"
				+ "(Name: Gender; Nationality)");
		for (Index i : newCourse.getIndex()) {
			for (String m : i.getStu()) {
				Student s = Student.loadData(m);
				System.out.println(s.getName() + ": "
						+ s.getGender() + "; "
						+ s.getNational());
			}
		}
		System.out.println("Press <Enter> to continue");
		sc.nextLine();
		sc.nextLine();
	}
	
	/**
	 * Print a list of students who have registered the same index
	 * Only name, gender and nationality will be printed
	 */
	public void studentList_Index() {
		System.out.println("===========PRINT STUDENT LIST IN INDEX===========");
		System.out.println("Enter the Index: ");
		String index_input = sc.next();
		Index i = Index.loadData(index_input);
		if (i == null) {
			return;
		}
		System.out.println("Students who have registered this Index: \n"
				+ "(Name: Gender; Nationality)");
		for (String m : i.getStu()) {
			Student s = Student.loadData(m);
			System.out.println(s.getName() + ": "
					+ s.getGender() + "; "
					+ s.getNational());
		}
		System.out.println("Press <Enter> to continue");
		sc.nextLine();
		sc.nextLine();
	}
	
}
