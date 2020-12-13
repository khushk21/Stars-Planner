package STARSapp;
import java.io.FileNotFoundException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Functions that a student can do in the application
 * @author Favian
 * @version 1.0
 * @since 2020-11-22
 */
public class StudentController {
	/**
	 * Send email notification to student
	 */
	private Notification Email;
	
	/**
	 *@param EmailNotif to instantiate EmailNotification for StudentController
	 */
	public StudentController(Notification EmailNotif)
	{
		Email = EmailNotif;
	}
	
	/**
	 * Add course for student
	 * @param student The student (current user) information
	 * @param courseIndex The course index that the student wants to add
	 * @return int to indicate specific error or success
	 */
	public int addCourse(Student student, String courseIndex){

		Index index = Index.loadData(courseIndex);
		String cid = index.getCourseId();
		Course course = Course.loadData(cid);
		
		for(Index i: course.getIndex()) {
			for(String j: i.getStu()) {
				if(student.getMatric().equals(j)) {
					return 1; //Student is in course
				}
			}
			for(String j: i.getWait()) {
				if(student.getMatric().equals(j)) {
					return 0; //Student is in waitlist
				}
			}
		}
		
		ArrayList<Lesson>lessonList = new ArrayList<Lesson>();
		ArrayList<Course>courseList = new ArrayList<Course>();
		courseList = Course.loadAllData();
		for(Course i:courseList) {//for registered
			for(Index j: i.getIndex()) {
				for(int k=0;k<j.getStu().size();k++) {
					if(j.getStu().get(k).equals(student.getMatric())){//if index equals student
							for(int m=0;m<j.getLectures().size();m++) {
								lessonList.add(j.getLectures().get(m));//get all the reg lectures
							}
							if(j.getTut() != null) {
								lessonList.add(j.getTut());//get all the reg tutorial
							}
							if(j.getLab() != null) {
								lessonList.add(j.getLab());//get all the reg lab
							}
						}
						
					}
				}
				
			}
		

		if(index.getLab() != null) {
			if(checkTimeClash(lessonList, index.getLab()) == 0) {
				return 10; //clash with new lab
			}
		}
		if(index.getTut() != null) {
			if(checkTimeClash(lessonList, index.getTut()) == 0) {
				return 11; //clash with new tut
			}
		}
		for(int n=0;n<index.getLectures().size();n++) {
			if(checkTimeClash(lessonList, index.getLectures().get(n)) == 0) {
				return 12; //clash with new lect
			}
		}

		
		
		int vac = checkVacancies(courseIndex); //get vacancy of selected course
		while(vac != -1) {
			if(vac < 1) { //if course has no vacancy
				for(Index i: course.getIndex()) {
					if(i.getIndexid().equals(courseIndex)) {
						i.addStu_waitlist(student.getMatric());
						System.out.println("Sending Email..");
						Email.sendMessage(student.getEmail(), "Added to waiting list of " + cid, student.getName() + " has been added to the waiting list of course " + cid);
						Course.saveData(course);
						return 2; //add student to waiting list
					}
				}
			}
			else { //if course have vacancy
				if(student.getAU()+course.getAU()<=Student.getMAX_AU()) {
					for(Index i: course.getIndex()) {
						if(i.getIndexid().equals(courseIndex)) {
							i.regStu(student.getMatric()); 
							System.out.println("Sending Email..");
							Email.sendMessage(student.getEmail(), "Success in adding " + cid, student.getName() + " has successfully added course " + cid + " of index " + courseIndex);
							student.addAU(course.getAU()); //add the amount of au to the student
							i.setVacancy(index.getVacancy()-1); //reduce vacancy by 1
							Course.saveData(course);
							return 3; //add student to index's student list
						}
					}
				}
				else {
					return 4; // You cannot add any more course as there is a maximum of 21-23 AU
				}
			}
		}
		Course.saveData(course);
		return -1;
	}

	/**
	 * Check for time clash between lessons
	 * @param lessonList Contains student current REGISTERED lessons
	 * @param newLesson The lesson that the student wants to add
	 * @return int to indicate specific error or success
	 */
	public int checkTimeClash(ArrayList<Lesson> lessonList, Lesson newLesson) {
		for (Lesson l : lessonList) {
			if (l.getday() == newLesson.getday()) {
				if (l.getst().compareTo(newLesson.getst())*l.getet().compareTo(newLesson.getst())<0
						|| l.getst().compareTo(newLesson.getst())==0 
						|| l.getst().compareTo(newLesson.getet())*l.getet().compareTo(newLesson.getet())<0
						|| l.getet().compareTo(newLesson.getet())==0) {
					return 0;
				}
			}
		}
		return 1;
	}
	
	/**
	 * Drop course for student
	 * @param student The student (current user) information
	 * @param courseIndex The course index that the student wants to drop
	 * @return int to indicate specific error or success
	 */
	public int dropCourse(Student student, String courseIndex){
		
		Index index = Index.loadData(courseIndex);
		String cid = index.getCourseId();
		Course course = Course.loadData(cid);
		
		for(Index i: course.getIndex()) {
			if(i.getIndexid().contains(courseIndex)) {
				for(int j=0;j<i.getWait().size();j++) {
					if(student.getMatric().contains(i.getWait().get(j))) {
						i.getWait().remove(j);
						Course.saveData(course);
						return 1;//remove student from waitlist
					}
				}
			}
		}
		
		for(Index i: course.getIndex()) {
			if(i.getIndexid().equals(courseIndex)) {
			for(String j: i.getStu()) {
				if(student.getMatric().equals(j)) {
					i.removeStu(j);
					student.removeAU(course.getAU()); //remove amount of AU
					System.out.println("Sending Email..");
					Email.sendMessage(student.getEmail(), "Success in dropping " + cid, student.getName() + " has successfully dropped course " + cid + " of index " + courseIndex);
					i.setVacancy(i.getVacancy() + 1); //increase vacancy by 1
					
					if(!i.getWait().isEmpty()) {//if waiting list not empty
						for (int p = 0; p < i.getWait().size(); p++) {
							String matric = i.getWait().get(p);
							Student stu = Student.loadData(matric);
							if(stu.getAU()+course.getAU()<=Student.getMAX_AU()) {
								i.regStu(matric);
								i.popStu_waitlist();
								i.setVacancy(i.getVacancy()-1);
								System.out.println("Sending Email..");
								Email.sendMessage(stu.getEmail(), "Success in adding " + course.getCourseId(), 
										stu.getName() + " has successfully added course " + course.getCourseId() 
										+ " of index " + i.getIndexid());
								break; //register the first available student in the waitlist
							}
						}
					}
					
					Course.saveData(course);
					return 1; //remove student from index
				}
			}
		}
		}
		return -1; //student has not registered this index
	}
	
	
	/**
	 * Check/print courses registered for student
	 * @param student The student (current user) information
	 * @return int to indicate specific error or success
	 */
	public int printCourse(Student student) {
		ArrayList<Course>courseList = new ArrayList<Course>();
		boolean check = false;
		
		courseList = Course.loadAllData();
		System.out.println("You are enrolled in the following courses: ");
		System.out.println("Course\t\tAU\t\tIndex\t\tStatus");
		for(Course i:courseList) {//for registered
			for(Index j: i.getIndex()) {
				for(int k=0;k<j.getStu().size();k++) {
					if(j.getStu().get(k).equals(student.getMatric())){//if index contains student
						System.out.println(j.getCourseId() + "\t\t" + i.getAU() + "\t\t" + j.getIndexid() + "\t\t" + "REGISTERED");
						check = true;
						break;
					}
				}
				
			}
		}
		for(Course j:courseList) {//for waitlist
			for(Index k: j.getIndex()) {
				if(!k.getWait().isEmpty()) { //if waiting list is not empty
					for(int m=0;m<k.getWait().size();m++) {
						if(k.getWait().get(m).equals(student.getMatric())) {//if waitlist contains student
							System.out.println(k.getCourseId() + "\t\t" + j.getAU() + "\t\t" + k.getIndexid() + "\t\t" + "WAITLIST");
							check = true;
							break;
						}
					}
				}
			}
		}
		if (check == true) {
			return 1; //student is registered in a course/waitlist
		}
		else {
			return -1; //student is not registered in a course/waitlist
		}
	}
	
	/**
	 * Check vacancies in a course
	 * @param courseIndex The course index that the student wants to drop
	 * @return vacancies in a course
	 */
	public int checkVacancies(String courseIndex) {
		Index index = Index.loadData(courseIndex);
		
		while (index == null) {
			return -1;
		}
		
		return index.getVacancy(); //return vacancy of selected course and index
	}
	
	/**
	 * Change index number of REGISTERED course for student
	 * @param student The student (current user) information
	 * @param oldIndex The course index that the student wants to change
	 * @param newIndex The course index that the student wants to change to
	 * @return int to indicate specific error or success
	 * @throws ClassNotFoundException Throws if the class is not found
	 * @throws FileNotFoundException Throws if the file is not found
	 */
	public int changeIndex(Student student, String oldIndex, String newIndex) throws ClassNotFoundException, FileNotFoundException{
		Index indexold = Index.loadData(oldIndex);
		String cidold = indexold.getCourseId();
		Course courseold = Course.loadData(cidold);
		Index indexnew = Index.loadData(newIndex);
		String cidnew = indexold.getCourseId();
		Course coursenew = Course.loadData(cidnew);

		boolean check = false;
		
		if(cidold != cidnew) {
			return 1;//oldIndex course != newIndex course
		}
		
		if(indexnew.getVacancy() == 0) {
			return 2;//newIndex NO MORE VACANCY
		}
		
		for(String i : indexold.getStu()) {
			if(student.getMatric().equals(i)) {//Check if student is in oldIndex
				check = true;
			}
		}
		
		if(!check) {
			return 3;//student IS NOT IN oldIndex
		}
		
		ArrayList<Lesson>lessonList = new ArrayList<Lesson>();
		ArrayList<Course>courseList = new ArrayList<Course>();
		courseList = Course.loadAllData();
		for(Course i:courseList) {//for registered
			for(Index j: i.getIndex()) {
				if(!j.getIndexid().equals(indexold.getIndexid())) {
				for(int k=0;k<j.getStu().size();k++) {
					if(j.getStu().get(k).equals(student.getMatric())){//if index contains student
							for(int m=0;m<j.getLectures().size();m++) {
								lessonList.add(j.getLectures().get(m));//get all the reg lectures
							}
							if(j.getTut() != null) {
								lessonList.add(j.getTut());//get all the reg tutorial
							}
							if(j.getLab() != null) {
								lessonList.add(j.getLab());//get all the reg lab
							}
						}
						
					}
				}
				}
				
			}
		
		if(indexnew.getLab() != null) {
			if(checkTimeClash(lessonList, indexnew.getLab()) == 0) {
				return 10; //clash with new lab
			}
		}
		if(indexnew.getTut() != null) {
			if(checkTimeClash(lessonList, indexnew.getTut()) == 0) {
				return 11; //clash with new tut
			}
		}
		
		for(Index i: courseold.getIndex()) {
			if(i.getIndexid().equals(indexold.getIndexid())) {
				for(Index j: coursenew.getIndex()) {
					if(j.getIndexid().equals(indexnew.getIndexid())) {	
						dropCourse(student, i.getIndexid());
						addCourse(student, j.getIndexid());
						System.out.println("Sending Email..");
						Email.sendMessage(student.getEmail(), "Success in changing index of " + cidold, student.getName() + " has successfully changed course " + cidold + " index " + oldIndex + " to " + newIndex);
						return 4; //successfully changed index
					}
				}
			}
		}
		return -1;
	}
	
	
	/**
	 * Swap index number with another peer
	 * @param student1 The student (current user) information
	 * @param student1Index The course index that the student wants to change
	 * @param student2 The peer information that the student wants to swap with
	 * @param student2Index The course index that the student wants to change to
	 * @param student2user The peer username
	 * @param student2pw The peer password
	 * @return int to indicate specific error or success
	 */
	public int swapIndex(Student student1, String student1Index, Student student2, String student2Index, String student2user, String student2pw) {
		
		Index index1 = Index.loadData(student1Index);
		String cid1 = index1.getCourseId();
		
		Index index2 = Index.loadData(student2Index);
		String cid2 = index2.getCourseId();
		Course course = Course.loadData(cid2);
		
		
		boolean check1 = false;
		boolean check2 = false;

		
		if(!(cid1.equals(cid2))) {
			return 0;//student1 course != student2 course
		}
		
		for(String i : index1.getStu()) {
			if(student1.getMatric().equals(i)) {//Check if student1 is in index1
				check1 = true;
			}
		}
		
		if(!check1) {
			return 1;//student1 not in index1
		}
		
		for(String i : index2.getStu()) {
			if(student2.getMatric().equals(i)) {//Check if student2 is in index2
				check2 = true;
			}
		}
		if(!check2) {
			return 2;//student2 not in index2
		}
		

		if(student1Index.equals(student2Index))
		{
			return 3; //check if the index of student1 and student2 is the same
		}

		ArrayList<Lesson>lessonList1 = new ArrayList<Lesson>();
		ArrayList<Lesson>lessonList2 = new ArrayList<Lesson>();
		ArrayList<Course>courseList = new ArrayList<Course>();
		courseList = Course.loadAllData();
		for(Course i:courseList) {//for student 1 registered
			for(Index j: i.getIndex()) {
				if(!j.getIndexid().equals(index1.getIndexid())) {
				for(int k=0;k<j.getStu().size();k++) {
					if(j.getStu().get(k).equals(student1.getMatric())){//if index contains student
							for(int m=0;m<j.getLectures().size();m++) {
								lessonList1.add(j.getLectures().get(m));//get all the reg lectures
							}
							if(j.getTut() != null) {
								lessonList1.add(j.getTut());//get all the reg tutorial
							}
							if(j.getLab() != null) {
								lessonList1.add(j.getLab());//get all the reg lab
							}
						}
						
					}
			}
				}
				
			}
		

		if(index2.getLab() != null) {
			if(checkTimeClash(lessonList1, index2.getLab()) == 0) {
				return 7; //student1 reg clash with new lab
			}
		}
		if(index2.getTut() != null) {
			if(checkTimeClash(lessonList1, index2.getTut()) == 0) {
				return 8; //student1 reg clash with new tut
			}
		}
		
		for(Course i:courseList) {//for student 2 registered
			for(Index j: i.getIndex()) {
				if(!j.getIndexid().equals(index2.getIndexid())) {
				for(int k=0;k<j.getStu().size();k++) {
					if(j.getStu().get(k).equals(student2.getMatric())){//if index contains student
							for(int m=0;m<j.getLectures().size();m++) {
								lessonList2.add(j.getLectures().get(m));//get all the reg lectures
							}
							if(j.getTut() != null) {
								lessonList2.add(j.getTut());//get all the reg tutorial
							}
							if(j.getLab() != null) {
								lessonList2.add(j.getLab());//get all the reg lab
							}
						}
						
					}
				}
				}
				
			}
		
		if(index1.getLab() != null) {
			if(checkTimeClash(lessonList2, index1.getLab()) == 0) {
				return 9; //student2 reg clash with new lab
			}
		}
		if(index1.getTut() != null) {
			if(checkTimeClash(lessonList2, index1.getTut()) == 0) {
				return 10; //student2 reg clash with new tut
			}
		}
		
		int student1in = 0;
		int student2in = 0;
		for(Index i: course.getIndex()) {
			if(i.getIndexid().equals(student1Index)) {
				for(int m=0;m<i.getStu().size();m++) {
					if(i.getStu().get(m).equals(student1.getMatric())) {
						student1in = m;
						break;
					}
				}
				for(Index j: course.getIndex()) {
					if(j.getIndexid().equals(student2Index)) {
						for(int n=0;n<j.getStu().size();n++) {
							if(j.getStu().get(n).equals(student2.getMatric())) {
								student2in = n;
								break;
							}
						}
						i.getStu().remove(student1in); //remove student1 from student1Index
						i.regStu(student2.getMatric()); //add student2 to student1Index
						j.getStu().remove(student2in); //remove student2 from student2Index
						j.regStu(student1.getMatric()); //add student1 to student2Index
						Course.saveData(course);
						System.out.println("Sending 2 Emails..");
						Email.sendMessage(student1.getEmail(), "Success in changing index of " + cid1, 
								student1.getName() + " has successfully changed course " + cid1 + " index " + student1Index + " to " + student2Index);
						Email.sendMessage(student2.getEmail(), "Success in changing index of " + cid1, 
								student2.getName() + " has successfully changed course " + cid1 + " index " + student2Index + " to " + student1Index);
						return 4; //successfully swapped
					}
				}
			}
		}

		return -1;
	}
	
}
